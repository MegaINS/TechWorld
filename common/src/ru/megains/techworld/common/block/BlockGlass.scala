package ru.megains.techworld.common.block

class BlockGlass(name:String) extends Block(name) {


    override val blockType: BlockType.Value = BlockType.GLASS
    override def isOpaqueCube:Boolean = false
}
