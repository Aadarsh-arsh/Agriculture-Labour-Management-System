package com.example.kisanmitra.ui.screens.wage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kisanmitra.data.Wage

private val FarmGreen = Color(0xFF2E7D32)
private val LightGreen = Color(0xFFE8F5E9)
private val DarkGreen = Color(0xFF1B5E20)
private val SoftBackground = Color(0xFFF7FAF7)
private val DeleteRed = Color(0xFFD32F2F)
private val Gold = Color(0xFFF9A825)

@Composable
fun WageScreen(
    onBack: () -> Unit,
    viewModel: WageViewModel = viewModel()
) {

    val labourers by viewModel.labourers.collectAsState(
        initial = emptyList()
    )

    val attendanceList by viewModel.attendance.collectAsState(
        initial = emptyList()
    )

    val wages by viewModel.wages.collectAsState(
        initial = emptyList()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBackground)
    ) {

        // Header
        Surface(
            color = FarmGreen,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 18.dp
                )
            ) {

                Text(
                    text = "Wage Management",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Calculate and manage labour payments",
                    color = Color.White.copy(alpha = 0.85f),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Summary
            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    SummaryCard(
                        title = "Labourers",
                        value = labourers.size.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    SummaryCard(
                        title = "Saved Records",
                        value = wages.size.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Calculate wages section
            item {

                Text(
                    text = "Calculate Labour Wages",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = "Wages are calculated from Present attendance",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            items(
                items = labourers,
                key = { it.id }
            ) { labour ->

                val presentDays = attendanceList.count {
                    it.labourId == labour.id &&
                            it.status.trim().equals(
                                "Present",
                                ignoreCase = true
                            )
                }

                val totalWage =
                    presentDays * labour.dailyWage

                LabourWageCard(
                    labourName = labour.name,
                    labourId = labour.id,
                    dailyWage = labour.dailyWage,
                    presentDays = presentDays,
                    totalWage = totalWage,
                    onCalculate = {

                        viewModel.calculateAndSaveWage(
                            labour = labour,
                            attendanceList = attendanceList
                        )
                    }
                )
            }

            // Saved records heading
            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                ) {

                    Text(
                        text = "Saved Wage Records",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${wages.size} record${
                            if (wages.size == 1) "" else "s"
                        } saved",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Saved wages
            items(
                items = wages,
                key = { it.id }
            ) { wage ->

                WageCard(
                    wage = wage,
                    onDelete = {
                        viewModel.deleteWage(wage)
                    }
                )
            }

            // Back
            item {

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {

                    Text(
                        text = "Back",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SummaryCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun LabourWageCard(
    labourName: String,
    labourId: Int,
    dailyWage: Double,
    presentDays: Int,
    totalWage: Double,
    onCalculate: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = labourName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = DarkGreen
                    )

                    Text(
                        text = "Labour ID: $labourId",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Text(
                    text = "₹${String.format("%.0f", dailyWage)}/day",
                    color = FarmGreen,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        LightGreen,
                        RoundedCornerShape(14.dp)
                    )
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        text = "PRESENT DAYS",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = presentDays.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = "TOTAL WAGE",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "₹${String.format("%.0f", totalWage)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = DarkGreen,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
            }

            Button(
                onClick = onCalculate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FarmGreen
                )
            ) {

                Text(
                    text = "Calculate & Save Wage",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun WageCard(
    wage: Wage,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = wage.labourName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "₹${String.format("%.0f", wage.dailyWage)} per day",
                        color = FarmGreen,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .background(
                            Color(0xFFFFEBEE),
                            RoundedCornerShape(10.dp)
                        )
                ) {

                    Text(
                        text = "×",
                        color = DeleteRed,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        LightGreen,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        text = "PRESENT DAYS",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = wage.presentDays.toString(),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = "TOTAL PAYMENT",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "₹${String.format("%.0f", wage.totalWage)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = DarkGreen,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
            }
        }
    }
}