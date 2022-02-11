package ru.megains.techworld.client.network

class ServerData(val serverName: String, val serverIP: String, lanServer: Boolean) {


    var populationInfo: String = _
    /**
      * (better variable name would be 'hostname') server name as displayed in the server browser's second line (grey
      * text)
      */
    var serverMOTD: String = _
    /** last server ping that showed up in the server browser */
    var pingToServer: Long = -1L
    var version: Int = 210
    /** Game version for this server. */
    var gameVersion: String = "1.10.2"
    var pinged: Boolean = false
    var playerList: String = _
    private var serverIcon: String = _
    /** True if the server is a LAN server */







    /**
      * Returns the base-64 encoded representation of the server's icon, or null if not available
      */
    def getBase64EncodedIconData: String = this.serverIcon

    def setBase64EncodedIconData(icon: String) {
        this.serverIcon = icon
    }

    /**
      * Return true if the server is a LAN server
      */
    def isOnLAN: Boolean = this.lanServer

}
