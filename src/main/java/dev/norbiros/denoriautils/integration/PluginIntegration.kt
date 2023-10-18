package dev.norbiros.denoriautils.integration

interface PluginIntegration {
    val name: String
    fun init()
    fun reload() {}
}