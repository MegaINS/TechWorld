package ru.megains.techworld.common.nbt.tag

import ru.megains.techworld.common.nbt.NBTType._
import ru.megains.techworld.common.nbt.tag.NBTBase._

import java.io.{DataInput, DataOutput}


object NBT{

    abstract class NBT {
        val nbtType:NBTType
        def write(output: DataOutput):Unit
        def read(input: DataInput):Unit
    }

    def apply(nbtType:NBTType): NBT = getClassOfType(nbtType).newInstance()

    def getClassOfType(nbtType:NBTType): Class[_ <: NBT]  = nbtType match {
        case EnumNBTEnd => classOf[NBTEnd]
        case EnumNBTByte => classOf[ NBTByte]
        case EnumNBTBoolean => classOf[ NBTBoolean]
        case EnumNBTShort => classOf[ NBTShort]
        case EnumNBTInt => classOf[ NBTInt]
        case EnumNBTLong => classOf[ NBTLong]
        case EnumNBTFloat => classOf[ NBTFloat]
        case EnumNBTDouble => classOf[ NBTDouble]
        case EnumNBTString => classOf[ NBTString]
        case EnumNBTArrayByte => classOf[ NBTArrayByte]
        case EnumNBTArrayShort => classOf[ NBTArrayShort]
        case EnumNBTArrayInt => classOf[ NBTArrayInt]
        case EnumNBTCompound => classOf[ NBTCompound]
        case EnumNBTList => classOf[NBTList]
        case _ => classOf[NBTNull]
    }

}