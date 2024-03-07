package com.example.greenscape;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext; // Контекст для адаптера Context for the adapter
    private List<Upload> mUploads; // Список об'єктів Upload для відображення  List of Upload objects to display

    // Конструктор для ImageAdapter Constructor for the ImageAdapter
    public ImageAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Надуваємо макет для кожного елемента в RecyclerView
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return  new ImageViewHolder(v); // Повертаємо ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        // Прив'язка даних до кожного елемента в RecyclerView

        // Отримання об'єкта Upload за вказаною позицією
        Upload uploadCurrent = mUploads.get(position);

        // Встановлення назви завантаження в відповідному TextView
        holder.textViewName.setText(uploadCurrent.getName());

        // Використання бібліотеки Picasso для завантаження зображення з URL в ImageView
        Picasso.get().load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher) // Зображення-заповнювач під час завантаження
                .fit().centerCrop().into(holder.imageView); // Зміна розміру та центрування зображення
    }

    @Override
    public int getItemCount() {
        return  mUploads.size(); // Повертаємо загальну кількість елементів у списку Returning the total number of items in the list
    }

    // Клас ViewHolder для зберігання посилань на види для кожного елемента в RecyclerView ViewHolder class to hold references to the views for each item in the RecyclerView
    public  class  ImageViewHolder  extends  RecyclerView.ViewHolder{
        public TextView textViewName; // TextView для відображення назви завантаження  TextView to display the name of the upload
        public ImageView imageView; // ImageView для відображення зображення   ImageView to display the image

        // Конструктор для ViewHolder Constructor for the ViewHolder
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            // Пошук та призначення посилань на TextView та ImageView Finding and assigning references to TextView and ImageView
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }

}
