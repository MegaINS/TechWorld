package ru.megains.techworld.client.renderer.mesh

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20.{glEnableVertexAttribArray, glVertexAttribPointer}
import org.lwjgl.opengl.GL30.{glBindVertexArray, glGenVertexArrays}
import ru.megains.techworld.client.renderer.texture.TTexture

import java.awt.Color
import scala.::
import scala.collection.mutable.ArrayBuffer


class MeshMaker(makeMode: Int) {


    private var posArray: ArrayBuffer[Float] = ArrayBuffer[Float]()
    private var colourArray: ArrayBuffer[Float] = ArrayBuffer[Float]()
    private var textCordsArray: ArrayBuffer[Float] = ArrayBuffer[Float]()
    private var normalsArray: ArrayBuffer[Float] = ArrayBuffer[Float]()
    private var indicesArray: ArrayBuffer[Int] = ArrayBuffer[Int]()

    private var colorR: Float = 1
    private var colorG: Float = 1
    private var colorB: Float = 1
    private var colorA: Float = 1
    private var textureU: Float = 0
    private var textureV: Float = 0
    private var normalX: Float = 0
    private var normalY: Float = 0
    private var normalZ: Float = 0
    private var currentIndex: Int = 0
    private var vertexCount: Int = 0
    private var isColors: Boolean = false
    private var isNormals: Boolean = false
    private var texture: TTexture = _


    def setTexture(textureIn: TTexture): Unit = {
        texture = textureIn
    }

    def setCurrentIndex(): Unit = {
        currentIndex = vertexCount
    }

    def addIndex(index: Int*):Unit= {
        index.foreach(indicesArray += _ + currentIndex)
    }

    def addIndex(index: Int):Unit= {
        indicesArray :+= index + currentIndex
    }

    def addNormals(xn: Float, yn: Float, zn: Float):Unit= {
        normalX = xn
        normalY = yn
        normalZ = zn
        isNormals = true
    }

    def addVertexWithUV(x: Float, y: Float, z: Float, u: Float, v: Float) :Unit={
        addTextureUV(u, v)
        addVertex(x, y, z)
    }

    def addVertex(x: Float, y: Float, z: Float): Unit =  {
        textCordsArray ++= List(textureU,textureV)
        colourArray ++= List(colorR, colorG, colorB, colorA)
        normalsArray ++= List(normalX, normalY, normalZ)
        posArray ++= List(x, y, z)
        vertexCount += 1
    }

    def addColor(r: Float, g: Float, b: Float): Unit = addColor(r, g, b, 1)

    def addColor(r: Float, g: Float, b: Float, a: Float):Unit= {
        colorR = r
        colorG = g
        colorB = b
        colorA = a
        isColors = true
    }

    def addColorRGBA(r: Int, g: Int, b: Int, a: Int): Unit = {
        addColor(r / 255f, g / 255f, b / 255f, a / 255f)
    }

    def addColor(color: Color): Unit = {
        addColorRGBA(color.getRed, color.getGreen, color.getBlue, color.getAlpha)
    }

    def addTextureUV(u: Float, v: Float):Unit= {
        textureU = u
        textureV = v
    }

    def make(): Mesh = {

        val vaoId: Int = glGenVertexArrays

        val vboIdList: ArrayBuffer[Int] = ArrayBuffer[Int]()

        glBindVertexArray(vaoId)
        bindArray(vboIdList, 0, 3, posArray.toArray)
       // if (isColors) {
            bindArray(vboIdList, 1, 4, colourArray.toArray)
       // }
        if(texture!=null){
            bindArray(vboIdList, 2, 2, textCordsArray.toArray)
        }
        if (isNormals) {
            bindArray(vboIdList, 3, 3, normalsArray.toArray)
        }
        bindArrayIndices(vboIdList, indicesArray.toArray)
        glBindVertexArray(0)
        val mesh = new Mesh(makeMode, indicesArray.length, vaoId, vboIdList.toList, texture)

        mesh

    }

    private def bindArray(vboIdList: ArrayBuffer[Int], index: Int, size: Int, array: Array[Float]): Unit = {
        val vboId: Int = glGenBuffers
        vboIdList += vboId
        glBindBuffer(GL_ARRAY_BUFFER, vboId)
        glBufferData(GL_ARRAY_BUFFER, array, GL_STATIC_DRAW)
        glEnableVertexAttribArray(index)
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    private def bindArrayIndices(vboIdList: ArrayBuffer[Int], array: Array[Int]): Unit = {
        val vboId: Int = glGenBuffers
        vboIdList += vboId
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, array, GL_STATIC_DRAW)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }


}

object MeshMaker {


    def startMakeTriangles(): MeshMaker = {
        startMake(GL_TRIANGLES)
    }

    def startMake(mode: Int): MeshMaker = {
        new MeshMaker(mode)
    }
}
