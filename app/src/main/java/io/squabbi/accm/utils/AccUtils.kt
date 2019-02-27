package io.squabbi.accm.utils
import com.topjohnwu.superuser.Shell
import io.squabbi.accm.models.AccInfo


/***
 * ACC Utility Provided by MatteCarra, with additions.
 * squabbi's additions and modifications will be commented on.
 *
 * Source: https://github.com/mattecarra/acca
 */
object AccUtils {

    // String resources
    private val STRING_UNKNOWN = "Unknown"
    private val STRING_DISCHARGING = "Discharging"
    private val STRING_CHARGING = "Charging"

    val CAPACITY_CONFIG_REGEXP = """^\s*capacity=(\d*),(\d*),(\d+)-(\d+)""".toRegex(RegexOption.MULTILINE)
    val COOLDOWN_CONFIG_REGEXP = """^\s*coolDown=(\d*)/(\d*)""".toRegex(RegexOption.MULTILINE)
    val TEMP_CONFIG_REGEXP = """^\s*temp=(\d*)-(\d*)_(\d*)""".toRegex(RegexOption.MULTILINE)
    val RESET_UNPLUGGED_CONFIG_REGEXP = """^\s*resetUnplugged=(true|false)""".toRegex(RegexOption.MULTILINE)
    val ON_BOOT_EXIT = """^\s*onBootExit=(true|false)""".toRegex(RegexOption.MULTILINE)
    val ON_BOOT = """^\s*onBoot=([^#]+)""".toRegex(RegexOption.MULTILINE)
    val VOLT_FILE = """^\s*voltFile=([^#]+)""".toRegex(RegexOption.MULTILINE)

    // Regex for determining NAME of BATTERY
    private val NAME_REGEXP = "^\\s*NAME=([a-zA-Z0-9]+)".toRegex(RegexOption.MULTILINE)
    // Regex for INPUT_SUSPEND
    private val INPUT_SUSPEND_REGEXP = "^\\s*INPUT_SUSPEND=(0|1)".toRegex(RegexOption.MULTILINE)
    private val STATUS_REGEXP = "^\\s*STATUS=(Charging|Discharging)".toRegex(RegexOption.MULTILINE)
    private val HEALTH_REGEXP = "^\\s*HEALTH=([a-zA-Z]+)".toRegex(RegexOption.MULTILINE)
    // Regex for PRESENT value
    private val PRESENT_REGEXP = "^\\s*PRESENT=(\\d+)".toRegex(RegexOption.MULTILINE)
    // Regex for determining CHARGE_TYPE
    private val CHARGE_TYPE_REGEXP = "^\\s*CHARGE_TYPE=(N/A|[a-zA-Z]+)".toRegex(RegexOption.MULTILINE)
    // Regex for battery CAPACITY
    private val CAPACTIY_REGEXP = "^\\s*CAPACITY=(\\d+)".toRegex(RegexOption.MULTILINE)
    // Regex for CHARGER_TEMP
    private val CHARGER_TEMP_REGEXP = "^\\s*CHARGER_TEMP=(\\d+)".toRegex(RegexOption.MULTILINE)
    // Regex for CHARGER_TEMP_MAX
    private val CHARGER_TEMP_MAX_REGEXP = "^\\s*CHARGER_TEMP_MAX=(\\d+)".toRegex(RegexOption.MULTILINE)
    // Regex for INPUT_CURRENT_LIMITED, 0 = false, 1 = true
    private val INPUT_CURRENT_LIMITED_REGEXP = "^\\s*INPUT_CURRENT_LIMITED=(0|1)".toRegex(RegexOption.MULTILINE)
    private val VOLTAGE_NOW_REGEXP = "^\\s*VOLTAGE_NOW=(\\d+)".toRegex(RegexOption.MULTILINE)
    // Regex for VOLTAGE_MAX
    private val VOLTAGE_MAX_REGEXP = "^\\s*VOLTAGE_MAX=(\\d+)".toRegex(RegexOption.MULTILINE)
    // Regex for VOLTAGE_QNOVO
    private val VOLTAGE_QNOVO_REGEXP = "^\\s*VOLTAGE_QNOVO=(\\d+)".toRegex(RegexOption.MULTILINE)
    private val CURRENT_NOW_REGEXP = "^\\s*CURRENT_NOW=(-?\\d+)".toRegex(RegexOption.MULTILINE)
    // Regex for CURRENT_QNOVO
    private val CURRENT_QNOVO_REGEXP = "^\\s*CURRENT_NOW=(-?\\d+)".toRegex(RegexOption.MULTILINE)
    // Regex for CONSTANT_CHARGE_CURRENT_MAX
    private val CONSTANT_CHARGE_CURRENT_MAX_REGEXP = "^\\s*CONSTANT_CHARGE_CURRENT_MAX=(\\d+)".toRegex(RegexOption.MULTILINE)
    private val TEMP_REGEXP = "^\\s*TEMP=(\\d+)".toRegex(RegexOption.MULTILINE)
    // Regex for remaining 'acc -i' values
    private val TECHNOLOGY_REGEXP = "^\\s*TECHNOLOGY=([a-zA-Z\\-]+)".toRegex(RegexOption.MULTILINE)
    private val STEP_CHARGING_ENABLED_REGEXP = "^\\s*STEP_CHARGING_ENABLED=(0|1)".toRegex(RegexOption.MULTILINE)
    private val SW_JEITA_ENABLED_REGEXP = "^\\s*SW_JEITA_ENABLED=(0|1)".toRegex(RegexOption.MULTILINE)
    private val TAPER_CONTROL_ENABLED_REGEXP = "^\\s*TAPER_CONTROL_ENABLED=(0|1)".toRegex(RegexOption.MULTILINE)
    // CHARGE_DISABLE is true when ACC disables charging due to conditions
    private val CHARGE_DISABLE_REGEXP = "^\\s*TAPER_CONTROL_ENABLED=(0|1)".toRegex(RegexOption.MULTILINE)
    // CHARGE_DONE is true when the battery is done charging.
    private val CHARGE_DONE_REGEXP = "^\\s*CHARGE_DONE=(0|1)".toRegex(RegexOption.MULTILINE)
    private val PARALLEL_DISABLE_REGEXP = "^\\s*PARALLEL_DISABLE=(0|1)".toRegex(RegexOption.MULTILINE)
    private val SET_SHIP_MODE_REGEXP = "^\\s*SET_SHIP_MODE=(0|1)".toRegex(RegexOption.MULTILINE)
    private val DIE_HEALTH_REGEXP = "^\\s*DIE_HEALTH=([a-zA-Z]+)".toRegex(RegexOption.MULTILINE)
    private val RERUN_AICL_REGEXP = "^\\s*RERUN_AICL=(0|1)".toRegex(RegexOption.MULTILINE)
    private val DP_DM_REGEXP = "^\\s*DP_DM=(\\d+)".toRegex(RegexOption.MULTILINE)
    private val CHARGE_CONTROL_LIMIT_MAX_REGEXP = "^\\s*CHARGE_CONTROL_LIMIT_MAX=(\\d+)".toRegex(RegexOption.MULTILINE)
    private val CHARGE_CONTROL_LIMIT_REGEXP = "^\\s*CHARGE_CONTROL_LIMIT=(\\d+)".toRegex(RegexOption.MULTILINE)
    private val CHARGE_COUNTER_REGEXP = "^\\s*CHARGE_COUNTER=(\\d+)".toRegex(RegexOption.MULTILINE)
    private val INPUT_CURRENT_MAX_REGEXP = "^\\s*INPUT_CURRENT_MAX=(\\d+)".toRegex(RegexOption.MULTILINE)
    private val CYCLE_COUNT_REGEXP = "^\\s*CYCLE_COUNT=(\\d+)".toRegex(RegexOption.MULTILINE)

    fun getAccInfo(): AccInfo {
        val info =  Shell.su("acc -i").exec().out.joinToString(separator = "\n")

        return AccInfo(
            NAME_REGEXP.find(info)?.destructured?.component1() ?: STRING_UNKNOWN,
            INPUT_SUSPEND_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull().let { // If r == true (input is suspended)
                it == 0
            },
            STATUS_REGEXP.find(info)?.destructured?.component1() ?: STRING_DISCHARGING,
            HEALTH_REGEXP.find(info)?.destructured?.component1() ?: STRING_UNKNOWN,
            PRESENT_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            CHARGE_TYPE_REGEXP.find(info)?.destructured?.component1() ?: STRING_UNKNOWN,
            CAPACTIY_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            CHARGER_TEMP_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull()?.let { it/10 } ?: -1,
            CHARGER_TEMP_MAX_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull()?.let { it/10 } ?: -1,
            INPUT_CURRENT_LIMITED_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull().let {
                it == 0
            },
            VOLTAGE_NOW_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            VOLTAGE_MAX_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            VOLTAGE_QNOVO_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            CURRENT_NOW_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            CURRENT_QNOVO_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            CONSTANT_CHARGE_CURRENT_MAX_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            TEMP_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull()?.let { it/10 } ?: -1,
            TECHNOLOGY_REGEXP.find(info)?.destructured?.component1() ?: STRING_UNKNOWN,
            STEP_CHARGING_ENABLED_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull().let {
                it == 0
            },
            SW_JEITA_ENABLED_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull().let {
                it == 0
            },
            TAPER_CONTROL_ENABLED_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull().let {
                it == 0
            },
            CHARGE_DISABLE_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull().let {
                it == 0
            },
            CHARGE_DONE_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull().let {
                it == 0
            },
            PARALLEL_DISABLE_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull().let {
                it == 0
            },
            SET_SHIP_MODE_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull().let {
                it == 0
            },
            DIE_HEALTH_REGEXP.find(info)?.destructured?.component1() ?: STRING_UNKNOWN,
            RERUN_AICL_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull().let {
                it == 0
            },
            DP_DM_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull().let {
                it == 0
            },
            CHARGE_CONTROL_LIMIT_MAX_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            CHARGE_CONTROL_LIMIT_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            INPUT_CURRENT_MAX_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1,
            CYCLE_COUNT_REGEXP.find(info)?.destructured?.component1()?.toIntOrNull() ?: -1
        )
    }
}