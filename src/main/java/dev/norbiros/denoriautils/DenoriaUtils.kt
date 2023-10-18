package dev.norbiros.denoriautils

import dev.norbiros.denoriautils.integration.PluginIntegration
import dev.norbiros.denoriautils.integration.lands.LandsIntegration
import dev.norbiros.denoriautils.integration.mmocore.MMOCoreIntegration
import dev.norbiros.denoriautils.integration.papi.PapiIntegration
import dev.norbiros.denoriautils.listeners.InventoryListener
import dev.norbiros.denoriautils.listeners.PlayerListener
import dev.norbiros.denoriautils.utils.ConfigUtils
import org.bukkit.plugin.java.JavaPlugin
import org.lupus.commands.core.scanner.Scanner
import java.util.logging.Level

class DenoriaUtils : JavaPlugin() {

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
        Scanner(this).scan("dev.norbiros.denoriautils.commands", true)
        log("Finished loading commands")

        log("Successfully finished loading DenoriaUtils!")
    }

    override fun onDisable() {
        log("Disabled DenoriaUtils!")
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

lateinit var plugin: DenoriaUtils