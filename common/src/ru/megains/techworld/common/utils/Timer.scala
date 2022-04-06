package ru.megains.techworld.common.utils

class Timer(var ticksPerSecond:Int) {


    /**
     * The time reported by the high-resolution clock at the last call of updateTimer(), in seconds
     */
    private var lastHRTime = .0

    /**
     * How many full ticks have turned over since the last call to updateTimer(), capped at 10.
     */
    var elapsedTicks = 0

    /**
     * How much time has elapsed since the last tick, in ticks, for use by display rendering routines (range: 0.0 -
     * 1.0).  This field is frozen if the display is paused to eliminate jitter.
     */
    var renderPartialTicks = .0

    /**
     * A multiplier to make the timer (and therefore the game) go faster or slower.  0.5 makes the game run at half-
     * speed.
     */
    var timerSpeed = 1.0F

    /**
     * How much time has elapsed since the last tick, in ticks (range: 0.0 - 1.0).
     */
    var elapsedPartialTicks = .0

    /**
     * The time reported by the system clock at the last sync, in milliseconds
     */
    private var lastSyncSysClock = getTime

    /**
     * The time reported by the high-resolution clock at the last sync, in milliseconds
     */
    private var lastSyncHRClock = getTime
    private var field_74285_i = 0L

    /**
     * A ratio used to sync the high-resolution clock to the system clock, updated once per second
     */
    private var timeSyncAdjustment = 1.0D



    def updateTimer(): Unit = {
        val var1 = getTime
        val var3 = var1 - this.lastSyncSysClock
        val var5 = System.nanoTime / 1000000L
        val var7 = var5.toDouble / 1000.0D
        if (var3 <= 1000L && var3 >= 0L) {
            this.field_74285_i += var3
            if (this.field_74285_i > 1000L) {
                val var9 = var5 - this.lastSyncHRClock
                val var11 = this.field_74285_i.toDouble / var9.toDouble
                this.timeSyncAdjustment += (var11 - this.timeSyncAdjustment) * 0.20000000298023224D
                this.lastSyncHRClock = var5
                this.field_74285_i = 0L
            }
            if (this.field_74285_i < 0L) this.lastSyncHRClock = var5
        }
        else this.lastHRTime = var7
        this.lastSyncSysClock = var1
        var var13 = (var7 - this.lastHRTime) * this.timeSyncAdjustment
        this.lastHRTime = var7
        if (var13 < 0.0D) var13 = 0.0D
        if (var13 > 1.0D) var13 = 1.0D
        this.elapsedPartialTicks = (this.elapsedPartialTicks.toDouble + var13 * this.timerSpeed.toDouble * this.ticksPerSecond.toDouble).toFloat
        this.elapsedTicks = this.elapsedPartialTicks.toInt
        this.elapsedPartialTicks -= this.elapsedTicks.toFloat
        if (this.elapsedTicks > 10) this.elapsedTicks = 10
        this.renderPartialTicks = this.elapsedPartialTicks
    }




//    var lastLoopTime = .0
//    var tick = 0
//    var ellapsedTime = .0


//    def init(): Unit = {
//        lastLoopTime = getTime
//    }

    def getTime: Long = /*Sys.getTime * 1000L / Sys.getTimerResolution;*/ System.nanoTime     / 1000000000L


//    def update(): Unit = {
//        tick = Math.floor((getTime - lastLoopTime) * targetTick).toInt
//        lastLoopTime += (1f / targetTick) * tick
//        ellapsedTime = getTime
//        if (tick > 20) tick = 20
//    }
}
