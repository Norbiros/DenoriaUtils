package dev.norbiros.denoriautils.integration.papi.placeholders

import dev.norbiros.denoriautils.integration.lands.LandsIntegration
import dev.norbiros.denoriautils.integration.papi.Placeholder
import org.bukkit.OfflinePlayer

object TopNationBalancePlaceholder : Placeholder {

    override fun process(player: OfflinePlayer, params: String): String {
        val nations = LandsIntegration.getSortedNationsByBalance()
        val index = params.split("_")[3].toIntOrNull() ?: return "Index must be integer"
        if (index >= nations.size) return "$0"
        return nations[index].balanceDisplay.replace("\u00a0", " ")
    }
}