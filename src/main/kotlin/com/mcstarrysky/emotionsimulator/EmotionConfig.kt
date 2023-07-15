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

import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.EmotionConfig
 *
 * @author Mical
 * @since 2023/7/15 12:31
 */
object EmotionConfig {

    @Config
    lateinit var config: Configuration
        private set

    val database: ConfigurationSection
        get() = config.getConfigurationSection("database") ?: error("database configuration")
}