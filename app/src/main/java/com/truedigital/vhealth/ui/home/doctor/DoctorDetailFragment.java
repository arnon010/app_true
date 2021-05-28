package com.truedigital.vhealth.ui.home.doctor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.ui.adapter.DoctorAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ContactTypeUtil;
import com.truedigital.vhealth.utils.LanguageSkillsUtil;

public class DoctorDetailFragment extends BaseMvpFragment<DoctorDetailFragmentInterface.Presenter>
        implements DoctorDetailFragmentInterface.View {

    private TextView tvName;
    private TextView tvPrice;
    private TextView tvTitleBranch;
    private TextView tvSubTitleBranch;
    private TextView tvTitleSpecial;
    private TextView tvSubTitleSpecial;
    private TextView tvTitleEducation;
    private TextView tvSubTitleEducation;
    private TextView tvTitleCareer;
    private TextView tvSubTitleCareer;

    private ItemDoctorDao data;
    private Button btnAppointment;
    private LinearLayout lloThumbnail;
    private int doctorId;
    private ImageView imgProfile;
    private ImageView imgFavorite;
    private RecyclerView recyclerView;
    private DoctorAdapter adapter;
    private ImageView ivChat;
    private ImageView ivVoice;
    private ImageView ivVideo;
    private ImageView ivLangTh;
    private ImageView ivLangEn;
    private boolean isDoctorFavorite;
    private ImageView ivBack;
    private ImageView img_articles;

    public DoctorDetailFragment() {
        super();
    }

    public static DoctorDetailFragment newInstance(int doctorId) {
        DoctorDetailFragment fragment = new DoctorDetailFragment();
        Bundle args = new Bundle();
        args.putInt(AppConstants.EXTRA_DOCTOR_ID, doctorId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public DoctorDetailFragmentInterface.Presenter createPresenter() {
        return DoctorDetailFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_doctor_detail;
    }

    @Override
    public void bindView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);

        ivBack = view.findViewById(R.id.ivBack);
        imgProfile = view.findViewById(R.id.imgProfile);
        imgFavorite = view.findViewById(R.id.imgFavorite);
        img_articles = view.findViewById(R.id.img_article);
        //progressBar = view.findViewById(R.id.progressBar);
        lloThumbnail = view.findViewById(R.id.layout_thumbnail);
        tvName = view.findViewById(R.id.tv_name);
        //tv_title = view.findViewById(R.id.tv_title);
        //tv_subtitle = view.findViewById(R.id.tv_subtitle);
        tvPrice = view.findViewById(R.id.tv_price);

        tvTitleBranch = view.findViewById(R.id.tv_title);
        tvSubTitleBranch = view.findViewById(R.id.tv_subtitle);

        btnAppointment = view.findViewById(R.id.btn_appointment);

        //tvTitleBranch = view.findViewById(R.id.doctor_branch).findViewById(R.id.tv_title);
        //tvSubTitleBranch = view.findViewById(R.id.doctor_branch).findViewById(R.id.tv_subtitle);

        tvTitleSpecial = view.findViewById(R.id.doctor_specialist).findViewById(R.id.tv_title);
        tvSubTitleSpecial = view.findViewById(R.id.doctor_specialist).findViewById(R.id.tv_subtitle);

        tvTitleEducation = view.findViewById(R.id.doctor_education).findViewById(R.id.tv_title);
        tvSubTitleEducation = view.findViewById(R.id.doctor_education).findViewById(R.id.tv_subtitle);

        tvTitleCareer = view.findViewById(R.id.doctor_career_history).findViewById(R.id.tv_title);
        tvSubTitleCareer = view.findViewById(R.id.doctor_career_history).findViewById(R.id.tv_subtitle);

        ivChat = view.findViewById(R.id.item_chat);
        ivVoice = view.findViewById(R.id.item_voice);
        ivVideo = view.findViewById(R.id.item_video);
        ivLangTh = view.findViewById(R.id.item_lang_th);
        ivLangEn = view.findViewById(R.id.item_lang_en);
    }

    @Override
    public void setupInstance() {
    }

    private void showToolbar() {
        ((MainActivity) getActivity()).hideToolbar();
    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuHomeSelected();

        showToolbar();

        doctorId = getDoctorId();
        getPresenter().loadData();

        lloThumbnail.setVisibility(View.GONE);
        imgFavorite.setOnClickListener(onFavoriteClickListerner());
        img_articles.setOnClickListener(onArticlesClickListerner());
        btnAppointment.setOnClickListener(v -> openAppointmentCreate(data));
        ivBack.setOnClickListener(onBackButtonClickListener());

        if (AppManager.getDataManager().isDoctor()) {
            img_articles.setVisibility(View.GONE);
        }

    }

    private View.OnClickListener onBackButtonClickListener() {
        return v -> getActivity().onBackPressed();
    }

    private View.OnClickListener onFavoriteClickListerner() {
        return v -> getPresenter().onFavoriteClick(getDoctorId(), isDoctorFavorite());
    }

    private View.OnClickListener onArticlesClickListerner() {
        return v -> openListArticles();
    }

    @Override
    public void openListArticles() {
        ((MainActivity) getActivity()).openListArticles(data.getDoctorId(), data.getName(), 0);
    }

    @Override
    public boolean isDoctorFavorite() {
        return isDoctorFavorite;
    }

    @Override
    public void setDoctorFavorite(boolean doctorFavorite) {
        isDoctorFavorite = doctorFavorite;
        updateFavorite(isDoctorFavorite);
    }

    @Override
    public void updateFavorite(boolean isFavorite) {
        int resDrawer = (isFavorite) ? R.drawable.ic_favorite_green : R.drawable.ic_favorite_white;
        Glide.with(getActivity())
                .load(resDrawer).asBitmap()
                .error(R.drawable.ic_favorite_white)
                .placeholder(R.drawable.ic_favorite_white)
                .into(imgFavorite);
    }

    private void updateView() {
        Glide.with(getActivity())
                .load(data.getProfileImage()).asBitmap()
                .error(R.drawable.ic_profile_user)
                .placeholder(R.drawable.ic_profile_user)
                .into(imgProfile);

        isDoctorFavorite = false;
        if (data.getDoctorLangInformations() != null && data.getDoctorLangInformations().size() > 0) {
            if (data.getDoctorLangInformations().get(0).isFavorite()) {
                isDoctorFavorite = true;
            }
        }
        updateFavorite(isDoctorFavorite);

        tvName.setText(data.getName());
        tvPrice.setText(data.getPricePerMinuteFormat());

        new ContactTypeUtil(data.getContactTypes()).show(ivChat, ivVoice, ivVideo);
        new LanguageSkillsUtil(data.getLanguageSkills()).show(ivLangTh, ivLangEn);

        setupBranch();
        setupSpecial();
        setupEducation();
        setupCareer();

        if (MainActivity.isCreateAppointment()) {
            MainActivity.setCreateAppointment(false);
            btnAppointment.performClick();
        }
    }

    private void setupBranch() {
        tvTitleBranch.setText("" + data.getCategoryName());

        tvSubTitleBranch.setText("N/A");
        String strSubtitle = "";
        if (data.getSubCategoryName() != null) {
            for (String msg : data.getSubCategoryName()) {
                if (strSubtitle.equals("")) {
                    strSubtitle = msg;
                } else {
                    strSubtitle = strSubtitle + " , " + msg;
                }
            }
            tvSubTitleBranch.setText("" + strSubtitle);
        }
    }

    private void setupSpecial() {
        tvTitleSpecial.setText(R.string.doctor_detail_specialize);
        tvSubTitleSpecial.setText("" + data.getSpecialityDescription());
    }

    private void setupEducation() {
        tvTitleEducation.setText(R.string.doctor_detail_certificate);
        tvSubTitleEducation.setText("N/A");
        if (data.getDoctorLangInformations() != null) {
            String msg = "" + data.getDoctorLangInformations().get(0).getCertificateDetail();
            tvSubTitleEducation.setText(msg);
        }
    }

    private void setupCareer() {
        tvTitleCareer.setText(R.string.doctor_detail_career);
        tvSubTitleCareer.setText("N/A");
        if (data.getDoctorLangInformations() != null) {
            String msg = "" + data.getDoctorLangInformations().get(0).getCareerHistory();
            tvSubTitleCareer.setText(msg);
        }
    }

    @Override
    public void initialize() {
    }

    @Override
    public void openAppointmentCreate(ItemDoctorDao data) {
        ((MainActivity) getActivity()).openAppointmentCreate(data);
    }

    @Override
    public int getDoctorId() {
        return getArguments().getInt(AppConstants.EXTRA_DOCTOR_ID);
    }

    @Override
    public void setData(ItemDoctorDao data) {
        this.data = data;
        updateView();
    }
}
