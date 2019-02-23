package io.squabbi.accm

import android.app.Application
import com.topjohnwu.superuser.Shell

class AccmApplication: Application() {

    companion object {
        init {
            Shell.Config.setFlags(Shell.FLAG_REDIRECT_STDERR)
            Shell.Config.verboseLogging(false)
            Shell.Config.setTimeout(10)
        }
    }
}