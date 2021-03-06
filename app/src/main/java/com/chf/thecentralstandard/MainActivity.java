package com.chf.thecentralstandard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, DatePickerDialog.OnDateSetListener {
    public DrawerLayout mDrawerLayout;
    public ListView mDrawerList;
    public MyAdapter myAdapter;
    public ActionBarDrawerToggle drawerListener;
    public String[] mMenuItems;
    public String[] fragmentPages;
    public String appName;
    public String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMenuItems = getResources().getStringArray(R.array.menu_items);
        fragmentPages = getResources().getStringArray(R.array.fragment_pages);
        appName = getResources().getString(R.string.app_name);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.lvDrawer);
        myAdapter = new MyAdapter(this);
        mDrawerList.setAdapter(myAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        drawerListener = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(drawerListener);
        mDrawerList.setOnItemClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerListener.syncState();

        if (findViewById(R.id.flContent) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flContent, Fragment.instantiate(MainActivity.this, fragmentPages[0]))
                    .commit();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            setTitle(getSupportFragmentManager().getBackStackEntryAt((getSupportFragmentManager().getBackStackEntryCount()) - 1).getName());
        } else {
            setTitle(appName);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {
        selectItem(pos);
        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContent, Fragment.instantiate(MainActivity.this, fragmentPages[pos]))
                        .addToBackStack(mMenuItems[pos])
                        .commit();
            }
        });
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void selectItem(int position) {
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuItems[position]);
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        selectedDate = Integer.toString(monthOfYear + 1) + "/" + Integer.toString(dayOfMonth) + "/" + Integer.toString(year);
        Log.v("CHF1", selectedDate);
        Button dateButton = (Button) findViewById(R.id.button_requested_date);
        dateButton.setText(selectedDate);
    }

    public String getDate() {
        String date = selectedDate;
        return date;
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}

class MyAdapter extends BaseAdapter {
    Context context;
    String[] menuItems;
    int[] menuImages = {R.drawable.ic_launcher, R.drawable.bottle_icon, R.drawable.ticket_icon,
            R.drawable.ic_action_event, R.drawable.ic_action_about, R.drawable.ic_action_call};

    public MyAdapter(Context context) {
        this.context = context;
        menuItems = context.getResources().getStringArray(R.array.menu_items);
    }

    @Override
    public int getCount() {
        return menuItems.length;
    }

    @Override
    public Object getItem(int position) {
        return menuItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_row, parent, false);
        } else {
            row = convertView;
        }
        TextView titleTextView = (TextView) row.findViewById(R.id.text_view_1);
        ImageView iconImageView = (ImageView) row.findViewById(R.id.image_view_1);

        titleTextView.setText(menuItems[position]);
        iconImageView.setImageResource(menuImages[position]);

        return row;
    }
}