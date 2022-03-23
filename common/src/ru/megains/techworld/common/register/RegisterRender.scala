package ru.megains.techworld.common.register

import scala.collection.mutable


class RegisterRender[T] {

    val idRender: mutable.HashMap[Int, T] = new mutable.HashMap[Int, T]

    def registerRender(id: Int, aRender: T): Unit = {
        idRender += id -> aRender
    }

    def getRender(id: Int): T = idRender.getOrElse(id,default = null.asInstanceOf[T])
}
