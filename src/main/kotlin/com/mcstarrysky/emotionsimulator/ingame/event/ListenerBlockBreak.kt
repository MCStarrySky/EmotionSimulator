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
import com.mcstarrysky.emotionsimulator.api.inCd
import com.mcstarrysky.emotionsimulator.api.isCount
import com.mcstarrysky.emotionsimulator.api.setCd
import org.bukkit.Material
import org.bukkit.block.data.Ageable
import org.bukkit.event.block.BlockBreakEvent
import taboolib.common.platform.event.SubscribeEvent

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.ingame.event.ListenerBlockBreak
 *
 * @author Mical
 * @since 2023/7/15 21:47
 */
object ListenerBlockBreak {

    // FIXME: 代码过于傻逼, 令人窒息
    @SubscribeEvent
    fun e(e: BlockBreakEvent) {
        var type: String? = ""
        var blockEmotion: String? = ""
        when (e.block.type) {
            // 矿物
            Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE -> {
                type = "iron"
                blockEmotion = EmotionConfig.config.getString("ore.iron")
            }
            Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE -> {
                type = "coal"
                blockEmotion = EmotionConfig.config.getString("ore.coal")
            }
            Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE -> {
                type = "gold"
                blockEmotion = EmotionConfig.config.getString("ore.gold")
            }
            Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE -> {
                type = "redstone"
                blockEmotion = EmotionConfig.config.getString("ore.redstone")
            }
            Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE -> {
                type = "lapis"
                blockEmotion = EmotionConfig.config.getString("ore.lapis")
            }
            Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE -> {
                type = "diamond"
                blockEmotion = EmotionConfig.config.getString("ore.diamond")
            }
            Material.NETHER_GOLD_ORE -> {
                type = "nether_gold"
                blockEmotion = EmotionConfig.config.getString("ore.nether_gold")
            }
            Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE -> {
                type = "emerald"
                blockEmotion = EmotionConfig.config.getString("ore.emerald")
            }
            // 农田收获
            Material.WHEAT -> {
                type = "wheat"
                blockEmotion = EmotionConfig.config.getString("seeds.wheat")
            }
            Material.POTATOES -> {
                type = "potatoes"
                blockEmotion = EmotionConfig.config.getString("seeds.potatoes")
            }
            Material.CARROTS -> {
                type = "carrots"
                blockEmotion = EmotionConfig.config.getString("seeds.carrots")
            }
            Material.BEETROOTS -> {
                type = "beetroots"
                blockEmotion = EmotionConfig.config.getString("seeds.beetroots")
            }
            Material.PUMPKIN -> {
                type = "pumpkin"
                blockEmotion = EmotionConfig.config.getString("seeds.pumpkin")
            }
            Material.MELON -> {
                type = "melon"
                blockEmotion = EmotionConfig.config.getString("seeds.melon")
            }
            Material.COCOA -> {
                type = "cocoa"
                blockEmotion = EmotionConfig.config.getString("seeds.cocoa")
            }
            // 植物采集
            Material.SWEET_BERRY_BUSH -> {
                type = "sweet_berry_bush"
                blockEmotion = EmotionConfig.config.getString("plant.sweet_berry_bush")
            }
            Material.SUGAR_CANE -> {
                type = "sugar_cane"
                blockEmotion = EmotionConfig.config.getString("plant.sugar_cane")
            }
            Material.CACTUS -> {
                type = "cactus"
                blockEmotion = EmotionConfig.config.getString("plant.cactus")
            }
            Material.NETHER_WART -> {
                type = "nether_wart"
                blockEmotion = EmotionConfig.config.getString("plant.nether_wart")
            }
            else -> {}
        }
        if (type.isNullOrEmpty()) return
        if (blockEmotion.isNullOrEmpty()) return
        // 判断作物
        if (e.block.blockData is Ageable) {
            val data = e.block.blockData as Ageable
            if (data.age != data.maximumAge) return
        }
        if (e.player.inCd(type)) return
        val (delta, time, count) = blockEmotion.split("~", limit = 3).map { it.toDouble() }
        e.player.addCount(type)
        e.player.change(delta)
        if (e.player.isCount(type, count.toInt())) {
            e.player.setCd(type, time.toInt())
            e.player.resetCount(type)
        }
    }
}