package com.example.kisanmitra.ui.screens.labour

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kisanmitra.data.Labour

@Composable
fun LabourScreen(
    onBack: () -> Unit,
    viewModel: LabourViewModel = viewModel()
) {

    val labourers by viewModel.labourers.collectAsState()

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var wage by remember { mutableStateOf("") }
    var skill by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Labour Management"
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text("Labour Name")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = {
                Text("Phone Number")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = wage,
            onValueChange = { wage = it },
            label = {
                Text("Daily Wage")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = skill,
            onValueChange = { skill = it },
            label = {
                Text("Skill / Work Type")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {

                if (
                    name.isNotBlank() &&
                    phone.isNotBlank() &&
                    wage.isNotBlank() &&
                    skill.isNotBlank()
                ) {
                    viewModel.addLabour(
                        Labour(
                            name = name,
                            phone = phone,
                            dailyWage = wage.toDoubleOrNull() ?: 0.0,
                            skill = skill
                        )
                    )

                    name = ""
                    phone = ""
                    wage = ""
                    skill = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text("Add Labourer")
        }

        Text(
            text = "Labourers",
            modifier = Modifier.padding(top = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(
                items = labourers,
                key = { it.id }
            ) { labour ->

                LabourCard(
                    labour = labour,
                    onDelete = {
                        viewModel.deleteLabour(labour)
                    }
                )
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}

@Composable
private fun LabourCard(
    labour: Labour,
    onDelete: () -> Unit
) {

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
                text = "Phone: ${labour.phone}"
            )

            Text(
                text = "Daily Wage: ₹${labour.dailyWage}"
            )

            Text(
                text = "Skill: ${labour.skill}"
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