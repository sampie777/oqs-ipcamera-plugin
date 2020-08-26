package nl.sajansen.ipcamera.queItems

import nl.sajansen.ipcamera.IpCameraPlugin
import nl.sajansen.ipcamera.cameras.CameraMock
import objects.que.JsonQueue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class IpCameraActionQueItemTest {

    @Test
    fun testToJson() {
        val plugin = IpCameraPlugin()
        val camera = plugin.cameras.first()
        val queItem = IpCameraActionQueItem(plugin, "preset 1", camera, "/actionurl")

        val jsonQueItem = queItem.toJson()

        assertEquals("preset 1", jsonQueItem.name)
        assertEquals(camera.name, jsonQueItem.data["camera"])
        assertEquals("/actionurl", jsonQueItem.data["actionUrl"])
    }

    @Test
    fun testFromJson() {
        val plugin = IpCameraPlugin()
        val camera = plugin.cameras.first()
        val jsonQueItem = JsonQueue.QueueItem(
                "IpCameraPlugin",
                "IpCameraActionQueItem",
                "preset 1",
                false,
                null,
                hashMapOf(
                        "camera" to camera.name,
                        "actionUrl" to "/actionurl"
                )
        )
        val queItem = IpCameraActionQueItem.fromJson(plugin, jsonQueItem)

        assertEquals("preset 1", queItem.name)
        assertEquals(camera, queItem.camera)
        assertEquals("/actionurl", queItem.actionUrl)
    }

    @Test
    fun testAsyncActivateWithMockCamera() {
        val plugin = IpCameraPlugin()
        val queItem = IpCameraActionQueItem(plugin, "preset 1", CameraMock(), "/actionurl")

        try {
            queItem.asyncActivate(queItem.camera)
            fail("Expected NotImplementedError to be thrown")
        } catch (e: NotImplementedError) {
        }
    }
}