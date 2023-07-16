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