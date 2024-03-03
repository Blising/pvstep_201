package com.example.greenscape;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

    public class UploadImage extends AppCompatActivity {

        private static final int PICK_IMAGE_REQUEST = 1;
        private Button mButtonChooseImage;
        private Button mButtonUpload;
        private TextView mtextViewShowUpload;
        private EditText meditTextFileName;
        private ImageView mImageView;
        private ProgressBar mProgressBar;
        private Uri mImageUri;
        private StorageReference mStorageRef;
        private DatabaseReference mDatabaseRef;
        private StorageTask mUploadTask;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_upload_image);

            mButtonChooseImage = findViewById(R.id.button_choose_image);
            mButtonUpload = findViewById(R.id.button_upload);
            mtextViewShowUpload = findViewById(R.id.text_view_show_uploads);
            meditTextFileName = findViewById(R.id.edit_text_file_name);

            mImageView = findViewById(R.id.image_view);
            mProgressBar = findViewById(R.id.progress_bar);

            mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
            mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads");



            mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFileChooser();

                }
            });
            mtextViewShowUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    openImagesActivity();

                }
            });
            mButtonUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mUploadTask != null && mUploadTask.isInProgress()){
                        Toast.makeText(UploadImage.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                    }else {
                        uploadFile();
                    }
                }
            });


        }



        private void openFileChooser() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                mImageUri = data.getData();

                Picasso.get().load(mImageUri).into(mImageView);
// Встановлюємо зображення в mImageView
            }
        }

        private String getFileExtension(Uri uri) {
            ContentResolver cR = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cR.getType(uri));


        }

        private void uploadFile() {
            if (mImageUri != null) {
                StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() +
                        "." + getFileExtension(mImageUri));
                mUploadTask =    fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressBar.setProgress(0);
                                    }
                                }, 1500);
                                Toast.makeText(UploadImage.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                // Отримання URL завантаженого зображення
                                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String uploadId = mDatabaseRef.push().getKey(); // Створюємо унікальний ключ для нового об'єкта Upload
                                        Upload upload = new Upload(meditTextFileName.getText().toString().trim(),
                                                uri.toString());
                                        mDatabaseRef.child(uploadId).setValue(upload); // Зберігаємо об'єкт Upload у базі даних
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UploadImage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                mProgressBar.setProgress((int) progress);
                            }
                        });

            } else {
                Toast.makeText(this, "No file started", Toast.LENGTH_SHORT).show();
            }
        }
        private void openImagesActivity() {
            Intent intent = new Intent(UploadImage.this, ImageActivity.class);

            startActivity(intent);

        }
    }