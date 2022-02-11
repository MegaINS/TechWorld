package ru.megains.techworld.common.network.packet

import io.netty.buffer.ByteBuf
import io.netty.util.ByteProcessor

import java.nio.channels.FileChannel
import java.nio.charset.Charset


class PacketBufferS(p_i45154_1: ByteBuf) extends PacketBuffer(p_i45154_1){


//
//    def writeItemUser(item: ItemUser): Unit = {
//        writeInt(item.id)
//        writeStringToBuffer(item.name)
//        writeStringToBuffer(item.img)
//
//        writeInt(item.itemBase. itemParams.size)
//        item.itemBase.itemParams.foreach{
//            case (param,value)=>
//                writeInt(param.id)
//                writeInt(value)
//        }
//        writeInt(item.amount)
//        writeByte(item.action.id)
//        writeByte(item.slot.id)
//    }


//
//    def writeItemBase(item: ItemBase): Unit = {
//        writeInt(item.id)
//        writeStringToBuffer(item.name)
//        writeStringToBuffer(item.img)
//
//        writeInt(item.itemParams.size)
//        item.itemParams.foreach{
//            case (param,value)=>
//                writeInt(param.id)
//                writeInt(value)
//        }
//    }
    override def isReadOnly: Boolean = ???

    override def asReadOnly(): ByteBuf = ???

    override def getShortLE(index: Int): Short = ???

    override def getUnsignedShortLE(index: Int): Int = ???

    override def getMediumLE(index: Int): Int = ???

    override def getUnsignedMediumLE(index: Int): Int = ???

    override def getIntLE(index: Int): Int = ???

    override def getUnsignedIntLE(index: Int): Long = ???

    override def getLongLE(index: Int): Long = ???

    override def getBytes(index: Int, out: FileChannel, position: Long, length: Int): Int = ???

    override def getCharSequence(index: Int, length: Int, charset: Charset): CharSequence = ???

    override def setShortLE(index: Int, value: Int): ByteBuf = ???

    override def setMediumLE(index: Int, value: Int): ByteBuf = ???

    override def setIntLE(index: Int, value: Int): ByteBuf = ???

    override def setLongLE(index: Int, value: Long): ByteBuf = ???

    override def setBytes(index: Int, in: FileChannel, position: Long, length: Int): Int = ???

    override def setCharSequence(index: Int, sequence: CharSequence, charset: Charset): Int = ???

    override def readShortLE(): Short = ???

    override def readUnsignedShortLE(): Int = ???

    override def readMediumLE(): Int = ???

    override def readUnsignedMediumLE(): Int = ???

    override def readIntLE(): Int = ???

    override def readUnsignedIntLE(): Long = ???

    override def readLongLE(): Long = ???

    override def readRetainedSlice(length: Int): ByteBuf = ???

    override def readCharSequence(length: Int, charset: Charset): CharSequence = ???

    override def readBytes(out: FileChannel, position: Long, length: Int): Int = ???

    override def writeShortLE(value: Int): ByteBuf = ???

    override def writeMediumLE(value: Int): ByteBuf = ???

    override def writeIntLE(value: Int): ByteBuf = ???

    override def writeLongLE(value: Long): ByteBuf = ???

    override def writeBytes(in: FileChannel, position: Long, length: Int): Int = ???

    override def writeCharSequence(sequence: CharSequence, charset: Charset): Int = ???

    override def forEachByte(processor: ByteProcessor): Int = ???

    override def forEachByte(index: Int, length: Int, processor: ByteProcessor): Int = ???

    override def forEachByteDesc(processor: ByteProcessor): Int = ???

    override def forEachByteDesc(index: Int, length: Int, processor: ByteProcessor): Int = ???

    override def retainedSlice(): ByteBuf = ???

    override def retainedSlice(index: Int, length: Int): ByteBuf = ???

    override def retainedDuplicate(): ByteBuf = ???
}
