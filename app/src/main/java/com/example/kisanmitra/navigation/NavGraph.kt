package com.example.kisanmitra.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kisanmitra.ui.screens.assignment.LabourAssignmentScreen
import com.example.kisanmitra.ui.screens.attendance.AttendanceScreen
import com.example.kisanmitra.ui.screens.crop.CropScreen
import com.example.kisanmitra.ui.screens.crop.CropStageScreen
import com.example.kisanmitra.ui.screens.dashboard.DashboardScreen
import com.example.kisanmitra.ui.screens.farm.FarmScreen
import com.example.kisanmitra.ui.screens.labour.LabourScreen
import com.example.kisanmitra.ui.screens.organic.OrganicFarmingScreen
import com.example.kisanmitra.ui.screens.task.AgriculturalTaskScreen
import com.example.kisanmitra.ui.screens.wage.WageScreen

sealed class Screen(val route: String) {

    data object Dashboard : Screen("dashboard")

    data object Labour : Screen("labour")

    data object Attendance : Screen("attendance")

    data object Farm : Screen("farm")

    data object Crop : Screen("crop")

    data object CropStage : Screen("crop_stage")

    data object AgriculturalTask : Screen("agricultural_task")

    data object LabourAssignment : Screen("labour_assignment")

    data object Wage : Screen("wage")

    data object OrganicFarming : Screen("organic_farming")
}

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {

        // Dashboard
        composable(Screen.Dashboard.route) {

            DashboardScreen(
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }

        // Labour Management
        composable(Screen.Labour.route) {

            LabourScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Voice Attendance
        composable(Screen.Attendance.route) {

            AttendanceScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Farm Management
        composable(Screen.Farm.route) {

            FarmScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Crop Management
        composable(Screen.Crop.route) {

            CropScreen(
                onBack = {
                    navController.popBackStack()
                },
                onAgriculturalTask = {
                    navController.navigate(
                        Screen.AgriculturalTask.route
                    )
                },
                onCropStage = {
                    navController.navigate(
                        Screen.CropStage.route
                    )
                }
            )
        }

        // Crop Stage Management
        composable(Screen.CropStage.route) {

            CropStageScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Agricultural Task Management
        composable(Screen.AgriculturalTask.route) {

            AgriculturalTaskScreen(
                onBack = {
                    navController.popBackStack()
                },
                onAssignLabour = {
                    navController.navigate(
                        Screen.LabourAssignment.route
                    )
                }
            )
        }

        // Labour Assignment
        composable(Screen.LabourAssignment.route) {

            LabourAssignmentScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Wage Calculation
        composable(Screen.Wage.route) {

            WageScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Organic Farming
        composable(Screen.OrganicFarming.route) {

            OrganicFarmingScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}