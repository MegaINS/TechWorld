package ru.megains.techworld.common.block

import ru.megains.techworld.common.physics.{BlockSize, BoundingBox}

object BlockAir extends Block("air") {

    override val physicalBody: BoundingBox = new BoundingBox(0)
    override val blockSize: BlockSize = new BlockSize(0)
    override val boundingBox: BoundingBox = new BoundingBox(0)
    override def isOpaqueCube: Boolean = false

    override  def isAirBlock(state: BlockState) = true
}
