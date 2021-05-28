package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

public class ApiPregnantHistoryCriteriaObject extends NormalResponseObject {

    @SerializedName("Data")
    private PregnantHistoryCriteriaObject criteria;

    public PregnantHistoryCriteriaObject getCriteria() {
        return criteria;
    }

    public void setCriteria(PregnantHistoryCriteriaObject criteria) {
        this.criteria = criteria;
    }
}
