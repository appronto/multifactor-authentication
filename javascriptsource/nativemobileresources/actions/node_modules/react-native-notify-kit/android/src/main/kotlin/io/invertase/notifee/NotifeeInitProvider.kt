/*
 * Copyright (c) 2016-present Invertase Limited
 */

package io.invertase.notifee

import app.notifee.core.InitProvider
import app.notifee.core.Notifee

class NotifeeInitProvider : InitProvider() {

    override fun onCreate(): Boolean {
        val result = super.onCreate()
        Notifee.initialize(NotifeeEventSubscriber())
        return result
    }
}
