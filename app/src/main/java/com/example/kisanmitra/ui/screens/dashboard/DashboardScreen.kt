package com.example.kisanmitra.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kisanmitra.navigation.Screen
import com.example.kisanmitra.ui.components.DashboardCard

@Composable
fun DashboardScreen(
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {

    val darkGreen = Color(0xFF1B5E20)
    val mediumGreen = Color(0xFF2E7D32)
    val lightGreen = Color(0xFFE8F5E9)
    val softGreen = Color(0xFFF4F8F3)
    val textDark = Color(0xFF263238)
    val textGrey = Color(0xFF607D8B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCF9))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "KisanMitra2",
                    fontSize = 29.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = darkGreen
                )

                Text(
                    text = "Smart Farmer Management",
                    fontSize = 14.sp,
                    color = textGrey,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = lightGreen
                )
            ) {
                Text(
                    text = "🌿",
                    fontSize = 27.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

        // Welcome card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor = darkGreen
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(22.dp)
            ) {

                Text(
                    text = "Welcome, Farmer 👋",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Manage your farm, crops, labour and daily agricultural activities from one place.",
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = Color(0xFFE8F5E9),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2E7D32)
                    )
                ) {
                    Text(
                        text = "🌱  Your farm. Your data. Your control.",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier.padding(13.dp)
                    )
                }
            }
        }

        SectionTitle(
            title = "Quick Overview",
            subtitle = "Your farm management at a glance"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            OverviewCard(
                icon = "👷",
                title = "Labour",
                modifier = Modifier.weight(1f),
                backgroundColor = Color(0xFFFFF8E1),
                iconBackground = Color(0xFFFFECB3)
            )

            OverviewCard(
                icon = "🚜",
                title = "Farms",
                modifier = Modifier.weight(1f),
                backgroundColor = Color(0xFFE3F2FD),
                iconBackground = Color(0xFFBBDEFB)
            )

            OverviewCard(
                icon = "🌱",
                title = "Crops",
                modifier = Modifier.weight(1f),
                backgroundColor = Color(0xFFE8F5E9),
                iconBackground = Color(0xFFC8E6C9)
            )
        }

        SectionTitle(
            title = "Farm & Labour",
            subtitle = "Manage your workforce and daily operations"
        )

        DashboardCard(
            icon = "👷",
            title = "Labour Management",
            description = "Add and manage agricultural labourers",
            onClick = {
                onNavigate(Screen.Labour.route)
            }
        )

        DashboardCard(
            icon = "🚜",
            title = "Farm Management",
            description = "Manage farms and agricultural land",
            onClick = {
                onNavigate(Screen.Farm.route)
            }
        )

        DashboardCard(
            icon = "📋",
            title = "Agricultural Tasks",
            description = "Create and manage farming tasks",
            onClick = {
                onNavigate(Screen.AgriculturalTask.route)
            }
        )

        DashboardCard(
            icon = "👥",
            title = "Labour Assignment",
            description = "Assign labourers to farms, crops and tasks",
            onClick = {
                onNavigate(Screen.LabourAssignment.route)
            }
        )

        DashboardCard(
            icon = "🔊",
            title = "Voice Attendance",
            description = "Record labour attendance using voice assistance",
            onClick = {
                onNavigate(Screen.Attendance.route)
            }
        )

        DashboardCard(
            icon = "💰",
            title = "Wage Calculation",
            description = "Calculate labour wages from attendance",
            onClick = {
                onNavigate(Screen.Wage.route)
            }
        )

        SectionTitle(
            title = "Crop Management",
            subtitle = "Plan, monitor and manage your crops"
        )

        DashboardCard(
            icon = "🌱",
            title = "Crop Management",
            description = "Add and manage crops on your farms",
            onClick = {
                onNavigate(Screen.Crop.route)
            }
        )

        DashboardCard(
            icon = "📅",
            title = "Crop Stages / Farming Plan",
            description = "Track crop growth stages and farming schedules",
            onClick = {
                onNavigate(Screen.CropStage.route)
            }
        )

        DashboardCard(
            icon = "🌿",
            title = "Organic Farming",
            description = "Organic practices, natural fertilizers and pest management",
            onClick = {
                onNavigate(Screen.OrganicFarming.route)
            }
        )

        // About card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = softGreen
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "About KisanMitra2",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkGreen
                )

                Text(
                    text = "A farmer-focused agricultural labour and crop management system designed to simplify everyday farm operations.",
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    color = textDark,
                    modifier = Modifier.padding(top = 7.dp)
                )

                Text(
                    text = "🌾 Manage • 📋 Plan • 👷 Assign • 🗣️ Track • 💰 Calculate",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = mediumGreen,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }

        // Logout button
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 10.dp)
        ) {
            Text(
                text = "Logout",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    subtitle: String
) {

    Column(
        modifier = Modifier.padding(
            top = 5.dp,
            bottom = 1.dp
        )
    ) {

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF263238)
        )

        Text(
            text = subtitle,
            fontSize = 12.sp,
            color = Color(0xFF78909C),
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
private fun OverviewCard(
    icon: String,
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    iconBackground: Color
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = iconBackground,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 23.sp
                )
            }

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20),
                modifier = Modifier.padding(top = 7.dp)
            )
        }
    }
}