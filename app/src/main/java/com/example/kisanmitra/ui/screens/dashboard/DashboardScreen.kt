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
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "🌿 KisanMitra2",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )

        Text(
            text = "Smart Farmer Management",
            fontSize = 15.sp,
            color = Color(0xFF616161)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E9)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Welcome, Farmer 👋",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )

                Text(
                    text = "Manage your farm, crops, labour, attendance and wages from one place.",
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = Color(0xFF424242),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        SectionTitle("Quick Overview")

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

        SectionTitle("Farm & Labour")

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
            description = "Calculate labour wages from attendance",
            onClick = {
                onNavigate(Screen.Wage.route)
            }
        )

        SectionTitle("Crop Management")

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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 12.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF5F9F4)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "About KisanMitra2",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )

                Text(
                    text = "A farmer-focused agricultural labour and crop management system.",
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = Color(0xFF616161),
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(
    title: String
) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF212121),
        modifier = Modifier.padding(
            top = 8.dp,
            bottom = 2.dp
        )
    )
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                fontSize = 25.sp
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