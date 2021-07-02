package com.ART.shower_love.ui.donatereceive;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ART.shower_love.R;
import com.bumptech.glide.Glide;

import java.util.List;




public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<uploadinfo> MainImageUploadInfoList;
    private RecyclerViewListener listener2;


    public RecyclerViewAdapter(Context context, List<uploadinfo> TempList, RecyclerViewListener listener2) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
        this.listener2 = listener2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        uploadinfo UploadInfo = MainImageUploadInfoList.get(position);

        holder.imageNameTextView.setText(UploadInfo.getCategory());

        //Loading image from Glide library.
       holder.view_poastal_code.setText(UploadInfo.getPostalCode());
    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }
    public  interface RecyclerViewListener{
        void onClick(View v ,int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView view_poastal_code;
        public TextView imageNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            view_poastal_code =  itemView.findViewById(R.id.donar_postal_code);

            imageNameTextView = (TextView) itemView.findViewById(R.id.ImageNameTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener2.onClick(v,getAdapterPosition());
        }
    }
}