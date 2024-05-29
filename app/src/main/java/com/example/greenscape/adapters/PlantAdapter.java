package com.example.greenscape.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.greenscape.R;
import com.example.greenscape.entity.Plant;

import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {
    private List<Plant> plantList;

    public PlantAdapter(List<Plant> plantList) {
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plantnet, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = plantList.get(position);
        holder.scientificName.setText(plant.getScientificName());
        holder.commonNames.setText(plant.getCommonNames());
        holder.family.setText(plant.getFamily());
        holder.genus.setText(plant.getGenus());
        holder.score.setText(String.valueOf(plant.getScore()));
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    static class PlantViewHolder extends RecyclerView.ViewHolder {
        TextView scientificName, commonNames, family, genus, score;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            scientificName = itemView.findViewById(R.id.scientificName);
            commonNames = itemView.findViewById(R.id.commonNames);
            family = itemView.findViewById(R.id.family);
            genus = itemView.findViewById(R.id.genus);
            score = itemView.findViewById(R.id.score);
        }
    }
}
