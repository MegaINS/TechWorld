package ru.megains.techworld.client.renderer.api

import ru.megains.techworld.client.renderer.texture.TTexture


trait  TRenderItem  extends TTexture{

    def renderInInventory(): Unit

    def renderInWorld(): Unit

}
