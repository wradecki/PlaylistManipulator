package com.wradecki.view

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import org.jetbrains.skija.Image

val MyAppIcon = BitmapPainter(image = openResource("PLM_icon.ico"))

/**
 * From jetpack compose
 */
internal fun openResource(resourcePath: String): ImageBitmap {
    val classLoader = Thread.currentThread().contextClassLoader!!

    val stream = requireNotNull(classLoader.getResourceAsStream(resourcePath)) {
        "Resource $resourcePath not found"
    }

    return Image.makeFromEncoded(stream.readAllBytes()).asImageBitmap()
}