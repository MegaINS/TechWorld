package ru.megains.techworld.client.network

import java.net.IDN
import java.util
import javax.naming.directory.{Attributes, DirContext, InitialDirContext}


class ServerAddress(ipAddress: String, serverPort: Int) {


    def getIP: String = try
        IDN.toASCII(this.ipAddress)

    catch {
        case var2: IllegalArgumentException =>
            ""
    }

    def getPort: Int = this.serverPort


}

object ServerAddress {
    def fromString(addrString: String): ServerAddress = if (addrString == null) null
    else {
        var astring: Array[String] = addrString.split(":")
        if (addrString.startsWith("[")) {
            val i: Int = addrString.indexOf("]")
            if (i > 0) {
                val s: String = addrString.substring(1, i)
                var s1: String = addrString.substring(i + 1).trim
                if (s1.startsWith(":") && !s1.isEmpty) {
                    s1 = s1.substring(1)
                    astring = Array[String](s, s1)
                }
                else astring = Array[String](s)
            }
        }
        if (astring.length > 2) astring = Array[String](addrString)
        var s2: String = astring(0)
        var j: Int = if (astring.length > 1) parseIntWithDefault(astring(1), 25565)
        else 25565
        if (j == 25565) {
            val astring1: Array[String] = getServerAddress(s2)
            s2 = astring1(0)
            j = parseIntWithDefault(astring1(1), 25565)
        }
        new ServerAddress(s2, j)
    }

    /**
     * Returns a server's address and port for the specified hostname, looking up the SRV record if possible
     */
    private def getServerAddress(name: String): Array[String] = {
        try {
            Class.forName("com.sun.jndi.dns.DnsContextFactory")
            val hashtable: util.Hashtable[String, String] = new util.Hashtable[String, String]
            hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory")
            hashtable.put("java.naming.provider.url", "dns:")
            hashtable.put("com.sun.jndi.dns.timeout.retries", "1")
            val dircontext: DirContext = new InitialDirContext(hashtable)
            val attributes: Attributes = dircontext.getAttributes("_minecraft._tcp." + name, Array[String]("SRV"))
            val astring: Array[String] = attributes.get("srv").get.toString.split(" ", 4)
            Array[String](astring(3), astring(2))
        } catch {
            case var6: Throwable =>
                Array[String](name, Integer.toString(25565))
        }
    }

    private def parseIntWithDefault(value: String, defaultValue: Int): Int = {
        try {
            value.trim.toInt
        }
        catch {
            case var3: Exception => {
                defaultValue
            }
        }
    }
}

