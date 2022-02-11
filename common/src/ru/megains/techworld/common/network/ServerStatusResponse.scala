package ru.megains.techworld.common.network

import java.util.concurrent.Semaphore


class ServerStatusResponse {
    private var description: String = _
    private var version: ServerStatusResponse.Version = _
    private var favicon: String = _

    def getServerDescription: String = {
        description
    }

    def setServerDescription(descriptionIn: String): Unit =  {
        this.description = descriptionIn
        invalidateJson()
    }



    def getVersion: ServerStatusResponse.Version = {
        this.version
    }

    def setVersion(versionIn: ServerStatusResponse.Version) : Unit = {
        this.version = versionIn
        invalidateJson()
    }

    def setFavicon(faviconBlob: String): Unit =  {
        this.favicon = faviconBlob
        invalidateJson()
    }

    def getFavicon: String = {
        this.favicon
    }

    private val mutex: Semaphore = new Semaphore(1)
    private var json: String = _


    def invalidateJson(): Unit =  {
        this.json = null
    }
}

object ServerStatusResponse {



    class Version(val name: String, val protocol: Int) {
        def getName: String = this.name

        def getProtocol: Int = this.protocol
    }

}



