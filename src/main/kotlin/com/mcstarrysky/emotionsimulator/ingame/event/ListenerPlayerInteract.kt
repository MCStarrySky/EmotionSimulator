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
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.player.PlayerInteractEntityEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.ingame.event.ListenerPlayerInteract
 *
 * @author Mical
 * @since 2023/7/15 23:31
 */
object ListenerPlayerInteract {

    @SubscribeEvent(priority = EventPriority.MONITOR, ignoreCancelled = false)
    fun e(e: PlayerInteractEntityEvent) {
        if (e.isCancelled) return
        if (e.rightClicked.type == EntityType.COW && e.player.itemInUse?.type == Material.BUCKET) {
            if (e.player.inCd("milk")) return
            val emotion = EmotionConfig.config.getString("animal.milk") ?: return
            val (delta, time, count) = emotion.split("~", limit = 3).map { it.toDouble() }
            e.player.addCount("milk")
            e.player.change(delta)
            if (e.player.isCount("milk", count.toInt())) {
                e.player.setCd("milk", time.toInt())
                e.player.resetCount("milk")
            }
        }
    }
}