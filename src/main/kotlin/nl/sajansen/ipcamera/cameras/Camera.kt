package nl.sajansen.ipcamera.cameras

import nl.sajansen.ipcamera.CameraPreset
import nl.sajansen.ipcamera.IpCameraPlugin
import nl.sajansen.ipcamera.queItems.IpCameraActionQueItem

interface Camera {
    val name: String
    val username: String
    val password: String
    val ipAddress: String
    val port: String
    fun host(): String = if (port.isEmpty()) ipAddress else "$ipAddress:$port"

    fun getPresets(): List<CameraPreset>

    fun getPresetQueItem(plugin: IpCameraPlugin, preset: CameraPreset): IpCameraActionQueItem
}