package com.example.greenscape;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Test {
    private static final String IMAGE1 = "/data/media/image_1.jpeg";
    private static final String IMAGE2 = "/data/media/image_2.jpeg";
    private static final String PROJECT = "all";
    private static final String URL = "https://my-api.plantnet.org/v2/identify/" + PROJECT + "?api-key=";

    public static void main(String[] args) {
        File file1 = new File(IMAGE1);
        File file2 = new File(IMAGE2);

        OkHttpClient client = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("images", "image_1.jpeg", RequestBody.create(MediaType.parse("image/jpeg"), file1))
                .addFormDataPart("organs", "flower")
                .addFormDataPart("images", "image_2.jpeg", RequestBody.create(MediaType.parse("image/jpeg"), file2))
                .addFormDataPart("organs", "leaf");

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String jsonString = response.body().string();

            Gson gson = new Gson();
            PlantIdentificationResult result = gson.fromJson(jsonString, PlantIdentificationResult.class);
            List<PlantResult> plantResults = result.getResults();

            for (PlantResult plantResult : plantResults) {
                Species species = plantResult.getSpecies();
                System.out.println("Scientific name: " + species.getScientificName());
                System.out.println("Common names: " + String.join(", ", species.getCommonNames()));
                System.out.println("Family: " + species.getFamily().getScientificName());
                System.out.println("Genus: " + species.getGenus().getScientificName());
                System.out.println("Score: " + plantResult.getScore());
               System.out.println("Id"+plantResult.getId());
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class PlantIdentificationResult {
    @SerializedName("results")
    private List<PlantResult> results;

    public List<PlantResult> getResults() {
        return results;
    }

    public void setResults(List<PlantResult> results) {
        this.results = results;
    }
}

 class PlantResult {
    @SerializedName("id")

    private  int id;
    @SerializedName("score")
    private double score;

    @SerializedName("species")
    private Species species;

    public PlantResult() {

    }

    public PlantResult(int id, double score, Species species) {
        this.id = id;
        this.score = score;
        this.species = species;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public double getScore() {
        return score;
    }

    public Species getSpecies() {
        return species;
    }
}

 class Species {
    @SerializedName("scientificNameWithoutAuthor")
    private String scientificNameWithoutAuthor;

    @SerializedName("scientificNameAuthorship")
    private String scientificNameAuthorship;

    @SerializedName("genus")
    private Genus genus;

    @SerializedName("family")
    private Family family;

    @SerializedName("commonNames")
    private List<String> commonNames;

    @SerializedName("scientificName")
    private String scientificName;

    public String getScientificName() {
        return scientificName;
    }

    public List<String> getCommonNames() {
        return commonNames;
    }

    public Genus getGenus() {
        return genus;
    }

    public Family getFamily() {
        return family;
    }
}

class Genus {
    @SerializedName("scientificNameWithoutAuthor")
    private String scientificNameWithoutAuthor;

    @SerializedName("scientificNameAuthorship")
    private String scientificNameAuthorship;

    @SerializedName("scientificName")
    private String scientificName;

    public String getScientificName() {
        return scientificName;
    }
}

class Family {
    @SerializedName("scientificNameWithoutAuthor")
    private String scientificNameWithoutAuthor;

    @SerializedName("scientificNameAuthorship")
    private String scientificNameAuthorship;

    @SerializedName("scientificName")
    private String scientificName;

    public String getScientificName() {
        return scientificName;
    }
}
