package ru.megains.techworld.renderer.font

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11._
import org.lwjgl.stb.STBTruetype._
import org.lwjgl.stb.{STBTTFontinfo, STBTTPackContext, STBTTPackedchar}
import org.lwjgl.system.MemoryUtil.NULL
import ru.megains.techworld.File
import ru.megains.techworld.renderer.texture.TTexture

import java.io.IOException
import java.nio.ByteBuffer


class Font(name: String) extends TTexture() {


    val BITMAP_W = 2024
    val BITMAP_H = 2024
    var cdata: STBTTPackedchar.Buffer = _
    private val scale = Array(24.0f, 14.0f, 36f)
    var info:STBTTFontinfo = _
    var ttf:ByteBuffer =_
//    def this(name: String)= {
//        this(new TextureData())
//        height = 24
//        val texID = getGlTextureId
//        try {
//            val ttf = FileM.ioResourceToByteBuffer(s"fonts/$name.ttf", 160 * 1024)
//            val bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H)
//            STBTruetype.stbtt_BakeFontBitmap(ttf, 24, bitmap, BITMAP_W, BITMAP_H, 0, cdata)
//
//            val img = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H*4)
//
//            for (i <- 0 until BITMAP_W * BITMAP_H) {
//                val a = bitmap.get(i)
//                img.put(i*4+0,255.toByte)
//                img.put(i*4+1,255.toByte)
//                img.put(i*4+2,255.toByte)
//                img.put(i*4+3, a)
//            }
//
//            glBindTexture(GL_TEXTURE_2D, texID)
//            glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
//            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, BITMAP_W, BITMAP_H, 0, GL_RGBA, GL_UNSIGNED_BYTE, img)
//            // glGenerateMipmap(GL_TEXTURE_2D)
//        } catch {
//            case e: IOException =>
//                throw new RuntimeException(e)
//        }
//
//    }


    def getWidth(text: String): Float = {
        var length = 0f
        for (i <- 0 until text.length) {
            val advancewidth = BufferUtils.createIntBuffer(1)
            val leftsidebearing = BufferUtils.createIntBuffer(1)
            stbtt_GetCodepointHMetrics(info, text.charAt(i).toInt, advancewidth, leftsidebearing)
            length += advancewidth.get(0)
        }
        length* stbtt_ScaleForPixelHeight(info, 24)
    }

    override def loadTexture(): Boolean = {
        val texID = getGlTextureId
        val pc = STBTTPackContext.malloc

        try {
            cdata = STBTTPackedchar.malloc(9 * 1500)
            ttf = File.ioResourceToByteBuffer(s"fonts/$name.ttf", 512 * 1024)

            info = STBTTFontinfo.create()
            stbtt_InitFont(info, ttf)

            val bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H)
            stbtt_PackBegin(pc, bitmap, BITMAP_W, BITMAP_H, 0, 1, NULL)
            for (i <- 0 until 3) {
                var p = (i * 3 + 0) * 1500
                cdata.limit(p + 1500)
                cdata.position(p)
                stbtt_PackSetOversampling(pc, 1, 1)
                stbtt_PackFontRange(pc, ttf, 0, scale(i), 0, cdata)
                p = (i * 3 + 1) * 1500
                cdata.limit(p + 1500)
                cdata.position(p)
                stbtt_PackSetOversampling(pc, 2, 2)
                stbtt_PackFontRange(pc, ttf, 0, scale(i), 0, cdata)
                p = (i * 3 + 2) * 1500
                cdata.limit(p + 1500)
                cdata.position(p)
                stbtt_PackSetOversampling(pc, 3, 1)
                stbtt_PackFontRange(pc, ttf, 0, scale(i), 0, cdata)
            }
            cdata.clear
            stbtt_PackEnd(pc)

            val img = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H * 4)


            for (i <- 0 until BITMAP_W * BITMAP_H) {
                val a = bitmap.get(i)


                img.put(i * 4 + 0, 255.toByte)
                img.put(i * 4 + 1, 255.toByte)
                img.put(i * 4 + 2, 255.toByte)
                img.put(i * 4 + 3, a)
            }


            glBindTexture(GL_TEXTURE_2D, texID)
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, BITMAP_W, BITMAP_H, 0, GL_RGBA, GL_UNSIGNED_BYTE, img)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        } catch {
            case e: IOException =>
                throw new RuntimeException(e)
        } finally if (pc != null) pc.close()

        true
    }
}
