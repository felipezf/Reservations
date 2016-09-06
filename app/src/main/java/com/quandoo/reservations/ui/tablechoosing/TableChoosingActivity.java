package com.quandoo.reservations.ui.tablechoosing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quandoo.reservations.R;
import com.quandoo.reservations.data.entities.Table;
import com.quandoo.reservations.ui.tablechoosing.adapter.TableRecyclerAdapter;

import java.util.List;

public class TableChoosingActivity extends AppCompatActivity implements TableChoosingView,TableRecyclerAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private TableChoosingContract presenter;
    private ProgressBar progressBar;
    private TableRecyclerAdapter tableRecyclerAdapter;
    private TextView noDataTxtView;

    public static final String BUNDLE_CUSTOMERID = "customerId";
    public static final String BUNDLE_TABLE_RESERVED_NUMBER = "tableReservedNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_choosing);

        progressBar = (ProgressBar) findViewById(R.id.table_choosing_progress);
        noDataTxtView = (TextView) findViewById(R.id.table_choosing_nodata);
        recyclerView = (RecyclerView) findViewById(R.id.table_choosing_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        tableRecyclerAdapter = new TableRecyclerAdapter(this);
        recyclerView.setAdapter(tableRecyclerAdapter);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            Long customerId = bundle.getLong(BUNDLE_CUSTOMERID);
            Integer tableReservedId = bundle.getInt(BUNDLE_TABLE_RESERVED_NUMBER,-1);

            presenter = new TableChoosingPresenter(this,customerId,tableReservedId);
            presenter.populateData();
        }
    }

    @Override
    public void onItemClicked(List<Table> tableList, int tablePosition) {
        presenter.reserveTable(tableList,tablePosition);
    }

    @Override
    public void showTables(List<Table> tableList) {
        noDataTxtView.setVisibility(View.GONE);
        tableRecyclerAdapter.replaceData(tableList);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void markTableAsReserved(List<Table> tableList) {
        tableRecyclerAdapter.replaceData(tableList);
    }

    @Override
    public void unmarkTableAsReserved(List<Table> tableList) {
        tableRecyclerAdapter.replaceData(tableList);
    }

    @Override
    public void showNoDataError() {
        noDataTxtView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }
}
