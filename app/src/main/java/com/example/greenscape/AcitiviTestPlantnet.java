package com.example.greenscape;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenscape.adapters.PlantAdapter;
import com.example.greenscape.entity.Plant;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AcitiviTestPlantnet extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_PERMISSIONS = 100;

    private ImageView imageView;
    private TextView textView;
    private RecyclerView recyclerView;
    private PlantAdapter plantAdapter;
    private List<Plant> plantList;
    private OkHttpClient client;
    private File photoFile;

    private GoogleTranslateApi googleTranslateApi;
    private static final String API_KEY = "AIzaSyABmu1Xx6J8U1q2o3ysGZRqsTUQLty2-2s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivi_test_plantnet);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textViewValue);
        recyclerView = findViewById(R.id.recyclerView);
        Button buttonCamera = findViewById(R.id.buttonCamera);
        Button buttonGallery = findViewById(R.id.buttonGallery);

        plantList = new ArrayList<>();
        plantAdapter = new PlantAdapter(plantList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(plantAdapter);

        client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://translation.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        googleTranslateApi = retrofit.create(GoogleTranslateApi.class);

        buttonCamera.setOnClickListener(v -> {
            if (checkPermissions()) {
                dispatchTakePictureIntent();
            }
        });

        buttonGallery.setOnClickListener(v -> {
            if (checkPermissions()) {
                dispatchPickPictureIntent();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.backtomenu) {
            Intent intent = new Intent(AcitiviTestPlantnet.this, MenuActivity.class);
            startActivity(intent);

        }
            return true;



    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.INTERNET
                    },
                    REQUEST_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, proceed with the intended action
            } else {
                // Permissions denied, show a message to the user
                textView.setText("Permissions are required for this app to function.");
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.greenscape.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        String imageFileName = "JPEG_" + System.currentTimeMillis() + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

    private void dispatchPickPictureIntent() {
        Intent pickPictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPictureIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    if (photoFile != null && photoFile.exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        processImage(bitmap);
                    } else {
                        textView.setText("Error: Photo file is null or does not exist");
                    }
                    break;
                case REQUEST_IMAGE_PICK:
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            processImage(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            textView.setText("Error: Unable to load image from gallery");
                        }
                    } else {
                        textView.setText("Error: No data received from gallery");
                    }
                    break;
            }
        }
    }


    private void processImage(Bitmap bitmap) {
        Bitmap scaledBitmap = scaleBitmap(bitmap, 500, 500);
        imageView.setImageBitmap(scaledBitmap);
        new ImageProcessingTask(scaledBitmap).execute();
    }

    private Bitmap scaleBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float ratioBitmap = (float) width / (float) height;
        float ratioMax = (float) maxWidth / (float) maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;
        if (ratioMax > 1) {
            finalWidth = (int) ((float) maxHeight * ratioBitmap);
        } else {
            finalHeight = (int) ((float) maxWidth / ratioBitmap);
        }
        return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true);
    }

    private class ImageProcessingTask extends AsyncTask<Void, Void, File> {
        private Bitmap bitmap;

        public ImageProcessingTask(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        protected File doInBackground(Void... voids) {
            try {
                File imageFile = new File(getCacheDir(), "image.jpg");
                FileOutputStream fos = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                return imageFile;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(File imageFile) {
            if (imageFile != null) {
                identifyPlant(imageFile);
            } else {
                textView.setText("Error: Unable to process image");
            }
        }
    }

    private void identifyPlant(File imageFile) {
        String url = "https://my-api.plantnet.org/v2/identify/all?api-key=2b10zjl91QSvDPcXwYKHlEOO";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("images", imageFile.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), imageFile))
                .addFormDataPart("organs", "leaf")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> textView.setText("Error: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    Gson gson = new Gson();

                    PlantIdentificationResult result = gson.fromJson(responseData, PlantIdentificationResult.class);
                    if (result != null && result.getResults() != null && !result.getResults().isEmpty()) {
                        // Отображаем только лучший результат
                        PlantResult bestResult = result.getResults().get(0);
                        Species species = bestResult.getSpecies();
                        Plant plant = new Plant();
                        plant.setId(bestResult.getId());
                        plant.setScientificName(species.getScientificName());
                        translateText(species.getScientificName(), new TranslateCallback() {
                            @Override
                            public void onSuccess(String translatedText) {
                                plant.setScientificName(translatedText);
                                plant.setCommonNames(String.join(", ", species.getCommonNames()));
                                translateText(plant.getCommonNames(), new TranslateCallback() {
                                    @Override
                                    public void onSuccess(String translatedText) {
                                        plant.setCommonNames(translatedText);
                                        plant.setFamily(species.getFamily().getScientificName());
                                        translateText(species.getFamily().getScientificName(), new TranslateCallback() {
                                            @Override
                                            public void onSuccess(String translatedText) {
                                                plant.setFamily(translatedText);
                                                plant.setGenus(species.getGenus().getScientificName());
                                                translateText(species.getGenus().getScientificName(), new TranslateCallback() {
                                                    @Override
                                                    public void onSuccess(String translatedText) {
                                                        plant.setGenus(translatedText);
                                                        plant.setScore(bestResult.getScore());
                                                        plantList.clear();  // Очищаем текущий список
                                                        plantList.add(plant);  // Добавляем лучший результат
                                                        runOnUiThread(() -> {
                                                            plantAdapter.notifyDataSetChanged();
                                                            textView.setText("Рослина Ідентифікована Успішно:.");
                                                        });
                                                    }

                                                    @Override
                                                    public void onFailure(String error) {
                                                        runOnUiThread(() -> textView.setText("Error: " + error));
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(String error) {
                                                runOnUiThread(() -> textView.setText("Error: " + error));
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        runOnUiThread(() -> textView.setText("Error: " + error));
                                    }
                                });
                            }

                            @Override
                            public void onFailure(String error) {
                                runOnUiThread(() -> textView.setText("Error: " + error));
                            }
                        });

                    } else {
                        runOnUiThread(() -> textView.setText("No plant identified"));
                    }
                } else {
                    runOnUiThread(() -> textView.setText("Identification failed"));
                }
            }
        });
    }

    private void translateText(String text, TranslateCallback callback) {
        googleTranslateApi.translate(API_KEY, text, "en", "uk").enqueue(new retrofit2.Callback<TranslationResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TranslationResponse> call, retrofit2.Response<TranslationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String translatedText = response.body().data.translations.get(0).translatedText;
                    callback.onSuccess(translatedText);
                } else {
                    callback.onFailure("Помилка перекладу ");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<TranslationResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface TranslateCallback {
        void onSuccess(String translatedText);
        void onFailure(String error);
    }
}
