package ru.megains.techworld.common.nbt

import ru.megains.techworld.common.nbt.NBTType._
import ru.megains.techworld.common.nbt.tag.NBT.NBT
import ru.megains.techworld.common.nbt.tag.{NBT, NBTCompound, NBTList}

import java.io.{DataInput, DataInputStream, DataOutput, DataOutputStream}
import scala.reflect.io.{Directory, File, Path}


object NBTData{

    val VERSION_NBT:String = "NBT v0.5"
    val FILE_EXTENSION:String = ".nbt"


    def readOfFile(directory: Directory,fileName:String): NBTData ={
        if(directory.exists){
            val file:File = directory / Path(fileName+FILE_EXTENSION).toFile
            val input = new DataInputStream(file.inputStream())
            readOfStream(input)
        }else{
            new NBTData()
        }
    }

    def readOfStream(input: DataInputStream): NBTData ={
        val nbtData = new NBTData()
        val version = input.readUTF()
        if(VERSION_NBT != version){
            throw new Exception("Version NBT -> "+VERSION_NBT +", file -> "+ version)
        }
        nbtData.read(input)
        nbtData
    }

    def writeToFile(data:NBT,directory: Directory,fileName:String): Unit ={
        directory.createDirectory()
        val file:File = directory / Path(fileName+FILE_EXTENSION).toFile
        val output = new DataOutputStream(file.outputStream())
        writeToStream(data,output)
        output.close()
    }

    def writeToStream(data:NBT,output: DataOutputStream): Unit ={
        data match {
            case _:NBTCompound | _:NBTList  =>

            case _ => throw new Exception("NBT Class must be NBTCompound or NBTList")
        }
        output.writeUTF(VERSION_NBT)
        new NBTData(data).write(output)
    }

    def createCompound():NBTCompound = new NBTCompound

    def createList(nbtType:NBTType):NBTList = new NBTList(nbtType)

    private[nbt] class NBTData() {
        var nbt:NBT = _
        var nbtType:NBTType = EnumNBTNull

        def this(nbtIn:NBT){
            this()
            nbt = nbtIn
            nbtType = nbt.nbtType
        }

        def getList:NBTList = if(isList) nbt.asInstanceOf[NBTList] else new NBTList(EnumNBTList)
        def getCompound:NBTCompound = if(isCompound) nbt.asInstanceOf[NBTCompound] else new NBTCompound()
        def isList:Boolean = nbtType == EnumNBTEnd
        def isCompound:Boolean = nbtType == EnumNBTCompound
        def isNull:Boolean = nbtType == EnumNBTNull

        def read(input: DataInput): Unit = {
            nbtType = NBTType(input.readByte())
            nbt = NBT(nbtType)
            nbt.read(input)
        }
        def write(output: DataOutput): Unit = {
            output.writeByte(nbtType.id)
            nbt.write(output)
        }

    }
}

