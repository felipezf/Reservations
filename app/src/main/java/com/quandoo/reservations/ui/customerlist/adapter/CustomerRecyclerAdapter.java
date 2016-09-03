package com.quandoo.reservations.ui.customerlist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quandoo.reservations.R;
import com.quandoo.reservations.data.entities.Customer;
import com.quandoo.reservations.ui.tablechoosing.TableChoosingActivity;

import java.util.List;


public class CustomerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Customer> customers;
    private Context mContext;

   public CustomerRecyclerAdapter(List<Customer> bandsList, Context context){
       customers = bandsList;
       mContext = context;
   }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_customer, parent, false);
        return new CustomerHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Customer customer = customers.get(position);
        CustomerHolder customerHolder = (CustomerHolder) holder;

        if(!customer.getCustomerFirstName().isEmpty()){
            customerHolder.firstName.setText(customer.getCustomerFirstName());
        }

        if(!customer.getCustomerLastName().isEmpty()){
            customerHolder.lastName.setText(customer.getCustomerLastName());
        }


        customerHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TableChoosingActivity.class);
               // intent.putExtra(BandDetailActivity.BUNDLE_BAND, customer);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    class CustomerHolder extends RecyclerView.ViewHolder {

        public TextView firstName;
        public TextView lastName;

        public CustomerHolder(View view) {
            super(view);
            firstName = (TextView) view.findViewById(R.id.name);
            lastName = (TextView) view.findViewById(R.id.lastname);
        }
    }
}
