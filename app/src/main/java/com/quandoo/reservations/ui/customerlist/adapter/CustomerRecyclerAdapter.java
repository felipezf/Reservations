package com.quandoo.reservations.ui.customerlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quandoo.reservations.R;
import com.quandoo.reservations.data.entities.Customer;

import java.util.List;


public class CustomerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Customer> mCustomers;
    private ItemClickListener itemClickListener;

   public CustomerRecyclerAdapter(ItemClickListener itemClickListener, List<Customer> customerList){
       mCustomers = customerList;
       this.itemClickListener = itemClickListener;

   }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_customer, parent, false);
        return new CustomerHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Customer customer = mCustomers.get(position);
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
                itemClickListener.onItemClicked(mCustomers.get(position));
            }
        });

//                new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Intent intent = new Intent(mContext, TableChoosingActivity.class);
//               // intent.putExtra(BandDetailActivity.BUNDLE_BAND, customer);
//               // mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mCustomers.size();
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

    public void replaceData(List<Customer> tasks) {
        setList(tasks);
        notifyDataSetChanged();
    }

    private void setList(List<Customer> tasks) {
        if(tasks!=null) {
            mCustomers.clear();
            mCustomers.addAll(tasks);
        }
    }

    public interface ItemClickListener {
        void onItemClicked(Customer customer);
    }
}
