package ru.megains.techworld.client.renderer.texture

import org.lwjgl.opengl.{EXTTextureFilterAnisotropic, GL11}
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL30._
import ru.megains.techworld.client.renderer.api.{TRenderTexture, TTextureRegister}

import scala.collection.immutable.HashMap


class TextureMap(val name:String,prefix:String) extends TTexture() with TTextureRegister{

    var list: List[TextureAtlas] =  _
    var map:Array[Array[Boolean]] = _
    var textureBlockMap: HashMap[String, TextureAtlas] = new HashMap[String, TextureAtlas]
    val missingTexture = new TextureAtlas("textures/missing.png")





    def registerTexture(list:List[TRenderTexture]): Unit = {

        textureBlockMap += "missing" -> missingTexture
        list.foreach(_.registerTexture(this))

    }

    override def loadTexture(): Boolean ={

        list = textureBlockMap.values.toList.sortBy(_.width).reverse
        list.foreach(_.loadTexture())
        createTexture()
        updateTexture()
        glBindTexture(GL_TEXTURE_2D, getGlTextureId)


       // glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,GL_NEAREST_MIPMAP_NEAREST )
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
       // glTexParameterf(GL_TEXTURE_2D, 34046, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
       // glTexParameterf(GL_TEXTURE_2D,EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT,12)
        glGenerateMipmap(GL_TEXTURE_2D)

      //  GL_NEAREST_MIPMAP_NEAREST

      //  GL_LINEAR_MIPMAP_NEAREST
             //   GL_NEAREST_MIPMAP_LINEAR

     //   GL_LINEAR_MIPMAP_LINEAR


        glBindTexture(GL_TEXTURE_2D, 0)

        true
    }

    override def registerTexture(textureName : String): TextureAtlas={
        val tTexture = new TextureAtlas(s"textures/$prefix/$textureName.png")
        tTexture.loadTexture()
        if(!tTexture.isMissingTexture){
            textureBlockMap += textureName -> tTexture
            tTexture
        }else{
            println("Missing texture = "+textureName)
            missingTexture
        }
    }

    def updateTexture(): Unit ={
        glBindTexture(GL_TEXTURE_2D, getGlTextureId)
        list.foreach(_.updateTexture(width,height))

    }

    def createTexture(): Unit ={

        width = 1
        height = 1

        map = Array.ofDim[Boolean](width,height)
        list.foreach((tex:TextureAtlas)=>{
            val size:Int = tex.height
            searchBox(size,tex)

        })
        glBindTexture(GL_TEXTURE_2D,getGlTextureId)
//        glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
//
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
       // glBindTexture(GL_TEXTURE_2D, 0)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width , height , 0, GL_RGBA, GL_UNSIGNED_BYTE, 0)
        println("Create texture block map "+width+"-"+height+" pixels")
    }

    def searchBox(size:Int,tex:TextureAtlas): Boolean ={

        for(x <- 0 to (map.length - size);y <- 0 to (map(x).length - size)){
            var boxEmpty: Boolean = true

            for(i<- x until x + size; j<-y until y + size){
                if(map(i)(j)) boxEmpty=false
            }

            if(boxEmpty){
                for(i<-x until x + size; j<-y until y + size){map(i)(j)=true}
                tex.startX =x
                tex.startY =y
                return true
            }
        }
        resizeMap()
        searchBox(size,tex)
    }

    def resizeMap(): Unit ={
        width+=1
        height+=1
        val temp = Array.ofDim[Boolean](width,height)
        for(x<-map.indices;y<- map(x).indices){
            temp(x)(y) = map(x)(y)
        }
        map = temp
    }


}
