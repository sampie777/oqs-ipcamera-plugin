package nl.sajansen.ipcamera

import nl.sajansen.ipcamera.cameras.Camera
import themes.Theme
import java.awt.*
import javax.swing.*
import javax.swing.border.CompoundBorder
import javax.swing.border.EmptyBorder

class SourcePanel(private val plugin: IpCameraPlugin) : JPanel() {

    val cameraSelect = JComboBox<Camera>()
    val cameraPresetList = JList<CameraPreset>()

    init {
        initGui()
        loadCameraPresets()
    }

    private fun initGui() {
        layout = BorderLayout(10, 10)
        border = EmptyBorder(10, 10, 0, 10)

        cameraSelect.model = DefaultComboBoxModel(plugin.cameras.toTypedArray())
        cameraSelect.border = BorderFactory.createLineBorder(Theme.get.BORDER_COLOR)
        cameraSelect.addActionListener { loadCameraPresets() }
        add(cameraSelect, BorderLayout.PAGE_START)

        cameraPresetList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        cameraPresetList.dragEnabled = true
        cameraPresetList.transferHandler = PresetQueItemTransferHandler(plugin, cameraSelect)
        cameraPresetList.font = Font("Dialog", Font.PLAIN, 14)
        cameraPresetList.cursor = Cursor(Cursor.HAND_CURSOR)
        cameraPresetList.border = CompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 1, 1, Color(180, 180, 180)),
            EmptyBorder(10, 10, 0, 10)
        )

        val scrollPanelInnerPanel = JPanel(BorderLayout())
        scrollPanelInnerPanel.add(cameraPresetList, BorderLayout.PAGE_START)
        val scrollPanel = JScrollPane(scrollPanelInnerPanel)
        scrollPanel.border = null
        add(scrollPanel, BorderLayout.CENTER)
    }

    private fun loadCameraPresets() {
        val selectedCamera = cameraSelect.selectedItem as Camera
        cameraPresetList.setListData(selectedCamera.getPresets().toTypedArray())
    }
}