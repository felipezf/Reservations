package com.quandoo.reservations.ui.tablechoosing;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quandoo.reservations.R;
import com.quandoo.reservations.ReservationsApplication;
import com.quandoo.reservations.data.entities.Table;

import java.util.ArrayList;
import java.util.List;


public class TableRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Table> tableList;
    private ItemClickListener itemClickListener;

   public TableRecyclerAdapter(ItemClickListener itemClickListener){
       this.tableList = new ArrayList<Table>();
       this.itemClickListener = itemClickListener;
   }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_table, parent, false);
        return new TableHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Table table  = tableList.get(position);
        int color;

        TableHolder tableHolder = (TableHolder) holder;
        tableHolder.tableNumber.setText(String.valueOf(position));


        if(table.getCurrentReserve()){
            color = R.color.colorTableCurrentReserved;
        }
        else {
            if (table.getStatus()) {
                color = R.color.colorTableFree;
            }
            else {
                color = R.color.colorTableReserved;
            }
        }

        tableHolder.layout.setBackgroundColor(ContextCompat.getColor(ReservationsApplication.getContext(), color));

        tableHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClicked(tableList,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    class TableHolder extends RecyclerView.ViewHolder {

        public TextView tableNumber;
        public LinearLayout layout;
        public TableHolder(View view) {
            super(view);
            tableNumber = (TextView) view.findViewById(R.id.table_number);
            layout = (LinearLayout) view.findViewById(R.id.table_layout);
        }
    }

    public void replaceData(List<Table> tableList) {
        setList(new ArrayList<>(tableList));
        notifyDataSetChanged();
    }

    private void setList(List<Table> tables) {
        if(tables!=null) {
            this.tableList.clear();
            this.tableList.addAll(tables);
        }
    }

    public interface ItemClickListener {
        void onItemClicked(List<Table> tableList, int tablePosition);
    }
}
