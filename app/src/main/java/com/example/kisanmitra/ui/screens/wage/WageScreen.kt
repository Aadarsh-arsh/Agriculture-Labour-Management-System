package com.example.kisanmitra.ui.screens.wage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kisanmitra.data.Wage

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
            .padding(16.dp)
    ) {

        Text(
            text = "Wage Calculation"
        )

        Text(
            text = "Calculate wages from attendance",
            modifier = Modifier.padding(top = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(
                items = labourers,
                key = { it.id }
            ) { labour ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = labour.name
                        )

                        Text(
                            text = "Daily Wage: ₹${labour.dailyWage}"
                        )

                        val presentDays = attendanceList.count {
                            it.labourId == labour.id &&
                                    it.status == "Present"
                        }

                        val totalWage =
                            presentDays * labour.dailyWage

                        Text(
                            text = "Present Days: $presentDays",
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Text(
                            text = "Total Wage: ₹$totalWage",
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Button(
                            onClick = {
                                viewModel.calculateAndSaveWage(
                                    labour = labour,
                                    attendanceList = attendanceList
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Text("Calculate & Save Wage")
                        }
                    }
                }
            }
        }

        Text(
            text = "Saved Wage Records",
            modifier = Modifier.padding(top = 12.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(0.7f)
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

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
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Back")
        }
    }
}

@Composable
private fun WageCard(
    wage: Wage,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = wage.labourName
            )

            Text(
                text = "Daily Wage: ₹${wage.dailyWage}"
            )

            Text(
                text = "Present Days: ${wage.presentDays}"
            )

            Text(
                text = "Total Wage: ₹${wage.totalWage}"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {

                Button(
                    onClick = onDelete
                ) {
                    Text("Delete")
                }
            }
        }
    }
}