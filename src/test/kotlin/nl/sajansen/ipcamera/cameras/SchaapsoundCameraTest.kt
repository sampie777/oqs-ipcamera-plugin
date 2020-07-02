package nl.sajansen.ipcamera.cameras

import nl.sajansen.ipcamera.CameraPreset
import nl.sajansen.ipcamera.IpCameraPlugin
import kotlin.test.Test
import kotlin.test.assertEquals

class SchaapsoundCameraTest {

    @Test
    fun testGetPresetQueItem() {
        val item = SchaapsoundCamera().getPresetQueItem(IpCameraPlugin(), CameraPreset("3", "Preset 3"))

        assertEquals("Preset 3", item.name)
        assertEquals("/cgi-bin/ptzctrl.cgi?ptzcmd&poscall&3", item.actionUrl)
    }
}