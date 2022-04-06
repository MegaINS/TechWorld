package ru.megains.techworld.client.renderer.entity

import ru.megains.techworld.client.renderer.api.TRenderTexture
import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.common.entity.Entity
import ru.megains.techworld.common.world.World

trait TRenderEntity extends TRenderTexture{

    def init():Unit

    def render(entity: Entity, world: World,shader: Shader,partialTicks:Double): Boolean
}
