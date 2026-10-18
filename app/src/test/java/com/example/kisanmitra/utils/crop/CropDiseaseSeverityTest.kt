package com.example.kisanmitra.utils.crop

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CropDiseaseSeverityTest {

    @Test
    fun classifiesLowSeverity() {
        assertEquals(
            DiseaseSeverity.LOW,
            CropDiseaseSeverity.classify(5.0)
        )
    }

    @Test
    fun classifiesModerateSeverity() {
        assertEquals(
            DiseaseSeverity.MODERATE,
            CropDiseaseSeverity.classify(20.0)
        )
    }

    @Test
    fun classifiesHighSeverity() {
        assertEquals(
            DiseaseSeverity.HIGH,
            CropDiseaseSeverity.classify(50.0)
        )
    }

    @Test
    fun detectsConsecutiveDuplicateCrops() {
        assertTrue(
            CropRotationPlanner.hasConsecutiveDuplicate(
                listOf("Wheat", "Rice", "rice")
            )
        )
    }

    @Test
    fun acceptsDifferentConsecutiveCrops() {
        assertFalse(
            CropRotationPlanner.hasConsecutiveDuplicate(
                listOf("Wheat", "Rice", "Potato")
            )
        )
    }
}
