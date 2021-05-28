package com.truedigital.vhealth.ui.setting.testinsurance.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.truedigital.vhealth.R
import com.truedigital.vhealth.ui.setting.testinsurance.adapter.RecyclerViewAdapter
import com.truedigital.vhealth.ui.setting.testinsurance.model.User
import com.truedigital.vhealth.ui.setting.testinsurance.viewmodel.TestInsuranceViewModel
import java.util.*

class TestInsuranceActivity : AppCompatActivity(), LifecycleOwner {
    var context: TestInsuranceActivity? = null
    var viewModel: TestInsuranceViewModel? = null
    var recyclerView: RecyclerView? = null
    var recyclerViewAdapter: RecyclerViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_insurance)
        context = this
        recyclerView = findViewById(R.id.rv_main)
        viewModel = ViewModelProviders.of(context!!).get(
            TestInsuranceViewModel::class.java
        )
        viewModel!!.userMutableLiveData.observe(context!!, userListUpdateObserver)
    }

    private var userListUpdateObserver: Observer<ArrayList<User>?> =
        Observer<ArrayList<User>?> { userArrayList ->
            recyclerViewAdapter = context?.let { RecyclerViewAdapter(it, userArrayList) }
            recyclerView!!.layoutManager = LinearLayoutManager(context)
            recyclerView!!.adapter = recyclerViewAdapter
        }
}
