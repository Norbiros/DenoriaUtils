package dev.norbiros.denoriammo.utils

import dev.norbiros.denoriammo.plugin
import org.bukkit.scheduler.BukkitRunnable


object AsyncUtil {
    fun runAsync(fn: () -> Unit) {
        object : BukkitRunnable() {
            override fun run() {
                fn()
            }
        }.runTaskAsynchronously(plugin)
    }

    fun runAsyncLater(ticks: Long, fn: () -> Unit) {
        object : BukkitRunnable() {
            override fun run() {
                fn()
            }
        }.runTaskLaterAsynchronously(plugin, ticks)
    }

    fun runSync(fn: () -> Unit) {
        object : BukkitRunnable() {
            override fun run() {
                fn()
            }
        }.runTask(plugin)
    }

    fun runSyncLater(ticks: Long, fn: () -> Unit) {
        object : BukkitRunnable() {
            override fun run() {
                fn()
            }
        }.runTaskLater(plugin, ticks)
    }
}