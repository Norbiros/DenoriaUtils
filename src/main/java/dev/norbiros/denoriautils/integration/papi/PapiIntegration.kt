package dev.norbiros.denoriautils.integration.papi

import dev.norbiros.denoriautils.integration.PluginIntegration


object PapiIntegration : PluginIntegration {
    override val name: String
        get() = "PlaceholderAPI"

    override fun init() {
        PapiExpansion().register()
    }
}

