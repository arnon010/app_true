package com.truedigital.vhealth.ui.setting.insurance.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.truedigital.vhealth.R
import com.truedigital.vhealth.ui.setting.insurance.model.Insurance

class InsuranceActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

    val insurance = listOf<Insurance>()

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CheckWithImageView.ViewHolder>? = null

//    private lateinit var binding: ActivitySettingInsuranceBinding

//    private val retrofitService = RetrofitService.getInstance()
//    val adapter = MainAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_insurance)
        recyclerView = findViewById(R.id.recyclerViewInsurance)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager

        adapter = CheckWithImageView()
        recyclerView?.adapter = adapter

//        binding = ActivitySettingInsuranceBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
//            MainViewModel::class.java)
//
//        binding.recyclerViewInsurance.adapter = adapter
//
//        viewModel.movieList.observe(this, Observer {
//            Log.d(TAG, "onCreate: $it")
//            adapter.setMovieList(it)
//        })
//
//        viewModel.errorMessage.observe(this, Observer {
//
//        })
//        viewModel.getAllMovies()
    }

    companion object {
        @JvmStatic
        fun startIntern(context: Context) {
            Intent(context, InsuranceActivity::class.java)
                .run { context.startActivity(this) }
        }
    }
}