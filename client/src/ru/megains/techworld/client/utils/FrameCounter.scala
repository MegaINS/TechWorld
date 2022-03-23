package ru.megains.techworld.client.utils

object FrameCounter {

    var lastTime: Long = 0
    private var _frames:Int = 0
    private var _lastFrames:Int = 0
    private var _tick:Int = 0


    def start(): Unit ={
        lastTime = System.currentTimeMillis
    }

    def step(time:Long): Unit ={
        lastTime += time
        _lastFrames =_frames
        _frames = 0
        _tick = 0

    }

    def isTimePassed(value:Long): Boolean ={
        System.currentTimeMillis >= FrameCounter.lastTime + value
    }

    def gameUpdate(): Unit ={
        _tick += 1
    }

    def gameRender(): Unit ={
        _frames += 1
    }

    def frames:Int = _frames
    def tick:Int = _tick
    def lastFrames:Int = _lastFrames

}
