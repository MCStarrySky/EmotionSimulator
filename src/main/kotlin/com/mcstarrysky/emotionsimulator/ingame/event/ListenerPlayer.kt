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
import com.mcstarrysky.emotionsimulator.api.crazy
import com.mcstarrysky.emotionsimulator.api.emo
import com.mcstarrysky.emotionsimulator.api.isCrazy
import com.mcstarrysky.emotionsimulator.api.isEmo
import com.mcstarrysky.emotionsimulator.prettyInfo
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import java.nio.CharBuffer
import java.security.SecureRandom

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.ingame.event.ListenerPlayer
 *
 * @author Mical
 * @since 2023/7/15 12:48
 */
object ListenerPlayer {

    private val random = SecureRandom()

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        submit(delay = 10L) {
            when {
                e.player.isCrazy() -> {
                    e.player.crazy()
                }
                e.player.isEmo() -> {
                    e.player.emo()
                }
            }
        }
    }

    /**
     * 死亡后新的人生开启, 情绪值归零
     */
    @SubscribeEvent
    fun e(e: PlayerDeathEvent) {
        e.entity.set(EmotionConfig.config.getDouble("emotion.default"))
    }

    /**
     * 无法正常与人交流（发言时会将说的话替换为：#¥%#……¥%&%…&%*¥……*……）
     */
    @SubscribeEvent
    fun e(e: AsyncPlayerChatEvent) {
        val origin = e.message // 必须保留原消息
        if (e.player.isCrazy()) {
            prettyInfo("${e.player.name} 说: $origin")
            e.message = apply(origin)
        }
    }

    /*
     * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
     * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
     * @create 2020/04/02 01:27:02
     *
     * UntilTheEnd/UntilTheEnd/SanChattingProvider.java
     */
    private fun apply(source: String): String {
        val wrap = CharBuffer.wrap(source)
        val builder = StringBuilder()
        var insert = 0
        var wok = false
        while (wrap.hasRemaining()) {
            val next = wrap.get()
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    builder.append(next)
                } else {
                    builder.insert(insert, next)
                    if (wok) insert++
                }
            } else {
                if (random.nextBoolean()) {
                    wok = wok xor true
                }
                builder.insert(insert, (0xFF00 * random.nextDouble() + 0xFF).toInt().toChar())
                if (wok) insert++
            }
        }
        return builder.toString()
    }
}