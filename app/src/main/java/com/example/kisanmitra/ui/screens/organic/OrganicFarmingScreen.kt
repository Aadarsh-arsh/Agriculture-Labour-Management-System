package com.example.kisanmitra.ui.screens.organic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Header
        Text(
            text = "🌿 Organic Farming",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )

        Text(
            text = "Natural and sustainable farming guidance",
            fontSize = 14.sp,
            color = Color(0xFF616161)
        )

        // Intro Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E9)
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "🌱 Grow Naturally",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )

                Text(
                    text = "Explore organic crop practices, natural fertilizers, pest management and sustainable farming methods.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Text(
            text = "Organic Farming Services",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )

        OrganicSectionCard(
            icon = "🌱",
            title = "Organic Crop Guide",
            description = "Crop-wise practices, irrigation, soil preparation and crop-stage guidance.",
            onClick = {
                selectedSection = "crop"
            }
        )

        OrganicSectionCard(
            icon = "🧪",
            title = "Natural Fertilizers",
            description = "Vermicompost, compost, Jeevamrut, Panchagavya, neem cake and green manure.",
            onClick = {
                selectedSection = "fertilizer"
            }
        )

        OrganicSectionCard(
            icon = "🐛",
            title = "Natural Pest Management",
            description = "Neem-based products, plant extracts, sticky traps and biological pest control.",
            onClick = {
                selectedSection = "pest"
            }
        )

        OrganicSectionCard(
            icon = "📅",
            title = "Organic Farming Schedule",
            description = "Plan sowing, fertilization, irrigation, pest management and harvesting.",
            onClick = {
                selectedSection = "schedule"
            }
        )

        OrganicSectionCard(
            icon = "♻️",
            title = "Organic Farming Tips",
            description = "Crop rotation, mulching, composting and water conservation practices.",
            onClick = {
                selectedSection = "tips"
            }
        )

        // Details
        when (selectedSection) {

            "crop" -> CropGuideContent()

            "fertilizer" -> FertilizerContent()

            "pest" -> PestManagementContent()

            "schedule" -> FarmingScheduleContent()

            "tips" -> OrganicTipsContent()
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Back to Dashboard")
        }
    }
}

@Composable
private fun OrganicSectionCard(
    icon: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Card(
                modifier = Modifier.size(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = icon,
                        fontSize = 23.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp)
            ) {

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )

                Text(
                    text = description,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = Color(0xFF616161),
                    modifier = Modifier.padding(top = 4.dp)
                )

                Button(
                    onClick = onClick,
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Text("View Details")
                }
            }
        }
    }
}

@Composable
private fun CropGuideContent() {

    DetailCard(
        title = "🌱 Organic Crop Guide",
        content = """
            🍅 Tomato
            • Use well-decomposed compost before planting.
            • Use mulch to conserve soil moisture.
            • Provide regular irrigation without waterlogging.
            • Use suitable neem-based products for preventive pest management.

            🌾 Wheat
            • Prepare soil using compost or farmyard manure.
            • Maintain proper irrigation during important growth stages.
            • Practice crop rotation to maintain soil health.

            🍚 Rice
            • Use organic manure during soil preparation.
            • Maintain proper field drainage.
            • Manage irrigation according to crop requirements.

            🥔 Potato
            • Use healthy seed tubers.
            • Add compost or well-decomposed organic manure.
            • Use mulching to reduce weeds and conserve moisture.
        """.trimIndent()
    )
}

@Composable
private fun FertilizerContent() {

    DetailCard(
        title = "🧪 Natural Fertilizers",
        content = """
            🌿 Vermicompost
            Nutrient-rich organic fertilizer produced using earthworms.

            🌿 Compost
            Decomposed organic material that improves soil structure and fertility.

            🌿 Jeevamrut
            Traditional microbial preparation used to support soil biological activity.

            🌿 Panchagavya
            Traditional organic preparation made from cow-derived ingredients.

            🌿 Neem Cake
            Organic manure that can also help manage some soil pests.

            🌿 Green Manure
            Suitable crops can be incorporated into soil to add organic matter.
        """.trimIndent()
    )
}

@Composable
private fun PestManagementContent() {

    DetailCard(
        title = "🐛 Natural Pest Management",
        content = """
            🌿 Neem-Based Products
            Can be used as part of an integrated pest-management approach.

            🌶️ Garlic-Chilli Extract
            Plant-based extracts may help discourage certain insect pests.

            🟨 Sticky Traps
            Yellow or blue sticky traps can help monitor and reduce certain flying insects.

            🦋 Biological Pest Control
            Beneficial insects and microbial biocontrol agents can help manage pests.

            🛡️ Preventive Practices
            • Remove heavily infected plant material.
            • Maintain field sanitation.
            • Use healthy planting material.
            • Inspect crops regularly.
        """.trimIndent()
    )
}

@Composable
private fun FarmingScheduleContent() {

    DetailCard(
        title = "📅 Organic Farming Schedule",
        content = """
            1️⃣ Sowing
            Select healthy seeds and prepare soil using organic matter.

            2️⃣ Fertilization
            Apply compost, vermicompost or suitable organic manure according to crop requirements.

            3️⃣ Irrigation
            Provide water according to crop stage, soil condition and weather.

            4️⃣ Pest Management
            Regularly inspect crops and use suitable preventive and biological methods.

            5️⃣ Harvesting
            Harvest at the appropriate maturity stage and handle produce carefully.
        """.trimIndent()
    )
}

@Composable
private fun OrganicTipsContent() {

    DetailCard(
        title = "♻️ Organic Farming Tips",
        content = """
            🔄 Crop Rotation
            Rotate different crops to improve soil health and reduce recurring pest problems.

            🍂 Mulching
            Use suitable organic mulch to conserve moisture and suppress weeds.

            ♻️ Composting
            Convert suitable farm and plant waste into useful compost.

            💧 Water Conservation
            Use efficient irrigation and avoid unnecessary water loss.

            🌿 Reduce Chemical Inputs
            Use chemical inputs only when appropriate and follow recommended agricultural practices.
        """.trimIndent()
    )
}

@Composable
private fun DetailCard(
    title: String,
    content: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F9F4)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = title,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )

            Text(
                text = content,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}