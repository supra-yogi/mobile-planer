package com.skripsi.yogi.planner.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skripsi.yogi.planner.DAL.PlanningAPI;
import com.skripsi.yogi.planner.Domain.Plannings.Planning;
import com.skripsi.yogi.planner.Domain.Plannings.PriorityPlanning;
import com.skripsi.yogi.planner.R;
import com.skripsi.yogi.planner.UI.DetailPlanningActivity;

import java.util.ArrayList;

public class PriorityPlanningAdapter extends RecyclerView.Adapter<PriorityPlanningAdapter.MyHolder> {
    private Context context;
    private ArrayList<PriorityPlanning> planningArrayList;
    private PlanningAPI repository;

    /*
    CONSTRUCTOR
     */
    public PriorityPlanningAdapter(Context context, ArrayList<PriorityPlanning> planningArrayList) {
        this.context = context;
        this.planningArrayList = planningArrayList;
        repository = new PlanningAPI(context);
    }

    //INITIALIZE VIEW HOLDER
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_priority_planning, parent, false);
        return new MyHolder(v);
    }

    //BIND DATA
    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final int id = planningArrayList.get(position).getId();
        holder.txtGoalName.setText(planningArrayList.get(position).getGoalName());
        holder.txtPriority.setText(String.valueOf(planningArrayList.get(position).getPriority()));
        holder.txtRecommendation.setText(planningArrayList.get(position).getRecommendation().toString());

        holder.relativeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                Intent detailPlanning = new Intent(context, DetailPlanningActivity.class);
                detailPlanning.putExtras(bundle);
                context.startActivity(detailPlanning);
            }
        });
    }

    /*
    TOTAL ITEMS
     */
    @Override
    public int getItemCount() {
        return planningArrayList.size();
    }

    /*
    ADD DATA TO ADAPTER
     */
    public void add(PriorityPlanning planning) {
        planningArrayList.add(planning);
        notifyDataSetChanged();
    }

    /*
    CLEAR DATA FROM ADAPTER
     */
    public void clear() {
        planningArrayList.clear();
        notifyDataSetChanged();
    }

    /*
    VIEW HOLDER CLASS
     */
    class MyHolder extends RecyclerView.ViewHolder {
        TextView txtGoalName, txtPriority, txtRecommendation;
        RelativeLayout relativeCard;

        public MyHolder(View itemView) {
            super(itemView);

            this.txtGoalName = (TextView) itemView.findViewById(R.id.txtGoalName);
            this.txtPriority = (TextView) itemView.findViewById(R.id.txtPriority);
            this.txtRecommendation = (TextView) itemView.findViewById(R.id.txtRecommendation);
            this.relativeCard = (RelativeLayout) itemView.findViewById(R.id.relativeCard);
        }
    }
}