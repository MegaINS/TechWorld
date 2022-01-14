package ru.megains.techworld.renderer.shader

import ru.megains.techworld.renderer.shader.data.Shader

object ShaderManager {

    val guiShader: Shader = new GuiShader()
    val worldShader:Shader = new WorldShader()
    val skyBoxShader:Shader = new SkyBoxShader()

    var currentShader: Shader = _

    def init(): Unit ={
        guiShader.create()
        worldShader.create()
        skyBoxShader.create()
    }


    def bindShader(shader: Shader): Unit = {
        if(shader !=currentShader){
            if (currentShader != null) currentShader.unbind()
            currentShader = shader
            currentShader.bind()
        }
    }


    def unbindShader(): Unit = {
        if (currentShader != null) currentShader.unbind()
        currentShader = null
    }
}
