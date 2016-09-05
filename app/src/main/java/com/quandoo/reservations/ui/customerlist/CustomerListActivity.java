package com.quandoo.reservations.ui.customerlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        progressBar = (ProgressBar) findViewById(R.id.customer_list_progress);
        recyclerView = (RecyclerView) findViewById(R.id.customer_list_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        customerRecyclerAdapter = new CustomerRecyclerAdapter(this,new ArrayList<Customer>());
        recyclerView.setAdapter(customerRecyclerAdapter);

        presenter = new CustomerListPresenter(this);

        presenter.populateData();
    }

    @Override
    public void hideCustomerList() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCustomerList(List<Customer> customerList) {
        customerRecyclerAdapter.replaceData(customerList);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClicked(Customer customer) {

        Intent intent = new Intent(this, TableChoosingActivity.class);
        intent.putExtra(TableChoosingActivity.BUNDLE_CUSTOMERID,customer.getId());
        intent.putExtra(TableChoosingActivity.BUNDLE_TABLE_RESERVED_NUMBER,customer.getTableReservationNumber());
        startActivity(intent);
    }

}
