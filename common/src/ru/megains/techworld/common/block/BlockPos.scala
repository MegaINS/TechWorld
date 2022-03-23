package ru.megains.techworld.common.block

import ru.megains.techworld.common.world.World

class BlockPos(val x:Int, val y:Int, val z:Int) {



    def sum(inX: Int, inY: Int, inZ: Int) = new BlockPos(inX + x, inY + y, inZ + z)

//    def isValid(world: World): Boolean =
//        !(
//             z < -world.width  ||
//             y < -world.height ||
//             x < -world.length
//         )&&
//        !(
//             z >  world.width  ||
//             y >  world.height ||
//             x >  world.length
//         )
}
