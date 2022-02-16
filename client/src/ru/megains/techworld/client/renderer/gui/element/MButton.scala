package ru.megains.techworld.client.renderer.gui.element

import ru.megains.techworld.client.renderer.{MSprite, Resources}
import ru.megains.techworld.client.renderer.text.Label
import ru.megains.techworld.client.renderer.texture.TextureRegion


class MButton(val buttonText: String, widthIn: Int, heightIn: Int, func:MButton=>Unit) extends GuiElement {

    override val height: Int = heightIn
    override val width: Int = widthIn


    val textMesh: Label = new Label(buttonText){
        posX = (widthIn - width)/2
        posY = 10
    }

    val buttonUp:MSprite = new MSprite(new TextureRegion(Resources.WIDGETS,0,66,200,20),width,height)

    val buttonUpOver:MSprite = new MSprite(new TextureRegion(Resources.WIDGETS,0,86,200,20),width,height)
    val buttonDisable:MSprite = new MSprite(new TextureRegion(Resources.WIDGETS,0,46,200,20),width,height)
    var enable = true
    var over = false
    var click = false

    buttonUpOver.active = false
    buttonDisable.active = false

    addChildren(buttonUp,buttonUpOver,buttonDisable,textMesh)

    override def update(): Unit = {

        if (!enable) {
            buttonDisable.active = true
            buttonUp.active = false
        } else{
            buttonDisable.active = false
            buttonUp.active = true
        }
    }

    override def mouseMove(x: Int, y: Int): Unit = {
        if(enable){
            if(over != isMouseOver(x,y)){
                over = isMouseOver(x,y)
                if(over) {
                    buttonUp.active = false
                    buttonUpOver.active = true

                } else {
                    buttonUp.active = true
                    buttonUpOver.active = false

                }
            }
        }
    }


    override def mousePress(x: Int, y: Int,button:Int): Unit = {
        if(enable){
            if(isMouseOver(x: Int, y: Int)){
                func(this)
            }
        }

    }

    override def mouseRelease(x:Int,y:Int,button:Int):Unit= {

    }

    override def init(): Unit = {}
}
