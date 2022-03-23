package ru.megains.techworld.client.renderer.block

import ru.megains.techworld.client.renderer.api.TTextureRegister
import ru.megains.techworld.client.renderer.texture.TTexture
import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.utils.Direction
import ru.megains.techworld.common.world.World

class RenderBlockLog(name: String) extends RenderBlockStandard (name){

    var aTextureTop: TTexture = _



    override def registerTexture(textureRegister: TTextureRegister): Unit = {
        texture = textureRegister.registerTexture(name )
        aTextureTop = textureRegister.registerTexture(name + "_top")

    }

    override def getTexture(blockState: BlockState, direction: Direction, world: World): TTexture = {
        direction match {
            case Direction.UP |Direction.DOWN  => aTextureTop
            case _ => texture
        }

    }

}
