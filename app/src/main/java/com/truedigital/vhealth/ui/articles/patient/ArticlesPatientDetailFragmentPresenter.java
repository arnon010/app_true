package com.truedigital.vhealth.ui.articles.patient;

import android.util.Log;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiDoctorArticle;
import com.truedigital.vhealth.model.ApiDoctorArticleGroup;
import com.truedigital.vhealth.model.ApiDoctorArticleRequest;
import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.model.ItemArticleGroupDao;
import com.truedigital.vhealth.model.ItemArticleLangsDao;
import com.truedigital.vhealth.model.ItemArticleTagsDao;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.addRequestBody;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ArticlesPatientDetailFragmentPresenter extends BaseMvpPresenter<ArticlesPatientDetailFragmentInterface.View>
        implements ArticlesPatientDetailFragmentInterface.Presenter{

    private List<ItemArticleGroupDao> listData;

    public static ArticlesPatientDetailFragmentInterface.Presenter create(){
        return new ArticlesPatientDetailFragmentPresenter();
    }

    @Override
    public void loadData() {
        //setTempData();
        //
        //.. 0 -  new appointment
        //.. 1 -  history appointment
        //.. 2 -  cancel appointment
        int position = getView().getPosition();
        //getView().setData(listData);
        //callGetListAppointments(position);

    }

    @Override
    public void loadArticleGroup() {

        Call<ApiDoctorArticleGroup> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getArticlesGroups();
        call.enqueue(new Callback<ApiDoctorArticleGroup>() {
            @Override
            public void onResponse(Call<ApiDoctorArticleGroup> call, Response<ApiDoctorArticleGroup> response) {
                getView().setDataArticleGroup(response.body().getListdata());
            }

            @Override
            public void onFailure(Call<ApiDoctorArticleGroup> call, Throwable t) {

            }
        });
    }

    private String convert(ApiDoctorArticle data) {
        Gson gson = new Gson();
        Log.e("update ", gson.toJson(data));

        return gson.toJson(data);
    }
    @Override
    public void createArticle() {
        //getView().showSuccess();
        callServiceCreateArticle();
    }
    /*
    {"Article":
        {"ArticleGroupId":1,"Active": true,"SendNotification": true,"IsApprove":false,"IsBanner":true,"EffectiveDate":"2018-11-09T09:10:46","NotificationDate":"2018-11-09T09:10:46","EndOfEffectiveDate":"2019-05-09T09:10:46"},
        "ArticleLangs":
        [{"LanguageCode": "en-US","Title":"ยานัดหมอมีแก้ฝีแก้หิด","ShortDescription":"ยานัดหมอชิดแก้หิดแก้ฝี","Description":"แพ็คเกจนี้เพื่อลูกค้าที่เคารพรักทุกท่าน ซื้อ ๆ ไปเถอะครับเดี๋ยวก็ดีเอง"},{"LanguageCode": "th-TH","Title":"ยาหม่องตราลิงซ่อนหลังเสา","ShortDescription":"ของแท้ต้องไม่เห็นลิง","Description":"แพ็คเกจนี้เพื่อลูกค้าที่เคารพรักทุกท่าน ซื้อ ๆ ไปเถอะครับเดี๋ยวก็ดีเอง"}],
        "ArticleTags":[{"Tag":"ลิง"},{"Tag":"ฝี"}]}
        */

    private void callServiceCreateArticle() {
        getView().showLoading();

        String title = getView().getName();
        String description = getView().getDetail();
        int articleGroupId = getView().getArticleGroupId();

        List<ItemArticleLangsDao> listLangs = new ArrayList<>();
        ApiDoctorArticle apiDoctorArticle = new ApiDoctorArticle();
        ItemArticleLangsDao articleLangsDao = new ItemArticleLangsDao("th-TH",title,"",description);
        listLangs.add(articleLangsDao);

        ItemArticleDao articleDao = new ItemArticleDao();
        articleDao.setArticleGroupId(articleGroupId);
        articleDao.setActive(true);
        articleDao.setSendNotification(true);
        articleDao.setApprove(false);
        articleDao.setBanner(true);
        String strDate = ConvertDate.convertDateToServiceFormat(new Date());
        articleDao.setNotificationDate(strDate);
        articleDao.setEffectiveDate(strDate);
        //articleDao.setEndOfEffectiveDate(strDate);

        List<ItemArticleTagsDao> listTags = new ArrayList<>();
        listTags.add(new ItemArticleTagsDao(""));


        apiDoctorArticle.setArticle(articleDao);
        apiDoctorArticle.setArticleLangs(listLangs);
        apiDoctorArticle.setArticleTags(listTags);

        ApiDoctorArticleRequest apiDoctorArticleRequest = new ApiDoctorArticleRequest();
        apiDoctorArticleRequest.setArticle(apiDoctorArticle);


        String jsonBody = convert(apiDoctorArticle);

        Call<NormalResponseObject> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).postCreateArticles(addRequestBody(jsonBody));
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {

                    getView().showSuccess();
                    //Log.e(TAG,""+response.body().getErrorMessage());
                    //getView().openMainActivity();
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void getArticlesById(int id) {
        getView().showLoading();
        Call<ItemArticleDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getArticlesById(id);
        call.enqueue(new Callback<ItemArticleDao>() {
            @Override
            public void onResponse(Call<ItemArticleDao> call, Response<ItemArticleDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().setData(response.body());
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ItemArticleDao> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }

}
