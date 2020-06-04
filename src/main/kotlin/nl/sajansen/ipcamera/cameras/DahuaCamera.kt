package nl.sajansen.ipcamera.cameras

import nl.sajansen.ipcamera.CameraPreset
import nl.sajansen.ipcamera.IpCameraPlugin
import nl.sajansen.ipcamera.queItems.IpCameraActionQueItem
import objects.notifications.Notifications
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.logging.Logger
import kotlin.NoSuchElementException
import kotlin.collections.ArrayList

class DahuaCamera : Camera {
    private val logger = Logger.getLogger(DahuaCamera::class.java.name)

    override val name: String = "Dahua"
    override val username: String = "admin"
    override val password: String = "admin"
    override val ipAddress: String = "192.168.88.100"
    override val port: String = ""
    private val connectionTimeout = 5000

    override fun toString() = name

    override fun getPresets(): List<CameraPreset> {
        val presetsEndpoint = "/cgi-bin/ptz.cgi?action=getPresets&channel=0"
        logger.info("Trying to get presets from: $presetsEndpoint")

        val response = try {
            apiGet(presetsEndpoint)
        } catch (e: Exception) {
            logger.warning("Failed to retrieve presets from camera")
            e.printStackTrace()
            Notifications.add("Failed to retrieve presets from camera: ${e.localizedMessage}", "IP Camera")
            return emptyList()
        }

        return presetResponseToPresets(response)
    }

    override fun getPresetQueItem(plugin: IpCameraPlugin, preset: CameraPreset): IpCameraActionQueItem {
        return IpCameraActionQueItem(
            plugin,
            name = preset.title,
            camera = this,
            actionUrl = "/cgi-bin/ptz.cgi?action=start&channel=0&code=GotoPreset&arg1=0&arg2=${preset.id}&arg3=0"
        )
    }

    fun presetResponseToPresets(response: String): List<CameraPreset> {
        logger.fine("Creating presets from: $response")

        val paddedResponse = response.replace("\r\n", "\n") + "\n"
        val presets = ArrayList<CameraPreset>()
        val indexMatcher = "(?i)presets\\[{index}]\\.Index=(.*?)\n"
        val nameMatcher = "(?i)presets\\[{index}]\\.Name=(.*?)\n"

        val maxIndex: Int = try {
            indexMatcher
                .replace("{index}", "(\\d+)").toRegex()
                .findAll(paddedResponse)
                .last()
                .groupValues[1].toInt()
        } catch (e: NoSuchElementException) {
            logger.info("No presets found in response")
            return presets
        }

        logger.info("${maxIndex + 1} presets found")

        for (i in 0 until (maxIndex + 1)) {
            val index = indexMatcher
                .replace("{index}", i.toString()).toRegex()
                .find(paddedResponse)
                ?.groupValues?.get(1) ?: continue
            val name = nameMatcher
                .replace("{index}", i.toString()).toRegex()
                .find(paddedResponse)
                ?.groupValues?.get(1) ?: continue

            val preset = CameraPreset(index, "$index: $name")
            logger.info("Adding preset: $preset")
            presets.add(preset)
        }

        return presets
    }

    fun apiGet(endpoint: String): String {
        val apiUrl = "http://${host()}$endpoint"
        logger.info("Trying to get API response from: $apiUrl")

        val urlObject = URL(apiUrl)

        val authentication = Base64.getEncoder().encodeToString("$username:$password".toByteArray())

        val connection = urlObject.openConnection() as HttpURLConnection
        connection.connectTimeout = connectionTimeout
        connection.readTimeout = connectionTimeout
        connection.setRequestProperty("Authorization", "Basic $authentication")
        connection.requestMethod = "GET"

        return try {
            connection.inputStream.use {
                it.readBytes().toString(Charsets.UTF_8)
            }
        } catch (e: Exception) {
            logger.warning("Failed to connect with camera API")
            throw e
        }
    }
}