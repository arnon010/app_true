package com.truedigital.vhealth.ui.articles.patient;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.model.ItemArticleGroupDao;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.LoadData;
import com.truedigital.vhealth.utils.MyDialog;

import java.util.ArrayList;
import java.util.List;

public class ArticlesPatientDetailFragment extends BaseMvpFragment<ArticlesPatientDetailFragmentInterface.Presenter>
        implements ArticlesPatientDetailFragmentInterface.View {

    public static final String TAG = "Articles";
    private static final String KEY_ARTICLE_ID = "KEY_ARTICLE_ID";

    private EditText edit_title;
    private EditText edit_detail;
    private ItemArticleGroupDao articleGroupData;
    private ItemArticleDao data;
    private TextView tv_title;
    private TextView tv_name;
    private TextView tv_date;
    private TextView tv_description;
    private ImageView imgProfile;
    private int articleId = 0;

    public ArticlesPatientDetailFragment() {
        super();
    }

    public static ArticlesPatientDetailFragment newInstance(int articleId) {
        ArticlesPatientDetailFragment fragment = new ArticlesPatientDetailFragment();
        Bundle args = new Bundle();
        args.putInt(AppConstants.EXTRA_ARTICLE_ID, articleId);
        fragment.setArguments(args);
        return fragment;
    }

    public static ArticlesPatientDetailFragment newInstance(ItemArticleDao data) {
        ArticlesPatientDetailFragment fragment = new ArticlesPatientDetailFragment();
        fragment.data = data;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ArticlesPatientDetailFragmentInterface.Presenter createPresenter() {
        return ArticlesPatientDetailFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_patient_articles_detail;
    }

    @Override
    public void bindView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
    }

    @Override
    public void setupInstance() {
    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.article_title, true);
    }

    @Override
    public void setupView() {
        articleId = getArguments().getInt(AppConstants.EXTRA_ARTICLE_ID, 0);
        showToolbar();

        if (data != null) {
            setData(data);
            articleId = data.getId();
        } else {
            getPresenter().getArticlesById(articleId);
        }
    }

    @Override
    public void initialize() {
    }

    @Override
    public void restoreView(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public int getPosition() {
        return getArguments().getInt(KEY_ARTICLE_ID, 0);
    }

    @Override
    public String getName() {
        return edit_title.getText().toString();
    }

    @Override
    public String getDetail() {
        return edit_detail.getText().toString();
    }

    @Override
    public int getArticleGroupId() {
        return articleGroupData.getId();
    }

    @Override
    public int getArticleId() {
        return articleId;
    }

    @Override
    public void openDetail(String appointment_no) {
        //((MainActivity)getActivity()).openAppointmentDetail(appointment_no);
    }

    @Override
    public void openDetail(int position, ItemAppointmentDao data, ImageView shareView) {
        ((MainActivity) getActivity()).openAppointmentDetail(position, data, shareView);
    }


    @Override
    public void setDataArticleGroup(List<ItemArticleGroupDao> listData) {
    }


    @Override
    public void setData(ItemArticleDao data) {
        this.data = data;
        updateView();
    }

    private void updateView() {
        Glide.with(getActivity())
                .load(data.getCoverImage()).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(imgProfile);

        tv_title.setText(data.getTitle());
        tv_name.setText(data.getDoctorName());
        if (data.getEffectiveDate() != null) {
            tv_date.setText(ConvertDate.StringDateServiceFormatDate(data.getEffectiveDate()));
        }

        //tv_description.setText(data.getDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_description.setText(Html.fromHtml(data.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv_description.setText(Html.fromHtml(data.getDescription()));
        }
    }

    @Override
    public String getDataFromFile(String filename) {
        String data = new LoadData(getActivity()).loadJSONFromAsset(filename);
        return data;
    }

    @Override
    public List<ItemAppointmentDao> getData(String json, boolean isShowLog) {
        List<ItemAppointmentDao> listData = new ArrayList<>();
        /*

        Gson gson = new Gson();
        ListAppointmentDao data = gson.fromJson(json, ListAppointmentDao.class);
        listData = data.getListData();

        if (isShowLog) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            gson = gsonBuilder.create();
            Log.d("", "load data" + gson.toJson(data));
        }
        */
        return listData;
    }


    @Override
    public void openWriteArticle() {

    }

    @Override
    public void openUploadClip() {

    }

    @Override
    public void openAppointment() {

        MainActivity.setCreateAppointment(true);
        ((MainActivity) getActivity()).openAppointmentCreateFromArticle(getDoctorId());
    }

    private int getDoctorId() {
        return data.getDoctorId();
    }

    @Override
    public String getDoctorName() {
        return getArguments().getString(AppConstants.EXTRA_DOCTOR_NAME);
    }

    @Override
    public void showSuccess() {
        new MyDialog(getActivity()).showSuccess(getString(R.string.article_success), new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                ((MainActivity) getActivity()).onBackPressed();
            }

            @Override
            public void onClickCancel() {

            }
        });
    }
}
