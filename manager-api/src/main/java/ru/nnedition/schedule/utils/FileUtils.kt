@file:Suppress("NAME_SHADOWING")

package ru.nnedition.schedule.utils

import nn.edition.yalogger.Level
import nn.edition.yalogger.LoggerFactory
import java.io.*
import java.nio.channels.FileChannel

object FileUtils {
    val logger = LoggerFactory.getLogger(this.javaClass.name)

    fun saveResource(resourcePath: String, replace: Boolean) {
        require(resourcePath != "") { "ResourcePath cannot be null or empty" }

        val resourcePath = resourcePath.replace('\\', '/')
        val input = getResource(resourcePath) ?: run {
            throw IllegalArgumentException("The embedded resource '$resourcePath' cannot be found")
        }

        val outFile = File(resourcePath)
        val lastIndex = resourcePath.lastIndexOf('/')
        val outDir = File(resourcePath.substring(0, if (lastIndex >= 0) lastIndex else 0))

        if (!outDir.exists())
            outDir.mkdirs()

        try {
            if (!outFile.exists() || replace) {
                val out = FileOutputStream(outFile)
                val buf = ByteArray(1024)
                var len: Int
                while ((input.read(buf).also { len = it }) > 0) {
                    out.write(buf, 0, len)
                }
                out.close()
                input.close()
            } else {
                logger.log(Level.WARN, "Could not save ${outFile.name} to $outFile because ${outFile.name} already exists.")
            }
        } catch (ex: IOException) {
            logger.error("Could not save ${outFile.name} to $outFile", ex)
        }
    }

    fun getResource(filename: String): InputStream? {
        try {
            val url = this::class.java.classLoader.getResource(filename) ?: return null

            val connection = url.openConnection()
            connection.useCaches = false
            return connection.getInputStream()
        } catch (ex: IOException) {
            return null
        }
    }

    fun copy(inFile: File, outFile: File): Boolean {
        if (!inFile.exists()) {
            return false
        }

        var input: FileChannel? = null
        var out: FileChannel? = null

        try {
            input = FileInputStream(inFile).channel
            out = FileOutputStream(outFile).channel

            var pos: Long = 0
            val size = input.size()

            while (pos < size) {
                pos += input.transferTo(pos, (10 * 1024 * 1024).toLong(), out)
            }
        } catch (ioe: IOException) {
            return false
        } finally {
            try {
                input?.close()
                out?.close()
            } catch (ioe: IOException) {
                return false
            }
        }

        return true
    }
}