package ru.megains.techworld.client.renderer.shader.data

import org.lwjgl.BufferUtils

import java.nio.FloatBuffer

class UniformData(val uniformLocation: Int){

     val floatBuffer: FloatBuffer = BufferUtils.createFloatBuffer(16)

}
