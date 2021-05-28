package com.truedigital.vhealth.ui.setting.testinsurance.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.truedigital.vhealth.R
import com.truedigital.vhealth.ui.setting.testinsurance.model.User
import java.util.*

class TestInsuranceViewModel : ViewModel() {
    private var userLiveData: MutableLiveData<ArrayList<User>?> = MutableLiveData<ArrayList<User>?>()
    var userArrayList: ArrayList<User>? = null
    val userMutableLiveData: MutableLiveData<ArrayList<User>?>
        get() = userLiveData

    fun init() {
        populateList()
//        getGitUsersData()
        userLiveData.value = userArrayList
    }

    fun populateList() {

        val user = User()
        user.title = "AIA"
        user.description = "XXX-X-XXX"
        user.imgIcon = R.drawable.aia

        val user2 = User()
        user2.title = "Cigna"
        user2.description = "XXX-X-XXX"
        user2.imgIcon = R.drawable.cigna

        userArrayList = ArrayList<User>()
        userArrayList!!.add(user)
        userArrayList!!.add(user2)
    }


    fun getGitUsersData(): MutableLiveData<ArrayList<User>?> {
        return userLiveData
    }

    init {

        // call your Rest API in init method
        init()
    }
}