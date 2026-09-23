package com.example.kisanmitra.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kisanmitra.R
import com.example.kisanmitra.navigation.Screen
import com.example.kisanmitra.ui.components.DashboardCard
import com.example.kisanmitra.ui.components.LanguageSelector

@Composable
fun DashboardScreen(
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    onLanguageChange: (Boolean) -> Unit,
    isHindi: Boolean
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

        // =========================
        // HEADER
        // =========================

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
                    text = stringResource(R.string.dashboard_subtitle),
                    fontSize = 14.sp,
                    color = textGrey,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

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

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                LanguageSelector(
                    isHindi = isHindi,
                    onLanguageChange = onLanguageChange
                )
            }
        }

        // =========================
        // WELCOME CARD
        // =========================

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
                    text = stringResource(R.string.welcome_farmer),
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = stringResource(R.string.dashboard_description),
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
                        containerColor = mediumGreen
                    )
                ) {

                    Text(
                        text = stringResource(
                            R.string.your_farm_data_control
                        ),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier.padding(13.dp)
                    )
                }
            }
        }

        // =========================
        // QUICK OVERVIEW
        // =========================

        SectionTitle(
            title = stringResource(R.string.quick_overview),
            subtitle = stringResource(
                R.string.farm_management_at_glance
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            OverviewCard(
                icon = "👷",
                title = stringResource(R.string.labour),
                modifier = Modifier.weight(1f),
                backgroundColor = Color(0xFFFFF8E1),
                iconBackground = Color(0xFFFFECB3)
            )

            OverviewCard(
                icon = "🚜",
                title = stringResource(R.string.farms),
                modifier = Modifier.weight(1f),
                backgroundColor = Color(0xFFE3F2FD),
                iconBackground = Color(0xFFBBDEFB)
            )

            OverviewCard(
                icon = "🌱",
                title = stringResource(R.string.crops),
                modifier = Modifier.weight(1f),
                backgroundColor = Color(0xFFE8F5E9),
                iconBackground = Color(0xFFC8E6C9)
            )
        }

        // =========================
        // FARM & LABOUR
        // =========================

        SectionTitle(
            title = stringResource(R.string.farm_labour),
            subtitle = stringResource(R.string.manage_workforce)
        )

        DashboardCard(
            icon = "👷",
            title = stringResource(R.string.labour_management),
            description = stringResource(
                R.string.labour_management_desc
            ),
            onClick = {
                onNavigate(Screen.Labour.route)
            }
        )

        DashboardCard(
            icon = "🚜",
            title = stringResource(R.string.farm_management),
            description = stringResource(
                R.string.farm_management_desc
            ),
            onClick = {
                onNavigate(Screen.Farm.route)
            }
        )

        DashboardCard(
            icon = "📋",
            title = stringResource(R.string.agricultural_tasks),
            description = stringResource(
                R.string.agricultural_tasks_desc
            ),
            onClick = {
                onNavigate(Screen.AgriculturalTask.route)
            }
        )

        DashboardCard(
            icon = "👥",
            title = stringResource(R.string.labour_assignment),
            description = stringResource(
                R.string.labour_assignment_desc
            ),
            onClick = {
                onNavigate(Screen.LabourAssignment.route)
            }
        )

        DashboardCard(
            icon = "🔊",
            title = stringResource(R.string.voice_attendance),
            description = stringResource(
                R.string.voice_attendance_desc
            ),
            onClick = {
                onNavigate(Screen.Attendance.route)
            }
        )

        DashboardCard(
            icon = "💰",
            title = stringResource(R.string.wage_calculation),
            description = stringResource(
                R.string.wage_calculation_desc
            ),
            onClick = {
                onNavigate(Screen.Wage.route)
            }
        )

        // =========================
        // CROP MANAGEMENT
        // =========================

        SectionTitle(
            title = stringResource(
                R.string.crop_management_section
            ),
            subtitle = stringResource(
                R.string.plan_monitor_manage_crops
            )
        )

        DashboardCard(
            icon = "🌱",
            title = stringResource(
                R.string.crop_management
            ),
            description = stringResource(
                R.string.crop_management_desc
            ),
            onClick = {
                onNavigate(Screen.Crop.route)
            }
        )

        DashboardCard(
            icon = "📅",
            title = stringResource(
                R.string.crop_stages_farming_plan
            ),
            description = stringResource(
                R.string.crop_stages_desc
            ),
            onClick = {
                onNavigate(Screen.CropStage.route)
            }
        )

        DashboardCard(
            icon = "🌿",
            title = stringResource(
                R.string.organic_farming
            ),
            description = stringResource(
                R.string.organic_farming_desc
            ),
            onClick = {
                onNavigate(Screen.OrganicFarming.route)
            }
        )

        // =========================
        // ABOUT
        // =========================

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
                    text = stringResource(
                        R.string.about_kisanmitra
                    ),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkGreen
                )

                Text(
                    text = stringResource(
                        R.string.about_kisanmitra_desc
                    ),
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    color = textDark,
                    modifier = Modifier.padding(top = 7.dp)
                )

                Text(
                    text = stringResource(
                        R.string.about_features
                    ),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = mediumGreen,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }

        // =========================
        // LOGOUT
        // =========================

        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 4.dp,
                    bottom = 10.dp
                )
        ) {

            Text(
                text = stringResource(R.string.logout),
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
                .padding(
                    vertical = 14.dp,
                    horizontal = 8.dp
                ),
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