package ru.megains.techworld.renderer.text

import ru.megains.techworld.renderer.MContainer
import ru.megains.techworld.renderer.font.{FontRender, Fonts}
import ru.megains.techworld.renderer.model.Model
import ru.megains.techworld.renderer.shader.data.Shader

import java.awt.Color


class Label(private var _text: String = "",private var _textStyle: TextStyle = TextStyle.default) extends MContainer {

    def width: Float = Fonts.firaSans.getWidth(text)

    var textModel:Model = new Model()
    var shadowModel:Model = new Model(){
        posX = 2
        posY = 2
    }

    addChildren(shadowModel,textModel)

    text = _text
    def text: String = _text

    def text_=(text: String): Unit = {
        _text = text

        textModel.mesh =FontRender.createStringGui(text, Color.WHITE, Fonts.firaSans)
        shadowModel.mesh = FontRender.createStringGui(text, Color.BLACK, Fonts.firaSans)
    }

    override def render(shader: Shader): Unit = {
        super.render(shader)
    }

    def textStyle: TextStyle = _textStyle

    def textStyle_=(textStyle: TextStyle): Unit = {
        _textStyle = textStyle
    }


}
