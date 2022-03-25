package ru.megains.techworld.client.renderer.texture

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL30.glGenerateMipmap
import org.lwjgl.stb.STBImage.{stbi_failure_reason, stbi_info_from_memory, stbi_load_from_memory}
import org.lwjgl.system.MemoryStack.stackPush
import ru.megains.techworld.client.File

private class Texture(name: String) extends TTexture() {


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
                val image = stbi_load_from_memory(imageBuffer, w, h, comp, 0)
                if (image == null) throw new RuntimeException("Failed to load image: " + stbi_failure_reason)
                width = w.get(0)
                height = h.get(0)
                components = comp.get(0)


                glBindTexture(GL_TEXTURE_2D, getGlTextureId)
                glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)


                //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
                //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
                //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR)
                //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
                //        glGenerateMipmap(GL_TEXTURE_2D)
                //
                //       // glGenerateMipmap(GL_TEXTURE_2D)
                //        //glGenerateMipmap(GL_TEXTURE_2D)

                // glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                // Когда минимизируем — берем две ближних мипмапы и лиейно смешиваем цвета
                // glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
                // И создаем сами мипмапы.
                // glGenerateMipmap(GL_TEXTURE_2D)
                var format = 0
                if (components == 3) {
                    if ((width & 3) != 0) glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (width & 1))
                    format = GL_RGB
                }
                else {

                    format = GL_RGBA
                }
                glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, image)

                //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
                //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
                //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_REPEAT)
                //        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
                glGenerateMipmap(GL_TEXTURE_2D)
            } finally if (stack != null) stack.close()
        } catch {
            case e: Exception =>
                println(e.fillInStackTrace())
        }

        true
    }




}
