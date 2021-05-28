package com.truedigital.vhealth.ui.articles.similar;


import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.ui.adapter.ListArticlesAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.List;

public class ArticlesSimilarFragment extends BaseMvpFragment<ArticlesSimilarFragmentInterface.Presenter>
        implements ArticlesSimilarFragmentInterface.View{

    public static final String TAG = "ArticlesSimilarFragment";
    private RecyclerView recyclerView;
    private ListArticlesAdapter adapter;

    public ArticlesSimilarFragment() {
        super();
    }

    public static ArticlesSimilarFragment newInstance(int articleId) {
        ArticlesSimilarFragment fragment = new ArticlesSimilarFragment();
        Bundle args = new Bundle();
        args.putInt(AppConstants.EXTRA_ARTICLE_ID,articleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ArticlesSimilarFragmentInterface.Presenter createPresenter(){
        return ArticlesSimilarFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_articles_similar;
    }

    @Override
    public void bindView( View view ){
        recyclerView = view.findViewById(R.id.recycler_view);
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
    }

    @Override
    public void setupView(){
        getPresenter().loadData();
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
    public int getArticleId() {
        return getArguments().getInt(AppConstants.EXTRA_ARTICLE_ID,0);
    }

    @Override
    public void openDetail(int articleId) {
        //((MainActivity)getActivity()).openArticleDetail(articleId);
        ((MainActivity)getActivity()).openSimilarArticle(articleId);
    }

    @Override
    public void openDetail(ItemArticleDao data) {
        getActivity().onBackPressed();
        ((MainActivity)getActivity()).openArticleDetail(data);
    }

    @Override
    public void setData(List<ItemArticleDao> listData) {
        adapter.setListData(listData);
    }

}

