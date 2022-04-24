package ru.megains.techworld.client.renderer.block

import ru.megains.techworld.client.renderer.api.TTextureRegister
import ru.megains.techworld.client.renderer.texture.TTexture
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.utils.Direction
import ru.megains.techworld.common.world.World

class RenderBlockGrass(name: String) extends RenderBlockStandard(name) {


    var aTextureUp: TTexture = _
    var aTextureDown: TTexture = _


    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        texture = textureRegister.registerTexture(name + "_side")
        aTextureUp = textureRegister.registerTexture(name + "_up")
        aTextureDown = textureRegister.registerTexture(name + "_down")
    }



    override def getTexture(direction: Direction): TTexture = {
        direction match {
            case Direction.UP => aTextureUp
            case Direction.DOWN => aTextureDown
            case _ => texture
        }
    }
}
