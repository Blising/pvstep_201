package com.example.greenscape.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenscape.R;
import com.example.greenscape.entity.Plants;

import java.util.List;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantViewHolder> {
    private List<Plants> plants;

    public PlantsAdapter(List<Plants> plants) {
        this.plants = plants;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsAdapter.PlantViewHolder holder, int position) {
        Plants plant = plants.get(position);
        holder.bind(plant);
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder {

        private TextView txId;
        private TextView txName;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            txId = itemView.findViewById(R.id.TWid);
            txName = itemView.findViewById(R.id.TWname);

        }
        public  void bind(Plants plant){
            txId.setText(String.valueOf(plant.getId()));
            txName.setText(plant.getName());
        }
    }
}