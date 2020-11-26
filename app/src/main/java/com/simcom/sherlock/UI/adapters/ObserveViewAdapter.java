package com.simcom.sherlock.UI.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.simcom.sherlock.R;
import com.simcom.sherlock.model.Friend;
import com.simcom.sherlock.UI.fragments.ObserveFragment;

import java.util.ArrayList;
import java.util.List;

public class ObserveViewAdapter extends RecyclerView.Adapter<ObserveViewAdapter.ViewHolder> {

    private List<Friend> friends = new ArrayList<>();
    private ObserveFragment fragment;

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }
    public ObserveViewAdapter(ObserveFragment fragment){
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(friends.get(position).getDisplayName());
        holder.card.setOnClickListener(view -> {
            fragment.openMap(friends.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            card = itemView.findViewById(R.id.card);
        }
    }
}
