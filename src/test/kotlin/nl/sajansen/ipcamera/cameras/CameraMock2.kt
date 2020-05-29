package nl.sajansen.ipcamera.cameras

import nl.sajansen.ipcamera.CameraPreset
import nl.sajansen.ipcamera.IpCameraPlugin
import nl.sajansen.ipcamera.queItems.IpCameraActionQueItem

class CameraMock2 : Camera {
    override val name: String = "Dahua"
    override val username: String = "user"
    override val password: String = "1234"
    override val ipAddress: String = "123.456.789.0"
    override val port: String = ""

    override fun getPresets(): List<CameraPreset> {
        TODO("Not yet implemented")
    }

    override fun getPresetQueItem(plugin: IpCameraPlugin, preset: CameraPreset): IpCameraActionQueItem {
        TODO("Not yet implemented")
    }
}