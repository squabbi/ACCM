package io.squabbi.accm.repo

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.topjohnwu.superuser.internal.UiThreadHandler
import io.squabbi.accm.models.AccInfo
import io.squabbi.accm.utils.AccUtils

/**
 * Repository class for grabbing acc commands.
 */
class AccRepository {

    val mAccInfoLiveData: MutableLiveData<AccInfo> = MutableLiveData()

    val accInfoRunnable = object: Runnable {
        override fun run() {
            mAccInfoLiveData.value = AccUtils.getAccInfo()
            UiThreadHandler.handler.postDelayed(this, 1000)
        }
    }

    init {
        val handler: Handler = Handler()
        handler.post(accInfoRunnable)
    }
}