package ru.megains.techworld.common.register

import scala.collection.mutable


class RegisterRender[K,T] {

    val idRender: mutable.HashMap[K, T] = new mutable.HashMap[K, T]

    def registerRender(id: K, aRender: T): Unit = {
        idRender += id -> aRender
    }

    def getRender(id: K): T = idRender.getOrElse(id,default = null.asInstanceOf[T])
}
