package com.example.kisanmitra.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kisanmitra.data.SupabaseClientProvider
import com.example.kisanmitra.ui.screens.assignment.LabourAssignmentScreen
import com.example.kisanmitra.ui.screens.attendance.AttendanceScreen
import com.example.kisanmitra.ui.screens.auth.LoginScreen
import com.example.kisanmitra.ui.screens.auth.SignUpScreen
import com.example.kisanmitra.ui.screens.crop.CropScreen
import com.example.kisanmitra.ui.screens.crop.CropStageScreen
import com.example.kisanmitra.ui.screens.dashboard.DashboardScreen
import com.example.kisanmitra.ui.screens.farm.FarmScreen
import com.example.kisanmitra.ui.screens.labour.LabourScreen
import com.example.kisanmitra.ui.screens.organic.OrganicFarmingScreen
import com.example.kisanmitra.ui.screens.task.AgriculturalTaskScreen
import com.example.kisanmitra.ui.screens.wage.WageScreen
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {

    data object Login : Screen("login")
    data object SignUp : Screen("signup")

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
fun NavGraph(navController: NavHostController) {

    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        // -------------------------
        // LOGIN
        // -------------------------

        composable(Screen.Login.route) {

            LoginScreen(
                onLogin = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },

                onSignUp = {
                    navController.navigate(Screen.SignUp.route)
                },

                onForgotPassword = {
                    // Forgot password will be added later
                }
            )
        }

        // -------------------------
        // SIGN UP
        // -------------------------

        composable(Screen.SignUp.route) {

            SignUpScreen(
                onSignUp = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // -------------------------
        // DASHBOARD
        // -------------------------

        composable(Screen.Dashboard.route) {

            DashboardScreen(
                onNavigate = { route ->
                    navController.navigate(route)
                },

                onLogout = {
                    scope.launch {

                        try {

                            SupabaseClientProvider.client.auth.signOut()

                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Dashboard.route) {
                                    inclusive = true
                                }
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            )
        }

        // -------------------------
        // LABOUR
        // -------------------------

        composable(Screen.Labour.route) {

            LabourScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // -------------------------
        // ATTENDANCE
        // -------------------------

        composable(Screen.Attendance.route) {

            AttendanceScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // -------------------------
        // FARM
        // -------------------------

        composable(Screen.Farm.route) {

            FarmScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // -------------------------
        // CROP
        // -------------------------

        composable(Screen.Crop.route) {

            CropScreen(
                onBack = {
                    navController.popBackStack()
                },

                onAgriculturalTask = {
                    navController.navigate(Screen.AgriculturalTask.route)
                },

                onCropStage = {
                    navController.navigate(Screen.CropStage.route)
                }
            )
        }

        // -------------------------
        // CROP STAGE
        // -------------------------

        composable(Screen.CropStage.route) {

            CropStageScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // -------------------------
        // AGRICULTURAL TASK
        // -------------------------

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

        // -------------------------
        // LABOUR ASSIGNMENT
        // -------------------------

        composable(Screen.LabourAssignment.route) {

            LabourAssignmentScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // -------------------------
        // WAGE
        // -------------------------

        composable(Screen.Wage.route) {

            WageScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // -------------------------
        // ORGANIC FARMING
        // -------------------------

        composable(Screen.OrganicFarming.route) {

            OrganicFarmingScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}