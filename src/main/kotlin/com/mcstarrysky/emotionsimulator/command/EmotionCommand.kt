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
package com.mcstarrysky.emotionsimulator.command

import com.mcstarrysky.emotionsimulator.command.impl.CommandAdd
import com.mcstarrysky.emotionsimulator.command.impl.CommandReload
import com.mcstarrysky.emotionsimulator.command.impl.CommandSet
import com.mcstarrysky.emotionsimulator.command.impl.CommandTake
import com.mcstarrysky.starrysky.command.CommandExecutor
import com.mcstarrysky.starrysky.command.CommandHandler
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import java.util.concurrent.ConcurrentHashMap

@CommandHeader(name = "es", permission = "emotion.use")
object EmotionCommand : CommandHandler {

    override val sub: ConcurrentHashMap<String, CommandExecutor> = ConcurrentHashMap()

    @CommandBody(hidden = true, permission = "emotion.add")
    val add = CommandAdd.command

    @CommandBody(hidden = true, permission = "emotion.reload")
    val reload = CommandReload.command

    @CommandBody(hidden = true, permission = "emotion.set")
    val set = CommandSet.command

    @CommandBody(hidden = true, permission = "emotion.take")
    val take = CommandTake.command
}