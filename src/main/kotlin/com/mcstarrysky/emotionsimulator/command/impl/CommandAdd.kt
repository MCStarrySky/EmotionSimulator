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
package com.mcstarrysky.emotionsimulator.command.impl

import com.mcstarrysky.emotionsimulator.api.change
import com.mcstarrysky.emotionsimulator.api.getEmotion
import com.mcstarrysky.emotionsimulator.command.EmotionCommand
import com.mcstarrysky.starrysky.command.CommandExecutor
import com.mcstarrysky.starrysky.command.executeAsCommandSender
import com.mcstarrysky.starrysky.i18n.sendLang
import org.bukkit.entity.Player
import taboolib.common.platform.command.SimpleCommandBody
import taboolib.common.platform.command.player
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggestPlayers
import kotlin.math.abs

object CommandAdd : CommandExecutor {

    override val command: SimpleCommandBody
        get() = subCommand {
            player("player") {
                suggestPlayers()
                dynamic("value") {
                    executeAsCommandSender { sender, context, _ ->
                        val user = context.player("player")
                        val before = user.cast<Player>().getEmotion()
                        user.cast<Player>().change(abs(context["value"].toDoubleOrNull() ?: return@executeAsCommandSender))
                        sender.sendLang("command.subCommands.add.success", "now" to user.cast<Player>().getEmotion(), "before" to before)
                    }
                }
            }
        }

    override val name: String
        get() = "add"

    init {
        EmotionCommand.sub[name] = this
    }
}