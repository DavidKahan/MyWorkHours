package com.kahan.david.myworkhours.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kahan.david.myworkhours.R;
import com.kahan.david.myworkhours.model.StatItem;

import java.util.List;

/**
 * Created by david on 19/04/2018.
 */
public class UserStatsAdapter extends RecyclerView.Adapter<UserStatsAdapter.MyViewHolder> {

    private final List<StatItem> mStatItems;

    public UserStatsAdapter(List<StatItem> mStatItems) {
        this.mStatItems = mStatItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stat_item_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StatItem statItem = mStatItems.get(position);
        holder.date.setText(statItem.getDate().toString());
        holder.duration.setText(statItem.getDuration().toString());
    }

    @Override
    public int getItemCount() {
        return mStatItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date, duration;
        View taskView;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(false);
            taskView = itemView;
            date = itemView.findViewById(R.id.dateTV);
            duration = itemView.findViewById(R.id.durationTV);
        }
    }
}
