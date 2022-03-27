package ru.megains.techworld.client.renderer.api

import ru.megains.techworld.client.renderer.model.Model
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.client.renderer.texture.TTexture


trait  TRenderItem  extends TRenderTexture{

    def renderInInventory(): Unit

    def renderInWorld( shader: Shader): Unit

    def getInventoryModel:Model

}
