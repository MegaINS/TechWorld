package ru.megains.techworld.common.utils

import org.slf4j
import org.slf4j.LoggerFactory

trait Logger {
    val log: slf4j.Logger = LoggerFactory.getLogger(this.getClass)
}
