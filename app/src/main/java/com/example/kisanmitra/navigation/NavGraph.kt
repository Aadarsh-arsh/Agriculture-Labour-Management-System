package com.example.kisanmitra.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kisanmitra.ui.screens.attendance.AttendanceScreen
import com.example.kisanmitra.ui.screens.crop.CropScreen
import com.example.kisanmitra.ui.screens.dashboard.DashboardScreen
import com.example.kisanmitra.ui.screens.farm.FarmScreen
import com.example.kisanmitra.ui.screens.labour.LabourScreen

sealed class Screen(val route: String) {

    data object Dashboard : Screen("dashboard")

    data object Labour : Screen("labour")

    data object Attendance : Screen("attendance")

    data object Farm : Screen("farm")

    data object Crop : Screen("crop")
}

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {

        composable(Screen.Dashboard.route) {

            DashboardScreen(
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(Screen.Labour.route) {

            LabourScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Attendance.route) {

            AttendanceScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Farm.route) {

            FarmScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Crop.route) {

            CropScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}