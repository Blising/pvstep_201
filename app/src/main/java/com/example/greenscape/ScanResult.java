package com.example.greenscape;

import com.google.mlkit.vision.label.ImageLabel;

import java.util.List;

public class ScanResult {
    private List<String> labels;

    public ScanResult() {
        // Потрібно для Firebase
    }

    public ScanResult(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
