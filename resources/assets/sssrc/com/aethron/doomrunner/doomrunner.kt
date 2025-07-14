package com.aethron.doomrunner

import android.content.Context
import com.aliucord.plugins.Plugin
import com.aliucord.Utils
import java.io.File

class DoomRunner : Plugin() {
    override fun start(ctx: Context) {
        Utils.thread {
            try {
                val doomJar = File(ctx.filesDir, "mochadoom.jar")

                if (!doomJar.exists()) {
                    Utils.showToast("Doom JAR not found")
                    return@thread
                }

                val pb = ProcessBuilder("java", "-jar", doomJar.absolutePath)
                pb.redirectErrorStream(true)
                val process = pb.start()
                val reader = process.inputStream.bufferedReader()
                reader.lines().forEach { println("[DOOM] $it") }

            } catch (e: Exception) {
                Utils.showToast("Error: ${e.message}")
            }
        }
    }

    override fun stop(ctx: Context) {
        patcher.unpatchAll()
    }
}
