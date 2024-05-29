package com.example.greenscape.entity;

public class Plant {
   private int id;
   private String scientificName;
   private String commonNames;
   private String family;
   private String genus;
   private double score;

   public Plant(int id, String scientificName, String commonNames, String family, String genus, double score) {
      this.id = id;
      this.scientificName = scientificName;
      this.commonNames = commonNames;
      this.family = family;
      this.genus = genus;
      this.score = score;
   }

   public Plant() {
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getScientificName() {
      return scientificName;
   }

   public void setScientificName(String scientificName) {
      this.scientificName = scientificName;
   }

   public String getCommonNames() {
      return commonNames;
   }

   public void setCommonNames(String commonNames) {
      this.commonNames = commonNames;
   }

   public String getFamily() {
      return family;
   }

   public void setFamily(String family) {
      this.family = family;
   }

   public String getGenus() {
      return genus;
   }

   public void setGenus(String genus) {
      this.genus = genus;
   }

   public double getScore() {
      return score;
   }

   public void setScore(double score) {
      this.score = score;
   }

   // Constructor, getters, and setters
}
