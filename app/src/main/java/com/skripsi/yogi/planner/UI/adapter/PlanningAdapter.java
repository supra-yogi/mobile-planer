package com.skripsi.yogi.planner.UI.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.yogi.planner.Common.CurrencyFormat;
import com.skripsi.yogi.planner.Common.ResponseCallBack;
import com.skripsi.yogi.planner.DAL.PlanningAPI;
import com.skripsi.yogi.planner.Domain.Plannings.Planning;
import com.skripsi.yogi.planner.R;
import com.skripsi.yogi.planner.UI.DetailPlanningActivity;
import com.skripsi.yogi.planner.UI.EditPlanningActivity;
import com.skripsi.yogi.planner.UI.MainMenuActivity;

import org.json.JSONArray;

import java.util.ArrayList;

public class PlanningAdapter extends RecyclerView.Adapter<PlanningAdapter.MyHolder> {
    private Context context;
    private ArrayList<Planning> planningArrayList;
    private PlanningAPI repository;

    /*
    CONSTRUCTOR
     */
    public PlanningAdapter(Context context, ArrayList<Planning> planningArrayList) {
        this.context = context;
        this.planningArrayList = planningArrayList;
        repository = new PlanningAPI(context);
    }

    //INITIALIZE VIEW HOLDER
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_planning, parent, false);
        return new MyHolder(v);
    }

    //BIND DATA
    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final int id = planningArrayList.get(position).getId();
        holder.txtGoalName.setText(planningArrayList.get(position).getGoalName());
        holder.txtJangkaWaktu.setText(String.valueOf(planningArrayList.get(position).getTimePeriod()));
        holder.txtCurrentCost.setText(planningArrayList.get(position).getCurrentCost().toString());
        CurrencyFormat.onFillTextView(holder.txtCurrentCost);

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

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.more);
                popupMenu.getMenuInflater().inflate(R.menu.more_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.edit:
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", id);
                                Intent editPlanning = new Intent(context, EditPlanningActivity.class);
                                editPlanning.putExtras(bundle);
                                context.startActivity(editPlanning);
                                break;
                            case R.id.delete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Are you sure to delete this planning?")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            repository.delete(new ResponseCallBack() {
                                                @Override
                                                public void onResponse(JSONArray response) {
                                                }

                                                @Override
                                                public void onResponse(String response) {
                                                    planningArrayList.remove(position);
                                                    notifyDataSetChanged();
                                                }

                                                @Override
                                                public void onError(String error) {
                                                    Toast.makeText(context, "Error: " + error, Toast.LENGTH_LONG).show();
                                                }
                                            }, id);
                                        }
                                    }).setNegativeButton("Cancel", null);
                                AlertDialog alert = builder.create();
                                alert.show();
                                break;
                        }
                        return true;
                    }
                });

                popupMenu.show();
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
    public void add(Planning planning) {
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
        TextView txtGoalName, txtJangkaWaktu, txtCurrentCost, more;
        RelativeLayout relativeCard;

        public MyHolder(View itemView) {
            super(itemView);

            this.txtGoalName = (TextView) itemView.findViewById(R.id.txtGoalName);
            this.txtJangkaWaktu = (TextView) itemView.findViewById(R.id.txtJangkaWaktu);
            this.txtCurrentCost = (TextView) itemView.findViewById(R.id.txtCurrentCost);
            this.more = (TextView) itemView.findViewById(R.id.more);
            this.relativeCard = (RelativeLayout) itemView.findViewById(R.id.relativeCard);
        }
    }
}