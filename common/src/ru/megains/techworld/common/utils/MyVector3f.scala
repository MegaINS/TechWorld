package ru.megains.techworld.common.utils

import org.joml.Vector3d

object MyVector3f {

    implicit def myVector3f(vecIn: Vector3d): MyVector3f = new MyVector3f(vecIn)

    class MyVector3f(vecIn: Vector3d) {

        def getIntermediateWithXValue(vec: Vector3d, x: Double): Vector3d = {
            val d0: Double = vec.x - vecIn.x
            val d1: Double = vec.y - vecIn.y
            val d2: Double = vec.z - vecIn.z

            if (d0 * d0 < 1.0000000116860974E-7D) {
                null
            } else {
                val d3: Double = (x - vecIn.x) / d0
                if (d3 >= 0.0D && d3 <= 1.0D) new Vector3d((vecIn.x + d0 * d3), (vecIn.y + d1 * d3), (vecIn.z + d2 * d3))
                else null

            }
        }

        def getIntermediateWithYValue(vec: Vector3d, y: Double): Vector3d = {
            val d0: Double = vec.x - vecIn.x
            val d1: Double = vec.y - vecIn.y
            val d2: Double = vec.z - vecIn.z

            if (d1 * d1 < 1.0000000116860974E-7D) {
                null
            } else {

                val d3: Double = (y - vecIn.y) / d1
                if (d3 >= 0.0D && d3 <= 1.0D) new Vector3d((vecIn.x + d0 * d3), (vecIn.y + d1 * d3), (vecIn.z + d2 * d3))
                else null
            }
        }


        def getIntermediateWithZValue(vec: Vector3d, z: Double): Vector3d = {
            val d0: Double = vec.x - vecIn.x
            val d1: Double = vec.y - vecIn.y
            val d2: Double = vec.z - vecIn.z

            if (d2 * d2 < 1.0000000116860974E-7D) {
                null
            } else {
                val d3: Double = (z - vecIn.z) / d2
                if (d3 >= 0.0D && d3 <= 1.0D) new Vector3d((vecIn.x + d0 * d3), (vecIn.y + d1 * d3), (vecIn.z + d2 * d3))
                else null
            }
        }

    }


}
