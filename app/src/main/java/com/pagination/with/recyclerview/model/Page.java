package com.pagination.with.recyclerview.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {

    @SerializedName("current_page_number")
    @Expose
    private Integer currentPageNumber;
    @SerializedName("total_number_of_items")
    @Expose
    private Integer totalNumberOfItems;
    @SerializedName("item_in_one_page")
    @Expose
    private Integer itemInOnePage;
    @SerializedName("total_number_of_pages")
    @Expose
    private Integer totalNumberOfPages;
    @SerializedName("users")
    @Expose
    private List<User> users = null;

    public Integer getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(Integer currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public Integer getTotalNumberOfItems() {
        return totalNumberOfItems;
    }

    public void setTotalNumberOfItems(Integer totalNumberOfItems) {
        this.totalNumberOfItems = totalNumberOfItems;
    }

    public Integer getItemInOnePage() {
        return itemInOnePage;
    }

    public void setItemInOnePage(Integer itemInOnePage) {
        this.itemInOnePage = itemInOnePage;
    }

    public Integer getTotalNumberOfPages() {
        return totalNumberOfPages;
    }

    public void setTotalNumberOfPages(Integer totalNumberOfPages) {
        this.totalNumberOfPages = totalNumberOfPages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
