package ru.megains.techworld.common.nbt.tag

import ru.megains.techworld.common.nbt.NBTType
import ru.megains.techworld.common.nbt.NBTType._
import ru.megains.techworld.common.nbt.tag.NBT.NBT
import ru.megains.techworld.common.nbt.tag.NBTBase._

import java.io.{DataInput, DataOutput}
import scala.collection.mutable.ArrayBuffer

class  NBTList private[nbt]() extends NBT{

    var listType: NBTType = EnumNBTNull
    val nbtList:ArrayBuffer[NBT] = ArrayBuffer[NBT]()
    private[nbt] def this(listTypeIn: NBTType){
        this()
        listType = listTypeIn
    }

    override val nbtType: NBTType = EnumNBTList

    def getNBT(id:Int): NBT = nbtList(id)

    def setNBT(values: NBT*): Unit = {
        values.foreach(value => if (listType == value.nbtType) nbtList += value)

    }

    def setValue(values: Any*): Unit ={
        values.foreach {
            case nbt: NBT => setNBT(nbt)
            case value => if (listType == NBTType.getType(value)) nbtList += NBTBase(value)
        }
    }

    def getLength:Int = nbtList.length

    override def write(output: DataOutput): Unit = {
        output.writeInt(nbtList.length)
        output.writeByte(listType.id)
        nbtList.foreach(_.write(output))
    }

    override def read(input: DataInput): Unit = {
        val length = input.readInt()
        listType = NBTType(input.readByte())
        for(_<-0 until length){
            var nbt = NBT(listType)
            nbt.read(input)
            nbtList += nbt
        }
    }

    def createCompound():NBTCompound ={
        val compound = new NBTCompound
        setNBT(compound)
        compound
    }

    def createList(nbtType:NBTType):NBTList = {
        val list =  new NBTList(nbtType)
        setNBT(list)
        list
    }

    def containsKey(key: Int,nbtType:NBTType):Boolean = if( key > -1 && key < nbtList.length) listType == nbtType  else false

    def getByte(key: Int):Byte = if(listType == EnumNBTByte) nbtList(key).asInstanceOf[NBTByte].data  else 0

    def getBoolean(key: Int):Boolean = if(containsKey(key,EnumNBTBoolean)) nbtList(key).asInstanceOf[NBTBoolean].data  else false

    def getShort(key: Int):Short = if(containsKey(key,EnumNBTShort)) nbtList(key).asInstanceOf[NBTShort].data  else 0

    def getInt(key: Int):Int = if(containsKey(key,EnumNBTInt)) nbtList(key).asInstanceOf[NBTInt].data  else 0

    def getLong(key: Int):Long = if(containsKey(key,EnumNBTLong)) nbtList(key).asInstanceOf[NBTLong].data  else 0

    def getFloat(key: Int):Float = if(containsKey(key,EnumNBTFloat)) nbtList(key).asInstanceOf[NBTFloat].data  else 0

    def getDouble(key: Int):Double = if(containsKey(key,EnumNBTDouble)) nbtList(key).asInstanceOf[NBTDouble].data  else 0

    def getString(key: Int):String = if(containsKey(key,EnumNBTString)) nbtList(key).asInstanceOf[NBTString].data  else ""

    def getArrayByte(key: Int):Array[Byte] = if(containsKey(key,EnumNBTArrayByte)) nbtList(key).asInstanceOf[NBTArrayByte].data  else Array[Byte]()

    def getArrayShort(key: Int):Array[Short] = if(containsKey(key,EnumNBTArrayShort)) nbtList(key).asInstanceOf[NBTArrayShort].data  else Array[Short]()

    def getArrayInt(key: Int):Array[Int] = if(containsKey(key,EnumNBTArrayInt)) nbtList(key).asInstanceOf[NBTArrayInt].data  else Array[Int]()

    def getCompound(key: Int):NBTCompound = if(containsKey(key,EnumNBTCompound)) nbtList(key).asInstanceOf[NBTCompound]  else new NBTCompound()

    def getList(key: Int):NBTList = if(containsKey(key,EnumNBTList)) nbtList(key).asInstanceOf[NBTList]  else new NBTList(EnumNBTNull)

    override def toString: String = s"NBTList(${nbtList.length})"
}
