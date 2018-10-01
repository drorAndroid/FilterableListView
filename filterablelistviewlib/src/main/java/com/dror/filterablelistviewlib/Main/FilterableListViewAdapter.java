package com.dror.filterablelistviewlib.Main;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

public class FilterableListViewAdapter<HeaderViewHolder extends Object, SectionViewHolder extends Object> extends BaseAdapter  implements Filterable {
    private Application context;
    private ArrayList<FilterableListViewItem> mList;
    private ArrayList<FilterableListViewItem> mOriginalList;

    private FilterableListViewManager.OnHeaderItemLayoutInflated mOnHeaderItemLayoutInflated;
    private FilterableListViewManager.OnSectionItemLayoutInflated mOnSectionItemLayoutInflated;
    private FilterableListViewManager.OnHeaderItemVisible mOnHeaderItemVisible;
    private FilterableListViewManager.OnSectionItemVisible mOnSectionItemVisible;

    private int mHeaderItemLayout;
    private int mSectionItemLayout;

    private ValueFilter valueFilter;

    public class FLHeaderViewHolder {
        public HeaderViewHolder headerViewHolder;
    }

    public class FLSectionViewHolder {
        public SectionViewHolder sectionViewHolder;
    }

    public FilterableListViewAdapter(@NonNull Application context, @NonNull ArrayList<FilterableListViewItem> list,
                                     int headerItemLayout, int sectionItemLayout,
                                     @NonNull FilterableListViewManager.OnHeaderItemLayoutInflated onHeaderItemLayoutInflated,
                                     @NonNull FilterableListViewManager.OnSectionItemLayoutInflated onSectionItemLayoutInflated,
                                     @NonNull FilterableListViewManager.OnHeaderItemVisible onHeaderItemVisible,
                                     @NonNull FilterableListViewManager.OnSectionItemVisible onSectionItemVisible) {
        this.context = context;
        this.mList = list;
        this.mOriginalList = list;
        this.mHeaderItemLayout = headerItemLayout;
        this.mSectionItemLayout = sectionItemLayout;
        this.mOnHeaderItemLayoutInflated = onHeaderItemLayoutInflated;
        this.mOnSectionItemLayoutInflated = onSectionItemLayoutInflated;
        this.mOnHeaderItemVisible = onHeaderItemVisible;
        this.mOnSectionItemVisible = onSectionItemVisible;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return ((FilterableListViewItem)getItem(position)).getType().ordinal();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(inflater == null)
            return null;

        if(mList.get(i).getType() == FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_HEADER) {
            FLHeaderViewHolder headerViewHolder;

            if (view == null) {
                headerViewHolder = new FLHeaderViewHolder();
                view = inflater.inflate(mHeaderItemLayout, null);

                mOnHeaderItemLayoutInflated.perform(view, headerViewHolder);
                view.setTag(headerViewHolder);
            } else {
                headerViewHolder = (FLHeaderViewHolder) view.getTag();
            }

            mOnHeaderItemVisible.perform(headerViewHolder, mList.get(i));
        }
        else {
            FLSectionViewHolder sectionViewHolder;

            if (view == null) {
                sectionViewHolder = new FLSectionViewHolder();
                view = inflater.inflate(mSectionItemLayout, null);

                mOnSectionItemLayoutInflated.perform(view, sectionViewHolder);
                view.setTag(sectionViewHolder);
            } else {
                sectionViewHolder = (FLSectionViewHolder) view.getTag();
            }

            mOnSectionItemVisible.perform(sectionViewHolder, mList.get(i));
        }

        return view;
    }


    @Override
    public Filter getFilter() {
        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {

        private int prevLength;
        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<FilterableListViewItem> filterList=new ArrayList<>();
                for(int i=0;i<mOriginalList.size();i++){
                    if((mOriginalList.get(i).getItem().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(mOriginalList.get(i));
                    }
                }
                results.count=filterList.size();
                results.values=filterList;

                prevLength = constraint.length();
            }
            else {
                results.count=mOriginalList.size();
                results.values=mOriginalList;
            }

            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mList = (ArrayList<FilterableListViewItem>)results.values;
            notifyDataSetChanged();
        }
    }
}
