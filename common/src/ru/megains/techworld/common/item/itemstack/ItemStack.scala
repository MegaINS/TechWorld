package ru.megains.techworld.common.item.itemstack

import ru.megains.techworld.common.item.{Item, ItemType}


class ItemStack private(val item: Item, var stackSize:Int, var stackMass:Int) {

    //def this(block: Block) = this(Item.getItemFromBlock(block), 1, block.mass)

  //  def this(block: Block, size:Int) = this(Item.getItemFromBlock(block), size,block.mass * size)

    def this(itemStack: ItemStack) = this(itemStack.item,itemStack.stackSize,itemStack.stackMass)

    def this(item: Item) = this(item, 1,item.mass)

    def this(item: Item,sizeOrMass:Int) ={
        this(item,
            item.itemType match {
                case ItemType.STACK => sizeOrMass
                case ItemType.MASS | ItemType.SINGLE => 1
            },
            item.itemType match {
                case ItemType.STACK => item.mass * sizeOrMass
                case ItemType.MASS => sizeOrMass
                case ItemType.SINGLE => 1
            })
    }

    def splitStack(size: Int): ItemStack = {

        item.itemType match {
            case ItemType.STACK | ItemType.SINGLE  =>
                stackSize -= size
                stackMass -= item.mass * size
                new ItemStack(item, size)
            case ItemType.MASS =>
                stackMass -= item.mass * size
                new ItemStack(item, size)
        }
    }

//    def onItemUse(playerIn: EntityPlayer, worldIn: World, pos: BlockState, side: BlockDirection, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {
//
//        val enumactionresult: EnumActionResult = item.onItemUse(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ)
//        enumactionresult
//    }


    def canEqual(other: Any): Boolean = other.isInstanceOf[ItemStack]

    override def equals(other: Any): Boolean = other match {
        case that: ItemStack =>
            (that canEqual this) &&
                    item == that.item &&
                    stackSize == that.stackSize &&
                    stackMass == that.stackMass
        case _ => false
    }

    override def hashCode(): Int = {
        val state = Seq(item, stackSize, stackMass)
        state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
}
