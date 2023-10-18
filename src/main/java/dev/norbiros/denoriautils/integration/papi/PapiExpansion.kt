package dev.norbiros.denoriautils.integration.papi

import dev.norbiros.denoriautils.integration.papi.placeholders.TopNationBalancePlaceholder
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import dev.norbiros.denoriautils.plugin as DenoriaUtils

class PapiExpansion : PlaceholderExpansion() {

    override fun getIdentifier(): String {
        return DenoriaUtils.pluginMeta.name
    }

    override fun getAuthor(): String {
        return DenoriaUtils.pluginMeta.authors.first()
    }

    override fun getVersion(): String {
        return DenoriaUtils.pluginMeta.version
    }

    override fun onRequest(player: OfflinePlayer, id: String): String? {
        return if (id.startsWith("nation_top_balance_"))
            TopNationBalancePlaceholder.process(player, id)
        else
            null
    }
}