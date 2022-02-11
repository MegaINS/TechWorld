package ru.megains.techworld.client.renderer.shader.data

import org.joml.{Matrix4f, Vector3f}
import org.lwjgl.opengl.GL20._
import ru.megains.techworld.client.File
import ru.megains.techworld.client.renderer.camera.Camera

import scala.collection.mutable

abstract class Shader {


    lazy val programId: Int = glCreateProgram


    var vertexShaderId: Int = 0
    var fragmentShaderId: Int = 0
    val dir: String = ""

    var uniforms: mutable.HashMap[String, UniformData] = new mutable.HashMap()


    def create(): Unit = {
        if (programId == 0) throw new Exception("Could not create Shader")
        createVertexShader(File.loadResource(s"$dir/vertex.glsl"))
        createFragmentShader(File.loadResource(s"$dir/fragment.glsl"))
        link()
        createUniforms()
    }

    def createUniforms(): Unit

    def setUniform(camera: Camera): Unit

    def createShader(shaderCode: String, shaderType: Int): Int = {
        val shaderId = glCreateShader(shaderType)
        if (shaderId == 0) throw new Exception("Error creating shader. Code: " + shaderId)
        glShaderSource(shaderId, shaderCode)
        glCompileShader(shaderId)
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024))
        glAttachShader(programId, shaderId)
        shaderId
    }

    def createVertexShader(shaderCode: String): Unit = {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER)
    }

    def createFragmentShader(shaderCode: String): Unit = {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER)
    }

    def link(): Unit = {
        glLinkProgram(programId)
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) throw new Exception("Error linking Shader code: " + glGetShaderInfoLog(programId, 1024))
        glValidateProgram(programId)
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) throw new Exception("Warning validating Shader code: " + glGetShaderInfoLog(programId, 1024))
    }

    def createUniform(uniformName: String): Unit = {
        val uniformLocation = glGetUniformLocation(programId, uniformName)
        if (uniformLocation < 0) throw new Exception("Could not find uniform:" + uniformName)
        uniforms.put(uniformName, new UniformData(uniformLocation))
    }

    def setUniform(uniformName: String, value: Matrix4f): Unit = {
        val uniformData = getUniform(uniformName)
        value.get(uniformData.floatBuffer)
        glUniformMatrix4fv(uniformData.uniformLocation, false, uniformData.floatBuffer)
    }

    def setUniform(uniformName: String, value: Boolean): Unit = {
        val uniformData = getUniform(uniformName)
        glUniform1i(uniformData.uniformLocation, if (value) 1 else 0)
    }

    var uniformDataBuf = new mutable.HashMap[UniformData, Object]()

    def setUniform(uniformName: String, value1: Boolean, value2: Boolean): Unit = {
        val uniformData = getUniform(uniformName)
        uniformDataBuf.get(uniformData) match {
            case Some(value) =>
                if (value != (value1, value2)) {
                    uniformDataBuf += uniformData -> (value1, value2)
                    glUniform2i(uniformData.uniformLocation, if (value1) 1 else 0, if (value2) 1 else 0)
                }
            case None =>
                uniformDataBuf += uniformData -> (value1, value2)
                glUniform2i(uniformData.uniformLocation, if (value1) 1 else 0, if (value2) 1 else 0)
        }

        //glUniform2i(uniformData.uniformLocation, if (value1) 1 else 0, if (value2) 1 else 0)
    }


    //    def setUniform(uniformName: String, value: Float): Unit = {
    //        val uniformData = getUniform(uniformName)
    //        glUniform1f(uniformData.uniformLocation, value)
    //    }
    //
    //    def setUniform(uniformName: String, value: Int): Unit = {
    //        val uniformData = getUniform(uniformName)
    //        glUniform1i(uniformData.uniformLocation, value)
    //    }

    def setUniform(uniformName: String, value: Vector3f): Unit = {
        val uniformData = getUniform(uniformName)
        glUniform3f(uniformData.uniformLocation, value.x, value.y, value.z)
    }


    def getUniform(uniformName: String): UniformData = {
        uniforms.get(uniformName) match {
            case Some(value) => value
            case None => null //throw new RuntimeException("Uniform [" + uniformName + "] has nor been created")
        }
    }

    def bind(): Unit = {
        glUseProgram(programId)
    }

    def unbind(): Unit = glUseProgram(0)

}
