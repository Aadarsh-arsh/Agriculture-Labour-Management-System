package com.example.kisanmitra

import com.example.kisanmitra.utils.LabourEntryValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LabourEntryValidatorTest {

    @Test
    fun acceptsNonBlankWorkerName() {
        assertTrue(LabourEntryValidator.isValidWorkerName("Ramesh"))
    }

    @Test
    fun rejectsBlankWorkerName() {
        assertFalse(LabourEntryValidator.isValidWorkerName("   "))
    }

    @Test
    fun acceptsNonNegativeDaysWorked() {
        assertTrue(LabourEntryValidator.isValidDaysWorked(10))
        assertTrue(LabourEntryValidator.isValidDaysWorked(0))
    }

    @Test
    fun rejectsNegativeDaysWorked() {
        assertFalse(LabourEntryValidator.isValidDaysWorked(-1))
    }

    @Test
    fun acceptsValidDailyRate() {
        assertTrue(LabourEntryValidator.isValidDailyRate(500.0))
        assertTrue(LabourEntryValidator.isValidDailyRate(0.0))
    }

    @Test
    fun rejectsInvalidDailyRate() {
        assertFalse(LabourEntryValidator.isValidDailyRate(-1.0))
        assertFalse(LabourEntryValidator.isValidDailyRate(Double.NaN))
        assertFalse(
            LabourEntryValidator.isValidDailyRate(Double.POSITIVE_INFINITY)
        )
    }
}