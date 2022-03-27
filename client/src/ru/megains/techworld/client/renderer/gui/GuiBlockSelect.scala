package ru.megains.techworld.client.renderer.gui

import ru.megains.techworld.client.renderer.text.Label
import ru.megains.techworld.common.utils.{RayTraceResult, RayTraceType}

class GuiBlockSelect() extends GuiScreen {

    var ray: RayTraceResult = RayTraceResult.VOID


    var name:Label = _
    var block_name:Label = _
    var block_side:Label = _
    var block_x:Label = _
    var block_y:Label = _
    var block_z:Label = _

    override def init(): Unit = {
        posY = 200
        posZ = 100
        name = new Label("Block select:")
        block_name = new Label(""){
            posY = 20
        }
        block_x = new Label(""){
            posY = 40
        }
        block_y = new Label(""){
            posY = 60
        }
        block_z = new Label(""){
            posY = 80
        }
        block_side = new Label(""){
            posY = 100
        }

        addChildren(name,block_name,block_side,block_x,block_y,block_z)
    }

    override def update(): Unit ={


        if (game.rayTrace != ray) {
            ray =game.rayTrace

            ray.traceType match {
                case RayTraceType.VOID =>
                    block_name.text = ""
                    block_x.text = ""
                    block_y.text = ""
                    block_z.text = ""
                    block_side.text = ""
                case RayTraceType.BLOCK =>
                    block_name.text = "name: "+ray.blockState.block.name
                    block_x.text = "x: "+ray.blockState.x.toString
                    block_y.text = "y: "+ray.blockState.y.toString
                    block_z.text = "z: "+ray.blockState.z.toString
                    block_side.text = "side: "+ray.sideHit.name
                case RayTraceType.ENTITY =>

            }


        }
    }

    override def resize( width:Int,height:Int): Unit ={

    }
}
