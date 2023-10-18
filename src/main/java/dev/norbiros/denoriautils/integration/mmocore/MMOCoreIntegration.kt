package dev.norbiros.denoriautils.integration.mmocore


import dev.norbiros.denoriautils.integration.PluginIntegration
import dev.norbiros.denoriautils.utils.ConfigUtils
import net.Indyuce.mmocore.MMOCore
import net.Indyuce.mmocore.api.player.PlayerData
import net.Indyuce.mmocore.experience.EXPSource
import net.Indyuce.mmocore.experience.Profession
import org.bukkit.entity.Player


object MMOCoreIntegration : PluginIntegration {
    override val name: String
        get() = "MMOCore"

    var profession: Profession? = null

    override fun init() {
        profession = MMOCore.plugin.professionManager.get(ConfigUtils.getBreweryProfessionName())
    }

    override fun reload() {
        init()
    }

    fun addXp(player: Player, amount: Double) {
        profession?.giveExperience(PlayerData.get(player), amount, player.location, EXPSource.OTHER)
    }
}

