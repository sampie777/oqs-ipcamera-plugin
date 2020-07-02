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

class SchaapsoundCamera : Camera {
    private val logger = Logger.getLogger(SchaapsoundCamera::class.java.name)

    override val name: String = "Schaapsound"
    override val username: String = "admin"
    override val password: String = "admin"
    override val ipAddress: String = "192.168.88.26"
    override val port: String = ""
    private val connectionTimeout = 5000

    override fun toString() = name

    override fun getPresets(): List<CameraPreset> {
        return (0 .. 254).map { CameraPreset(it.toString(), "Preset $it") }
    }

    override fun getPresetQueItem(plugin: IpCameraPlugin, preset: CameraPreset): IpCameraActionQueItem {
        return IpCameraActionQueItem(
            plugin,
            name = preset.title,
            camera = this,
            actionUrl = "/cgi-bin/ptzctrl.cgi?ptzcmd&poscall&${preset.id}"
        )
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