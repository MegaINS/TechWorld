package ru.megains.techworld.client.gui

import ru.megains.techworld.client.gui.element.MButton
import ru.megains.techworld.client.network.handler.NetHandlerLoginClient
import ru.megains.techworld.client.network.{NetworkManagerClient, ServerAddress, ServerData}
import ru.megains.techworld.client.renderer.text.Label
import ru.megains.techworld.common.network.{ConnectionState, NetworkManager}
import ru.megains.techworld.common.network.packet.handshake.client.CHandshake
import ru.megains.techworld.common.network.packet.login.client.CPacketLoginStart
import ru.megains.techworld.common.utils.Logger

import java.net.{InetAddress, UnknownHostException}

class GuiConnecting(previousScreen: GuiScreen, serverDataIn: ServerData) extends GuiScreen with Logger {


    val name = new Label("Connecting")
    val serveraddress: ServerAddress = new ServerAddress(serverDataIn.serverIP, 20000)
    var networkManager: NetworkManager = _
    var cancel = false
    var error: GuiScreen = _
    val buttonCancel: MButton = new MButton("Cancel", 300, 40, _ => {
        game.setScreen(previousScreen)
    })

    override def resize(width: Int, height: Int): Unit = {
        name.posX = (width - name.width) / 2
        name.posY = 200
        buttonCancel.posX = 500
        buttonCancel.posY = height - 70
    }

    override def init(): Unit = {
        addChildren(name, buttonCancel)
    }

    connect(serveraddress.getIP, serveraddress.getPort)

    private def connect(ip: String, port: Int): Unit = {
        log.info("Connecting to {}, {}", Array[AnyRef](ip, Integer.valueOf(port)))
        new Thread("Server Connector #" /* + CONNECTION_ID.incrementAndGet*/) {
            override def run(): Unit = {
                var inetaddress: InetAddress = null
                try {
                    if (cancel) {
                        return
                    }

                    inetaddress = InetAddress.getByName(ip)
                    networkManager = NetworkManagerClient.createNetworkManagerAndConnect(inetaddress, port, game.packetProcessHandler)
                    networkManager.setNetHandler(new NetHandlerLoginClient(networkManager, previousScreen))
                    networkManager.sendPacket(new CHandshake(210, ip, port, ConnectionState.LOGIN))
                    networkManager.sendPacket(new CPacketLoginStart(game.playerName))
                    game.networkManager = networkManager
                }
                catch {
                    case unknownhostexception: UnknownHostException =>
                        if (cancel) {
                            return
                        }
                        log.error("Couldn\'t connect to server", unknownhostexception.asInstanceOf[Throwable])

                        error = new GuiDisconnected(previousScreen, "connect.failed", "Unknown host")
                    case exception: Exception =>
                        if (cancel) {
                            return
                        }
                        log.error("Couldn\'t connect to server", exception.asInstanceOf[Throwable])
                        var s: String = exception.toString
                        if (inetaddress != null) {
                            val s1: String = inetaddress + ":" + port
                            s = s.replaceAll(s1, "")
                        }
                        error = new GuiDisconnected(previousScreen,  "connect.failed", s)
                }
            }
        }.start()
    }


    override def update(): Unit = {
        super.update()
        if (error != null) {
            game.setScreen(error)
        }
    }


}
