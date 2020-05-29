package nl.sajansen.ipcamera

import handles.QueItemTransferHandler
import nl.sajansen.ipcamera.cameras.Camera
import java.awt.datatransfer.Transferable
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JList

class PresetQueItemTransferHandler(private val plugin: IpCameraPlugin, private val cameraSelect: JComboBox<Camera>) : QueItemTransferHandler() {

    override fun createTransferable(component: JComponent): Transferable {
        val list = component as JList<*>
        val preset = list.selectedValue as CameraPreset

        val camera = cameraSelect.selectedItem as Camera
        queItem = camera.getPresetQueItem(plugin, preset = preset)

        return super.createTransferable(component)
    }
}