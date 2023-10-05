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
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import taboolib.common.platform.event.SubscribeEvent

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.ingame.event.ListenerPlayerAttack
 *
 * @author Mical
 * @since 2023/7/15 23:45
 */
object ListenerPlayerAttack {

    @SubscribeEvent
    fun e(e: EntityDeathEvent) {
        if (e.entity.lastDamageCause?.cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK ||
            e.entity.lastDamageCause?.cause == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK
        ) {
            val damageEvent = e.entity.lastDamageCause as? EntityDamageByEntityEvent ?: return
            val player = damageEvent.damager as? Player ?: return

            var type: String? = ""
            var emotion: String? = ""

            // 先处理与击杀有关的
            if (e.entity is Player) {
                type = "player"
                emotion = EmotionConfig.config.getString("kill.player")
            } else {
                when (e.entity.type.name) {
                    in EmotionConfig.config.getStringList("kill.creature.easy-list") -> {
                        type = "easy"
                        emotion = EmotionConfig.config.getString("kill.creature.easy")
                    }
                    in EmotionConfig.config.getStringList("kill.creature.normal-list") -> {
                        type = "normal"
                        emotion = EmotionConfig.config.getString("kill.creature.normal")
                    }
                    in EmotionConfig.config.getStringList("kill.creature.hard-list") -> {
                        type = "hard"
                        emotion = EmotionConfig.config.getString("kill.creature.hard")
                    }
                    else -> {}
                }
            }
            deal(type, emotion, player)
            type = ""
            emotion = ""

            // 再处理动物采集
            when {
                e.drops.any { it.type == Material.LEATHER } -> {
                    if (e.entity.type in listOf(EntityType.COW, EntityType.MUSHROOM_COW, EntityType.LLAMA, EntityType.HOGLIN, EntityType.MULE, EntityType.DONKEY, EntityType.HORSE)) {
                        type = "leather"
                        emotion = EmotionConfig.config.getString("animal.leather")
                    }
                }
                e.drops.any { it.type == Material.BEEF || it.type == Material.PORKCHOP || it.type == Material.MUTTON || it.type == Material.CHICKEN || it.type == Material.RABBIT } -> {
                    if (e.entity.type in listOf(EntityType.PIG, EntityType.COW, EntityType.MUSHROOM_COW, EntityType.RABBIT, EntityType.SHEEP, EntityType.CHICKEN)) {
                        type = "meat"
                        emotion = EmotionConfig.config.getString("animal.meat")
                    }
                }
                e.drops.any { it.type == Material.BLAZE_ROD } -> {
                    if (e.entity.type == EntityType.BLAZE) {
                        type = "blaze_rod"
                        emotion = EmotionConfig.config.getString("adventure.materials.blaze_rod")
                    }
                }
                e.drops.any { it.type == Material.GUNPOWDER } -> {
                    if (e.entity.type in listOf(EntityType.CREEPER, EntityType.GHAST, EntityType.WITCH)) {
                        type = "gunpowdwer"
                        emotion = EmotionConfig.config.getString("adventure.materials.gunpowder")
                    }
                }
                e.drops.any { it.type == Material.SPIDER_EYE } -> {
                    if (e.entity.type in listOf(EntityType.SPIDER, EntityType.CAVE_SPIDER, EntityType.WITCH)) {
                        type = "spider_eye"
                        emotion = EmotionConfig.config.getString("adventure.materials.spider_eye")
                    }
                }
                e.drops.any { it.type == Material.BONE } -> {
                    if (e.entity.type in listOf(EntityType.SKELETON, EntityType.SKELETON_HORSE, EntityType.WITHER_SKELETON)) {
                        type = "bone"
                        emotion = EmotionConfig.config.getString("adventure.materials.bone")
                    }
                }
                else -> {}
            }

            deal(type, emotion, player)
        }
    }

    private fun deal(type: String?, emotion: String?, player: Player) {
        if (type.isNullOrEmpty()) return
        if (emotion.isNullOrEmpty()) return

        if (player.inCd(type)) return
        val (delta, time, count) = emotion.split("~", limit = 3).map { it.toDouble() }
        player.addCount(type)
        player.change(delta)
        if (player.isCount(type, count.toInt())) {
            player.setCd(type, time.toInt())
            player.resetCount(type)
        }
    }
}