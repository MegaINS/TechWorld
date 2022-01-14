package ru.megains.techworld.renderer.texture

class TextureRegion(texture: TTexture) extends TTexture() {


    def this(texture: TTexture,x:Int,y:Int,width:Int, height:Int){
        this(texture)
        minU = x.toFloat/texture.width.toFloat
        maxU = (x+width).toFloat/texture.width.toFloat
        minV = y.toFloat/texture.width.toFloat
        maxV = (y+height).toFloat/texture.width.toFloat
    }
    def this(texture: TTexture,minUIn:Float,minVIn:Float,maxUIn:Float,maxVIn:Float){
        this(texture)
        minU = minUIn
        maxU = maxUIn
        minV = minVIn
        maxV = maxVIn
    }





    override def getGlTextureId: Int = texture.getGlTextureId

    override def deleteGlTexture(): Unit = texture.deleteGlTexture()

    override def loadTexture(): Boolean = true
}
