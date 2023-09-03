package dev.norbiros.denoriammo.listeners

import dev.norbiros.denoriammo.plugin
import dev.norbiros.denoriammo.utils.AsyncUtil
import dev.norbiros.denoriammo.utils.ConfigUtils
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.persistence.PersistentDataType

object PlayerListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val key = NamespacedKey(plugin, "join-command-executed")
        val player = event.player
        if (player.persistentDataContainer.getOrDefault(key, PersistentDataType.BOOLEAN, false)) return
        player.persistentDataContainer.set(key, PersistentDataType.BOOLEAN, true)
        AsyncUtil.runSyncLater(20) {
            player.performCommand(ConfigUtils.getFirstJoinCommand())
        }
    }
}