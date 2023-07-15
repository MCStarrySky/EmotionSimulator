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
package com.mcstarrysky.emotionsimulator.api

import com.mcstarrysky.emotionsimulator.EmotionConfig
import com.mcstarrysky.emotionsimulator.api.event.EmotionChangeEvent
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import taboolib.expansion.getDataContainer

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.api.EmotionAPI
 *
 * @author Mical
 * @since 2023/7/15 12:49
 */
object EmotionAPI {

    /**
     * 改变玩家的情绪值
     */
    fun change(player: Player, delta: Double, set: Boolean = false) {
        val emoAdd = EmotionConfig.config.getDouble("emotion.emoAdd")
        val emoTake = EmotionConfig.config.getDouble("emotion.emoTake")
        val crazyAdd = EmotionConfig.config.getDouble("emotion.crazyAdd")
        val crazyTake = EmotionConfig.config.getDouble("emotion.crazyTake")
        val deltaDouble = if (set) delta else when {
            isEmo(player) -> {
                if (delta > 0) delta * emoAdd else delta * emoTake
            }
            isCrazy(player) -> {
                if (delta > 0) delta * crazyAdd else delta * crazyTake
            }
            else -> delta
        }

        if (EmotionChangeEvent(player, deltaDouble).call()) {
            player.getDataContainer()["emotion"] = (player.getDataContainer()["emotion"]?.toDoubleOrNull() ?: EmotionConfig.config.getDouble("emotion.default").also {
                player.getDataContainer()["emotion"] = it
            }) + deltaDouble
        }
    }

    /**
     * 直接设置玩家的情绪值
     */
    fun set(player: Player, to: Double) {
        change(player, to - getEmotion(player), true)
    }

    /**
     * 获取玩家的情绪值
     */
    fun getEmotion(player: Player): Double {
        return player.getDataContainer()["emotion"]?.toDoubleOrNull() ?: EmotionConfig.config.getDouble("emotion.default").also {
            player.getDataContainer()["emotion"] = it
        }
    }

    /**
     * 玩家是否处于抑郁/创伤状态
     */
    fun isEmo(player: Player): Boolean {
        return player.getEmotion() <= EmotionConfig.config.getDouble("emotion.emo")
    }

    /**
     * 玩家是否处于狂躁/疯癫状态
     */
    fun isCrazy(player: Player): Boolean {
        return player.getEmotion() >= EmotionConfig.config.getDouble("emotion.crazy")
    }

    /**
     * 玩家是否处于正常状态
     */
    fun isNormal(player: Player): Boolean {
        return !isEmo(player) && !isCrazy(player)
    }

    /**
     * 使玩家处于抑郁/创伤状态
     */
    fun emo(player: Player) {
        player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, -1, 1, false, false, true)) // 失明
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, -1, 1, false, false, true)) // 缓慢
        player.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, -1, 1, false, false, true)) // 饥饿
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_DIGGING, -1, 1, false, false, true)) // 挖掘疲劳
        player.addPotionEffect(PotionEffect(PotionEffectType.WEAKNESS, -1, 1, false, false, true)) // 虚弱
        player.addPotionEffect(PotionEffect(PotionEffectType.UNLUCK, -1, 1, false, false, true)) // 霉运
    }

    /**
     * 使玩家处于狂躁/疯癫状态
     */
    fun crazy(player: Player) {
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, -1, 1, false, false, true)) // 移动速度提高
        player.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, -1, 2, false, false, true)) // 饥饿值消耗大幅提高
    }

    /**
     * 使玩家恢复正常, 清除负面效果
     */
    fun normal(player: Player) {
        PotionEffectType.values().forEach { player.removePotionEffect(it) }
    }
}

internal fun Player.change(delta: Double) = EmotionAPI.change(this, delta)

internal fun Player.getEmotion(): Double = EmotionAPI.getEmotion(this)

internal fun Player.set(to: Double) = EmotionAPI.set(this, to)

internal fun Player.isEmo(): Boolean = EmotionAPI.isEmo(this)

internal fun Player.isCrazy(): Boolean = EmotionAPI.isCrazy(this)

internal fun Player.isNormal(): Boolean = EmotionAPI.isNormal(this)

internal fun Player.emo() = EmotionAPI.emo(this)

internal fun Player.crazy() = EmotionAPI.crazy(this)

internal fun Player.normal() = EmotionAPI.normal(this)