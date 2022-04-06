package ru.megains.techworld.common.nbt.tag

import ru.megains.techworld.common.nbt.NBTType
import ru.megains.techworld.common.nbt.NBTType._
import ru.megains.techworld.common.nbt.tag.NBT.NBT
import ru.megains.techworld.common.nbt.tag.NBTBase._

import java.io.{DataInput, DataOutput}
import scala.collection.mutable

class NBTCompound private[nbt]() extends NBT{

   val nbtMap: mutable.HashMap[String, NBT] = new mutable.HashMap[String, NBT]

   def setNBT(key: String, value: NBT): Unit = nbtMap += key -> value

   def setValue(key: String, value: Any): Unit = {
       value match {
           case nbt:NBT =>setNBT(key, nbt)
           case _ => nbtMap += key -> NBTBase(value)
       }
   }

   def getNBT(key: String): NBT = nbtMap(key)

   def containsKey(key: String):Boolean = nbtMap.contains(key)

   def containsKey(key: String,nbtType:NBTType):Boolean = if( nbtMap.contains(key)) nbtMap(key).nbtType == nbtType  else false

   def getByte(key: String):Byte = if(containsKey(key,EnumNBTByte)) nbtMap(key).asInstanceOf[NBTByte].data  else 0

   def getBoolean(key: String):Boolean = if(containsKey(key,EnumNBTBoolean)) nbtMap(key).asInstanceOf[NBTBoolean].data  else false

   def getShort(key: String):Short = if(containsKey(key,EnumNBTShort)) nbtMap(key).asInstanceOf[NBTShort].data  else 0

   def getInt(key: String):Int = if(containsKey(key,EnumNBTInt)) nbtMap(key).asInstanceOf[NBTInt].data  else 0

   def getLong(key: String):Long = if(containsKey(key,EnumNBTLong)) nbtMap(key).asInstanceOf[NBTLong].data  else 0

   def getFloat(key: String):Float = if(containsKey(key,EnumNBTFloat)) nbtMap(key).asInstanceOf[NBTFloat].data  else 0

   def getDouble(key: String):Double = if(containsKey(key,EnumNBTDouble)) nbtMap(key).asInstanceOf[NBTDouble].data  else 0

   def getString(key: String):String = if(containsKey(key,EnumNBTString)) nbtMap(key).asInstanceOf[NBTString].data  else ""

   def getArrayByte(key: String):Array[Byte] = if(containsKey(key,EnumNBTArrayByte)) nbtMap(key).asInstanceOf[NBTArrayByte].data  else Array[Byte]()

    def getArrayShort(key: String):Array[Short] = if(containsKey(key,EnumNBTArrayShort)) nbtMap(key).asInstanceOf[NBTArrayShort].data  else Array[Short]()

   def getArrayInt(key: String):Array[Int] = if(containsKey(key,EnumNBTArrayInt)) nbtMap(key).asInstanceOf[NBTArrayInt].data  else Array[Int]()

   def getCompound(key: String):NBTCompound = if(containsKey(key,EnumNBTCompound)) nbtMap(key).asInstanceOf[NBTCompound]  else new NBTCompound()

   def getList(key: String):NBTList = if(containsKey(key,EnumNBTList)) nbtMap(key).asInstanceOf[NBTList]  else new NBTList(EnumNBTNull)

    def createCompound(key: String):NBTCompound ={
        val compound = new NBTCompound
        nbtMap += key -> compound
        compound
    }

    def createList(key: String,nbtType:NBTType):NBTList = {
        val list =  new NBTList(nbtType)
        nbtMap += key -> list
        list
    }

   private def writeNBT(name: String, data: NBT, output: DataOutput): Unit = {
       output.writeByte(data.nbtType.id)
       if (data.nbtType != NBTType.EnumNBTEnd) {
           output.writeUTF(name)
           data.write(output)
       }

   }
   private def readType(input: DataInput): NBTType =NBTType(input.readByte)
   private def readKey(input: DataInput): String = input.readUTF
   private def readNBT(nbtType:NBTType, key: String, input: DataInput): NBT = {
       val nbt: NBT = NBT(nbtType)
       nbt.read(input)
       nbt
   }


   override val nbtType:NBTType = EnumNBTCompound
   override def write(output: DataOutput): Unit = {
       nbtMap.foreach{ case (name,data)=>
           writeNBT(name, data, output)
       }

       writeNBT("END",NBTBase(),output)
   }
   override def read(input: DataInput): Unit = {
       nbtMap.clear()
       var nbtType:NBTType = readType(input)
       while (nbtType != EnumNBTEnd) {
           val s: String = readKey(input)
           val nbt: NBT = readNBT(nbtType, s, input)
           nbtMap.put(s, nbt)
           nbtType = readType(input)
       }
   }
   override def toString: String = "NBTCompound()"



}
