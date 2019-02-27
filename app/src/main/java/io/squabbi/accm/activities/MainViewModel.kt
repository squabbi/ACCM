package io.squabbi.accm.activities

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.topjohnwu.superuser.internal.UiThreadHandler.handler
import io.squabbi.accm.models.AccConfig
import io.squabbi.accm.models.AccInfo
import io.squabbi.accm.repo.AccRepository
import io.squabbi.accm.utils.AccUtils

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //TODO: Create repository which does the live reading of acc info/status console output
    private val repository: AccRepository

    val mAccInfoLiveData: LiveData<AccInfo>
    val mAccConfigLiveData: LiveData<AccConfig>

    init {
        repository = AccRepository()
        mAccInfoLiveData = repository.mAccInfoLiveData
        mAccConfigLiveData = repository.mAccConfigLiveData
    }
}