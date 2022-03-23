package ru.megains.techworld.client.renderer.entity

import ru.megains.techworld.client.renderer.mesh.MeshMaker
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.texture.{TTexture, TextureRegion}

class EntityBox(minX: Float, minY: Float, minZ: Float, width: Float, height: Float, depth: Float, texture: TTexture, minU: Float, minV: Float, widthTexture: Float, heightTexture: Float) extends Model {


    def init(): Unit = {
        val mm = MeshMaker.startMakeTriangles()
        mm.setTexture(texture)
        val maxX: Float = width + minX
        val maxY: Float = height + minY
        val maxZ: Float = depth + minZ


        val dx = width / (width + depth) / 2
        val dy = height / (height + depth)

        val dz = depth / (width + depth) / 2


        val westT = new TextureRegion(texture, minU + (dx + dz) * widthTexture, minV, (minU + (dx + dz * 2) * widthTexture), minV + dy * heightTexture)
        renderSideWest(mm, minX, minY, maxY, minZ, maxZ, westT)

        val eastT = new TextureRegion(texture, minU + 0f * widthTexture, minV, (minU + dz * widthTexture), minV + dy * heightTexture)
        renderSideEast(mm, maxX, minY, maxY, minZ, maxZ, eastT)

        val northT = new TextureRegion(texture, minU + dz * widthTexture, minV, (minU + (dx + dz) * widthTexture), minV + dy * heightTexture)
        renderSideNorth(mm, minX, maxX, minY, maxY, minZ, northT)

        val southT = new TextureRegion(texture, minU + (dx + dz * 2) * widthTexture, minV, (minU + widthTexture), minV + dy * heightTexture)
        renderSideSouth(mm, minX, maxX, minY, maxY, maxZ, southT)


        val downT = new TextureRegion(texture, minU + (dx + dz)* widthTexture, minV + dy * heightTexture, (minU + (dz + dx * 2) * widthTexture), minV + heightTexture)
        renderSideDown(mm, minX, maxX, minY, minZ, maxZ, downT)

        val upT = new TextureRegion(texture, minU + dz * widthTexture, minV + dy * heightTexture, (minU + (dx + dz) * widthTexture), minV + heightTexture)
        renderSideUp(mm, minX, maxX, maxY, minZ, maxZ, upT)


        mesh = mm.make()
    }


    def renderSideUp(mm: MeshMaker, minX: Float, maxX: Float, maxY: Float, minZ: Float, maxZ: Float, texture: TTexture): Unit = {

        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV

        //  //mm.addNormals(0, 1, 0)
        mm.setCurrentIndex()
        //  mm.addColor(color)
        mm.addVertexWithUV(minX, maxY, minZ, maxU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, maxU, minV)
        mm.addVertexWithUV(maxX, maxY, minZ, minU, maxV)
        mm.addVertexWithUV(maxX, maxY, maxZ, minU, minV)
        mm.addIndex(0, 1, 2)
        mm.addIndex(1, 3, 2)

    }


    def renderSideDown(mm: MeshMaker, minX: Float, maxX: Float, minY: Float, minZ: Float, maxZ: Float, texture: TTexture): Unit = {

        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV

        // //mm.addNormals(0, -1, 0)
        mm.setCurrentIndex()
        // mm.addColor(color)
        mm.addVertexWithUV(minX, minY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, minY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, minY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, minY, maxZ, maxU, minV)
        mm.addIndex(0, 2, 1)
        mm.addIndex(1, 2, 3)
    }


    def renderSideWest(mm: MeshMaker, minX: Float, minY: Float, maxY: Float, minZ: Float, maxZ: Float, texture: TTexture): Unit = {


        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV

        ////mm.addNormals(-1, 0, 0)
        mm.setCurrentIndex()
        // mm.addColor(color)
        mm.addVertexWithUV(minX, minY, minZ, minU, minV)
        mm.addVertexWithUV(minX, minY, maxZ, maxU, minV)
        mm.addVertexWithUV(minX, maxY, minZ, minU, maxV)
        mm.addVertexWithUV(minX, maxY, maxZ, maxU, maxV)

        mm.addIndex(0, 1, 2)
        mm.addIndex(1, 3, 2)

    }


    def renderSideEast(mm: MeshMaker, maxX: Float, minY: Float, maxY: Float, minZ: Float, maxZ: Float, texture: TTexture): Unit = {
        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV

        //mm.addNormals(1, 0, 0)
        mm.setCurrentIndex()
        // mm.addColor(color)
        mm.addVertexWithUV(maxX, minY, minZ, maxU, minV)
        mm.addVertexWithUV(maxX, minY, maxZ, minU, minV)
        mm.addVertexWithUV(maxX, maxY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, maxY, maxZ, minU, maxV)
        mm.addIndex(0, 2, 1)
        mm.addIndex(1, 2, 3)

    }

    def renderSideSouth(mm: MeshMaker, minX: Float, maxX: Float, minY: Float, maxY: Float, maxZ: Float, texture: TTexture): Unit = {


        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV
        //mm.addNormals(0, 0, 1)
        mm.setCurrentIndex()
        // mm.addColor(color)
        mm.addVertexWithUV(minX, minY, maxZ, minU, minV)
        mm.addVertexWithUV(minX, maxY, maxZ, minU, maxV)
        mm.addVertexWithUV(maxX, minY, maxZ, maxU, minV)
        mm.addVertexWithUV(maxX, maxY, maxZ, maxU, maxV)

        mm.addIndex(0, 2, 1)
        mm.addIndex(1, 2, 3)
    }


    def renderSideNorth(mm: MeshMaker, minX: Float, maxX: Float, minY: Float, maxY: Float, minZ: Float, texture: TTexture): Unit = {

        val minU = texture.minU
        val maxU = texture.maxU
        val minV = texture.minV
        val maxV = texture.maxV

        //mm.addNormals(0, 0, -1)
        mm.setCurrentIndex()
        // mm.addColor(color)
        mm.addVertexWithUV(minX, minY, minZ, maxU, minV)
        mm.addVertexWithUV(minX, maxY, minZ, maxU, maxV)
        mm.addVertexWithUV(maxX, minY, minZ, minU, minV)
        mm.addVertexWithUV(maxX, maxY, minZ, minU, maxV)
        mm.addIndex(0, 1, 2)
        mm.addIndex(1, 3, 2)
    }


}
