package com.example.kisanmitra.utils.organic

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class OrganicFarmingManualTest {

    @Test
    fun manualContainsAllExpectedSections() {
        val sectionIds = OrganicFarmingManual.sections.map { it.id }

        assertEquals(
            listOf("crop", "fertilizer", "pest", "schedule", "tips"),
            sectionIds
        )
    }

    @Test
    fun cropGuideContainsExpectedCrops() {
        val section = OrganicFarmingManual.findSection("crop")

        assertNotNull(section)

        val itemTitles = section!!.items.map { it.title }

        assertEquals(
            listOf("Tomato", "Wheat", "Rice", "Potato"),
            itemTitles
        )
    }

    @Test
    fun fertilizerSectionContainsExpectedItems() {
        val section = OrganicFarmingManual.findSection("fertilizer")

        assertNotNull(section)

        val itemTitles = section!!.items.map { it.title }

        assertTrue(itemTitles.contains("Vermicompost"))
        assertTrue(itemTitles.contains("Compost"))
        assertTrue(itemTitles.contains("Jeevamrut"))
        assertTrue(itemTitles.contains("Panchagavya"))
        assertTrue(itemTitles.contains("Neem Cake"))
        assertTrue(itemTitles.contains("Green Manure"))
    }

    @Test
    fun pestSectionContainsExpectedItems() {
        val section = OrganicFarmingManual.findSection("pest")

        assertNotNull(section)

        val itemTitles = section!!.items.map { it.title }

        assertTrue(itemTitles.contains("Neem-Based Spray"))
        assertTrue(itemTitles.contains("Garlic-Chilli Extract"))
        assertTrue(itemTitles.contains("Sticky Traps"))
        assertTrue(itemTitles.contains("Biological Pest Control"))
        assertTrue(itemTitles.contains("Preventive Practices"))
    }

    @Test
    fun scheduleContainsFiveStages() {
        val section = OrganicFarmingManual.findSection("schedule")

        assertNotNull(section)
        assertEquals(5, section!!.items.size)

        assertEquals("1. Sowing", section.items[0].title)
        assertEquals("5. Harvesting", section.items[4].title)
    }

    @Test
    fun tipsSectionContainsExpectedTopics() {
        val section = OrganicFarmingManual.findSection("tips")

        assertNotNull(section)

        val itemTitles = section!!.items.map { it.title }

        assertTrue(itemTitles.contains("Crop Rotation"))
        assertTrue(itemTitles.contains("Mulching"))
        assertTrue(itemTitles.contains("Composting"))
        assertTrue(itemTitles.contains("Water Conservation"))
        assertTrue(itemTitles.contains("Reduce Chemical Inputs"))
    }

    @Test
    fun unknownSectionReturnsNull() {
        assertNull(OrganicFarmingManual.findSection("unknown"))
    }
}
