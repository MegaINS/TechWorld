package ru.megains.techworld.utils

import ru.megains.techworld.utils.Direction.{EAST, NORTH, SOUTH}

sealed abstract class Direction private(val x: Int, val y: Int, val z: Int, val name: String, val id: Int) {
    def getAngel: Int ={
        this match {
            case NORTH => 90
            case EAST => 180
            case SOUTH => 270
            case _ => 0
        }
    }
}

object Direction {

    case object DOWN extends Direction(0, -1, 0, "down", 0)

    case object UP extends Direction(0, 1, 0, "up", 1)

    case object NORTH extends Direction(0, 0, -1, "north", 2)

    case object SOUTH extends Direction(0, 0, 1, "south", 3)

    case object WEST extends Direction(-1, 0, 0, "west", 4)

    case object EAST extends Direction(1, 0, 0, "east", 5)

    case object NONE extends Direction(0, 0, 0, "none", 6)

    val DIRECTIONAL_BY_ID: Array[Direction] = Array(DOWN, UP, NORTH, SOUTH, WEST, EAST,NONE)


}

