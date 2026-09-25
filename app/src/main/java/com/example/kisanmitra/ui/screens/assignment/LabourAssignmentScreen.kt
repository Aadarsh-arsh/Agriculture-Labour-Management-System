package com.example.kisanmitra.ui.screens.assignment

import android.app.DatePickerDialog
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kisanmitra.data.LabourAssignment
import java.util.Calendar

@Composable
fun LabourAssignmentScreen(
    onBack: () -> Unit,
    viewModel: LabourAssignmentViewModel = viewModel()
) {
    val context = LocalContext.current

    val assignments by viewModel.assignments.collectAsState()
    val labourers by viewModel.labourers.collectAsState()
    val farms by viewModel.farms.collectAsState()
    val crops by viewModel.crops.collectAsState()
    val agriculturalTasks by viewModel.agriculturalTasks.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var selectedLabour by remember { mutableStateOf("") }
    var selectedLabourId by remember { mutableStateOf(0) }

    var selectedFarm by remember { mutableStateOf("") }
    var selectedCrop by remember { mutableStateOf("") }
    var selectedTask by remember { mutableStateOf("") }
    var assignmentDate by remember { mutableStateOf("") }

    var labourExpanded by remember { mutableStateOf(false) }
    var farmExpanded by remember { mutableStateOf(false) }
    var cropExpanded by remember { mutableStateOf(false) }
    var taskExpanded by remember { mutableStateOf(false) }

    var assignmentToDelete by remember {
        mutableStateOf<LabourAssignment?>(null)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text(
                text = "Labour Assignment"
            )
        }

        item {
            Column {
                OutlinedButton(
                    onClick = {
                        labourExpanded = true
                        viewModel.clearError()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (selectedLabour.isBlank()) {
                            "Select Labourer"
                        } else {
                            selectedLabour
                        }
                    )
                }

                DropdownMenu(
                    expanded = labourExpanded,
                    onDismissRequest = {
                        labourExpanded = false
                    }
                ) {
                    labourers.forEach { labour ->
                        DropdownMenuItem(
                            text = {
                                Text(labour.name)
                            },
                            onClick = {
                                selectedLabour = labour.name
                                selectedLabourId = labour.id
                                labourExpanded = false
                                viewModel.clearError()
                            }
                        )
                    }
                }
            }
        }

        item {
            Column {
                OutlinedButton(
                    onClick = {
                        farmExpanded = true
                        viewModel.clearError()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (selectedFarm.isBlank()) {
                            "Select Farm"
                        } else {
                            selectedFarm
                        }
                    )
                }

                DropdownMenu(
                    expanded = farmExpanded,
                    onDismissRequest = {
                        farmExpanded = false
                    }
                ) {
                    farms.forEach { farm ->
                        DropdownMenuItem(
                            text = {
                                Text(farm.farmName)
                            },
                            onClick = {
                                selectedFarm = farm.farmName
                                farmExpanded = false
                                viewModel.clearError()
                            }
                        )
                    }
                }
            }
        }

        item {
            Column {
                OutlinedButton(
                    onClick = {
                        cropExpanded = true
                        viewModel.clearError()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (selectedCrop.isBlank()) {
                            "Select Crop"
                        } else {
                            selectedCrop
                        }
                    )
                }

                DropdownMenu(
                    expanded = cropExpanded,
                    onDismissRequest = {
                        cropExpanded = false
                    }
                ) {
                    crops.forEach { crop ->
                        DropdownMenuItem(
                            text = {
                                Text(crop.cropName)
                            },
                            onClick = {
                                selectedCrop = crop.cropName
                                cropExpanded = false
                                viewModel.clearError()
                            }
                        )
                    }
                }
            }
        }

        item {
            Column {
                OutlinedButton(
                    onClick = {
                        taskExpanded = true
                        viewModel.clearError()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (selectedTask.isBlank()) {
                            "Select Agricultural Task"
                        } else {
                            selectedTask
                        }
                    )
                }

                DropdownMenu(
                    expanded = taskExpanded,
                    onDismissRequest = {
                        taskExpanded = false
                    }
                ) {
                    agriculturalTasks.forEach { task ->
                        DropdownMenuItem(
                            text = {
                                Text(task.taskName)
                            },
                            onClick = {
                                selectedTask = task.taskName
                                taskExpanded = false
                                viewModel.clearError()
                            }
                        )
                    }
                }
            }
        }

        item {
            OutlinedTextField(
                value = assignmentDate,
                onValueChange = {
                    assignmentDate = it
                    viewModel.clearError()
                },
                label = {
                    Text("Assignment Date")
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    TextButton(
                        onClick = {
                            val calendar =
                                Calendar.getInstance()

                            DatePickerDialog(
                                context,
                                { _, year, month, day ->
                                    assignmentDate =
                                        String.format(
                                            "%02d/%02d/%04d",
                                            day,
                                            month + 1,
                                            year
                                        )

                                    viewModel.clearError()
                                },
                                calendar.get(
                                    Calendar.YEAR
                                ),
                                calendar.get(
                                    Calendar.MONTH
                                ),
                                calendar.get(
                                    Calendar.DAY_OF_MONTH
                                )
                            ).show()
                        }
                    ) {
                        Text("Select")
                    }
                }
            )
        }

        item {
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red
                )
            }
        }

        item {
            Button(
                onClick = {
                    viewModel.addAssignment(
                        labourId = selectedLabourId,
                        labourName = selectedLabour,
                        farmName = selectedFarm,
                        cropName = selectedCrop,
                        taskName = selectedTask,
                        assignmentDate = assignmentDate
                    )

                    if (
                        selectedLabourId > 0 &&
                        selectedLabour.isNotBlank() &&
                        selectedFarm.isNotBlank() &&
                        selectedCrop.isNotBlank() &&
                        selectedTask.isNotBlank() &&
                        assignmentDate.isNotBlank()
                    ) {
                        selectedLabour = ""
                        selectedLabourId = 0
                        selectedFarm = ""
                        selectedCrop = ""
                        selectedTask = ""
                        assignmentDate = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Assign Labour")
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Assignments"
            )
        }

        items(assignments) { assignment ->

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text =
                                "Labour: ${assignment.labourName}"
                        )

                        Text(
                            text =
                                "Farm: ${assignment.farmName}"
                        )

                        Text(
                            text =
                                "Crop: ${assignment.cropName}"
                        )

                        Text(
                            text =
                                "Task: ${assignment.taskName}"
                        )

                        Text(
                            text =
                                "Date: ${assignment.assignmentDate}"
                        )
                    }

                    IconButton(
                        onClick = {
                            assignmentToDelete =
                                assignment
                        }
                    ) {
                        Text("×")
                    }
                }
            }
        }

        item {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }

    if (assignmentToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                assignmentToDelete = null
            },
            title = {
                Text("Delete Labour Assignment?")
            },
            text = {
                Text(
                    "Are you sure you want to delete the assignment for \"${assignmentToDelete?.labourName}\"?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        assignmentToDelete?.let {
                            viewModel.deleteAssignment(it)
                        }

                        assignmentToDelete = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        assignmentToDelete = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

