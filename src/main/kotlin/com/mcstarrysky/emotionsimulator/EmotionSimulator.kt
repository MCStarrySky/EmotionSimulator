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

import com.mcstarrysky.emotionsimulator.data.PluginDatabase
import com.mcstarrysky.emotionsimulator.ingame.Task
import taboolib.common.platform.Plugin

object EmotionSimulator : Plugin() {

    override fun onEnable() {
        PluginDatabase.initialize()
        Task.initialize()
        prettyInfo("已成功加载插件.")
    }
}