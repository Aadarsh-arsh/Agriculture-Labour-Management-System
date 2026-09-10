package com.example.kisanmitra.ui.screens.assignment

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
import com.example.kisanmitra.data.LabourAssignment

@Composable
fun LabourAssignmentScreen(
    onBack: () -> Unit,
    viewModel: LabourAssignmentViewModel = viewModel()
) {

    val assignments by viewModel.assignments.collectAsState(
        initial = emptyList()
    )

    var labourId by remember { mutableStateOf("") }
    var labourName by remember { mutableStateOf("") }
    var farmName by remember { mutableStateOf("") }
    var cropName by remember { mutableStateOf("") }
    var taskName by remember { mutableStateOf("") }
    var assignmentDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Labour Assignment"
        )

        OutlinedTextField(
            value = labourId,
            onValueChange = { labourId = it },
            label = { Text("Labour ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        OutlinedTextField(
            value = labourName,
            onValueChange = { labourName = it },
            label = { Text("Labour Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = farmName,
            onValueChange = { farmName = it },
            label = { Text("Farm Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = cropName,
            onValueChange = { cropName = it },
            label = { Text("Crop Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Agricultural Task") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = assignmentDate,
            onValueChange = { assignmentDate = it },
            label = { Text("Assignment Date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {

                viewModel.addAssignment(
                    labourId = labourId.toIntOrNull() ?: 0,
                    labourName = labourName,
                    farmName = farmName,
                    cropName = cropName,
                    taskName = taskName,
                    assignmentDate = assignmentDate
                )

                labourId = ""
                labourName = ""
                farmName = ""
                cropName = ""
                taskName = ""
                assignmentDate = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text("Assign Labour")
        }

        Text(
            text = "Current Assignments",
            modifier = Modifier.padding(top = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(
                items = assignments,
                key = { it.id }
            ) { assignment ->

                AssignmentCard(
                    assignment = assignment,
                    onDelete = {
                        viewModel.deleteAssignment(assignment)
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
private fun AssignmentCard(
    assignment: LabourAssignment,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = assignment.labourName
            )

            Text(
                text = "Farm: ${assignment.farmName}"
            )

            Text(
                text = "Crop: ${assignment.cropName}"
            )

            Text(
                text = "Task: ${assignment.taskName}"
            )

            Text(
                text = "Date: ${assignment.assignmentDate}"
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