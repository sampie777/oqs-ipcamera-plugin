package nl.sajansen.ipcamera

import java.util.*

object PluginInfo {
    private val properties = Properties()
    val version: String
    val author: String

    init {
        properties.load(IpCameraPlugin::class.java.getResourceAsStream("/nl/sajansen/ipcamera/plugin.properties"))
        version = properties.getProperty("version")
        author = properties.getProperty("author")
    }
}