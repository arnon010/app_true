package com.truedigital.vhealth.ui.setting.testinsurance.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truedigital.vhealth.R
import com.truedigital.vhealth.api.network.Result
import com.truedigital.vhealth.ui.setting.testinsurance.model.User
import com.truedigital.vhealth.ui.setting.testinsurance.repo.InsuranceRepository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class TestInsuranceViewModel(private val insuranceRepository: InsuranceRepository) : ViewModel() {

    private val _user = MutableLiveData<Result<ArrayList<User>>>()
    val user: LiveData<Result<ArrayList<User>>> get() = _user

//    fun init() {
//        getPopulate()
//    }

    fun getPopulate() {
        viewModelScope.launch {
            _user.value = Result.Loading()
            val userData = insuranceRepository.getInsurance()
            val userDataInfo = userData.body()
            if (userData.isSuccessful && userDataInfo != null) {
                _user.value = Result.Success(userDataInfo)
            } else {
                val users = ArrayList<User>()
                users.add(User("AIA", "XXX-X-XXX", R.drawable.aia))
                users.add(User("Cigna", "XXX-X-XXX", R.drawable.cigna))
                _user.value = Result.Success(users)
//                _user.value = Result.Error()
            }
        }
    }
}