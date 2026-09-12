package com.example.kisanmitra.ui.screens.organic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrganicFarmingScreen(
    onBack: () -> Unit
) {

    var selectedSection by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(text = "🌿 Organic Farming")

        Text(
            text = "Learn sustainable and natural farming practices."
        )

        OrganicSectionCard(
            title = "🌱 Organic Crop Guide",
            description = "Crop-wise organic practices, irrigation, soil preparation and crop-stage guidance.",
            onClick = {
                selectedSection = "crop"
            }
        )

        OrganicSectionCard(
            title = "🧪 Natural Fertilizers",
            description = "Learn about vermicompost, compost, Jeevamrut, Panchagavya, neem cake and green manure.",
            onClick = {
                selectedSection = "fertilizer"
            }
        )

        OrganicSectionCard(
            title = "🐛 Natural Pest Management",
            description = "Explore neem-based sprays, garlic-chilli extracts, sticky traps and biological pest management.",
            onClick = {
                selectedSection = "pest"
            }
        )

        OrganicSectionCard(
            title = "📅 Organic Farming Schedule",
            description = "Plan sowing, fertilization, irrigation, pest management and harvesting activities.",
            onClick = {
                selectedSection = "schedule"
            }
        )

        OrganicSectionCard(
            title = "♻️ Organic Farming Tips",
            description = "Learn about crop rotation, mulching, composting, water conservation and sustainable farming.",
            onClick = {
                selectedSection = "tips"
            }
        )

        if (selectedSection != null) {

            when (selectedSection) {

                "crop" -> CropGuideContent()

                "fertilizer" -> FertilizerContent()

                "pest" -> PestManagementContent()

                "schedule" -> FarmingScheduleContent()

                "tips" -> OrganicTipsContent()
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Back")
        }
    }
}

@Composable
private fun OrganicSectionCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(text = title)

            Text(
                text = description,
                modifier = Modifier.padding(top = 6.dp)
            )

            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("View Details")
            }
        }
    }
}

@Composable
private fun CropGuideContent() {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text("🌱 Organic Crop Guide")

            Text("Tomato")
            Text("• Use well-decomposed compost before planting.")
            Text("• Use mulch to conserve soil moisture.")
            Text("• Provide regular irrigation without waterlogging.")
            Text("• Use neem-based products for preventive pest management.")

            Text("Wheat")
            Text("• Prepare soil using compost or farmyard manure.")
            Text("• Maintain proper irrigation during important growth stages.")
            Text("• Practice crop rotation to maintain soil health.")

            Text("Rice")
            Text("• Use organic manure during soil preparation.")
            Text("• Avoid unnecessary standing water.")
            Text("• Maintain proper field drainage.")

            Text("Potato")
            Text("• Use healthy seed tubers.")
            Text("• Add compost or well-decomposed organic manure.")
            Text("• Use mulching to reduce weeds and conserve moisture.")
        }
    }
}

@Composable
private fun FertilizerContent() {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text("🧪 Natural Fertilizers")

            Text("Vermicompost")
            Text("• Nutrient-rich organic fertilizer produced using earthworms.")

            Text("Compost")
            Text("• Decomposed organic material that improves soil structure and fertility.")

            Text("Jeevamrut")
            Text("• Traditional microbial preparation used to support soil biological activity.")

            Text("Panchagavya")
            Text("• Traditional organic preparation made from cow-derived ingredients.")

            Text("Neem Cake")
            Text("• Organic manure that can also help in managing some soil pests.")

            Text("Green Manure")
            Text("• Crops such as legumes can be grown and incorporated into soil to add organic matter.")
        }
    }
}

@Composable
private fun PestManagementContent() {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text("🐛 Natural Pest Management")

            Text("Neem-Based Spray")
            Text("• Neem-based products can be used as part of an integrated pest-management approach.")

            Text("Garlic-Chilli Extract")
            Text("• Plant-based extracts may help discourage certain insect pests.")

            Text("Sticky Traps")
            Text("• Yellow or blue sticky traps can help monitor and reduce certain flying insects.")

            Text("Biological Pest Control")
            Text("• Beneficial insects and microbial biocontrol agents can help manage pests.")

            Text("Preventive Practices")
            Text("• Remove heavily infected plant material.")
            Text("• Maintain field sanitation.")
            Text("• Use healthy planting material.")
            Text("• Inspect crops regularly.")
        }
    }
}

@Composable
private fun FarmingScheduleContent() {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text("📅 Organic Farming Schedule")

            Text("1. Sowing")
            Text("• Select healthy seeds and prepare the soil using organic matter.")

            Text("2. Fertilization")
            Text("• Apply compost, vermicompost or suitable organic manure according to crop requirements.")

            Text("3. Irrigation")
            Text("• Provide water according to crop stage, soil condition and weather.")

            Text("4. Pest Management")
            Text("• Regularly inspect crops and use suitable preventive and biological methods.")

            Text("5. Harvesting")
            Text("• Harvest at the appropriate maturity stage and handle produce carefully.")
        }
    }
}

@Composable
private fun OrganicTipsContent() {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text("♻️ Organic Farming Tips")

            Text("Crop Rotation")
            Text("• Rotate different crops to improve soil health and reduce recurring pest problems.")

            Text("Mulching")
            Text("• Use suitable organic mulch to conserve moisture and suppress weeds.")

            Text("Composting")
            Text("• Convert suitable farm and plant waste into useful compost.")

            Text("Water Conservation")
            Text("• Use efficient irrigation and avoid unnecessary water loss.")

            Text("Reduce Chemical Inputs")
            Text("• Use chemical inputs only when appropriate and follow recommended agricultural practices.")
        }
    }
}