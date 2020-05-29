package nl.sajansen.ipcamera.cameras

import nl.sajansen.ipcamera.CameraPreset
import nl.sajansen.ipcamera.IpCameraPlugin
import nl.sajansen.ipcamera.queItems.IpCameraActionQueItem

class CameraMock : Camera {
    override val name: String = "Dahua"
    override val username: String = "user"
    override val password: String = "1234"
    override val ipAddress: String = "localhost"
    override val port: String = "80"

    override fun getPresets(): List<CameraPreset> {
        TODO("Not yet implemented")
    }

    override fun getPresetQueItem(plugin: IpCameraPlugin, preset: CameraPreset): IpCameraActionQueItem {
        TODO("Not yet implemented")
    }
}