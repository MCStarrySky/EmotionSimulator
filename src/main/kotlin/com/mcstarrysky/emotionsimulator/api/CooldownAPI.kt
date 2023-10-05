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

import com.mcstarrysky.emotionsimulator.data.PluginDatabase.get
import com.mcstarrysky.emotionsimulator.data.PluginDatabase.insert
import org.bukkit.entity.Player

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.api.CooldownAPI
 *
 * @author Mical
 * @since 2023/7/15 21:59
 */
object CooldownAPI {

    /**
     * 获取已经触发的次数
     */
    fun getCount(player: Player, type: String): Int {
        return player.get(type)?.toIntOrNull() ?: (0).also { resetCount(player, type) }
    }

    /**
     * 是否已达限制触发次数
     */
    fun isCount(player: Player, type: String, count: Int): Boolean {
        return getCount(player, type) >= count
    }

    /**
     * 增加触发次数
     */
    fun addCount(player: Player, type: String) {
        player.insert(type, getCount(player, type) + 1)
    }

    /**
     * 重置触发次数
     */
    fun resetCount(player: Player, type: String) {
        player.insert(type, 0)
    }

    /**
     * 获取冷却
     */
    fun getCd(player: Player, type: String): Long {
        return player.get("${type}Cd")?.toLongOrNull() ?: (0L).also { player.insert("${type}Cd", 0L) }
    }

    /**
     * 是否处于冷却中
     */
    fun inCd(player: Player, type: String): Boolean {
        return System.currentTimeMillis() < getCd(player, type)
    }

    /**
     * 设置冷却
     */
    fun setCd(player: Player, type: String, seconds: Int) {
        player.insert("${type}Cd", System.currentTimeMillis() + seconds * 1000L)
    }
}

internal fun Player.getCount(type: String): Int = CooldownAPI.getCount(this, type)

internal fun Player.isCount(type: String, count: Int): Boolean = CooldownAPI.isCount(this, type, count)

internal fun Player.addCount(type: String) = CooldownAPI.addCount(this, type)

internal fun Player.resetCount(type: String) = CooldownAPI.resetCount(this, type)

internal fun Player.getCd(type: String): Long = CooldownAPI.getCd(this, type)

internal fun Player.inCd(type: String): Boolean = CooldownAPI.inCd(this, type)

internal fun Player.setCd(type: String, seconds: Int) = CooldownAPI.setCd(this, type, seconds)