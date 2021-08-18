// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication(title = "Playlist manipulator") {
    Column {
        for (i in 1..3)
            Row {
                Text("Test fgfgkjhsdkfjglhsdkljfhgksjdhfgkljsh fkjshdf kjgshdf jkgshddkjf ghskdljfgh sdjfgh ksdlfhg kdsjhfg klsjdhfgkljhsdklfj hgsklfdjhglskd jgklsj dfhgklsh dkfghs dkufggsdufgisdyfgiosuynsdnfgksdfngkj sdhkj ghsdkjfhg ksjndfgsdjfh gkjsdhfg kljhsdfg kjsdfkjg hsdkfjg hkfdjhh $i")
            }
    }
}