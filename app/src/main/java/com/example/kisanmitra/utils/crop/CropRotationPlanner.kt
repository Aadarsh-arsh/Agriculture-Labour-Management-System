package com.example.kisanmitra.utils.crop

object CropRotationPlanner {

    fun hasConsecutiveDuplicate(crops: List<String>): Boolean {
        return crops.zipWithNext().any { (first, second) ->
            first.trim().equals(second.trim(), ignoreCase = true)
        }
    }

    fun normalize(crops: List<String>): List<String> {
        return crops.map { it.trim() }
            .filter { it.isNotEmpty() }
    }
}
