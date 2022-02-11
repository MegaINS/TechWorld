package ru.megains.techworld.client.renderer.model

import ru.megains.techworld.client.renderer.MObject
import ru.megains.techworld.client.renderer.mesh.Mesh
import ru.megains.techworld.client.renderer.shader.data.Shader

class Model extends MObject{

    private var _mesh:Mesh = _
    var active:Boolean = true

    def this(meshIn:Mesh)={
        this()
        mesh = meshIn
    }

    def mesh: Mesh = _mesh

    def mesh_=(mesh: Mesh): Unit = {
        cleanUp()
        _mesh = mesh
    }

    override def render(shader:Shader): Unit = {
        if (mesh != null && active) {
            shader.setUniform("modelMatrix", buildMatrix())
            mesh.render()
        }
    }

    override def update(): Unit = {

    }

    def cleanUp(): Unit = if (_mesh != null) _mesh.cleanUp()


}
