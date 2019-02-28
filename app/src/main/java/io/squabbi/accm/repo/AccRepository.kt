package io.squabbi.accm.repo

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.topjohnwu.superuser.internal.UiThreadHandler
import io.squabbi.accm.models.AccConfig
import io.squabbi.accm.models.AccInfo
import io.squabbi.accm.utils.AccUtils

/**
 * Repository class for grabbing acc commands.
 */
class AccRepository {

    val mAccInfoLiveData: MutableLiveData<AccInfo> = MutableLiveData()
    val mAccConfigLiveData: MutableLiveData<AccConfig> = MutableLiveData()
    val mAccDaemonRunningLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val accInfoRunnable = object: Runnable {
        override fun run() {
            mAccInfoLiveData.value = AccUtils.getAccInfo()
            mAccConfigLiveData.value = AccUtils.getAccConfig()
            mAccDaemonRunningLiveData.value = AccUtils.isDaemonRunning()
            UiThreadHandler.handler.postDelayed(this, 1500)
        }
    }

    init {
        val handler: Handler = Handler()
        handler.post(accInfoRunnable)
    }

    fun isAccDaemonRunning(): Boolean {
        return AccUtils.isDaemonRunning()
    }

    fun stopAccDaemon(): Boolean {
        return AccUtils.stopAccDaemon()
    }

    fun startAccDaemon(): Boolean {
        return AccUtils.startAccDaemon()
    }

    fun restartAccDaemon(): Boolean {
        return AccUtils.restartAccDaemon()
    }
}