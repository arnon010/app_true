package com.truedigital.vhealth.ui.articles.detail;


import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemArticleGroupDao;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.adapter.ListArticleGroupAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.dialog.ArticleBottomSheetFragment;
import com.truedigital.vhealth.ui.dialog.GridBottomSheetFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.LoadData;
import com.truedigital.vhealth.utils.MyDialog;

import java.util.ArrayList;
import java.util.List;

public class ArticlesDetailFragment extends BaseMvpFragment<ArticlesDetailFragmentInterface.Presenter>
        implements ArticlesDetailFragmentInterface.View{

    public static final String TAG = "Articles";
    private static final String KEY_POSITION = "POSITION";
    private RecyclerView recyclerView;
    private ListArticleGroupAdapter adapter;
    private int position;
    private boolean isUploadArticle;

    private LinearLayout layoutBottomSheet;
    private BottomSheetBehavior sheetBehavior;
    private RelativeLayout layoutAddArticle;
    private ArticleBottomSheetFragment articlesBottomSheet;
    private TextView tv_category;
    private ImageView imgUploadArticle;
    private ImageView imgUploadClip;
    private EditText edit_title;
    private EditText edit_detail;
    private int articleGroupId = 0;
    private RelativeLayout layout_category;
    private GridBottomSheetFragment bottomSheetFragment;
    private ItemArticleGroupDao articleGroupData;
    private Button btnDone;


    public ArticlesDetailFragment() {
        super();
    }

    public static ArticlesDetailFragment newInstance(int articleId) {
        ArticlesDetailFragment fragment = new ArticlesDetailFragment();
        Bundle args = new Bundle();
        //..posion 0 = upload article
        //..posion 1 = upload clip
        args.putInt(KEY_POSITION,articleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ArticlesDetailFragmentInterface.Presenter createPresenter(){
        return ArticlesDetailFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_articles_detail;
    }

    @Override
    public void bindView( View view ){
        btnDone = (Button) view.findViewById(R.id.btn_done);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layout_category = (RelativeLayout) view.findViewById(R.id.layout_articles_category);
        imgUploadArticle = (ImageView) view.findViewById(R.id.image_upload_article);
        imgUploadClip = (ImageView) view.findViewById(R.id.image_upload_clip);
        edit_title  = (EditText) view.findViewById(R.id.edit_title);
        edit_detail  = (EditText) view.findViewById(R.id.edit_detail);
        tv_category = (TextView) view.findViewById(R.id.tv_category);
    }

    @Override
    public void setupInstance(){


        /*
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        int spacingInPixels = 8; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter.setOnClickListener(new ListAppointmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ImageView shareView) {

                openDetail( getPosition(), getPresenter().loadData(position), shareView);
            }

            @Override
            public void onFavoriteClick(View view, int position) {

            }
        });

        */
        setupArticleGroup();
    }

    private void setupArticleGroup() {
        adapter = new ListArticleGroupAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        getPresenter().loadArticleGroup();

        adapter.setOnClickListener(new ListArticleGroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ItemArticleGroupDao data) {
                Log.e(TAG,data.getId() + " " + data.getGroupName());
                updateCategory(data);
                bottomSheetFragment.dismiss();
            }
        });
    }

    private void updateCategory(ItemArticleGroupDao data) {
        articleGroupData = data;
        tv_category.setText(data.getGroupName());
    }


    @Override
    public void setupView(){

        position = getArguments().getInt(KEY_POSITION);
        isUploadArticle = (position == 0) ? true : false;
        if (isUploadArticle) {
            showToolbar(R.string.article_doctor_bottom_write);
            imgUploadClip.setVisibility(View.GONE);
            tv_category.setText(R.string.article_write_category);
            edit_title.setHint(R.string.article_write_name);
            edit_detail.setHint(R.string.article_write_detail);
        }
        else {
            showToolbar(R.string.article_doctor_bottom_upload_clip);
            imgUploadClip.setVisibility(View.VISIBLE);
            tv_category.setText(R.string.article_clip_category);
            edit_title.setHint(R.string.article_clip_name);
            edit_detail.setHint(R.string.article_clip_detail);
        }

        //getPresenter().loadData();

        //layoutAddArticle.setOnClickListener(addNewArticleListener());
        layout_category.setOnClickListener(onCategoryListener());
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().createArticle();
            }
        });
    }

    private View.OnClickListener onCategoryListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetFragment = GridBottomSheetFragment.newInstance(R.layout.fragment_list_bottom_sheet,(int) CommonUtils.convertDpToPixel(getActivity(), 200), adapter);
                bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        };
    }

    private View.OnClickListener addNewArticleListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //articlesBottomSheet = ArticleBottomSheetFragment.newInstance(R.layout.bottomsheet_article, (int) CommonUtils.convertDpToPixel(getActivity(), 200), adapter);
                //articlesBottomSheet.show(getActivity().getSupportFragmentManager(), articlesBottomSheet.getTag());

                Log.e(TAG,"add article click");
                ArticleBottomSheetFragment bottomSheetFragment = new ArticleBottomSheetFragment();
                bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());

                bottomSheetFragment.setOnItemSelectListerner(new ArticleBottomSheetFragment.OnItemSelectListerner() {
                    @Override
                    public void onClickWrite() {
                        Log.e(TAG,"write click");
                        openWriteArticle();
                    }

                    @Override
                    public void onClickUpload() {
                        Log.e(TAG,"upload click");
                        openUploadClip();
                    }
                });
            }
        };
    }

    private void showToolbar(int resId) {
        ((MainActivity) getActivity()).showToolbar(resId,true);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void restoreView( Bundle savedInstanceState ){

    }

    @Override
    public void onSaveInstanceState( Bundle outState ){
        super.onSaveInstanceState( outState );
    }

    @Override
    public void onRestoreInstanceState( Bundle savedInstanceState ){
        super.onRestoreInstanceState( savedInstanceState );
    }

    @Override
    public int getPosition() {
        return getArguments().getInt(KEY_POSITION,0);
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
    public void openDetail(String appointment_no) {
        //((MainActivity)getActivity()).openAppointmentDetail(appointment_no);
    }

    @Override
    public void openDetail(int position, ItemAppointmentDao data, ImageView shareView) {
        ((MainActivity)getActivity()).openAppointmentDetail(position,data, shareView);
    }


    @Override
    public void setDataArticleGroup(List<ItemArticleGroupDao> listData) {
        adapter.setListData(listData);
    }

    @Override
    public void setData(ItemArticleGroupDao data) {

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

