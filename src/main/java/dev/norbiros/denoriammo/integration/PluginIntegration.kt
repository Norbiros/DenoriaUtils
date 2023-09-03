package dev.norbiros.denoriammo.integration

interface PluginIntegration {
    val name: String
    fun init()
    fun reload() {}
}