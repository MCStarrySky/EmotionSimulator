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

import com.mcstarrysky.emotionsimulator.api.getEmotion
import com.mcstarrysky.emotionsimulator.api.set
import com.mcstarrysky.emotionsimulator.command.CommandExecutor
import com.mcstarrysky.emotionsimulator.command.CommandHandler
import com.mcstarrysky.emotionsimulator.prettyInfo
import org.bukkit.entity.Player
import taboolib.common.platform.command.*

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.command.sub.CommandDev
 *
 * @author Mical
 * @since 2023/7/15 13:46
 */
object CommandDev : CommandExecutor {

    override val command: SimpleCommandBody
        get() = subCommand {
            dynamic("value", optional = true) {
                restrictDouble()
                execute<Player> { sender, context, _ ->
                    val value = context.double("value")
                    sender.set(value)
                    sender.prettyInfo("已设置.")
                }
            }
            execute<Player> { sender, _, _ ->
                sender.prettyInfo("你的情绪值为: ${sender.getEmotion()}")
            }
        }

    override val name: String
        get() = "dev"

    override val description: String
        get() = "测试命令"

    override val usage: String
        get() = "§7<§8数值§7>"

    init {
        CommandHandler.sub[name] = this
    }
}