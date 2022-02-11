package ru.megains.techworld.client.renderer

import ru.megains.techworld.client.renderer.texture.{TTexture, Texture, TextureManager}

object Resources {

    val WIDGETS: TTexture = TextureManager.getTexture("textures/gui/widgets.png")

    val PLAYER_INVENTORY: TTexture = TextureManager.getTexture("textures/gui/playerInventory.png")
}
