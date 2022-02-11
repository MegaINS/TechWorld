package ru.megains.techworld.client.renderer.texture

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL30.glGenerateMipmap
import org.lwjgl.stb.STBImage.{stbi_failure_reason, stbi_image_free, stbi_info_from_memory, stbi_load_from_memory}
import org.lwjgl.stb.STBImageResize._
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.{memAlloc, memFree}
import ru.megains.techworld.client.File

import java.nio.ByteBuffer


class TextureAtlas(name:String) extends TTexture {

    var startX: Int = 0
    var startY: Int = 0
    var image: ByteBuffer = _

    override def loadTexture(): Boolean = {
        try {
            val imageBuffer = File.ioResourceToByteBuffer(name, 8 * 1024)
            val stack = stackPush
            try {
                val w = stack.mallocInt(1)
                val h = stack.mallocInt(1)
                val comp = stack.mallocInt(1)
                // Use info to read image metadata without decoding the entire image.
                // We don't need this for this demo, just testing the API.
                if (!stbi_info_from_memory(imageBuffer, w, h, comp)) {
                    throw new RuntimeException("Failed to read image information: " + stbi_failure_reason)
                }
                System.out.println("OK with reason: " + stbi_failure_reason)
                System.out.println("Image imagePath: " + name)
                System.out.println("Image width: " + w.get(0))
                System.out.println("Image height: " + h.get(0))
                System.out.println("Image components: " + comp.get(0))
                // System.out.println("Image HDR: " + stbi_is_hdr_from_memory(imageBuffer))
                // Decode the image
                image = stbi_load_from_memory(imageBuffer, w, h, comp, 0)
                if (image == null) throw new RuntimeException("Failed to load image: " + stbi_failure_reason)
                width = w.get(0)
                height = h.get(0)
                components = comp.get(0)

            } finally if (stack != null) stack.close()
        } catch {
            case e: Exception =>
                println(e.fillInStackTrace())
        }

        true

    }

    def isMissingTexture: Boolean = image == null



    def updateTexture(widthAll: Float, heightAll: Float): Unit = {
        minU = startX / widthAll
        maxU = (startX + width) / widthAll
        minV = startY / heightAll
        maxV = (startY + height) / heightAll
        averageU = (minU + maxU) / 2
        averageV = (minV + maxV) / 2

        var format = 0
        if ( components == 3) {
            if ((width & 3) != 0) glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (width & 1))
            format = GL_RGB
        }
        else {
            //premultiplyAlpha()
            glEnable(GL_BLEND)
            glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA)
            format = GL_RGBA
        }

        glTexSubImage2D(GL_TEXTURE_2D, 0, startX, startY, width, height, format, GL_UNSIGNED_BYTE, image)
        var input_pixels = image
        var input_w = width
        var input_h = height
        var mipmapLevel = 0
        while (1 < input_w || 1 < input_h) {
            val output_w = Math.max(1, input_w >> 1)
            val output_h = Math.max(1, input_h >> 1)
            val output_pixels = memAlloc(output_w * output_h * components)
            stbir_resize_uint8_generic(input_pixels, input_w, input_h, input_w * components, output_pixels, output_w, output_h, output_w * components, components, if (components == 4) 3
            else STBIR_ALPHA_CHANNEL_NONE, STBIR_FLAG_ALPHA_PREMULTIPLIED, STBIR_EDGE_CLAMP, STBIR_FILTER_MITCHELL, STBIR_COLORSPACE_SRGB)
            if (mipmapLevel == 0) stbi_image_free(image)
            else memFree(input_pixels)
            mipmapLevel += 1
            glTexSubImage2D(GL_TEXTURE_2D, mipmapLevel, startX, startY, width, height, format, GL_UNSIGNED_BYTE, output_pixels)

            input_pixels = output_pixels
            input_w = output_w
            input_h = output_h
        }
        if (mipmapLevel == 0) stbi_image_free(image)
        else memFree(input_pixels)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
//        glGenerateMipmap(GL_TEXTURE_2D)
    }


}
