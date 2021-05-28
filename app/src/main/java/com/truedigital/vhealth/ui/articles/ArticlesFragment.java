package com.truedigital.vhealth.ui.articles;


import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.ui.adapter.ListArticlesAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.dialog.ArticleBottomSheetFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.List;

public class ArticlesFragment extends BaseMvpFragment<ArticlesFragmentInterface.Presenter>
        implements ArticlesFragmentInterface.View{

    public static final String TAG = "Articles";
    private static final String KEY_POSITION = "POSITION";
    private RecyclerView recyclerView;
    private ListArticlesAdapter adapter;
    private int position;

    private LinearLayout layoutBottomSheet;
    private BottomSheetBehavior sheetBehavior;
    private RelativeLayout layoutAddArticle;
    private ArticleBottomSheetFragment articlesBottomSheet;

    public ArticlesFragment() {
        super();
    }

    public static ArticlesFragment newInstance(int doctorId, String doctorName, int articleGroupId) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle args = new Bundle();

        args.putInt(AppConstants.EXTRA_DOCTOR_ID,doctorId);
        args.putString(AppConstants.EXTRA_DOCTOR_NAME,doctorName);
        args.putInt(AppConstants.EXTRA_ARTICLE_GROUP_ID,articleGroupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ArticlesFragmentInterface.Presenter createPresenter(){
        return ArticlesFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_articles;
    }

    @Override
    public void bindView( View view ){
        recyclerView = view.findViewById(R.id.recycler_view);
        //layoutBottomSheet = view.findViewById(R.id.bottom_sheet);
        layoutAddArticle = (RelativeLayout) view.findViewById(R.id.layout_add_articles);
    }

    @Override
    public void setupInstance(){
        adapter = new ListArticlesAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        int spacingInPixels = 8; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter.setOnClickListener(new ListArticlesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ItemArticleDao data) {
                //openDetail(adapter.getData(position).getId());
                openDetail(adapter.getData(position));
            }
        });

        //sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
    }

    @Override
    public void setupView(){
        showToolbar();
        getPresenter().loadData();

        layoutAddArticle.setOnClickListener(addNewArticleListener());

        if (AppManager.getDataManager().isDoctor()) {
            layoutAddArticle.setVisibility(View.VISIBLE);
        }
        else {
            layoutAddArticle.setVisibility(View.GONE);
        }
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

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.article_title,true);
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
    public int getDoctorId() {
        return getArguments().getInt(AppConstants.EXTRA_DOCTOR_ID,0);
    }

    @Override
    public String getDoctorName() {
        return getArguments().getString(AppConstants.EXTRA_DOCTOR_NAME);
    }

    @Override
    public int getArticleGroupId() {
        return getArguments().getInt(AppConstants.EXTRA_ARTICLE_GROUP_ID,0);
    }

    @Override
    public void openDetail(int articleId) {
        ((MainActivity)getActivity()).openArticleDetail(articleId);
    }

    @Override
    public void openDetail(ItemArticleDao data) {
        ((MainActivity)getActivity()).openArticleDetail(data);
    }

    @Override
    public void setData(List<ItemArticleDao> listData) {
        adapter.setListData(listData);
    }


    @Override
    public void openWriteArticle() {
        //((MainActivity)getActivity()).openWriteArticle();
    }

    @Override
    public void openUploadClip() {
        //((MainActivity)getActivity()).openUploadClip();
    }
}

