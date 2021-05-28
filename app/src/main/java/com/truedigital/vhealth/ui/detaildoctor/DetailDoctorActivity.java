package com.truedigital.vhealth.ui.detaildoctor;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiDoctorObject;
import com.truedigital.vhealth.model.ConsultDao;
import com.truedigital.vhealth.ui.adapter.ConsultAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import java.util.ArrayList;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class DetailDoctorActivity extends BaseMvpActivity<DetailDoctorActivityInterface.Presenter>
        implements DetailDoctorActivityInterface.View{


    private int doctorId;
    private TextView tvDoctorPrice;
    private TextView tvDoctorInfo;
    private TextView tvDoctorWorked;
    private TextView tvDoctorCertificate;
    private ImageView imgProfile;
    private TextView tvName;
    private Toolbar toolbar;
    private RecyclerView rv_consult;
    private ConsultAdapter adapter_consult;
    private ArrayList<ConsultDao> listDataConsult;
    private RecyclerView rv_doctor;
    private ConsultAdapter adapter_doctor;


    @Override
    protected DetailDoctorActivityInterface.Presenter createPresenter() {
        return DetailDoctorActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_detail_doctor;
    }

    @Override
    protected void bindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName = (TextView) findViewById(R.id.card_title);
        tvDoctorPrice = (TextView) findViewById(R.id.tvDoctorPrice);
        tvDoctorInfo = (TextView) findViewById(R.id.tvDoctoInfo);
        tvDoctorWorked = (TextView) findViewById(R.id.tvDoctoWorked);
        tvDoctorCertificate = (TextView) findViewById(R.id.tvDoctorEducation);
        imgProfile = (ImageView) findViewById(R.id.card_image);
        //rv_doctor = (RecyclerView) findViewById(R.id.recycler_view_doctor);
        rv_consult = (RecyclerView) findViewById(R.id.recycler_view_consult);
    }

    @Override
    protected void initInstance() {

    }

    @Override
    protected void setupView() {
        setupToolbar();
        setupRecyclerView();

        doctorId = getIntent().getIntExtra(AppConstants.EXTRA_DOCTOR_ID,0);
        Log.d("Detaile Doctor ","Doctor Id: "+doctorId);
        getPresenter().loadDoctorInfo(doctorId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkToken();
    }

    private void checkToken() {
        Log.e("","check token");
        String token = AppManager.getDataManager().getAccess_token();
        getPresenter().onCheckToken(token, new BaseMvpPresenter.OnTokenListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail() {

            }
        });
    }

    private void setupRecyclerView() {

        //adapter_doctor = new ConsultAdapter(rv_doctor.getContext());
        //rv_doctor.setAdapter(adapter_doctor);
        //rv_doctor.setHasFixedSize(true);
        //rv_doctor.setLayoutManager(new LinearLayoutManager(this));

        adapter_consult = new ConsultAdapter(rv_consult.getContext());
        rv_consult.setAdapter(adapter_consult);
        rv_consult.setHasFixedSize(true);
        rv_consult.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(null);
            }
        }
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void showDoctorInfo(ApiDoctorObject.AccountObject doctorInfo) {
        String specialty = "";
        String subSpecialty = "";
        int i = 0;
        for (ApiDoctorObject.AccountObject.Skill skill : doctorInfo.getSpecialtyList()) {
            if (i == 0) {
                specialty = skill.getDetail();
            } else {
                specialty = specialty + ", " + skill.getDetail();
            }
            i++;
        }

        i = 0;
        for (ApiDoctorObject.AccountObject.Skill skill : doctorInfo.getSubSpecialtyList()) {
            if (i == 0) {
                subSpecialty = skill.getDetail();
            } else {
                subSpecialty = subSpecialty + ", " + skill.getDetail();
            }
            i++;
        }

        String name = "" + doctorInfo.getTitleName() + " " + doctorInfo.getTitleStudy() + " " +
                doctorInfo.getName() + " " + doctorInfo.getLastname();

        tvName.setText(name);
        tvDoctorPrice.setText(getString(R.string.money_baht_B_int_per_minute, doctorInfo.getContactPrice()));
        tvDoctorInfo.setText(specialty);
        tvDoctorWorked.setText(doctorInfo.getCareerHistory());
        tvDoctorWorked.setText(getString(R.string.large_text));
        tvDoctorCertificate.setText(doctorInfo.getDiploma());
        Glide.with(this)
                .load(doctorInfo.getProfileImage()).asBitmap()
                .placeholder(R.drawable.ic_profile_user)
                .centerCrop()
                .into(imgProfile);

        /*
        ArrayList<ApiListDoctor.FilterList> listDoctor = new ArrayList<ApiListDoctor.FilterList>();
        ApiListDoctor.FilterList data;
        listDoctor.add(0,data);
        */

        listDataConsult = new ArrayList<ConsultDao>();
        listDataConsult.add(new ConsultDao(0,R.drawable.ic_action_video_grey,R.drawable.ic_action_video,getString(R.string.consult_video),doctorInfo.isVideo(),false));
        listDataConsult.add(new ConsultDao(1,R.drawable.ic_action_voice_grey,R.drawable.ic_action_voice,getString(R.string.consult_voice),doctorInfo.isVoice(),false));
        listDataConsult.add(new ConsultDao(2,R.drawable.ic_action_chat_grey,R.drawable.ic_action_chat,getString(R.string.consult_chat),doctorInfo.isChat(),false));
        updateConsult(listDataConsult);

    }



    @Override
    public void updateConsult(ArrayList<ConsultDao> listData) {
        adapter_consult.setListData(listData);
    }

    /*
    private void updateDetailDoctor(ApiDoctorObject.AccountObject accountObject) {
        name = accountObject.getTitleStudy() + accountObject.getTitleName() +
                accountObject.getName() + " " + accountObject.getLastname();
        txtName.setText(name);
        txtReviews.setText(getString(R.string.detail_doctor_reviews, accountObject.getViewer()));
        ratingBar.setRating(accountObject.getAmountRate());


        String specialty = "";
        String subSpecialty = "";
        int i = 0;
        for (ApiDoctorObject.AccountObject.Skill skill : accountObject.getSpecialtyList()) {
            if (i == 0) {
                specialty = skill.getDetail();
            } else {
                specialty = specialty + ", " + skill.getDetail();
            }
            i++;
        }

        i = 0;
        for (ApiDoctorObject.AccountObject.Skill skill : accountObject.getSubSpecialtyList()) {
            if (i == 0) {
                subSpecialty = skill.getDetail();
            } else {
                subSpecialty = subSpecialty + ", " + skill.getDetail();
            }
            i++;
        }

        if (accountObject.isChat()) {
            imgChat.setVisibility(View.VISIBLE);
        } else {
            imgChat.setVisibility(View.GONE);
        }
        if (accountObject.isVoice()) {
            imgVoice.setVisibility(View.VISIBLE);
        } else {
            imgVoice.setVisibility(View.GONE);
        }
        if (accountObject.isVideo()) {
            imgVideo.setVisibility(View.VISIBLE);
        } else {
            imgVideo.setVisibility(View.GONE);
        }

        txtPrice.setText(getString(R.string.money_baht_B_int, accountObject.getContactPrice()));
        txtSpecialty.setText(specialty);
        txtSubSpecialty.setText(subSpecialty);
        txtSpecialtyAll.setText(specialty);
        txtCertificate.setText(accountObject.getDiploma());
        txtWorkHistory.setText(accountObject.getCareerHistory());
        imgLanguage.setImageResource(LanguageIcon.getLanguageIcon(accountObject.getLanguageId()));
        Glide.with(activity)
                .load(accountObject.getProfileImage()).asBitmap()
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .centerCrop()
                .into(imgProfile);

    }
    */
}
