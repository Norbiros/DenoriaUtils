package dev.norbiros.denoriammo.integration.lands


import dev.norbiros.denoriammo.integration.PluginIntegration
import dev.norbiros.denoriammo.plugin
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

