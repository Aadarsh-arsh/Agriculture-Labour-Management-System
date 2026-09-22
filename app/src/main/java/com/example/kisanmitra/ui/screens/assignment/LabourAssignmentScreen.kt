package com.example.kisanmitra.ui.screens.assignment

import android.app.DatePickerDialog
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kisanmitra.data.LabourAssignment
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabourAssignmentScreen(
    onBack: () -> Unit,
    viewModel: LabourAssignmentViewModel = viewModel()
) {

    val context = LocalContext.current

    val assignments by viewModel.assignments.collectAsState(
        initial = emptyList()
    )

    val labourers by viewModel.labourers.collectAsState(
        initial = emptyList()
    )

    val farms by viewModel.farms.collectAsState(
        initial = emptyList()
    )

    val crops by viewModel.crops.collectAsState(
        initial = emptyList()
    )

    val agriculturalTasks by viewModel.agriculturalTasks.collectAsState(
        initial = emptyList()
    )

    var selectedLabourId by remember {
        mutableStateOf(0)
    }

    var selectedLabourName by remember {
        mutableStateOf("")
    }

    var labourDropdownExpanded by remember {
        mutableStateOf(false)
    }

    var selectedFarmName by remember {
        mutableStateOf("")
    }

    var farmDropdownExpanded by remember {
        mutableStateOf(false)
    }

    var selectedCropName by remember {
        mutableStateOf("")
    }

    var cropDropdownExpanded by remember {
        mutableStateOf(false)
    }

    var selectedTaskName by remember {
        mutableStateOf("")
    }

    var taskDropdownExpanded by remember {
        mutableStateOf(false)
    }

    var assignmentDate by remember {
        mutableStateOf("")
    }

    val calendar = remember {
        Calendar.getInstance()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Labour Assignment"
        )

        // -------------------------
        // Labour Selection
        // -------------------------

        ExposedDropdownMenuBox(
            expanded = labourDropdownExpanded,
            onExpandedChange = {
                labourDropdownExpanded = !labourDropdownExpanded
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {

            OutlinedTextField(
                value = selectedLabourName,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Select Labour")
                },
                placeholder = {
                    Text("Choose registered labour")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = labourDropdownExpanded
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = labourDropdownExpanded,
                onDismissRequest = {
                    labourDropdownExpanded = false
                }
            ) {

                if (labourers.isEmpty()) {

                    DropdownMenuItem(
                        text = {
                            Text("No labourers registered")
                        },
                        onClick = {
                            labourDropdownExpanded = false
                        }
                    )

                } else {

                    labourers.forEach { labour ->

                        DropdownMenuItem(
                            text = {
                                Text(
                                    "${labour.name} - ID ${labour.id}"
                                )
                            },
                            onClick = {

                                selectedLabourId = labour.id
                                selectedLabourName = labour.name

                                labourDropdownExpanded = false
                            }
                        )
                    }
                }
            }
        }

        // -------------------------
        // Farm Selection
        // -------------------------

        ExposedDropdownMenuBox(
            expanded = farmDropdownExpanded,
            onExpandedChange = {
                farmDropdownExpanded = !farmDropdownExpanded
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {

            OutlinedTextField(
                value = selectedFarmName,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Select Farm")
                },
                placeholder = {
                    Text("Choose registered farm")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = farmDropdownExpanded
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = farmDropdownExpanded,
                onDismissRequest = {
                    farmDropdownExpanded = false
                }
            ) {

                if (farms.isEmpty()) {

                    DropdownMenuItem(
                        text = {
                            Text("No farms registered")
                        },
                        onClick = {
                            farmDropdownExpanded = false
                        }
                    )

                } else {

                    farms.forEach { farm ->

                        DropdownMenuItem(
                            text = {
                                Text(farm.farmName)
                            },
                            onClick = {

                                selectedFarmName = farm.farmName

                                farmDropdownExpanded = false
                            }
                        )
                    }
                }
            }
        }

        // -------------------------
        // Crop Selection
        // -------------------------

        ExposedDropdownMenuBox(
            expanded = cropDropdownExpanded,
            onExpandedChange = {
                cropDropdownExpanded = !cropDropdownExpanded
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {

            OutlinedTextField(
                value = selectedCropName,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Select Crop")
                },
                placeholder = {
                    Text("Choose registered crop")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = cropDropdownExpanded
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = cropDropdownExpanded,
                onDismissRequest = {
                    cropDropdownExpanded = false
                }
            ) {

                if (crops.isEmpty()) {

                    DropdownMenuItem(
                        text = {
                            Text("No crops registered")
                        },
                        onClick = {
                            cropDropdownExpanded = false
                        }
                    )

                } else {

                    crops.forEach { crop ->

                        DropdownMenuItem(
                            text = {
                                Text(
                                    "${crop.cropName} - ${crop.farmName}"
                                )
                            },
                            onClick = {

                                selectedCropName = crop.cropName

                                cropDropdownExpanded = false
                            }
                        )
                    }
                }
            }
        }

        // -------------------------
        // Agricultural Task Selection
        // -------------------------

        ExposedDropdownMenuBox(
            expanded = taskDropdownExpanded,
            onExpandedChange = {
                taskDropdownExpanded = !taskDropdownExpanded
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {

            OutlinedTextField(
                value = selectedTaskName,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Select Agricultural Task")
                },
                placeholder = {
                    Text("Choose registered task")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = taskDropdownExpanded
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = taskDropdownExpanded,
                onDismissRequest = {
                    taskDropdownExpanded = false
                }
            ) {

                if (agriculturalTasks.isEmpty()) {

                    DropdownMenuItem(
                        text = {
                            Text("No agricultural tasks registered")
                        },
                        onClick = {
                            taskDropdownExpanded = false
                        }
                    )

                } else {

                    agriculturalTasks.forEach { task ->

                        DropdownMenuItem(
                            text = {
                                Text(
                                    "${task.taskName} - ${task.cropName}"
                                )
                            },
                            onClick = {

                                selectedTaskName = task.taskName

                                taskDropdownExpanded = false
                            }
                        )
                    }
                }
            }
        }

        // -------------------------
        // Assignment Date
        // -------------------------

        OutlinedTextField(
            value = assignmentDate,
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Assignment Date")
            },
            placeholder = {
                Text("DD/MM/YYYY")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {

                val datePicker = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->

                        assignmentDate = String.format(
                            "%02d/%02d/%04d",
                            dayOfMonth,
                            month + 1,
                            year
                        )
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )

                datePicker.show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Select Assignment Date")
        }

        // -------------------------
        // Assign Labour
        // -------------------------

        Button(
            onClick = {

                viewModel.addAssignment(
                    labourId = selectedLabourId,
                    labourName = selectedLabourName,
                    farmName = selectedFarmName,
                    cropName = selectedCropName,
                    taskName = selectedTaskName,
                    assignmentDate = assignmentDate
                )

                selectedLabourId = 0
                selectedLabourName = ""
                selectedFarmName = ""
                selectedCropName = ""
                selectedTaskName = ""
                assignmentDate = ""

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text("Assign Labour")
        }

        // -------------------------
        // Current Assignments
        // -------------------------

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
                key = {
                    it.id
                }
            ) { assignment ->

                AssignmentCard(
                    assignment = assignment,
                    onDelete = {
                        viewModel.deleteAssignment(assignment)
                    }
                )
            }
        }

        // -------------------------
        // Back
        // -------------------------

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
                text = "Labour ID: ${assignment.labourId}"
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