package dev.norbiros.denoriammo.listeners

import com.dre.brewery.Barrel
import com.dre.brewery.Brew
import com.dre.brewery.filedata.BConfig
import com.dre.brewery.lore.BrewLore
import dev.norbiros.denoriammo.integration.mmocore.MMOCoreIntegration
import dev.norbiros.denoriammo.plugin
import dev.norbiros.denoriammo.utils.AsyncUtil
import dev.norbiros.denoriammo.utils.ConfigUtils
import dev.norbiros.denoriammo.utils.FormulaParser
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.persistence.PersistentDataType
import kotlin.jvm.optionals.getOrNull


object InventoryListener : Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onInventoryClick(event: InventoryClickEvent) {
        val key = NamespacedKey(plugin, "gave-exp")
        if (event.inventory.type == InventoryType.BREWING) {
            if (event.slot > 2) {
                return
            }
        } else if (event.inventory.holder !is Barrel && event.inventory.holder !is org.bukkit.block.Barrel) {
            return
        }
        val item = event.getCurrentItem()
        if (item == null || item.type != Material.POTION || !item.hasItemMeta()) return
        if (item.itemMeta.persistentDataContainer.getOrDefault(key, PersistentDataType.BYTE, 0) == 1.toByte()) return

        val meta = item.itemMeta as PotionMeta
        val brew = Brew.get(meta) ?: return
        val lore = BrewLore(brew, meta)
        if (BrewLore.hasColorLore(meta)) {
            lore.convertLore(false)
        } else if (!BConfig.alwaysShowAlc && event.inventory.type === InventoryType.BREWING) {
            lore.updateAlc(false)
        } else {
            return
        }

        if (!lore.isBrewLore(0)) {
            return
        }

        val itemMeta = item.itemMeta
        itemMeta.persistentDataContainer.set(key, PersistentDataType.BYTE, 1)
        item.setItemMeta(itemMeta)

        AsyncUtil.runAsync {
            val defaultExpression = ConfigUtils.getBrewery()["default"] ?: "0"
            var expression = ConfigUtils.getBrewery()[brew.currentRecipe.optionalID.getOrNull()] ?: defaultExpression
            expression = expression.replace("%default%", defaultExpression)
            expression = expression.replace("%difficulty%", brew.currentRecipe.difficulty.toString())
            expression = expression.replace("%quality%", brew.quality.toString())
            val exp: Double = FormulaParser().eval(expression)

            AsyncUtil.runSync {
                MMOCoreIntegration.addXp(event.whoClicked as Player, exp)
            }
        }
    }
}