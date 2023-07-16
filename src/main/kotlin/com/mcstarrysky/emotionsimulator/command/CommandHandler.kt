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
@file:Suppress("deprecation")
package com.mcstarrysky.emotionsimulator.command

import com.mcstarrysky.emotionsimulator.command.sub.CommandDev
import com.mcstarrysky.emotionsimulator.command.sub.CommandReload
import com.mcstarrysky.emotionsimulator.prettyInfo
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.component.CommandBase
import taboolib.common.platform.command.component.CommandComponent
import taboolib.common.platform.command.component.CommandComponentLiteral
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.function.pluginVersion
import taboolib.common.util.Strings
import taboolib.module.chat.RawMessage
import taboolib.module.nms.MinecraftVersion
import java.util.concurrent.ConcurrentHashMap

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.command.CommandHandler
 *
 * @author Mical
 * @since 2023/7/12 00:20
 */
@CommandHeader(name = "emotionsimulator", aliases = ["emotion", "es"])
object CommandHandler {

    val sub = ConcurrentHashMap<String, CommandExecutor>()

    @CommandBody
    val main = mainCommand {
        createTabooHelper()
    }

    @CommandBody
    val dev = CommandDev.command

    @CommandBody
    val reload = CommandReload.command

    private fun CommandComponent.createTabooHelper() {
        execute<ProxyCommandSender> { sender, context, _ ->
            sender.sendMessage("")
            RawMessage()
                .append("  ").append("§cEmotionSimulator")
                .hoverText("§7StarrySky 情绪模拟")
                .append(" ").append("§f${pluginVersion}")
                .hoverText(
                    """
                §7插件版本: §2${pluginVersion}
                §7游戏版本: §b${MinecraftVersion.minecraftVersion}
            """.trimIndent()
                ).sendTo(sender)
            sender.sendMessage("")
            RawMessage()
                .append("  §7命令: ").append("§f/emotionsimulator §8[...]")
                .hoverText("§f/emotionsimulator §8[...]")
                .suggestCommand("/emotionsimulator ")
                .sendTo(sender)
            sender.sendMessage("  §7参数:")


            for (command in children.filterIsInstance<CommandComponentLiteral>()) {
                val name = command.aliases[0]
                var usage = sub[name]?.usage ?: ""
                if (usage.isNotEmpty()) {
                    usage += " "
                }
                val description = sub[name]?.description ?: "没有描述"

                RawMessage()
                    .append("    §8- ").append("§f$name")
                    .hoverText("§f/emotionsimulator $name $usage §8- §7$description")
                    .suggestCommand("/emotionsimulator $name ")
                    .sendTo(sender)
                sender.sendMessage("      §7$description")
            }

            sender.sendMessage("")
        }

        if (this is CommandBase) {
            incorrectCommand { sender, ctx, _, state ->
                val name = ctx.args().first()
                var usage = sub[name]?.usage ?: ""
                if (usage.isNotEmpty()) {
                    usage += " "
                }
                val description = sub[name]?.description ?: "没有描述"
                when (state) {
                    1 -> {
                        sender.prettyInfo("指令 §f{0} §7参数不足.", name)
                        sender.prettyInfo("正确用法:")
                        sender.prettyInfo("§f/emotionsimulator {0} {1}§8- §7{2}", name, usage, description)
                    }

                    2 -> {
                        if (ctx.args().size > 1) {
                            sender.prettyInfo("指令 §f{0} §7参数有误.", name)
                            sender.prettyInfo("正确用法:")
                            sender.prettyInfo("§f/emotionsimulator {0} {1}§8- §7{2}", name, usage, description)
                        } else {
                            val similar = sub.keys.maxByOrNull { Strings.similarDegree(name, it) }!!
                            sender.prettyInfo("指令 §f{0} §7不存在.", name)
                            sender.prettyInfo("你可能想要:")
                            sender.prettyInfo(similar)
                        }
                    }
                }
            }
            incorrectSender { sender, ctx ->
                sender.prettyInfo("指令 §f{0} §7只能由 §f玩家 §7执行.", ctx.args().first())
            }
        }
    }
}