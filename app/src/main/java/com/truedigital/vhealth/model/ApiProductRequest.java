package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiProductRequest {

    /*
    {
      "Product": [],
      "OmiseToken": "tempor esse cupidatat proid",
      "DiscountCode": "id exercitation quis",
      "PaymentCode": "commodo labore",
      "ShippingLocations": "pariatur",
      "Name": "ut non",
      "IsPaymentCode": true,
      "AppointmentNumber": "laborum"
    }
    {
  "OmiseToken": "string",
  "DiscountCode": "string",
  "PaymentCode": "string",
  "ShippingLocations": "string",
  "Name": "string",
  "IsPaymentCode": true,
  "IsRememberCard": true,
  "CardId": 0,
  "Fingerprint": "string",
  "AppointmentNumber": "string",
  "Product": "string",
  "ProductList": [
    {
      "ProductId": 0,
      "Quantity": 0
    }
  ]
}
    */

    @SerializedName("PatientId") private int patientId;
    @SerializedName("Product") private String Product;
    @SerializedName("ProductList") private List<ItemProductDao> Products;
    @SerializedName("IsPaymentCode") private boolean IsPaymentCode;
    @SerializedName("DiscountCode") private String discountCode;
    @SerializedName("PaymentCode") private String paymentCode;
    @SerializedName("AppointmentNumber") private String AppointmentNumber;
    @SerializedName("ShippingLocations") private String ShippingLocations;
    @SerializedName("Name") private String Name;
    @SerializedName("ContactNo") private String ContactNo;

    @SerializedName("OmiseToken") private String omiseToken;
    @SerializedName("isRememberCard") private boolean isRememberCard;
    @SerializedName("Fingerprint") private String Fingerprint;
    @SerializedName("CardId") private int CardId;

    @SerializedName("Lat") private double latitude;
    @SerializedName("Long") private double longtitude;

    @SerializedName("SlipUrl") private String slipUrl;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public List<ItemProductDao> getProducts() {
        return Products;
    }

    public void setProducts(List<ItemProductDao> products) {
        Products = products;
    }

    public boolean isPaymentCode() {
        return IsPaymentCode;
    }

    public void setPaymentCode(boolean paymentCode) {
        IsPaymentCode = paymentCode;
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

    public String getAppointmentNumber() {
        return AppointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        AppointmentNumber = appointmentNumber;
    }

    public String getShippingLocations() {
        return ShippingLocations;
    }

    public void setShippingLocations(String shippingLocations) {
        ShippingLocations = shippingLocations;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getOmiseToken() {
        return omiseToken;
    }

    public void setOmiseToken(String omiseToken) {
        this.omiseToken = omiseToken;
    }

    public boolean isRememberCard() {
        return isRememberCard;
    }

    public void setRememberCard(boolean rememberCard) {
        isRememberCard = rememberCard;
    }

    public String getFingerprint() {
        return Fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        Fingerprint = fingerprint;
    }

    public int getCardId() {
        return CardId;
    }

    public void setCardId(int cardId) {
        CardId = cardId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getSlipUrl() {
        return slipUrl;
    }

    public void setSlipUrl(String slipUrl) {
        this.slipUrl = slipUrl;
    }
}
