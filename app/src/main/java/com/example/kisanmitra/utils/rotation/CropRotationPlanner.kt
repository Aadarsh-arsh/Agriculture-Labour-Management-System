package com.example.kisanmitra.utils.rotation

data class CropRotationResult(
    val isValid: Boolean,
    val repeatedCrops: List<String>,
    val previousCrop: String?
)

object CropRotationPlanner {

    fun validateRotation(
        previousCrop: String?,
        proposedCrops: List<String>
    ): CropRotationResult {

        val repeated = proposedCrops
            .zipWithNext()
            .filter { it.first.equals(it.second, ignoreCase = true) }
            .map { it.second }
            .distinct()

        val valid = repeated.isEmpty()

        return CropRotationResult(
            isValid = valid,
            repeatedCrops = repeated,
            previousCrop = previousCrop
        )
    }

    fun nextCropAllowed(
        previousCrop: String?,
        proposedCrop: String
    ): Boolean {
        return previousCrop == null ||
            !previousCrop.equals(proposedCrop, ignoreCase = true)
    }
}
