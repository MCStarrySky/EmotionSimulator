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
package com.mcstarrysky.emotionsimulator

import org.bukkit.command.CommandSender
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.function.console
import taboolib.common.util.replaceWithOrder
import taboolib.module.lang.asLangText
import taboolib.platform.util.asLangText

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.Utils
 *
 * @author Mical
 * @since 2023/7/15 12:34
 */
fun ProxyCommandSender.prettyInfo(message: String, vararg args: Any) {
    sendMessage("${asLangText("prefix")} §7" + message.replaceWithOrder(*args))
}

fun CommandSender.prettyInfo(message: String, vararg args: Any) {
    sendMessage("${asLangText("prefix")} §7" + message.replaceWithOrder(*args))
}

fun prettyInfo(message: String, vararg args: Any) {
    console().prettyInfo(message, args)
}