package ru.megains.techworld.common.nbt

import scala.runtime.BoxedUnit


object NBTType extends Enumeration{

    type NBTType = Value
    val EnumNBTEnd,
        EnumNBTNull,
        EnumNBTCompound,
        EnumNBTList,
        EnumNBTBoolean,
        EnumNBTByte,
        EnumNBTShort,
        EnumNBTInt,
        EnumNBTLong,
        EnumNBTFloat,
        EnumNBTDouble,
        EnumNBTString,
        EnumNBTArrayByte,
        EnumNBTArrayShort,
        EnumNBTArrayInt = Value

    def getType(value:Any): NBTType = value match {
        case _:Byte => EnumNBTByte
        case _:Boolean => EnumNBTBoolean
        case _:Short => EnumNBTShort
        case _:Int => EnumNBTInt
        case _:Long => EnumNBTLong
        case _:Float => EnumNBTFloat
        case _:Double => EnumNBTDouble
        case _:String => EnumNBTString
        case _:Array[Byte] => EnumNBTArrayByte
        case _:Array[Short] => EnumNBTArrayShort
        case _:Array[Int] => EnumNBTArrayInt
        case _:BoxedUnit => EnumNBTEnd
        case _ => EnumNBTNull
    }
}

