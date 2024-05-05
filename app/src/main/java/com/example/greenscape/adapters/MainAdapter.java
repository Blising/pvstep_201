package com.example.greenscape.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenscape.R;
import com.example.greenscape.model.MainModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
        holder.course.setText(model.getCourses());
        holder.email.setText(model.getEmail());

        Glide.with(holder.img.getContext())
                .load(model.getTurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_light_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition(); // Get the adapter position
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                            .setContentHolder(new ViewHolder(R.layout.update_popup))
                            .setExpanded(true, 1200)
                            .create();
                    dialogPlus.show();

                    View view = dialogPlus.getHolderView();
                    EditText name = view.findViewById(R.id.txtName);
                    EditText courses = view.findViewById(R.id.txtCourse);
                    EditText email = view.findViewById(R.id.txtEmail);
                    EditText turl = view.findViewById(R.id.txtImageUrl);
                    Button btnUpdate = view.findViewById(R.id.btnUpdate);
                    name.setText(model.getName());
                    courses.setText(model.getCourses());
                    email.setText(model.getEmail());
                    turl.setText(model.getTurl());

                    dialogPlus.show();

                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int adapterPosition = holder.getAdapterPosition(); // Get the adapter position again
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("name", name.getText().toString());
                                map.put("courses", courses.getText().toString());
                                map.put("email", email.getText().toString());
                                map.put("turl", turl.getText().toString());
                                String key = getRef(adapterPosition).getKey(); // Get the key using adapter position
                                if (key != null) {
                                    FirebaseDatabase.getInstance().getReference().child("teacher")
                                            .child(key).updateChildren(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(holder.name.getContext(),"Data Updated Succsessfully",Toast.LENGTH_SHORT).show();
                                                    dialogPlus.dismiss();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(holder.name.getContext(),"Error While Updated",Toast.LENGTH_SHORT).show();
                                                    dialogPlus.dismiss();
                                                }
                                            });
                                }
                            }
                        }
                    });
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition(); // Get the adapter position
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                    builder.setTitle("Are you Sure?");
                    builder.setMessage("Deleted data can't be undone.");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String key = getRef(adapterPosition).getKey();
                            if (key != null) {
                                FirebaseDatabase.getInstance().getReference().child("teacher")
                                        .child(key).removeValue();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(holder.name.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
            }
        });
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name, course, email;
        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);


            img = itemView.findViewById(R.id.img1);
            name = itemView.findViewById(R.id.nametext);
            course = itemView.findViewById(R.id.coursetext);
            email = itemView.findViewById(R.id.emailtext);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);


        }
    }





}
