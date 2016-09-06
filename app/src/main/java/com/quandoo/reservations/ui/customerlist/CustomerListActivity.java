package com.quandoo.reservations.ui.customerlist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.quandoo.reservations.R;
import com.quandoo.reservations.data.entities.Customer;
import com.quandoo.reservations.ui.customerlist.adapter.CustomerRecyclerAdapter;
import com.quandoo.reservations.ui.tablechoosing.TableChoosingActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity implements CustomerRecyclerAdapter.ItemClickListener, CustomerListView {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private CustomerListContract presenter;
    private ProgressBar progressBar;
    private CustomerRecyclerAdapter customerRecyclerAdapter;
    private TextView noDataTxtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        progressBar = (ProgressBar) findViewById(R.id.customer_list_progress);
        recyclerView = (RecyclerView) findViewById(R.id.customer_list_recyclerview);
        noDataTxtView = (TextView) findViewById(R.id.customer_list_nodata);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        customerRecyclerAdapter = new CustomerRecyclerAdapter(this, new ArrayList<Customer>());
        recyclerView.setAdapter(customerRecyclerAdapter);

        presenter = new CustomerListPresenter(this);

        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (intent!=null && Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);

            if(query!=null && !query.isEmpty()){
                presenter.search(query);
            }
        }
        else{
            presenter.startClearReservationsAlarm(this);
            presenter.populateData();
        }
    }

    @Override
    public void hideCustomerList() {
        noDataTxtView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCustomerList(List<Customer> customerList) {
        noDataTxtView.setVisibility(View.GONE);
        customerRecyclerAdapter.replaceData(customerList);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoDataError() {
        noDataTxtView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClicked(Customer customer) {

        Intent intent = new Intent(this, TableChoosingActivity.class);
        intent.putExtra(TableChoosingActivity.BUNDLE_CUSTOMERID, customer.getId());
        intent.putExtra(TableChoosingActivity.BUNDLE_TABLE_RESERVED_NUMBER, customer.getTableReservationNumber());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        MenuItem menuItem = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        presenter.populateData();
                        return true;
                    }
                }

        );

        return true;
    }
}

