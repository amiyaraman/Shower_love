package com.ART.shower_love.ui.donatereceive;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ART.shower_love.R;

import java.util.List;




public class BloodRecyclerViewAdapter extends RecyclerView.Adapter<BloodRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<BloodDonoruploadinfo> MainImageUploadInfoList;
    private RecyclerViewListener listener;

    public BloodRecyclerViewAdapter(Context context, List<BloodDonoruploadinfo> TempList, RecyclerViewListener listener) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BloodDonoruploadinfo bloodDonoruploadinfo = MainImageUploadInfoList.get(position);

        holder.imageNameTextView.setText(bloodDonoruploadinfo.getBloodGroup());

        //Loading image from Glide library.
        holder.nametextview.setText(bloodDonoruploadinfo.getAddress());
    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }
    public  interface RecyclerViewListener{
        void onClick(View v ,int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nametextview;
        public TextView imageNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nametextview = (TextView) itemView.findViewById(R.id.name) ;

            imageNameTextView = (TextView) itemView.findViewById(R.id.BloodImageNameTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());

        }
    }
}