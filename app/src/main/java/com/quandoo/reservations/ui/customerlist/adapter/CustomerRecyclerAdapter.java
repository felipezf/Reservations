package com.quandoo.reservations.ui.customerlist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwsbrazil.interview.R;
import com.iwsbrazil.interview.activities.BandDetailActivity;
import com.iwsbrazil.interview.model.Band;

import java.util.List;

/**
 * Created by felipe on 8/19/16.
 */
public class CustomerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Band> mBands;
    private Context mContext;

   public CustomerRecyclerAdapter(List<Band> bandsList, Context context){
       mBands = bandsList;
       mContext = context;
   }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_band, parent, false);
        return new BandHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Band band = mBands.get(position);
        BandHolder bandHolder = (BandHolder) holder;

        if(!band.getName().isEmpty()){
            bandHolder.bandName.setText(band.getName());
        }

        bandHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BandDetailActivity.class);
                intent.putExtra(BandDetailActivity.BUNDLE_BAND, band);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBands.size();
    }

    class BandHolder extends RecyclerView.ViewHolder {

        public TextView bandName;

        public BandHolder(View view) {
            super(view);
            bandName = (TextView) view.findViewById(R.id.band_name);
        }
    }
}
