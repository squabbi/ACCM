package io.squabbi.accm.activities

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.topjohnwu.superuser.internal.UiThreadHandler.handler
import io.squabbi.accm.models.AccInfo
import io.squabbi.accm.utils.AccUtils

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //TODO: Create repository which does the live reading of acc info/status console output

    val mAccInfoLiveData: MutableLiveData<AccInfo> = MutableLiveData()

    val accInfoRunnable = object: Runnable {
        override fun run() {
            mAccInfoLiveData.value = AccUtils.getBatteryInfo()
            handler.postDelayed(this, 1000)
        }
    }

    init {
        val handler: Handler = Handler()
        handler.post(accInfoRunnable)
    }
}