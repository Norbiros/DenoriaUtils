package dev.norbiros.denoriammo.integration.papi

import dev.norbiros.denoriammo.integration.PluginIntegration


object PapiIntegration : PluginIntegration {
    override val name: String
        get() = "PlaceholderAPI"

    override fun init() {
        PapiExpansion().register()
    }
}

