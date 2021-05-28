package com.truedigital.vhealth.model;

public class ItemAntiMedicine {
    private int id;
    private int CategoryId;
    private String Description;
    private boolean showIconRemove;
    private boolean showIconAdd;

    public ItemAntiMedicine() {
    }

    public ItemAntiMedicine(int id, int categoryId, String description, boolean showIconRemove, boolean showIconAdd) {
        this.id = id;
        CategoryId = categoryId;
        Description = description;
        this.showIconRemove = showIconRemove;
        this.showIconAdd = showIconAdd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isShowIconRemove() {
        return showIconRemove;
    }

    public void setShowIconRemove(boolean showIconRemove) {
        this.showIconRemove = showIconRemove;
    }

    public boolean isShowIconAdd() {
        return showIconAdd;
    }

    public void setShowIconAdd(boolean showIconAdd) {
        this.showIconAdd = showIconAdd;
    }
}
