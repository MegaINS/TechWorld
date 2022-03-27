package ru.megains.techworld.client.renderer

import ru.megains.techworld.client.renderer.texture.{TTexture, Texture, TextureManager}

object Resources {

    val TARGET: TTexture = TextureManager.getTexture("textures/gui/target.png")

    val WIDGETS: TTexture = TextureManager.getTexture("textures/gui/widgets.png")

    val PLAYER_INVENTORY: TTexture = TextureManager.getTexture("textures/gui/playerInventory.png")

    val STACK_SELECT: TTexture = TextureManager.getTexture("textures/gui/stackSelect.png")

    val HOT_BAR: TTexture = TextureManager.getTexture("textures/gui/hotBar.png")

}
