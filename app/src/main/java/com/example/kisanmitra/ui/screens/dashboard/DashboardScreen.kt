package com.example.kisanmitra.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = "KisanMitra2"
        )

        Text(
            text = "Farmer Dashboard"
        )

        Text(
            text = "Welcome, Farmer 👋"
        )

        Text(
            text = "Manage your farm, crops and agricultural labour from one place."
        )

        Text(
            text = "Quick Actions",
            modifier = Modifier.padding(top = 16.dp)
        )

        DashboardCard(
            title = "👷 Labour Management",
            description = "Add and manage agricultural labourers",
            onClick = {
                onNavigate(Screen.Labour.route)
            }
        )

        DashboardCard(
            title = "📋 Voice Attendance",
            description = "Record labour attendance using voice assistance",
            onClick = {
                onNavigate(Screen.Attendance.route)
            }
        )

        DashboardCard(
            title = "💰 Wage Calculation",
            description = "Calculate labour wages based on attendance",
            onClick = {
                onNavigate(Screen.Wage.route)
            }
        )

        DashboardCard(
            title = "🌾 Farm Management",
            description = "Manage your farms and agricultural land",
            onClick = {
                onNavigate(Screen.Farm.route)
            }
        )

        DashboardCard(
            title = "🌱 Crop Management",
            description = "Manage crops and agricultural activities",
            onClick = {
                onNavigate(Screen.Crop.route)
            }
        )

        Text(
            text = "Agriculture Services",
            modifier = Modifier.padding(top = 16.dp)
        )

        DashboardCard(
            title = "🔬 Crop Care & Disease Detection",
            description = "Get information about crop health and diseases",
            onClick = {
                // Feature will be added later
            }
        )

        DashboardCard(
            title = "📖 Farming Guide",
            description = "Access useful agricultural information and crop-care guidance",
            onClick = {
                // Feature will be added later
            }
        )
    }
}