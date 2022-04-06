package ru.megains.techworld.common.tileentity

import ru.megains.techworld.common.block.BlockState
import ru.megains.techworld.common.nbt.tag.NBTCompound
import ru.megains.techworld.common.world.World

abstract class TileEntity(val blockState: BlockState) {

    def update(world: World): Unit = {

    }

    def writeToNBT(data: NBTCompound): Unit = {

    }

    def readFromNBT(data: NBTCompound): Unit= {

    }

}
