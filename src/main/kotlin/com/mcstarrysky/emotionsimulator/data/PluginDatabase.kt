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
package com.mcstarrysky.emotionsimulator.data

import com.mcstarrysky.emotionsimulator.EmotionConfig
import org.bukkit.entity.Player
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.HostSQL
import taboolib.module.database.Table

/**
 * EmotionSimulator
 * com.mcstarrysky.emotionsimulator.data.PluginDatabase
 *
 * @author Mical
 * @since 2023/7/15 12:31
 */
object PluginDatabase {

    private val host = HostSQL(EmotionConfig.database)

    private val table = Table("emotionsimulator", host) {
        add("user") {
            type(ColumnTypeSQL.VARCHAR, 36) {
                options(ColumnOptionSQL.KEY)
            }
        }
        add("key") {
            type(ColumnTypeSQL.VARCHAR, 64) {
                options(ColumnOptionSQL.KEY)
            }
        }
        add("value") {
            type(ColumnTypeSQL.VARCHAR, 128)
        }
    }

    private val dataSource = host.createDataSource()

    init {
        table.workspace(dataSource) { createTable(true) }.run()
    }

    fun Player.insert(key: String, value: Any) {
        if (table.find(dataSource) {
            where {
                "user" eq uniqueId.toString()
                "key" eq key
            }
            }) {
            table.update(dataSource) {
                set("value", value.toString())
                where {
                    "user" eq uniqueId.toString()
                    "key" eq key
                }
            }
        } else {
            table.insert(dataSource, "user", "key", "value") {
                value(uniqueId.toString(), key, value.toString())
            }
        }
    }

    fun Player.get(key: String): String? {
        return table.select(dataSource) {
            where {
                "user" eq uniqueId.toString()
                "key" eq key
            }
        }.firstOrNull { getString("value") }
    }
}