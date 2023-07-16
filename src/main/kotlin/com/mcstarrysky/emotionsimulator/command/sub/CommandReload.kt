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
package com.mcstarrysky.emotionsimulator.command.sub

import com.mcstarrysky.emotionsimulator.EmotionConfig
import com.mcstarrysky.emotionsimulator.command.CommandExecutor
import com.mcstarrysky.emotionsimulator.command.CommandHandler
import com.mcstarrysky.emotionsimulator.prettyInfo
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.SimpleCommandBody
import taboolib.common.platform.command.subCommand
import taboolib.module.lang.Language

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.command.sub.CommandReload
 *
 * @author Mical
 * @since 2023/7/16 15:19
 */
object CommandReload : CommandExecutor {

    override val command: SimpleCommandBody
        get() = subCommand {
            execute<ProxyCommandSender> { sender, _, _ ->
                Language.reload()
                EmotionConfig.config.reload()
                sender.prettyInfo("插件已成功重载.")
            }
        }

    override val name: String
        get() = "reload"

    override val description: String
        get() = "重载插件"

    override val usage: String
        get() = ""

    init {
        CommandHandler.sub[CommandDev.name] = this
    }
}