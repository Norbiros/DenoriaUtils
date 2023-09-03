package dev.norbiros.denoriammo

import dev.norbiros.denoriammo.integration.PluginIntegration
import dev.norbiros.denoriammo.integration.lands.LandsIntegration
import dev.norbiros.denoriammo.integration.mmocore.MMOCoreIntegration
import dev.norbiros.denoriammo.integration.papi.PapiIntegration
import dev.norbiros.denoriammo.listeners.InventoryListener
import dev.norbiros.denoriammo.listeners.PlayerListener
import dev.norbiros.denoriammo.utils.ConfigUtils
import org.bukkit.plugin.java.JavaPlugin
import org.lupus.commands.core.scanner.Scanner
import java.util.logging.Level

class DenoriaMMO : JavaPlugin() {

    private val integrations: List<PluginIntegration> = listOf(
        PapiIntegration,
        MMOCoreIntegration,
        LandsIntegration
    )

    override fun onEnable() {
        plugin = this

        log("Loading config")
        if (!ConfigUtils.init()) {
            severeLog("Unexpected error while loading config! Disabling!")
            server.pluginManager.disablePlugin(this)
            return
        }
        log("Loaded config")

        log("Loading integrations...")
        for (integration in integrations) {
            log("Loading integration ${integration.name}...")
            integration.init()
            log("Finished loading ${integration.name}!")
        }
        log("Finished loading integrations!")

        log("Registering event listeners")
        server.pluginManager.registerEvents(InventoryListener, this)
        server.pluginManager.registerEvents(PlayerListener, this)
        log("Registered listeners!")

        log("Started loading commands...")
        Scanner(this).scan("dev.norbiros.denoriammo.commands", true)
        log("Finished loading commands")

        log("Successfully finished loading DenoriaMMO!")
    }

    override fun onDisable() {
        log("Disabled DenoriaMMO!")
    }
}

fun log(message: String) {
    plugin.logger.log(Level.INFO, message)
}

fun logWarning(message: String) {
    plugin.logger.log(Level.WARNING, message)
}

fun severeLog(message: String) {
    plugin.logger.log(Level.SEVERE, message)
}

lateinit var plugin: DenoriaMMO