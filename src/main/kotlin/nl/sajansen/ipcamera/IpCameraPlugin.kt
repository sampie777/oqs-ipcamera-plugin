package nl.sajansen.ipcamera

import nl.sajansen.ipcamera.cameras.Camera
import nl.sajansen.ipcamera.cameras.DahuaCamera
import nl.sajansen.ipcamera.queItems.IpCameraActionQueItem
import objects.que.JsonQue
import objects.que.QueItem
import plugins.common.QueItemBasePlugin
import java.awt.Color
import java.net.URL
import java.util.logging.Logger
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.swing.JComponent

class IpCameraPlugin : QueItemBasePlugin {
    private val logger = Logger.getLogger(IpCameraPlugin::class.java.name)

    override val name: String = "IpCameraPlugin"
    override val description: String = "Plugin to control your IP camera"
    override val version: String = PluginInfo.version
    override val icon: Icon? = createImageIcon("/nl/sajansen/ipcamera/icon-14.png")

    override val tabName: String = "IP Camera"

    internal val quickAccessColor = Color(229, 255, 247)

    val cameras = listOf(DahuaCamera() as Camera)

    override fun sourcePanel(): JComponent {
        return SourcePanel(this)
    }

    override fun configStringToQueItem(value: String): QueItem {
        throw NotImplementedError("This method is deprecated")
    }

    override fun jsonToQueItem(jsonQueItem: JsonQue.QueItem): QueItem {
        return when (jsonQueItem.className) {
            IpCameraActionQueItem::class.java.simpleName -> IpCameraActionQueItem.fromJson(this, jsonQueItem)
            else -> throw IllegalArgumentException("Invalid $name queue item: ${jsonQueItem.className}")
        }
    }

    private fun createImageIcon(path: String): ImageIcon? {
        val imgURL = IpCameraPlugin::class.java.getResource(path)
        if (imgURL != null) {
            return ImageIcon(imgURL)
        }

        logger.severe("Couldn't find imageIcon: $path")
        return null
    }
}