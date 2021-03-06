package com.prototype.isbi.isbiprototype1;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ExpandableListView;

import com.prototype.isbi.isbiprototype1.databaseHandlers.Expenses;
import com.prototype.isbi.isbiprototype1.databaseHandlers.ExpensesHandler;
import com.prototype.isbi.isbiprototype1.expandableListAdapter.SixELAdapter;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by MRuto on 1/25/2017.
 */

public class ExpensesDisplayActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    public static String TAG = "ExpensesDisplayActivity";

    SixELAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    NavigationItemSelected navigationItemSelected = new NavigationItemSelected(this);

    ExpensesHandler edb = new ExpensesHandler(this);

    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new SixELAdapter(this, listDataHeader,
                listDataChild, "EXP", false);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                return false;
            }
        });



        for (int i = 0; i < listDataHeader.size(); i++){
            expListView.expandGroup(i);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        navigationItemSelected.NavigationItem(item);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        List<String> expenses = new ArrayList<>();
        List<String> salary = new ArrayList<>();
        List<String> rent = new ArrayList<>();
        List<String> utilities = new ArrayList<>();
        List<String> wage = new ArrayList<>();
        List<String> taxes = new ArrayList<>();
        List<String> airtime = new ArrayList<>();
        List<String> equipment = new ArrayList<>();
        List<String> other = new ArrayList<>();



        List<Expenses> allExpenses = edb.getAllExpenses();

        String title = "Type-Name-Total-Method-Info/Period-Date";
        //expenses.add(title);
        int count = 0;
        for (Expenses pd : allExpenses) {
            String log = pd.getType()+ "-" + pd.getName() + "-" + numberFormat.format(pd.getTotal()) + "-"
                    + pd.getPayment() + "-" + pd.getInfo()  + "-" + pd.getDate();
            Log.d(TAG, "Insert: " + log);
            expenses.add(log);
            count++;

            if (pd.getType().matches("Salary")){
                salary.add(log);
            } else if (pd.getType().matches("Rent")){
                rent.add(log);
            } else if (pd.getType().matches("Utilities")){
                utilities.add(log);
            } else if (pd.getType().matches("Wage")){
                wage.add(log);
            } else if (pd.getType().matches("Taxes")){
                taxes.add(log);
            } else if (pd.getType().matches("Airtime")){
                airtime.add(log);
            } else if (pd.getType().matches("Equipment")){
                equipment.add(log);
            } else if (pd.getType().matches("Other")){
                other.add(log);
            }
        }

//        listDataHeader.add("Expenses: " + count + "-" + title);

        if (salary.size() > 0){
            listDataHeader.add("Salary: " + salary.size() + "-" + title);
        }
        if (rent.size() > 0){
            listDataHeader.add("Rent: " + rent.size() + "-" + title);
        }
        if (utilities.size() > 0){
            listDataHeader.add("Utilities: " + utilities.size() + "-" + title);
        }
        if (wage.size() > 0){
            listDataHeader.add("Wage: " + wage.size() + "-" + title);
        }
        if (taxes.size() > 0){
            listDataHeader.add("Taxes: " + taxes.size() + "-" + title);
        }
        if (airtime.size() > 0){
            listDataHeader.add("Airtime: " + airtime.size() + "-" + title);
        }
        if (equipment.size() > 0){
            listDataHeader.add("Equipment: " + equipment.size() + "-" + title);
        }
        if (other.size() > 0){
            listDataHeader.add("Other: " + other.size() + "-" + title);
        }

//        listDataChild.put(listDataHeader.get(0), expenses); // Header, Child data

//        for (int i = 0; i < listDataHeader.size(); i++){
//            listDataChild.put(listDataHeader.get(1), expenses); // Header, Child data
//        }

        int i = 0;
        if (salary.size() > 0){
            listDataChild.put(listDataHeader.get(i), salary);
            i++;

        }
        if (rent.size() > 0){
            listDataChild.put(listDataHeader.get(i), rent);
            i++;

        }
        if (utilities.size() > 0){
            listDataChild.put(listDataHeader.get(i), utilities);
            i++;

        }
        if (wage.size() > 0){
            listDataChild.put(listDataHeader.get(i), wage);
            i++;

        }
        if (taxes.size() > 0){
            listDataChild.put(listDataHeader.get(i), taxes);
            i++;

        }
        if (airtime.size() > 0){
            listDataChild.put(listDataHeader.get(i), airtime);
            i++;

        }
        if (equipment.size() > 0){
            listDataChild.put(listDataHeader.get(i), equipment);
            i++;

        }
        if (other.size() > 0){
            listDataChild.put(listDataHeader.get(i), other);
            i++;

        }
        i = 0;

    }
}
