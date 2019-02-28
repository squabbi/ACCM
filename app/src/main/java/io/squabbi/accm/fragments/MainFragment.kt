package io.squabbi.accm.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.squabbi.accm.R
import io.squabbi.accm.activities.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set ViewModel
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        // Observables
        mainViewModel.mAccInfoLiveData.observe(this, Observer {
                info ->
            // Update UI
            info?.let {
                tv_main_batteryStatus.text = getString(R.string.info_status_extended, info.status, info.getCurrentNow())
                tv_main_batterySpeed.text = info.chargeType
                updateCapacity(info.capacity)
                tv_main_batteryTemp.text = info.temperature.toString().plus(Typography.degree)
            }
        })

        mainViewModel.mAccConfigLiveData.observe(this, Observer {
                info ->
            // Update UI
            info?.let {
                // Capacity
                tv_main_config_capacity_shutdown.text = getString(R.string.config_capacity_shutdownAt,
                    info.capacity[0])
                tv_main_config_capacity_coolDown.text = getString(R.string.config_capacity_coolDownAt,
                    info.capacity[1])
                tv_main_config_capacity_chargeBetween.text = getString(R.string.config_capacity_chargeBetween,
                    info.capacity[2], info.capacity[3])

                // Cool Down
                tv_main_config_coolDown_charge.text = resources.getQuantityString(R.plurals.plural_config_coolDown_charge,
                    info.coolDown[0], info.coolDown[0])
                tv_main_config_coolDown_pause.text = resources.getQuantityString(R.plurals.plural_config_coolDown_pause,
                    info.coolDown[1], info.coolDown[1])

                // Temperature
                tv_main_config_temp_coolDown.text = getString(R.string.config_temp_coolDownAt, info.temperature[0])
                tv_main_config_temp_pauseCharging.text = getString(R.string.config_temp_pauseCharging, info.temperature[1])
                tv_main_config_temp_wait.text = resources.getQuantityString(R.plurals.plural_config_temp_pause,
                    info.temperature[2], info.temperature[2])

                // Verbose
                tv_main_config_verbose.text = info.verbose

                // Reset on Unplug
                tv_main_config_resetOnUnplugged.text = info.resetUnplugged

                // Loop Delay
                tv_main_config_loopDelay.text = resources.getQuantityString(R.plurals.plural_config_loopDelay,
                    info.loopDelay, info.loopDelay)

                // Max Log Size
                tv_main_config_maxLogSize.text = getString(R.string.config_maxLogSize, info.maxLogSize)

                // Exit after OnBoot
                tv_main_config_onBootExit.text = info.onBootExit

                // Self Upgrade
                tv_main_config_selfUpgrade.text = info.selfUpgrade
            }
        })

        mainViewModel.mAccDaemonRunning.observe(this, Observer {
            // Update UI
            if (it) {
                // Running
                tv_main_accdStatus.text = getString(R.string.info_daemon_started)
                fl_status_container.background = ColorDrawable(resources.getColor(R.color.colorSuccessful))
                iv_main_status_icon.setImageResource(R.drawable.ic_baseline_check_circle_24px)
            } else {
                // Not running
                tv_main_accdStatus.text = getString(R.string.info_daemon_stopped)
                fl_status_container.background = ColorDrawable(resources.getColor(R.color.colorError))
                iv_main_status_icon.setImageResource(R.drawable.ic_baseline_error_24px)
            }
        })
    }

    private fun updateCapacity(capacity: Int) {
        tv_main_batteryCapacity.text = capacity.toString().plus("%")
        progressBar_capacity.progress = capacity
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}