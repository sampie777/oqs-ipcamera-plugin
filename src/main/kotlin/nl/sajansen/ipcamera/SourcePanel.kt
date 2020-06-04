package nl.sajansen.ipcamera

import nl.sajansen.ipcamera.cameras.Camera
import gui.utils.DefaultSourcesList
import objects.notifications.Notifications
import themes.Theme
import java.awt.*
import java.util.logging.Logger
import javax.swing.*
import javax.swing.border.EmptyBorder

class SourcePanel(private val plugin: IpCameraPlugin) : JPanel() {
    private val logger = Logger.getLogger(SourcePanel::class.java.name)

    val cameraSelect = JComboBox<Camera>()
    val cameraPresetList = DefaultSourcesList<CameraPreset>()

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

        cameraPresetList.transferHandler = PresetQueItemTransferHandler(plugin, cameraSelect)

        val scrollPanelInnerPanel = JPanel(BorderLayout())
        scrollPanelInnerPanel.add(cameraPresetList, BorderLayout.PAGE_START)
        val scrollPanel = JScrollPane(scrollPanelInnerPanel)
        scrollPanel.border = BorderFactory.createMatteBorder(0, 1, 1, 1, Color(180, 180, 180))
        add(scrollPanel, BorderLayout.CENTER)
    }

    private fun loadCameraPresets() {
        val selectedCamera = cameraSelect.selectedItem as Camera
        logger.info("Loading camera presets for camera: ${selectedCamera.name}")
        try {
            Thread {
                cameraPresetList.setListData(selectedCamera.getPresets().toTypedArray())
            }.start()
        } catch (e: Exception) {
            logger.severe("Failed to start tread for getting camera presets")
            e.printStackTrace()
            Notifications.add("Could not get preset from camera: ${e.localizedMessage}", "IP Camera")
        }
    }
}