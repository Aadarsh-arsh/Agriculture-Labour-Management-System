package com.example.kisanmitra.utils.organic

data class OrganicFarmingItem(
    val title: String,
    val description: String
)

data class OrganicFarmingSection(
    val id: String,
    val title: String,
    val description: String,
    val items: List<OrganicFarmingItem>
)

object OrganicFarmingManual {

    val sections = listOf(
        OrganicFarmingSection(
            id = "crop",
            title = "Organic Crop Guide",
            description = "Crop-wise organic practices, irrigation, soil preparation and crop-stage guidance.",
            items = listOf(
                OrganicFarmingItem(
                    "Tomato",
                    "Use well-decomposed compost before planting. Use mulch to conserve soil moisture. Provide regular irrigation without waterlogging. Use neem-based products for preventive pest management."
                ),
                OrganicFarmingItem(
                    "Wheat",
                    "Prepare soil using compost or farmyard manure. Maintain proper irrigation during important growth stages. Practice crop rotation to maintain soil health."
                ),
                OrganicFarmingItem(
                    "Rice",
                    "Use organic manure during soil preparation. Avoid unnecessary standing water. Maintain proper field drainage."
                ),
                OrganicFarmingItem(
                    "Potato",
                    "Use healthy seed tubers. Add compost or well-decomposed organic manure. Use mulching to reduce weeds and conserve moisture."
                )
            )
        ),
        OrganicFarmingSection(
            id = "fertilizer",
            title = "Natural Fertilizers",
            description = "Learn about vermicompost, compost, Jeevamrut, Panchagavya, neem cake and green manure.",
            items = listOf(
                OrganicFarmingItem(
                    "Vermicompost",
                    "Nutrient-rich organic fertilizer produced using earthworms."
                ),
                OrganicFarmingItem(
                    "Compost",
                    "Decomposed organic material that improves soil structure and fertility."
                ),
                OrganicFarmingItem(
                    "Jeevamrut",
                    "Traditional microbial preparation used to support soil biological activity."
                ),
                OrganicFarmingItem(
                    "Panchagavya",
                    "Traditional organic preparation made from cow-derived ingredients."
                ),
                OrganicFarmingItem(
                    "Neem Cake",
                    "Organic manure that can also help in managing some soil pests."
                ),
                OrganicFarmingItem(
                    "Green Manure",
                    "Crops such as legumes can be grown and incorporated into soil to add organic matter."
                )
            )
        ),
        OrganicFarmingSection(
            id = "pest",
            title = "Natural Pest Management",
            description = "Organic and preventive approaches for monitoring and managing crop pests.",
            items = listOf(
                OrganicFarmingItem(
                    "Neem-Based Spray",
                    "Neem-based products can be used as part of an integrated pest-management approach."
                ),
                OrganicFarmingItem(
                    "Garlic-Chilli Extract",
                    "Plant-based extracts may help discourage certain insect pests."
                ),
                OrganicFarmingItem(
                    "Sticky Traps",
                    "Yellow or blue sticky traps can help monitor and reduce certain flying insects."
                ),
                OrganicFarmingItem(
                    "Biological Pest Control",
                    "Beneficial insects and microbial biocontrol agents can help manage pests."
                ),
                OrganicFarmingItem(
                    "Preventive Practices",
                    "Remove heavily infected plant material. Maintain field sanitation. Use healthy planting material. Inspect crops regularly."
                )
            )
        ),
        OrganicFarmingSection(
            id = "schedule",
            title = "Organic Farming Schedule",
            description = "Plan sowing, fertilization, irrigation, pest management and harvesting activities.",
            items = listOf(
                OrganicFarmingItem(
                    "1. Sowing",
                    "Select healthy seeds and prepare the soil using organic matter."
                ),
                OrganicFarmingItem(
                    "2. Fertilization",
                    "Apply compost, vermicompost or suitable organic manure according to crop requirements."
                ),
                OrganicFarmingItem(
                    "3. Irrigation",
                    "Provide water according to crop stage, soil condition and weather."
                ),
                OrganicFarmingItem(
                    "4. Pest Management",
                    "Regularly inspect crops and use suitable preventive and biological methods."
                ),
                OrganicFarmingItem(
                    "5. Harvesting",
                    "Harvest at the appropriate maturity stage and handle produce carefully."
                )
            )
        ),
        OrganicFarmingSection(
            id = "tips",
            title = "Organic Farming Tips",
            description = "Learn about crop rotation, mulching, composting, water conservation and sustainable farming.",
            items = listOf(
                OrganicFarmingItem(
                    "Crop Rotation",
                    "Rotate different crops to improve soil health and reduce recurring pest problems."
                ),
                OrganicFarmingItem(
                    "Mulching",
                    "Use suitable organic mulch to conserve moisture and suppress weeds."
                ),
                OrganicFarmingItem(
                    "Composting",
                    "Convert suitable farm and plant waste into useful compost."
                ),
                OrganicFarmingItem(
                    "Water Conservation",
                    "Use efficient irrigation and avoid unnecessary water loss."
                ),
                OrganicFarmingItem(
                    "Reduce Chemical Inputs",
                    "Use chemical inputs only when appropriate and follow recommended agricultural practices."
                )
            )
        )
    )

    fun findSection(id: String): OrganicFarmingSection? =
        sections.firstOrNull { it.id == id }
}
