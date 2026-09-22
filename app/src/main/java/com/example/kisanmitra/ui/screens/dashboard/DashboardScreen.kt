package com.example.kisanmitra.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
    onNavigate: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        // ---------------------------------------------------------
        // APP HEADER
        // ---------------------------------------------------------

        Text(
            text = "🌿 KisanMitra2",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )

        Text(
            text = "Farmer Dashboard",
            fontSize = 16.sp,
            color = Color(0xFF616161)
        )

        // ---------------------------------------------------------
        // WELCOME CARD
        // ---------------------------------------------------------

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E9)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Welcome, Farmer 👋",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )

                Text(
                    text = "Manage your farm, crops and agricultural labour from one place.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF424242),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // ---------------------------------------------------------
        // QUICK OVERVIEW
        // ---------------------------------------------------------

        Text(
            text = "Quick Overview",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121),
            modifier = Modifier.padding(top = 14.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            OverviewCard(
                icon = "👷",
                title = "Labour",
                modifier = Modifier.weight(1f)
            )

            OverviewCard(
                icon = "🌾",
                title = "Farms",
                modifier = Modifier.weight(1f)
            )

            OverviewCard(
                icon = "🌱",
                title = "Crops",
                modifier = Modifier.weight(1f)
            )
        }

        // ---------------------------------------------------------
        // FARM & LABOUR
        // ---------------------------------------------------------

        Text(
            text = "Farm & Labour",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121),
            modifier = Modifier.padding(top = 14.dp)
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
            icon = "🌾",
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
            description = "Calculate labour wages based on attendance",
            onClick = {
                onNavigate(Screen.Wage.route)
            }
        )

        // ---------------------------------------------------------
        // CROP MANAGEMENT
        // ---------------------------------------------------------

        Text(
            text = "Crop Management",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121),
            modifier = Modifier.padding(top = 14.dp)
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

        // ---------------------------------------------------------
        // PROJECT INFORMATION
        // ---------------------------------------------------------

        Text(
            text = "KisanMitra2",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20),
            modifier = Modifier.padding(top = 18.dp)
        )

        Text(
            text = "Farmer-focused agricultural labour and crop management system",
            fontSize = 13.sp,
            color = Color(0xFF616161),
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}

@Composable
private fun OverviewCard(
    icon: String,
    title: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F9F4)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = icon,
                fontSize = 24.sp
            )

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20),
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}