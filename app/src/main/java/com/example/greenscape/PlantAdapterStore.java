package com.example.greenscape;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlantAdapterStore extends RecyclerView.Adapter<PlantAdapterStore.PlantViewHolder> {

    private List<PlantStoreModel> plantStoreModelList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onAddToCartClick(PlantStoreModel plantStoreModel);
        void onRemoveFromCartClick(PlantStoreModel plantStoreModel);
    }

    public PlantAdapterStore(List<PlantStoreModel> plantStoreModelList, OnItemClickListener listener) {
        this.plantStoreModelList = plantStoreModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant_store, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        PlantStoreModel plantStoreModel = plantStoreModelList.get(position);
        holder.bind(plantStoreModel, listener);
    }

    @Override
    public int getItemCount() {
        return plantStoreModelList.size();
    }

    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        private ImageView plantImage;
        private TextView plantName;
        private TextView plantDescription;
        private TextView plantPrice;
        private Button addToCartButton;
        private Button removeFromCartButton;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            plantImage = itemView.findViewById(R.id.plantImage);
            plantName = itemView.findViewById(R.id.plantName);
            plantDescription = itemView.findViewById(R.id.plantDescription);
            plantPrice = itemView.findViewById(R.id.plantPrice);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            removeFromCartButton = itemView.findViewById(R.id.removeFromCartButton);
        }

        public void bind(PlantStoreModel plantStoreModel, OnItemClickListener listener) {
            plantImage.setImageResource(plantStoreModel.getImageResId());
            plantName.setText(plantStoreModel.getName());
            plantDescription.setText(plantStoreModel.getDescription());
            plantPrice.setText(String.valueOf(plantStoreModel.getPrice()));

            addToCartButton.setOnClickListener(v -> listener.onAddToCartClick(plantStoreModel));
            removeFromCartButton.setOnClickListener(v -> listener.onRemoveFromCartClick(plantStoreModel));
        }
    }
}
