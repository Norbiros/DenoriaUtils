package dev.norbiros.denoriautils.integration.lands


import dev.norbiros.denoriautils.integration.PluginIntegration
import dev.norbiros.denoriautils.plugin
import me.angeschossen.lands.api.LandsIntegration
import me.angeschossen.lands.api.nation.Nation


object LandsIntegration : PluginIntegration {
    override val name: String
        get() = "Lands"

    private lateinit var landsIntegration: LandsIntegration

    override fun init() {
        landsIntegration = LandsIntegration.of(plugin)
    }

    fun getSortedNationsByBalance(): List<Nation> {
        return landsIntegration.nations.sortedBy { e -> e.balance }
    }
}

