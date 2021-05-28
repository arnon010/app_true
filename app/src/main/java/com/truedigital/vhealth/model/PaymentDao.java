package com.truedigital.vhealth.model;

public class PaymentDao {

    private int doctorId;
    private int productId;
    private String products;
    private String omiseToken;
    private String discountCode;
    private String paymentCode;
    private String shippingLocation;
    private String shippingName;
    private boolean isPaymentCode;
    private String appointmentNumber;
    private String ContactNo;
    //private boolean payByCreditCard;



    public PaymentDao() {

    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getOmiseToken() {
        return omiseToken;
    }

    public void setOmiseToken(String omiseToken) {
        this.omiseToken = omiseToken;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getShippingLocation() {
        return shippingLocation;
    }

    public void setShippingLocation(String shippingLocation) {
        this.shippingLocation = shippingLocation;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public boolean isPaymentCode() {
        return isPaymentCode;
    }

    public void setPaymentCode(boolean paymentCode) {
        isPaymentCode = paymentCode;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }
}
