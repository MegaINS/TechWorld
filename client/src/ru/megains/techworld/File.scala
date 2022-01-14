package ru.megains.techworld

import org.lwjgl.BufferUtils
import org.lwjgl.BufferUtils.createByteBuffer
import org.lwjgl.system.MemoryUtil.memUTF8

import java.awt.image.BufferedImage
import java.io.File
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.file.{Files, Paths}
import javax.imageio.ImageIO

object File {



    var gamePath: String = System.getProperty("user.dir").replaceAll("\\\\", "/") + "/client/resources/"

    def read(name: String): BufferedImage = {
        ImageIO.read(new File(gamePath+name))
    }

    def loadResource(filePath: String): String ={
        val source = ioResourceToByteBuffer(filePath, 4 * 1024)
        val t = memUTF8(source)//.replaceAll("\t", "    ") // Replace tabs

        t
    }
    private def resizeBuffer(buffer: ByteBuffer, newCapacity: Int) = {
        buffer.flip
        BufferUtils.createByteBuffer(newCapacity).put(buffer)
    }

    def exists(filePath: String): Boolean = Files.exists(Paths.get(gamePath+filePath))






    def ioResourceToByteBuffer(resource: String, bufferSize: Int): ByteBuffer = {
        var buffer: ByteBuffer = null
        val path = Paths.get(gamePath+resource)
        if (Files.isReadable(path)) try {
            val fc = Files.newByteChannel(path)
            try {
                buffer = BufferUtils.createByteBuffer(fc.size.toInt + 1)
                while (fc.read(buffer) != -1){}
                buffer.flip
            } catch {
                case e: Throwable => e.printStackTrace()
            }finally if (fc != null) fc.close()
        }catch {
            case e: Throwable => e.printStackTrace()
        }
        else {
            try {

                val source =  File.getClass.getResourceAsStream(gamePath+resource)
                val rbc = Channels.newChannel(source)
                try {
                    buffer = createByteBuffer(bufferSize)
                    var run = true
                    while (run) {
                        val bytes = rbc.read(buffer)
                        if (bytes == -1) run = false
                        if (buffer.remaining == 0) buffer = resizeBuffer(buffer, buffer.capacity * 2)

                    }
                    buffer.flip
                }catch {
                    case e: Throwable => e.printStackTrace()
                } finally {
                    if (source != null) source.close()
                    if (rbc != null) rbc.close()
                }
            }catch {
                case e: Throwable => println( s"ioResourceToByteBuffer $resource")
            }
        }

        buffer
    }
}
