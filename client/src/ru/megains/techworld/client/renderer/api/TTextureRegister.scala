package ru.megains.techworld.client.renderer.api


import ru.megains.techworld.client.renderer.texture.TTexture


trait TTextureRegister {

    def registerTexture(textureName : String): TTexture

}
