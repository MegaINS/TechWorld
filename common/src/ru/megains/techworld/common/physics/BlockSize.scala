package ru.megains.techworld.common.physics

import ru.megains.techworld.common.utils.Direction

class BlockSize(var minX:Int,var minY:Int,var minZ:Int,var maxX:Int,var maxY:Int,var maxZ:Int) {


    def this(sizeX:Int,sizeY:Int,sizeZ:Int)={
        this(0,0,0,sizeX,sizeY,sizeZ)
    }

    def this(size:Int)={
        this(size,size,size)
    }

    def rotate(side: Direction/*, boxes: BoundingBoxes*/): BlockSize  = {
        side match {
            case Direction.SOUTH=> new BlockSize( /*boxes.*/maxZ - maxZ , minY,minX , /*boxes.*/maxZ - minZ, maxY, maxX  )
            case Direction.WEST=> new BlockSize(/*boxes.*/maxX - maxX, minY, /*boxes.*/maxZ - maxZ,/*boxes.*/maxX - minX, maxY,/*boxes.*/maxZ - minZ)
            case Direction.NORTH=> new BlockSize(minZ, minY,/*boxes.*/maxX - maxX, maxZ , maxY, /*boxes.*/maxX - minX  )
            case _  => getCopy
        }
    }

    def sum(x: Int, y: Int, z: Int):BlockSize = new BlockSize(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z)

    def getCopy = new BlockSize(minX, minY, minZ, maxX, maxY, maxZ)


}
