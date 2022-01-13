package ru.megaims.techworld.common.utils



class Timer(var targetTick:Double) {

    var lastLoopTime = .0
    var tick = 0
    var ellapsedTime = .0


    def init(): Unit = {
        lastLoopTime = getTime
    }

    def getTime: Double = System.nanoTime / 1000000000.0


    def update(): Unit = {
        tick = Math.floor((getTime - lastLoopTime) * targetTick).toInt
        lastLoopTime += (1f / targetTick) * tick
        ellapsedTime = getTime
        if (tick > 20) tick = 20
    }
}
