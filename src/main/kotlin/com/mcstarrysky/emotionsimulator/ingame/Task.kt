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
package com.mcstarrysky.emotionsimulator.ingame

import com.mcstarrysky.emotionsimulator.EmotionConfig
import com.mcstarrysky.emotionsimulator.api.change
import org.bukkit.GameMode
import org.bukkit.attribute.Attribute
import taboolib.common.platform.function.submit
import taboolib.common.util.sync
import taboolib.platform.util.onlinePlayers
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.ingame.Task
 *
 * @author Mical
 * @since 2023/7/15 12:30
 */
object Task {

    private val dataMap = ConcurrentHashMap<UUID, Pair<Int, Int>>()

    fun initialize() {
        submit(
            async = true,
            period = 1L
        ) {
            runCatching {
                for (it in onlinePlayers) {
                    if (it.gameMode == GameMode.CREATIVE) continue
                    var (healTick, unHealTick) = dataMap.computeIfAbsent(it.uniqueId) { 0 to 0 }
                    if (it.health == (it.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: it.maxHealth) && it.foodLevel == 20) {
                        unHealTick = 0
                        healTick++
                        if (healTick >= EmotionConfig.config.getInt("heal.time")) {
                            sync { it.change(EmotionConfig.config.getDouble("heal.per-tick-add")) }
                        }
                    } else {
                        healTick = 0
                        unHealTick++
                        if (unHealTick >= EmotionConfig.config.getInt("un-heal.time")) {
                            sync { it.change(EmotionConfig.config.getDouble("un-heal.per-tick-add")) }
                        }
                    }
                    dataMap[it.uniqueId] = healTick to unHealTick
                }
            }
        }
    }
}