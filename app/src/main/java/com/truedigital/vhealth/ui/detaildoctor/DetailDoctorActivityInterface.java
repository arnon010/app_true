package com.truedigital.vhealth.ui.detaildoctor;

import com.truedigital.vhealth.model.ApiDoctorObject;
import com.truedigital.vhealth.model.ConsultDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class DetailDoctorActivityInterface {
    public interface View extends BaseMvpInterface.View {

        void showDoctorInfo(ApiDoctorObject.AccountObject doctorInfo);
        void updateConsult(ArrayList<ConsultDao> listData);
    }
    public interface Presenter extends BaseMvpInterface.Presenter<DetailDoctorActivityInterface.View> {
        void loadDoctorInfo(int doctorId);
    }
}
