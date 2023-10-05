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
package com.mcstarrysky.emotionsimulator.data

import com.mcstarrysky.emotionsimulator.api.getEmotion
import com.mcstarrysky.emotionsimulator.api.isCrazy
import com.mcstarrysky.emotionsimulator.api.isEmo
import com.mcstarrysky.emotionsimulator.api.isNormal
import com.mcstarrysky.starrysky.i18n.asLangTextString
import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.data.EmotionExpansion
 *
 * @author Mical
 * @since 2023/7/16 13:52
 */
object EmotionExpansion : PlaceholderExpansion {
    override val identifier: String
        get() = "emotion"

    override fun onPlaceholderRequest(player: Player?, args: String): String {
        return when (args.lowercase()) {
            "state" -> {
                val state = when {
                    player?.isEmo() == true -> "emo"
                    player?.isNormal() == true -> "normal"
                    player?.isCrazy() == true -> "crazy"
                    else -> return ""
                }
                player.asLangTextString("state-$state")
            }
            "value" -> (player?.getEmotion() ?: -1.0).toString()
            else -> ""
        }
    }
}