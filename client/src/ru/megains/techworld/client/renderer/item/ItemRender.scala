package ru.megains.techworld.client.renderer.item

import ru.megains.techworld.client.renderer.mesh.Mesh

import scala.collection.mutable

class ItemRender() {
    val numberMeshMap = new mutable.HashMap[Int, Mesh]()
    val floatMeshMap = new mutable.HashMap[Float, Mesh]()







//    def getNumberMesh(number: Int): Mesh = {
//        numberMeshMap.getOrElse(number, default = {
//            val mesh = FontRender.createStringGui(number.toString, Color.BLACK, Fonts.timesNewRomanR)
//            numberMeshMap += number -> mesh
//            mesh
//        })
//    }
//    def getNumberMesh(number: Float): Mesh = {
//        floatMeshMap.getOrElse(number, default = {
//            val mesh = FontRender.createStringGui(number.toString, Color.BLACK, Fonts.timesNewRomanR)
//            floatMeshMap += number -> mesh
//            mesh
//        })
//    }
//    def drawFloat(x:Int,y:Int,number: Float): Unit = {
//        //Доделать
//       // val base:Int = number toInt
//       // val remain:Int = ((number - base) * 1000) toInt
//
//       val baseMesh = numberMeshMap.getOrElse(base, default = {
//            val mesh = FontRender.createStringGui(base.toString, Color.BLACK, Fonts.timesNewRomanR)
//            numberMeshMap += base -> mesh
//            mesh
//        })
//        val remainMesh = numberMeshMap.getOrElse(remain, default = {
//            val mesh = FontRender.createStringGui(remain.toString, Color.BLACK, Fonts.timesNewRomanR)
//            numberMeshMap += remain -> mesh
//            mesh
//        })
//    }
    //override def update(): Unit = {
//
//}
}
