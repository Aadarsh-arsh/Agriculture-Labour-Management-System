package com.example.kisanmitra.utils.crop

data class CropDiseaseRecord(
    val cropName: String,
    val diseaseName: String,
    val affectedAreaPercent: Double,
    val observedDate: String,
    val notes: String = ""
) {
    init {
        require(cropName.isNotBlank())
        require(diseaseName.isNotBlank())
        require(affectedAreaPercent.isFinite() &&
            affectedAreaPercent in 0.0..100.0)
    }
}
