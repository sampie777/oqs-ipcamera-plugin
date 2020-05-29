package nl.sajansen.ipcamera.cameras

import kotlin.test.Test
import kotlin.test.assertEquals

class CameraTest {

    @Test
    fun testHostGivesCorrectValues() {
        assertEquals("localhost:80", CameraMock().host())
        assertEquals("123.456.789.0", CameraMock2().host())
    }
}