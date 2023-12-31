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
import com.mcstarrysky.emotionsimulator.api.emo
import com.mcstarrysky.emotionsimulator.api.event.EmotionChangeEvent
import com.mcstarrysky.emotionsimulator.api.getEmotion
import com.mcstarrysky.emotionsimulator.api.isCrazy
import com.mcstarrysky.emotionsimulator.api.isEmo
import com.mcstarrysky.emotionsimulator.ingame.death.DeathCause
import com.mcstarrysky.emotionsimulator.ingame.death.DeathMessage
import org.bukkit.GameMode
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.kill
import taboolib.platform.util.title

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.ingame.event.ListenerEmotion
 *
 * @author Mical
 * @since 2023/7/15 12:47
 */
object ListenerEmotion {

    @SubscribeEvent(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun e(e: EmotionChangeEvent) {
        if (e.player.gameMode == GameMode.CREATIVE) {
            e.isCancelled = true
            return
        }
        val now = e.player.getEmotion()
        val min = EmotionConfig.config.getDouble("emotion.min")
        val max = EmotionConfig.config.getDouble("emotion.max")
        val emo = EmotionConfig.config.getDouble("emotion.emo")
        val crazy = EmotionConfig.config.getDouble("emotion.crazy")
        when {
            // 抑郁致死
            now + e.delta < min -> {
                DeathMessage.death[e.player.uniqueId] = DeathCause.EMO
                e.player.kill()
                e.isCancelled = true
            //    e.player.prettyInfo("你抑郁致死!")
                e.player.set(emo + 1.0)
            }
            // 进入抑郁/创伤状态
            now + e.delta < emo -> {
                if (!e.player.isEmo()) {
                    e.player.emo()
                //    e.player.prettyInfo("你抑郁了!")
                    e.player.title("§c你抑郁了!", "§7请尽快平稳情绪")
                }
            }
            // 恢复到正常时
            now + e.delta in emo..crazy  -> {
                if (!e.player.isNormal()) {
                    e.player.normal()
                 //   e.player.prettyInfo("你恢复正常!")
                }
            }
            // 狂躁致死
            now + e.delta > max -> {
                DeathMessage.death[e.player.uniqueId] = DeathCause.CRAZY
                e.player.kill()
                e.isCancelled = true
               // e.player.prettyInfo("你疯死了!")
                e.player.set(crazy - 1.0)
            }
            // 进入狂躁/疯癫状态
            now + e.delta > crazy -> {
                if (!e.player.isCrazy()) {
                    e.player.crazy()
                    e.player.title("§c你很狂躁!", "§7请尽快平稳情绪")
                //    e.player.prettyInfo("你疯了!")
                }
            }
        }
    }
}