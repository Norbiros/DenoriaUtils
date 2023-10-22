package dev.norbiros.denoriautils.utils

import dev.dejvokep.boostedyaml.YamlDocument
import dev.dejvokep.boostedyaml.dvs.Pattern
import dev.dejvokep.boostedyaml.dvs.segment.Segment
import dev.dejvokep.boostedyaml.dvs.versioning.AutomaticVersioning
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings
import dev.norbiros.denoriautils.logWarning
import dev.norbiros.denoriautils.plugin
import java.io.File
import java.io.IOException

object ConfigUtils {
    var config: YamlDocument? = null

    fun init(): Boolean {
        try {
            config = YamlDocument.create(
                File(plugin.dataFolder, "config.yml"),
                plugin.getResource("config.yml") ?: return false,
                GeneralSettings.DEFAULT,
                LoaderSettings.builder().setAutoUpdate(true).build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder().setVersioning(
                    AutomaticVersioning(
                        Pattern(
                            Segment.range(1, Integer.MAX_VALUE),
                            Segment.literal("."),
                            Segment.range(0, Integer.MAX_VALUE)
                        ), "config-version"
                    )
                ).build()
            )
        } catch (e: IOException) {
            logWarning("Unexpected exception while loading config: " + e.message)
            e.printStackTrace()
        }
        return validate()
    }

    fun reload() {
        config?.reload()
        validate()
    }

    private fun validate(): Boolean {
        var success = true
        if (getBrewery()["default"] == null) {
            logWarning("'default' value is not set in brewery!")
            success = false
        }
        if (config?.getString("brewery-profession-name") == null) {
            logWarning("'brewery-profession-name' field is removed")
            success = false
        }
        return success
    }

    fun getBrewery(): HashMap<String, String> {
        val brewery: HashMap<String, String> = hashMapOf()
        this.config?.getList("brewery")?.forEach {
            val obj = (it as LinkedHashMap<*, *>).entries.first()
            brewery[obj.key.toString()] = obj.value.toString()
        }
        return brewery
    }

    fun getBreweryProfessionName(): String {
        return config?.getString("brewery-profession-name") ?: ""
    }

    fun getFirstJoinCommand(): String {
        return config?.getString("first-join-command") ?: ""
    }

    fun getFirstJoinCommandStatus(): Boolean {
        return config?.getBoolean("enable-first-join-command") ?: false
    }
}