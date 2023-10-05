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
package com.mcstarrysky.emotionsimulator.ingame.death

import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.common.platform.event.SubscribeEvent
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.ingame.death.DeathMessage
 *
 * @author Mical
 * @since 2023/7/16 16:10
 */
object DeathMessage {

    val death = ConcurrentHashMap<UUID, DeathCause>()

    /**
     * 自定义死亡信息
     */
    @SubscribeEvent
    fun e(e: PlayerDeathEvent) {
        val cause = death.remove(e.entity.uniqueId) ?: return
        e.deathMessage = cause.getMessage(e.entity)
    }
}