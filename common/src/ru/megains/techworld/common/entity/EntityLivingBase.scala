package ru.megains.techworld.common.entity

abstract class EntityLivingBase(height: Float, wight: Float, levelView: Float) extends Entity(height, wight,levelView) {


    var newPosX = 0.0F

    var newPosY = 0.0F

    var newPosZ = 0.0F

    var newRotationYaw = 0.0F

    var newRotationPitch = 0.0F

    var newPosRotationIncrements = 0

    override def update(): Unit = {
        super.update()

        onLivingUpdate()

    }


   def  onLivingUpdate(): Unit ={

       if (newPosRotationIncrements > 0){
           val var1 = posX + (newPosX - posX) / newPosRotationIncrements.toFloat
           val var3 = posY + (newPosY - posY) / newPosRotationIncrements.toFloat
           val var5 = posZ + (newPosZ - posZ) / newPosRotationIncrements.toFloat
           val var7 = newRotationYaw - rotYaw
           rotYaw = (rotYaw.toDouble + var7 / newPosRotationIncrements.toFloat).toFloat
           rotPitch = (rotPitch.toDouble + (newRotationPitch - rotPitch) / newPosRotationIncrements.toFloat).toFloat
           newPosRotationIncrements -= 1
           setPosition(var1, var3, var5)
           setRotation(rotYaw, rotPitch)
       }
   }
    
    
    override def setPositionAndRotation2(x: Float, y: Float, z: Float, yaw: Float, pitch: Float, test: Int): Unit = {
       // yOffset = 0.0F
        newPosX = x
        newPosY = y
        newPosZ = z
        newRotationYaw = yaw
        newRotationPitch = pitch
        newPosRotationIncrements = test
    }
}
