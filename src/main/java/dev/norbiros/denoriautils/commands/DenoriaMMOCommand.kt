package dev.norbiros.denoriautils.commands


import dev.norbiros.denoriautils.utils.ConfigUtils
import org.bukkit.entity.Player
import org.lupus.commands.core.annotations.general.NoPerm

@NoPerm
@Suppress("unused", "functionName")
class DenoriaUtilsCommand {

    fun reload(player: Player): String {
        ConfigUtils.reload()
        return "<green>Config reloaded!"
    }
}