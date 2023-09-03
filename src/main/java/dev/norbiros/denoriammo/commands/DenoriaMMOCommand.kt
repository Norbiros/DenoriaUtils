package dev.norbiros.denoriammo.commands


import dev.norbiros.denoriammo.utils.ConfigUtils
import org.bukkit.entity.Player
import org.lupus.commands.core.annotations.general.NoPerm

@NoPerm
@Suppress("unused", "functionName")
class DenoriaMMOCommand {

    fun reload(player: Player): String {
        ConfigUtils.reload()
        return "<green>Config reloaded!"
    }
}