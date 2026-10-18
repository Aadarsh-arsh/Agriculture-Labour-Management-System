package com.example.kisanmitra.utils.crop

enum class DiseaseSeverity {
    LOW,
    MODERATE,
    HIGH
}

object CropDiseaseSeverity {

    fun classify(affectedAreaPercent: Double): DiseaseSeverity {
        require(affectedAreaPercent.isFinite() &&
            affectedAreaPercent in 0.0..100.0)

        return when {
            affectedAreaPercent < 10.0 -> DiseaseSeverity.LOW
            affectedAreaPercent < 30.0 -> DiseaseSeverity.MODERATE
            else -> DiseaseSeverity.HIGH
        }
    }
}
