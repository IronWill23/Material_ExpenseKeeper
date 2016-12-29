package com.library.ironwill.expensekeeper.model;

public class ItemCategory {
    private int categoryPic;
    private String categoryName;
    private String moneyNum;
    private int color;

    public ItemCategory(int categoryPic, String categoryName, String moneyNum, int color) {
        this.categoryPic = categoryPic;
        this.categoryName = categoryName;
        this.moneyNum = moneyNum;
        this.color = color;
    }

    public int getCategoryPic() {
        return categoryPic;
    }

    public void setCategoryPic(int categoryPic) {
        this.categoryPic = categoryPic;
    }

    public String getMoneyNum() {
        return moneyNum;
    }

    public void setMoneyNum(String moneyNum) {
        this.moneyNum = moneyNum;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
