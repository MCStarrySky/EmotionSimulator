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

import com.mcstarrysky.starrysky.i18n.asLangTextString
import org.bukkit.entity.Player

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.ingame.death.DeathCause
 *
 * @author Mical
 * @since 2023/7/16 15:42
 */
enum class DeathCause {

    EMO, CRAZY;

    fun getMessage(player: Player): String = player.asLangTextString("death-${this.name.lowercase()}", "name" to player.name)
}