package com.oli.testrelevant.adapteri;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oli.testrelevant.R;
import com.oli.testrelevant.klasi.Reward;
import com.oli.testrelevant.modeli.RewardsModel;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public Context mContext;
    public ArrayList<Reward> peopleList = new ArrayList<>();
    RewardsModel model;

    public RecyclerAdapter(Context mContext, RewardsModel model) {
        this.mContext = mContext;
        peopleList=model.rewards;
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rewards_row,parent,false);
        RecyclerAdapter.ViewHolder viewHolder = new RecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, final int position) {
        final Reward people = peopleList.get(position);
        holder.info.setText("  "+peopleList.get(position).getName()+"\n"+"  "+people.expiryDate);

    }

    @Override
    public int getItemCount() {
        if (peopleList==null){
            return 0;
        }
        return peopleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textRow)
        TextView info;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
