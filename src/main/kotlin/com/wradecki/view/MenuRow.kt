package com.wradecki.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuScope
import androidx.compose.ui.window.TrayState
import com.wradecki.model.SingleList
import com.wradecki.view.helpers.parseList
import com.wradecki.view.helpers.saveLists
import java.awt.Component
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

private val m3uFilter = FileNameExtensionFilter("SLTV", "m3u")

@Composable
fun FrameWindowScope.MenuRow() {
    MenuBar {
        Menu("File", mnemonic = 'F') {
            ListMenuItem("Load list", ::parseList) { fc -> fc::showOpenDialog }
            ListMenuItem("Export list", ::saveLists) { fc -> fc::showSaveDialog }
        }
    }
}

@Composable
private fun MenuScope.ListMenuItem(name: String, runMethod: (File, MutableList<SingleList>, TrayState) -> Unit, dialogFunction: (JFileChooser) -> (Component?) -> Unit) {
    Item(name, onClick = {
        chooseFile(runMethod, dialogFunction)
    })
}

private val noComponent = null

private fun chooseFile(runMethod: (File, MutableList<SingleList>, TrayState) -> Unit, dialogFunction: (JFileChooser) -> (Component?) -> Unit) {
    var file: File?
    JFileChooser().apply {
        fileFilter = m3uFilter
        dialogFunction(this)(noComponent)
        file = selectedFile
    }

    if (file != noComponent) {
        runMethod(file!!, lists, trayState)
    }
}