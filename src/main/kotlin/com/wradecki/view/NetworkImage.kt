package com.wradecki.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import org.jetbrains.skija.Image
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

@Composable
fun NetworkImage(url: String) {
    if (url.isNotBlank()) {
        Column {
            Image(bitmap = loadNetworkImage(url), contentDescription = null, modifier = Modifier.width(150.dp))
        }
    }
}

private fun loadNetworkImage(url: String): ImageBitmap {
    val connection = initConnection(url)
    val redirect = isRedirect(connection)

    return if (redirect) loadNewNetworkImage(connection) else loadImage(connection)
}

private fun loadNewNetworkImage(connection: HttpURLConnection): ImageBitmap {
    return loadNetworkImage(getNewUrl(connection))
}

private fun getNewUrl(connection: HttpURLConnection) = connection.getHeaderField("Location")

private fun initConnection(link: String): HttpURLConnection {
    val url = URL(link)
    val connection = url.openConnection() as HttpURLConnection
    connection.connect()
    return connection
}

private fun loadImage(connection: HttpURLConnection): ImageBitmap {
    val inputStream = connection.inputStream
    val bufferedImage = ImageIO.read(inputStream)

    val stream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage, "png", stream)
    val byteArray = stream.toByteArray()

    return Image.makeFromEncoded(byteArray).asImageBitmap()
}

private fun isRedirect(connection: HttpURLConnection): Boolean {
    var redirect = false
    val status: Int = connection.responseCode
    if (status != HttpURLConnection.HTTP_OK) {
        if (status == HttpURLConnection.HTTP_MOVED_TEMP
            || status == HttpURLConnection.HTTP_MOVED_PERM
            || status == HttpURLConnection.HTTP_SEE_OTHER
        )
            redirect = true;
    }
    return redirect
}
