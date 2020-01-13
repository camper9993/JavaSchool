package com.handtruth.javaschool.ui.user

import androidx.lifecycle.ViewModel
import com.handtruth.javaschool.data.model.Subscriber
import com.handtruth.javaschool.data.model.UserDetail

class UserViewModel : ViewModel(), Subscriber<UserDetail> {
    override fun insert(obj: UserDetail) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}