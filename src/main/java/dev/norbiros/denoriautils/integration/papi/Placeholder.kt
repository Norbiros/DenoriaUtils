package dev.norbiros.denoriautils.integration.papi

import org.bukkit.OfflinePlayer

interface Placeholder {
    /**
     * @param player the player to process the placeholder for
     * @param params the parameters to be passed to the placeholder
     * @return the value of the placeholder
     */
    fun process(player: OfflinePlayer, params: String): String?
}