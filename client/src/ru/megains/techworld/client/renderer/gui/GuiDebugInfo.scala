package ru.megains.techworld.client.renderer.gui

import ru.megains.techworld.client.renderer.shader.data.Shader
import ru.megains.techworld.client.renderer.text.Label
import ru.megains.techworld.client.renderer.world.ChunkRenderer
import ru.megains.techworld.client.utils.FrameCounter
import ru.megains.techworld.common.entity.{EntityPlayer, GameType}
import ru.megains.techworld.common.utils.Direction

class GuiDebugInfo() extends GuiScreen {

    var name: Label = _
    var position: Label = _
    var position_x: Label = _
    var position_y: Label = _
    var position_z: Label = _
    var position_yaw: Label = _
    var position_pitch: Label = _
    var position_side: Label = _
    var player_gameType: Label = _
    var fps: Label = _
    var memory: Label = _
    var chunkUpdate: Label = _
    var chunkRender: Label = _
    var blockRender: Label = _
    var renderRangeH: Label = _
    var renderRangeV: Label = _


    var player: EntityPlayer = _

    var x: Float = Float.MinValue
    var y: Float = Float.MinValue
    var z: Float = Float.MinValue
    var yaw: Float = Float.MinValue
    var pitch: Float = Float.MinValue
    var side: Direction = Direction.NONE
    var gameType:GameType = GameType.NOT_SET
    var tickI: Int = 20

    override def init(): Unit = {
        val height = game.window.height

        name = new Label("Name: ")

        position = new Label("Position:") {
            posY = 20
        }
        position_x = new Label("x:") {
            posY = 40
        }
        position_y = new Label("y:") {
            posY = 60
        }
        position_z = new Label("z:") {
            posY = 80
        }
        position_yaw = new Label("yaw:") {
            posY = 100
        }
        position_pitch = new Label("pitch:") {
            posY = 120
        }
        position_side = new Label("side:") {
            posY = 140
        }

        player_gameType = new Label("Game Type:") {
            posY = 160
        }
        renderRangeH = new Label("renderRangeH: " + game.settings.RENDER_DISTANCE_WIDTH) {
            posY = 180
        }
        renderRangeV = new Label("renderRangeV: " + game.settings.RENDER_DISTANCE_HEIGHT) {
            posY = 200
        }


        memory = new Label("Memory use:") {
            posY = height - 30
        }

        fps = new Label("FPS:") {
            posY = height - 50
        }

        chunkUpdate = new Label("Chunk update:") {
            posY = height - 70
        }

        chunkRender = new Label("Chunk render:") {
            posY = height - 90
        }

        blockRender = new Label("Block render:") {
            posY = height - 110
        }


        addChildren(name, memory, fps,player_gameType, position, position_x, position_y, position_z, position_yaw, position_pitch, position_side, renderRangeH, renderRangeV, chunkUpdate, chunkRender, blockRender)


    }

    var id = -1

    override def update(): Unit = {
        player = game.player

        if (player.id != x) {
            id = player.id
            name.text = s"Name: ${game.playerName} $id"
        }

        if (player.posX != x) {
            x = player.posX
            position_x.text = "x: " + player.posX
        }
        if (player.posY != y) {
            y = player.posY
            position_y.text = ("y: " + player.posY)
        }
        if (player.posZ != z) {
            z = player.posZ
            position_z.text = ("z: " + player.posZ)
        }
        if (player.rotYaw != yaw) {
            yaw = player.rotYaw
            position_yaw.text = ("yaw: " + player.rotYaw)
        }
        if (player.rotPitch != pitch) {
            pitch = player.rotPitch
            position_pitch.text = ("pitch: " + player.rotPitch)
        }
        if (player.side != side) {
            side = player.side
            position_side.text = ("side: " + side)
        }
        if (player.gameType != gameType) {
            gameType = player.gameType
            player_gameType.text = ("Game Type: " + gameType)
        }

        tickI += 1
        if (tickI > 19) {
            tickI = 0
            val usedBytes: Long = (Runtime.getRuntime.totalMemory - Runtime.getRuntime.freeMemory) / 1048576
            fps.text = ("FPS: " + FrameCounter.lastFrames)
            memory.text = ("Memory use: " + usedBytes + "/" + Runtime.getRuntime.totalMemory / 1048576 + "MB")

            chunkUpdate.text = ("Chunk update: " + ChunkRenderer.chunkUpdateLast)

            chunkRender.text = ("Chunk render: " + ChunkRenderer.chunkRenderLast / (if (FrameCounter.lastFrames == 0) 1 else FrameCounter.lastFrames))

            blockRender.text = ("Block render: " + ChunkRenderer.blockRenderLast / (if (FrameCounter.lastFrames == 0) 1 else FrameCounter.lastFrames))

        }


    }

    override def render(shader: Shader): Unit = {
        super.render(shader)

    }

    override def resize(width: Int, height: Int): Unit = {
        val height = game.window.height
        memory.posY = height - 30
        fps.posY = height - 50
        chunkUpdate.posY = height - 70
        chunkRender.posY = height - 90
        blockRender.posY = height - 110

    }

}
