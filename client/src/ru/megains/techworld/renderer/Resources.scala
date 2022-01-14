package ru.megains.techworld.renderer

import ru.megains.techworld.renderer.texture.{TTexture, Texture, TextureManager}

object Resources {

    val WIDGETS: TTexture = TextureManager.getTexture("textures/gui/widgets.png")

    val PLAYER_INVENTORY: TTexture = TextureManager.getTexture("textures/gui/playerInventory.png")
}
