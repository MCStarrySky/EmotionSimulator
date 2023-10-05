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

import com.mcstarrysky.emotionsimulator.EmotionConfig
import com.mcstarrysky.emotionsimulator.command.EmotionCommand
import com.mcstarrysky.starrysky.command.CommandExecutor
import com.mcstarrysky.starrysky.command.executeAsCommandSender
import com.mcstarrysky.starrysky.i18n.I18n
import com.mcstarrysky.starrysky.i18n.sendLang
import taboolib.common.platform.command.SimpleCommandBody
import taboolib.common.platform.command.subCommand

object CommandReload : CommandExecutor {

    override val command: SimpleCommandBody
        get() = subCommand {
            executeAsCommandSender { sender, _, _ ->
                I18n.reload()
                EmotionConfig.config.reload()
                sender.sendLang("command.subCommands.reload.success")
            }
        }

    override val name: String
        get() = "reload"

    init {
        EmotionCommand.sub[name] = this
    }
}