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
package com.mcstarrysky.emotionsimulator.ingame.event

import com.mcstarrysky.emotionsimulator.EmotionConfig
import com.mcstarrysky.emotionsimulator.api.*
import com.mcstarrysky.emotionsimulator.api.addCount
import com.mcstarrysky.emotionsimulator.api.change
import com.mcstarrysky.emotionsimulator.api.inCd
import com.mcstarrysky.emotionsimulator.api.isCount
import org.bukkit.entity.EntityType
import org.bukkit.event.player.PlayerShearEntityEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.ingame.event.ListenerPlayerShear
 *
 * @author Mical
 * @since 2023/7/15 23:24
 */
object ListenerPlayerShear {

    @SubscribeEvent(priority = EventPriority.MONITOR, ignoreCancelled = false)
    fun e(e: PlayerShearEntityEvent) {
        if (e.isCancelled) return
        if (e.entity.type == EntityType.SHEEP) {
            if (e.player.inCd("sheep")) return
            val emotion = EmotionConfig.config.getString("animal.sheep") ?: return
            val (delta, time, count) = emotion.split("~", limit = 3).map { it.toDouble() }
            e.player.addCount("sheep")
            e.player.change(delta)
            if (e.player.isCount("sheep", count.toInt())) {
                e.player.setCd("sheep", time.toInt())
                e.player.resetCount("sheep")
            }
        }
    }
}