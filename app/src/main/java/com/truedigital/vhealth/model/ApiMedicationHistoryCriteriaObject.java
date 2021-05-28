package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiMedicationHistoryCriteriaObject extends NormalResponseObject {

    @SerializedName("Data")
    private MedicationHistoryCriteriaObject criteria;

    public MedicationHistoryCriteriaObject getCriteria() {
        return criteria;
    }

    public void setCriteria(MedicationHistoryCriteriaObject criteria) {
        this.criteria = criteria;
    }
}