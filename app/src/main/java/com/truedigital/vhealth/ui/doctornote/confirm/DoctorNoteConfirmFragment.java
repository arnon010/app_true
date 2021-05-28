package com.truedigital.vhealth.ui.doctornote.confirm;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemMedicineDao;
import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.model.PaymentDao;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.model.appointment.ItemAppointmentShortnoteDao;
import com.truedigital.vhealth.ui.adapter.ListMedicineAdapter;
import com.truedigital.vhealth.ui.adapter.ListProductAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.decoration.ListDividerItemDecoration;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.payment.PaymentFragment;
import com.truedigital.vhealth.utils.MyDialog;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class DoctorNoteConfirmFragment extends BaseMvpFragment<DoctorNoteConfirmFragmentInterface.Presenter>
        implements DoctorNoteConfirmFragmentInterface.View{

    private ItemAppointmentDao dataAppointment;
    private ItemAppointmentShortnoteDao data;
    private TextView tv_name;
    private Button btn_done;
    private TextView tv_appointment_time;
    private View content_exp_buy;
    private View content_payment;

    private ListProductAdapter adapter_product;
    private ListMedicineAdapter adapter_medicine;

    private RecyclerView recycler_view_product;
    private RecyclerView recycler_view_medicine;

    boolean isViewOnly;
    boolean isShowPayment = true;
    private TextView tv_reason;
    private int productTotalPrice;
    private int medicineTotalPrice;

    public DoctorNoteConfirmFragment() {
        super();
    }

    public static DoctorNoteConfirmFragment newInstance(ItemAppointmentDao dataAppointment, boolean isViewOnly) {
        DoctorNoteConfirmFragment fragment = new DoctorNoteConfirmFragment();
        fragment.dataAppointment = dataAppointment;
        fragment.isViewOnly = isViewOnly;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static DoctorNoteConfirmFragment newInstance(ItemAppointmentDao dataAppointment, boolean isViewOnly , boolean isShowPayment) {
        DoctorNoteConfirmFragment fragment = new DoctorNoteConfirmFragment();
        fragment.dataAppointment = dataAppointment;
        fragment.isViewOnly = isViewOnly;
        fragment.isShowPayment = isShowPayment;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public DoctorNoteConfirmFragmentInterface.Presenter createPresenter(){
        return DoctorNoteConfirmFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_doctor_note_confirm;
    }

    @Override
    public void bindView( View view ){
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        btn_done = (Button) view.findViewById(R.id.btn_done);
        tv_appointment_time = (TextView) view.findViewById(R.id.tv_appointment_time);
        tv_reason = (TextView) view.findViewById(R.id.tv_reason);

        recycler_view_product = (RecyclerView) view.findViewById(R.id.recycler_view_product);
        recycler_view_medicine = (RecyclerView) view.findViewById(R.id.recycler_view_medicine);

        content_exp_buy = (View)view.findViewById(R.id.content_exp_buy);
        content_payment = (View)view.findViewById(R.id.content_payment);
    }

    @Override
    public void setupInstance(){

    }

    @Override
    public void setupView(){
        showToolbar();
        showAppointmentInfo();
        setupRecycleView();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfirm();
            }
        });
        //..if Doctor Send Note Already
        //..isViewOnly = True
        if (isViewOnly) {
            btn_done.setVisibility(View.GONE);
        }

        if(!isShowPayment) {
            content_exp_buy.setVisibility(View.GONE);
            content_payment.setVisibility(View.GONE);
        }

        if (getPresenter() == null) createPresenter();
        getPresenter().loadData();
    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.appointment_tag_history,true);
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
    public void openConfirm() {
        MyDialog myDialog = new MyDialog(getActivity());
        myDialog.showConfirm(R.string.doctor_note_confirm_dialog_title, R.string.doctor_write_button_no, R.string.doctor_note_confirm_button_send, new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                //showSuccess();
                getPresenter().sendDoctorNote(getAppointmentNumber());
            }

            @Override
            public void onClickCancel() {

            }
        });

    }

    @Override
    public void showSuccess() {
        final MyDialog myDialog = new MyDialog(getActivity());
        myDialog.showSuccess(getString(R.string.doctor_note_confirm_success), new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                onSuccess();
            }

            @Override
            public void onClickCancel() {

            }
        });
    }

    @Override
    public void onSuccess() {
        ((MainActivity) getActivity()).onBackPressed();
    }

    @Override
    public void updateView() {
        tv_reason.setText(data.getShortNote());
    }

    @Override
    public void updateViewPayment() {
        //if (!dataAppointment.isSold()) {
        //    setupPayment(dataAppointment.getAppointmentNumber(), getProducts(),getTotalPrice(),0, dataAppointment.isSold());
        //}
        setupPayment(dataAppointment.getAppointmentNumber(), getProducts(),getTotalPrice(),0, dataAppointment.isSold());
    }

    private int getTotalPrice() {
        return (getMedicineTotalPrice() + getProductTotalPrice());
    }

    private void showAppointmentInfo() {
        if (AppManager.getDataManager().isDoctor()) {
            showUserInfo();
        }
        else {
            showDoctorInfo();
        }

        tv_appointment_time.setText(
                dataAppointment.getShowShortDate() + " / " +
                        dataAppointment.getShowShortTime() + " / " +
                        dataAppointment.getContactMinute() + " " +
                        getString(R.string.time_minute));
    }

    private void showDoctorInfo() {
        tv_name.setText( dataAppointment.getDoctorName());
    }

    private void showUserInfo() {
        tv_name.setText( dataAppointment.getPatientUsername());
    }

    private int getDoctorId() {
        return dataAppointment.getDoctorId();
    }


    @Override
    public void setData(ItemAppointmentShortnoteDao data) {
        if (data.getAppointmentNumber() == null) return ;
        this.data = data;
        updateView();
    }

    @Override
    public void setDataProduct(List<ItemProductDao> listData) {
        adapter_product.setListData(listData);
        setProductTotalPrice(listData);
    }

    @Override
    public void setDataMedicine(List<ItemMedicineDao> listData) {
        adapter_medicine.setListData(listData);
        setMedicineTotalPrice(listData);
        //..
        updateViewPayment();
    }

    private void setProductTotalPrice(List<ItemProductDao> listData) {
        int totalPrice = 0;
        int amount = 0;
        for (ItemProductDao item : listData) {
            if (item.getPromotionSellingPrice() > 0) {
                amount = item.getQuantity() * item.getPromotionSellingPrice();
            }
            else {
                amount = item.getQuantity() * item.getNormalSellingPrice();
            }
            totalPrice += amount;
        }
        productTotalPrice = totalPrice;
        //return totalPrice;
    }

    private void setMedicineTotalPrice(List<ItemMedicineDao> listData) {
        int totalPrice = 0;
        int amount = 0;
        for (ItemMedicineDao item : listData) {
            if (item.getPromotionSellingPrice() > 0) {
                amount = item.getQuantity() * item.getPromotionSellingPrice();
            }
            else {
                amount = item.getQuantity() * item.getNormalSellingPrice();
            }
            totalPrice += amount;
        }
        medicineTotalPrice = totalPrice;
        //return totalPrice;
    }

    public int getProductTotalPrice() {
        return productTotalPrice;
    }

    public int getMedicineTotalPrice() {
        return medicineTotalPrice;
    }

    @Override
    public String getAppointmentNumber() {
        return dataAppointment.getAppointmentNumber();
    }

    @Override
    public String getReason() {
        return tv_reason.getText().toString();
    }

    private void setupRecycleView() {
        setupRecycleView_product();
        setupRecycleView_medicine();
    }

    private boolean hideCardButton() {
      return (AppManager.getDataManager().isDoctor() ? true : false);
    }

    private void setupRecycleView_product() {
        adapter_product = new ListProductAdapter(recycler_view_product.getContext(), hideCardButton());
        recycler_view_product.setAdapter(adapter_product);

        recycler_view_product.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        int spacingInPixels = 8; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recycler_view_product.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        adapter_product.setOnClickListener(new ListProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //int productId = adapter_product.getData(position).getId();
                int productId = adapter_product.getData(position).getProductId();
                openProductDetail(productId);
                Log.e("Doctor Note","item click " + position +  " " + productId);
            }

            @Override
            public void onFavoriteClick(View view, int position) {

            }

            @Override
            public void onItemAddCartClick(View view, int position) {

            }
        });
    }

    public List<ItemProductDao> getProducts() {

        List<ItemProductDao> listProducts = new ArrayList<>();
        ItemProductDao item;
        for(ItemProductDao data : adapter_product.getListData()) {
            item = new ItemProductDao();
            item.setProductId( data.getProductId());
            item.setQuantity( data.getQuantity());
            listProducts.add(item);
        }

        for(ItemMedicineDao data : adapter_medicine.getListData()) {
            item = new ItemProductDao();
            item.setProductId( data.getProductId());
            item.setQuantity( data.getQuantity());
            listProducts.add(item);
        }
        return listProducts;
    }

    private void setupRecycleView_medicine() {
        adapter_medicine = new ListMedicineAdapter(recycler_view_medicine.getContext());
        recycler_view_medicine.setAdapter(adapter_medicine);

        recycler_view_medicine.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        int spacingInPixels = 8; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recycler_view_medicine.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recycler_view_medicine.addItemDecoration(new ListDividerItemDecoration(getActivity(), R.drawable.line_divider_black, false));
    }

    @Override
    public void openProductDetail(int id) {
        ((MainActivity)getActivity()).openProductDetail(id);
    }


    private void setupPayment(String appointmentNumber, List<ItemProductDao> listProducts, int amount, int doctorPrice, boolean isSold) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //Fragment fragmentPayment = PaymentFragment.newInstance(PaymentFragment.PAYMENT_MODE_PRODUCT, getDoctorId(), amount, doctorPrice);
        Fragment fragmentPayment = PaymentFragment.newInstance(PaymentFragment.PAYMENT_MODE_PRODUCT, listProducts , amount, appointmentNumber, isSold);

        if(fragmentPayment != null) {
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            ft.replace(R.id.content_payment, fragmentPayment);
            ft.commitAllowingStateLoss();
        }

        ((PaymentFragment) fragmentPayment).setOnCallback(new PaymentFragment.OnCallback() {
            @Override
            public void onBuyProductSuccess(PaymentDao data) {

            }

            @Override
            public void onCreateAppointmentSuccess(ApiAppointmentRequest appointmentRequest, double discountPrice) {
                //appointmentData = appointmentRequest;
                //showSuccess(appointmentData.getAppointmentNumber(), discountPrice);
            }
        });

    }
}

