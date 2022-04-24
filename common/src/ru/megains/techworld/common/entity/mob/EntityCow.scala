package ru.megains.techworld.common.entity.mob

import ru.megains.techworld.common.entity.EntityLivingBase
import ru.megains.techworld.common.utils.Logger
import ru.megains.techworld.common.world.World

import scala.util.Random


class EntityCow(worldIn: World) extends EntityLivingBase(1.3f, 0.9f, 1.2f) with Logger{

    world = worldIn


    speed = 0.2f
    var x = 0
    var t = 20


    override def update(): Unit = {
        super.update()

       if(world.isServer) {
           x += 1
           if (x % t == 0) {
               //t = Random.nextInt(20)+1
               moveForward = 1 //if(Random.nextFloat()>0.5) 1 else 0
               // zo = if(Random.nextFloat()>0.5) 1 else 0
               //            if (onGround) {
               //                motionY = if (Random.nextFloat() < 0.2) 0.42f else 0
               //            }

               //if(x % 100 == 0){
               //turn(100, 0)
               // }
           }
           calculateMotion(moveForward, moveStrafing, if (onGround) 0.04f else 0.02f)
       }


       // log.info(motionZ.toString)
        move(motionX, motionY, motionZ)

        // log.info(motionZ + " " +posX)


        if(world.isServer){
            motionX *= 0.8f
            if (motionY > 0.0f) {
                motionY *= 0.90f
            }
            else {
                motionY *= 0.98f
            }
            motionZ *= 0.8f
            motionY -= 0.04f
            if (onGround) {
                motionX *= 0.9f
                motionZ *= 0.9f
            }
        }

    }



}
