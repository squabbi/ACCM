package io.squabbi.accm.utils

import com.topjohnwu.superuser.Shell
import io.squabbi.accm.models.AccInfo


/***
 * ACC Utility Provided by MatteCarra.
 * https://github.com/mattecarra/acca
 */
object AccUtils {

    val CAPACITY_CONFIG_REGEXP = """^\s*capacity=(\d*),(\d*),(\d+)-(\d+)""".toRegex(RegexOption.MULTILINE)
    val COOLDOWN_CONFIG_REGEXP = """^\s*coolDown=(\d*)/(\d*)""".toRegex(RegexOption.MULTILINE)
    val TEMP_CONFIG_REGEXP = """^\s*temp=(\d*)-(\d*)_(\d*)""".toRegex(RegexOption.MULTILINE)
    val RESET_UNPLUGGED_CONFIG_REGEXP = """^\s*resetUnplugged=(true|false)""".toRegex(RegexOption.MULTILINE)
    val ON_BOOT_EXIT = """^\s*onBootExit=(true|false)""".toRegex(RegexOption.MULTILINE)
    val ON_BOOT = """^\s*onBoot=([^#]+)""".toRegex(RegexOption.MULTILINE)
    val VOLT_FILE = """^\s*voltFile=([^#]+)""".toRegex(RegexOption.MULTILINE)

    private val STATUS_REGEXP = "^\\s*STATUS=(Charging|Discharging)".toRegex(RegexOption.MULTILINE)
    private val HEALTH_REGEXP = "^\\s*HEALTH=([a-zA-Z]+)".toRegex(RegexOption.MULTILINE)
    private val CURRENT_NOW_REGEXP = "^\\s*CURRENT_NOW=(-?\\d+)".toRegex(RegexOption.MULTILINE)
    private val VOLTAGE_NOW_REGEXP = "^\\s*VOLTAGE_NOW=(\\d+)".toRegex(RegexOption.MULTILINE)
    private val TEMP_REGEXP = "^\\s*TEMP=(\\d+)".toRegex(RegexOption.MULTILINE)

    fun getBatteryInfo(): AccInfo {
        val info =  Shell.su("acc -i").exec().out.joinToString(separator = "\n")

        return AccInfo(
            STATUS_REGEXP.find(info)?.destructured?.component1() ?: "Discharging",
            HEALTH_REGEXP.find(info)?.destructured?.component1() ?: "Unknown",
            CURRENT_NOW_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            VOLTAGE_NOW_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            TEMP_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull()?.let { it/10 } ?: -1
        )
    }
}