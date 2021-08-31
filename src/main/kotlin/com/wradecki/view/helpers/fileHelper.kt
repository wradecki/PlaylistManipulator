package com.wradecki.view.helpers

import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState
import com.wradecki.exporters.ExportFail
import com.wradecki.exporters.ExportSuccess
import com.wradecki.exporters.SltvExporter
import com.wradecki.model.SingleList
import com.wradecki.parsers.ParseFail
import com.wradecki.parsers.ParseSuccess
import com.wradecki.parsers.SltvParser
import java.io.File

fun parseList(file: File?, lists: MutableList<SingleList>, trayState: TrayState) {
    val parser = SltvParser()
    when (val result = parser.parse(file!!.path)) {
        is ParseSuccess -> {
            lists += result.list
            trayState.sendNotification(Notification("Success", "List added successfully", Notification.Type.Info))
        }
        is ParseFail -> {
            trayState.sendNotification(Notification("Error", result.error, Notification.Type.Error))
        }
    }
}


fun saveLists(file: File?, lists: MutableList<SingleList>, trayState: TrayState) {
    val exporter = SltvExporter()
    when (val result = exporter.export(lists, file!!.path)) {
        is ExportSuccess -> {
            trayState.sendNotification(Notification("Success", "List exported successfully", Notification.Type.Info))
        }
        is ExportFail -> {
            trayState.sendNotification(Notification("Error", result.error, Notification.Type.Error))
        }
    }
}