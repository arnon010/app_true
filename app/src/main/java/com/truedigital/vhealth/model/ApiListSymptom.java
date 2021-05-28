package com.truedigital.vhealth.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class ApiListSymptom extends NormalResponseObject{

    @SerializedName("List")
    private ArrayList<FilterList> filterArrayList;

    public ArrayList<FilterList> getFilterArrayList() {
        return filterArrayList;
    }

    public void setFilterArrayList(ArrayList<FilterList> filterArrayList) {
        this.filterArrayList = filterArrayList;
    }

    public class FilterList implements Serializable {

        @SerializedName("Id") private int id;
        @SerializedName("Title") private String title;
        @SerializedName("Icon") private String icon;
        @SerializedName("IsFavorite") private boolean favorite;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public boolean isFavorite() {
            return favorite;
        }

        public void setFavorite(boolean favorite) {
            this.favorite = favorite;
        }
    }
}
