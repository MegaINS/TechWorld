package ru.megains.techworld.utils

import org.slf4j
import org.slf4j.LoggerFactory

trait Logger[T] {
    val log: slf4j.Logger =  LoggerFactory.getLogger(asInstanceOf[T].getClass)
}
