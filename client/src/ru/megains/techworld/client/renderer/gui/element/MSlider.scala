package ru.megains.techworld.client.renderer.gui.element

import ru.megains.techworld.client.renderer.{MContainer, MSprite, Resources}
import ru.megains.techworld.client.renderer.text.Label
import ru.megains.techworld.client.renderer.texture.TextureRegion

class MSlider(val sliderText: String, widthIn: Int, height: Int, min: Int, max: Int, var value: Int,func:()=>Unit) extends MContainer {


    val label: Label = new Label(sliderText + ":  " + value) {
        posX = (widthIn -  width)/2
        posY = 10
    }
    val buttonUp: MSprite = new MSprite(new TextureRegion(Resources.WIDGETS, 0, 66, 200, 20), 20, height) {
        posX = 2 + (widthIn - 24) * value / max
    }
    val buttonDisable: MSprite = new MSprite(new TextureRegion(Resources.WIDGETS, 0, 46, 200, 20), widthIn, height)

    addChildren(buttonDisable, buttonUp, label)

    var enable = true
    var isDrag = false


    override def update(): Unit = {

    }

    override def mousePress(x: Int, y: Int,button:Int): Unit = {
        if (isMouseOver(x, y)) {
            isDrag = !isDrag
        }
    }

    override def mouseMove(x: Int, y: Int): Unit = {
        if (isDrag) {
            value = Math.min(Math.max(((x - posX - 12) / (widthIn.toFloat - 24) * max).toInt, min), max)
            label.text = sliderText + ":  " + value
            label.posX = (widthIn -  label.width)/2
            buttonUp.posX = 2 + ((widthIn - 24)/ (max- min).toFloat) * (value - min).toFloat
            func()
        }
    }

    override def mouseRelease(x: Int, y: Int,button:Int): Unit = {
        if (isDrag) isDrag = !isDrag
    }

    def isMouseOver(mouseX: Int, mouseY: Int): Boolean = {
        enable && mouseX >= posX && mouseX <= posX + widthIn && mouseY >= posY && mouseY <= posY + height
    }
}
