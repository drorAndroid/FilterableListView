package com.dror.filterablelistviewlib.Main;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class FilterableListViewManager<HeaderViewHolder, SectionViewHolder> {
    private ListView mListView;
    private SearchView mSearchView;
    ArrayList<FilterableListViewItem> mFilterableListViewItems;
    private Application mContext;
    private int mHeaderItemLayout;
    private int mSectionItemLayout;
    private FilterableListViewAdapter<HeaderViewHolder, SectionViewHolder> filterableListViewAdapter;
    private OnHeaderItemLayoutInflated mOnHeaderItemLayoutInflated;
    private OnSectionItemLayoutInflated mOnSectionItemLayoutInflated;
    private OnHeaderItemVisible mOnHeaderItemVisible;
    private OnSectionItemVisible mOnSectionItemVisible;

    public enum FilterableListViewItemType {
        FILTERABLE_LIST_VIEW_ITEM,
        FILTERABLE_LIST_VIEW_HEADER
    }

    public interface OnHeaderItemLayoutInflated {
        void perform(View view, FilterableListViewAdapter.FLHeaderViewHolder flHeaderViewHolder);
    }

    public interface OnSectionItemLayoutInflated {
        void perform(View view, FilterableListViewAdapter.FLSectionViewHolder flSectionViewHolder);
    }

    public interface OnHeaderItemVisible {
        void perform(FilterableListViewAdapter.FLHeaderViewHolder flHeaderViewHolder, FilterableListViewItem filterableListViewItem);
    }

    public interface OnSectionItemVisible {
        void perform(FilterableListViewAdapter.FLSectionViewHolder flSectionViewHolder, FilterableListViewItem filterableListViewItem);
    }

    public FilterableListViewManager(@NonNull Application context, ListView listView,
                                     @NonNull SearchView searchView,
                                     @NonNull ArrayList<FilterableListViewItem> filterableListViewItems,
                                     int headerItemLayout, int sectionItemLayout,
                                     @NonNull FilterableListViewManager.OnHeaderItemLayoutInflated onHeaderItemLayoutInflated,
                                     @NonNull FilterableListViewManager.OnSectionItemLayoutInflated onSectionItemLayoutInflated,
                                     @NonNull FilterableListViewManager.OnHeaderItemVisible onHeaderItemVisible,
                                     @NonNull FilterableListViewManager.OnSectionItemVisible onSectionItemVisible
    ) {
        mContext = context;
        mListView = listView;
        mSearchView = searchView;
        mFilterableListViewItems = filterableListViewItems;
        mHeaderItemLayout = headerItemLayout;
        mSectionItemLayout = sectionItemLayout;
        mOnHeaderItemLayoutInflated = onHeaderItemLayoutInflated;
        mOnSectionItemLayoutInflated = onSectionItemLayoutInflated;
        mOnHeaderItemVisible = onHeaderItemVisible;
        mOnSectionItemVisible = onSectionItemVisible;
    }

    public void start() {
        filterableListViewAdapter = new FilterableListViewAdapter<>(mContext, mFilterableListViewItems,
                                        mHeaderItemLayout, mSectionItemLayout,
                                        mOnHeaderItemLayoutInflated, mOnSectionItemLayoutInflated,
                                        mOnHeaderItemVisible, mOnSectionItemVisible
                                        );
        mListView.setAdapter(filterableListViewAdapter);
        mSearchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterableListViewAdapter.getFilter().filter(newText);

                return false;
            }
        });
    }
}
