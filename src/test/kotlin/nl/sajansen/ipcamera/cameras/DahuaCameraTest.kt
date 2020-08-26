package nl.sajansen.ipcamera.cameras

import nl.sajansen.ipcamera.CameraPreset
import nl.sajansen.ipcamera.IpCameraPlugin
import kotlin.test.Test
import kotlin.test.assertEquals

class DahuaCameraTest {

    @Test
    fun getPresetsFromResponse() {
        val response = """
presets[0].Index=1
presets[0].Name=Zaal
presets[1].Index=2
presets[1].Name=Podium
presets[2].Index=3
    presets[2].Name=Preekstoel
    presets[3].Index=4
    presets[3].Name=Dominee
    presets[4].Index=5
    presets[4].Name=5
    presets[5].Index=6
    presets[5].Name=Portret
presets[6].Index=7
presets[6].Name=Uitgezoomd
        """

        val presets = DahuaCamera().presetResponseToPresets(response)

        assertEquals(7, presets.size)
        assertEquals("1", presets[0].id)
        assertEquals("1: Zaal", presets[0].title)
        assertEquals("7", presets[6].id)
        assertEquals("7: Uitgezoomd", presets[6].title)
    }

    @Test
    fun getPresetsFromEmptyResponse() {
        val response = ""

        val presets = DahuaCamera().presetResponseToPresets(response)

        assertEquals(0, presets.size)
    }

    @Test
    fun testGetPresetQueItem() {
        val item = DahuaCamera().getPresetQueItem(IpCameraPlugin(), CameraPreset("3", "Preset 3"))

        assertEquals("[Dahua] Preset 3", item.name)
        assertEquals("/cgi-bin/ptz.cgi?action=start&channel=0&code=GotoPreset&arg1=0&arg2=3&arg3=0", item.actionUrl)
    }
}