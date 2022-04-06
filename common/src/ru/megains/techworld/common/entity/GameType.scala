package ru.megains.techworld.common.entity


sealed abstract class GameType private(val id: Int, val name: String, val shortName: String) {

    /*
            def configurePlayerCapabilities(capabilities: PlayerCapabilities) {
                if (this eq GameType.CREATIVE) {
                    capabilities.allowFlying = true
                    capabilities.isCreativeMode = true
                    capabilities.disableDamage = true
                }
                else if (this eq GameType.SPECTATOR) {
                    capabilities.allowFlying = true
                    capabilities.isCreativeMode = false
                    capabilities.disableDamage = true
                    capabilities.isFlying = true
                }
                else {
                    capabilities.allowFlying = false
                    capabilities.isCreativeMode = false
                    capabilities.disableDamage = false
                    capabilities.isFlying = false
                }
                capabilities.allowEdit = !this.isAdventure
            }
    */



    def isCreative: Boolean = this == GameType.CREATIVE

    def isSurvival: Boolean = this == GameType.SURVIVAL

    def isSpectator: Boolean = this == GameType.SPECTATOR
}


object GameType {
    def next(id: Int): GameType = {
        if(id==2){
            GameType(0)
        }else{
            GameType(id+1)
        }
    }


    def apply(shortName: String): GameType = {
        shortName match {
            case "cr" => CREATIVE
            case "su" => SURVIVAL
            case "sp" => SPECTATOR
            case _ => NOT_SET
        }
    }

    def apply(id: Int): GameType = {
        id match {
            case 2 => SPECTATOR
            case 1 => CREATIVE
            case 0 => SURVIVAL

            case _ => NOT_SET
        }
    }


    case object NOT_SET extends GameType(-1, "not_set", "")

    case object SURVIVAL extends GameType(0, "survival", "su")

    case object CREATIVE extends GameType(1, "creative", "cr")

    case object SPECTATOR extends GameType(2, "spectator", "sp")


}

