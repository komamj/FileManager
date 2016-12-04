package com.koma.filemanager.base;

import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;

import com.koma.filemanager.R;
import com.koma.filemanager.util.LogUtils;

/**
 * Created by koma on 2016/12/4.
 */

public abstract class BaseMenuActivity extends BaseSwipeBackActivity {
    private static final String TAG = "BaseMenuActivity";
    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG,"onCreate");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                break;
            case R.id.action_sort:
                showSortPopUpMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSortPopUpMenu() {
        PopupMenu popup = new PopupMenu(mContext, findViewById(R.id.action_sort));
        popup.getMenuInflater().inflate(R.menu.menu_sort, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sort_by_type:
                        sortByType();
                        break;
                    case R.id.sort_by_name:
                        sortByName();
                        break;
                    case R.id.sort_by_size:
                        sortBySize();
                        break;
                    case R.id.sort_by_date:
                        sortByDate();
                        break;
                    default:
                }
                return true;
            }
        });

        popup.show();
    }
    protected abstract void sortByType();
    protected abstract void sortByName();
    protected abstract void sortBySize();
    protected abstract void sortByDate();
}
