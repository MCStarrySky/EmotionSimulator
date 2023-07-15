/*
 *  Copyright (C) <2023>  <Mical>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.mcstarrysky.emotionsimulator.data

import com.mcstarrysky.emotionsimulator.EmotionConfig
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.expansion.releaseDataContainer
import taboolib.expansion.setupDataContainer
import taboolib.expansion.setupPlayerDatabase
import taboolib.platform.util.onlinePlayers

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.data.PluginDatabase
 *
 * @author Mical
 * @since 2023/7/15 12:31
 */
object PluginDatabase {

    fun initialize() {
        setupPlayerDatabase(EmotionConfig.database)
        onlinePlayers.forEach { it.setupDataContainer() }
    }

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        e.player.setupDataContainer()
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        e.player.releaseDataContainer()
    }
}