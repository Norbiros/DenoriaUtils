package dev.norbiros.denoriammo.integration.papi

import dev.norbiros.denoriammo.integration.papi.placeholders.TopNationBalancePlaceholder
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import dev.norbiros.denoriammo.plugin as DenoriaMMO

class PapiExpansion : PlaceholderExpansion() {

    override fun getIdentifier(): String {
        return DenoriaMMO.description.name
    }

    override fun getAuthor(): String {
        return DenoriaMMO.description.authors.first()
    }

    override fun getVersion(): String {
        return DenoriaMMO.description.version
    }

    override fun onRequest(player: OfflinePlayer, id: String): String? {
        return if (id.startsWith("nation_top_balance_"))
            TopNationBalancePlaceholder.process(player, id)
        else
            null
    }
}