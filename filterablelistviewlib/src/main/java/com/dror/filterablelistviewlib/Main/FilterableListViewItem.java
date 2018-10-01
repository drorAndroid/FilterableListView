package com.dror.filterablelistviewlib.Main;

public class FilterableListViewItem {

    private String mItem;
    private FilterableListViewManager.FilterableListViewItemType mType;

    public FilterableListViewItem(String item, FilterableListViewManager.FilterableListViewItemType type) {
        mItem = item;
        mType = type;
    }

    public String getItem() {
        return mItem;
    }

    public FilterableListViewManager.FilterableListViewItemType getType() {
        return mType;
    }
}
