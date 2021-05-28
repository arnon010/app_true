package com.truedigital.vhealth.ui.ehr.doctornote;


import com.truedigital.vhealth.model.BankAccountObject;
import com.truedigital.vhealth.model.DoctorNoteObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class DoctorNoteDetailFragmentInterface {
    public interface View extends BaseMvpInterface.View{
        void setDetailCoupons(float discountPrice, boolean isProduct);
        void setBankAccountList(ArrayList<BankAccountObject> dataList);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<DoctorNoteDetailFragmentInterface.View>{
        void getDetailCoupons(DoctorNoteObject data, String couponsCode, final boolean isProduct);
        void getBankAccountList();
    }
}
