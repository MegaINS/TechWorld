package ru.megains.techworld.renderer.font

import org.lwjgl.stb.{STBTTAlignedQuad, STBTruetype}
import org.lwjgl.system.MemoryStack
import ru.megains.techworld.renderer.mesh.{Mesh, MeshMaker}
import ru.megains.techworld.renderer.texture.TextureManager

import java.awt.Color
import scala.collection.mutable


object FontRender {

    val ZPOS: Float = 0.0f
    val fontsMap: mutable.HashMap[String, Font] = new mutable.HashMap[String, Font]



    def createStringGui(text: String, color: Color, font: Font): Mesh= {
        val stack: MemoryStack = MemoryStack.stackPush
        val x = stack.floats(0.0f)
        val y = stack.floats(0.0f)
        val q = STBTTAlignedQuad.malloc

        val mm = MeshMaker.startMakeTriangles()
        mm.setTexture(font)
        mm.addColor(color)
        val size = 16
        font.cdata.position(2* 1500)
        for (i <- text.indices) {
            val c: Char = text.charAt(i)
            if (c == '\n') {
                y.put(0, y.get(0) + 24)
                x.put(0, 0.0f)
            } else {
                STBTruetype.stbtt_GetPackedQuad(font.cdata, font.BITMAP_W, font.BITMAP_H, c, x, y, q, true)
                mm.setCurrentIndex()
                mm.addVertexWithUV(q.x0, q.y1+size , ZPOS, q.s0, q.t1)
                mm.addVertexWithUV(q.x0, q.y0+size , ZPOS, q.s0, q.t0)
                mm.addVertexWithUV(q.x1, q.y0+size , ZPOS, q.s1, q.t0)
                mm.addVertexWithUV(q.x1, q.y1+size , ZPOS, q.s1, q.t1)
                mm.addIndex(1, 0, 3)
                mm.addIndex(2, 1, 3)
            }
        }
        if (stack != null) stack.close()
        mm.make()
    }







    def loadFont(name: String): Font = {
        if (fontsMap.contains(name)) fontsMap(name)
        else {
            val font = new Font(name)
            TextureManager.loadTexture(name, font)
            fontsMap += name -> font
            font
        }
    }

}
