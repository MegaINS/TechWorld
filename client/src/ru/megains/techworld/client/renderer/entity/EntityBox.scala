package ru.megains.techworld.client.renderer.entity

import org.joml.Matrix4f
import ru.megains.techworld.client.renderer.mesh.MeshMaker
import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.texture.{TTexture, TextureRegion}

class EntityBox(entityModel: EntityModel, originX: Int, originY: Int, originZ: Int, width: Int, height: Int, depth: Int, textureOffsetX: Float, textureOffsetY: Float) extends Model {

    val textureWidth: Float = 64.0F
    val textureHeight: Float = 32.0F
    val heightTexture: Float = (height+depth)/textureHeight
    val widthTexture: Float = ((width+depth)*2)/textureWidth
    parent = entityModel
    var offsetX = 0
    var offsetY = 0
    var offsetZ = 0


    def init(): Unit = {
        val mm = MeshMaker.startMakeTriangles()
        mm.setTexture(entityModel.texture)

        val minX: Float = originX.toFloat / 16f
        val minY: Float = originY.toFloat / 16f
        val minZ: Float = originZ.toFloat / 16f


        val maxX: Float = (width + originX).toFloat / 16f
        val maxY: Float = (height + originY).toFloat / 16f
        val maxZ: Float = (depth + originZ).toFloat / 16f


        val dx: Float = (width / (width + depth.toFloat) / 2f)
        val dy: Float = (height / (height + depth.toFloat))
        val dz: Float = (depth / (width + depth.toFloat) / 2f)

        
        val textureX = textureOffsetX/textureWidth

        val textureY = textureOffsetY/textureHeight
        

        val westT = new TextureRegion(entityModel.texture, textureX + (dx + dz) * widthTexture, textureY, (textureX + (dx + dz * 2) * widthTexture), textureY + dy * heightTexture)
        renderSideWest(mm, minX, minY, maxY, minZ, maxZ, westT)

        val eastT = new TextureRegion(entityModel.texture, textureX + 0f * widthTexture, textureY, (textureX + dz * widthTexture), textureY + dy * heightTexture)
        renderSideEast(mm, maxX, minY, maxY, minZ, maxZ, eastT)

        val northT = new TextureRegion(entityModel.texture, textureX + dz * widthTexture, textureY, (textureX + (dx + dz) * widthTexture), textureY + dy * heightTexture)
        renderSideNorth(mm, minX, maxX, minY, maxY, minZ, northT)

        val southT = new TextureRegion(entityModel.texture, textureX + (dx + dz * 2) * widthTexture, textureY, (textureX + widthTexture), textureY + dy * heightTexture)
        renderSideSouth(mm, minX, maxX, minY, maxY, maxZ, southT)


        val downT = new TextureRegion(entityModel.texture, textureX + (dx + dz) * widthTexture, textureY + dy * heightTexture, (textureX + (dz + dx * 2) * widthTexture), textureY + heightTexture)
        renderSideDown(mm, minX, maxX, minY, minZ, maxZ, downT)

        val upT = new TextureRegion(entityModel.texture, textureX + dz * widthTexture, textureY + dy * heightTexture, (textureX + (dx + dz) * widthTexture), textureY + heightTexture)
        renderSideUp(mm, minX, maxX, maxY, minZ, maxZ, upT)


        mesh = mm.make()
    }

    override def buildMatrix(): Matrix4f = {
        matrix.identity
        // matrix.translate(rotationPoint)
        matrix.translate(posX, posY, posZ)
        matrix.translate(offsetX/16f, offsetY/16f, offsetZ/16f)
        matrix.translate(rotationPoint.x/16f,rotationPoint.y/16f,rotationPoint.z/16f)
        matrix.rotateXYZ(Math.toRadians(xRot).toFloat, Math.toRadians(yRot).toFloat, Math.toRadians(zRot).toFloat)
        matrix.translate(-rotationPoint.x/16f,-rotationPoint.y/16f,-rotationPoint.z/16f)

        //matrix.translate(rotationPoint)
        matrix.scale(scale)

        parent match {
            case null =>
                matrix
            case _ =>
                parent.buildMatrix().mul(matrix)
        }
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
