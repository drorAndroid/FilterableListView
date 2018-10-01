# FilterableListViewWithHeadersAndSections
this is a common list view with headers and sections that can be filtered by text. 

Instalation:
1) download or clone repo.
2) go to /Users/dror/Downloads/FilterableListView/filterablelistviewlib/build/outputs/aar.
3) put filterablelistviewlib-debug.aar in your "libs" directory.
4) in your app gradle under "repositories" add implementation fileTree(dir: 'libs', include: ['*.aar']).

Usege:
create ListView and SearchView in your activity layout.
create header item layout (represent an header cell of the list view) - R.layout.header_item_layout in the example.
create section item layout (represent a section cell of the list view) - R.layout.section_item_layout in the example.

put this code in your activity which its layout contain the ListView and SearchView :
# this example show simple use of "FilterableListViewWithHeadersAndSections" for displaying a ListView of fruits and vegtables that can be filtered by SearchView.

public class MainActivity extends AppCompatActivity {
    # declare new ViewHolder class for your header and section list cell
    public class HeaderItemViewHolder {
        public TextView mHeaderItemTextView;
        HeaderItemViewHolder(TextView headerItemTextView) {
            mHeaderItemTextView = headerItemTextView;
        }
    };
    public class SectionItemViewHolder {
        public TextView mSectionItemTextView;
        SectionItemViewHolder(TextView sectionItemTextView) {
            mSectionItemTextView = sectionItemTextView;
        }
    };

    FilterableListViewManager<HeaderItemViewHolder, SectionItemViewHolder> filterableListViewManager;
    ListView listView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.myListView);
        searchView = findViewById(R.id.mySearchView);

        # this 4 CallBack methods required to instanciate "FilterableListViewManager".
        # OnHeaderItemLayoutInflated and OnSectionItemLayoutInflated implementation must include attaching the header/section item layouts.
        FilterableListViewManager.OnHeaderItemLayoutInflated onHeaderItemLayoutInflated = new FilterableListViewManager.OnHeaderItemLayoutInflated() {
            @Override
            public void perform(View view, FilterableListViewAdapter.FLHeaderViewHolder flHeaderViewHolder) {
                TextView textView = view.findViewById(R.id.headerTextView);
                flHeaderViewHolder.headerViewHolder = new HeaderItemViewHolder(textView);
            }
        };
        FilterableListViewManager.OnSectionItemLayoutInflated onSectionItemLayoutInflated = new FilterableListViewManager.OnSectionItemLayoutInflated() {
            @Override
            public void perform(View view, FilterableListViewAdapter.FLSectionViewHolder flSectionViewHolder) {
                TextView textView = view.findViewById(R.id.sectionTextView);
                flSectionViewHolder.sectionViewHolder = new SectionItemViewHolder(textView);
            }
        };
        
        # OnHeaderItemVisible and OnSectionItemVisible implementation must include setting the filterableListViewItem in your view.
        FilterableListViewManager.OnHeaderItemVisible onHeaderItemVisible = new FilterableListViewManager.OnHeaderItemVisible() {
            @Override
            public void perform(FilterableListViewAdapter.FLHeaderViewHolder flHeaderViewHolder, FilterableListViewItem filterableListViewItem) {
                HeaderItemViewHolder headerItemViewHolder = (HeaderItemViewHolder) flHeaderViewHolder.headerViewHolder;
                headerItemViewHolder.mHeaderItemTextView.setText(filterableListViewItem.getItem());
            }
        };
        FilterableListViewManager.OnSectionItemVisible onSectionItemVisible = new FilterableListViewManager.OnSectionItemVisible() {
            @Override
            public void perform(FilterableListViewAdapter.FLSectionViewHolder flSectionViewHolder, FilterableListViewItem filterableListViewItem) {
                SectionItemViewHolder sectionViewHolder = (SectionItemViewHolder)flSectionViewHolder.sectionViewHolder;
                sectionViewHolder.mSectionItemTextView.setText(filterableListViewItem.getItem());
            }
        };

        filterableListViewManager = new FilterableListViewManager<>(getApplication(), listView, searchView, getFilterableListViewItemList(), R.layout.header_item_layout, R.layout.section_item_layout, onHeaderItemLayoutInflated, onSectionItemLayoutInflated, onHeaderItemVisible, onSectionItemVisible);
        filterableListViewManager.start();
    }

    # an helper method for creating a ArrayList<FilterableListViewItem>.
    private ArrayList<FilterableListViewItem> getFilterableListViewItemList() {
        return  new ArrayList<FilterableListViewItem>() {
            {
                add(new FilterableListViewItem("Fruits", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_HEADER));
                add(new FilterableListViewItem("apples", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("apricots", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("avocados", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("bananas", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("berries", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("cherries", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("grapes", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("kiwi", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("lemons", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("limes", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("melons", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("nectarines", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("oranges", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("papaya", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("peaches", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("pears", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("plums", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("pomegranate", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("watermelon", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));

                add(new FilterableListViewItem("Vegetables", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_HEADER));
                add(new FilterableListViewItem("artichokes", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("asparagus", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("basil", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("beets", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("broccoli", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("cabbage", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("cauliflower", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("carrots", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("celery", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("chilies", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("chives", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("cilantro", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("corn", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("cucumbers", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("eggplant", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("garlic", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("lettuce", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("onions", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("peppers", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("potatoes", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("spinach", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("sprouts", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("squash", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("tomatoes", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
                add(new FilterableListViewItem("zucchini", FilterableListViewManager.FilterableListViewItemType.FILTERABLE_LIST_VIEW_ITEM));
            }
        };
    }
}

![alt text](https://raw.githubusercontent.com/drorAndroid/FilterableListViewWithHeadersAndSections/master/filterablelistviewlib/images/Screenshot_2018-10-01-22-40-03.png)
![alt text](https://raw.githubusercontent.com/drorAndroid/FilterableListViewWithHeadersAndSections/master/filterablelistviewlib/images/Screenshot_2018-10-01-22-40-14.png)
![alt text](https://raw.githubusercontent.com/drorAndroid/FilterableListViewWithHeadersAndSections/master/filterablelistviewlib/images/Screenshot_2018-10-01-22-40-24.png)


