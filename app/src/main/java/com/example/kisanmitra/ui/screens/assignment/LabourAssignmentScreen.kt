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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kisanmitra.data.LabourAssignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabourAssignmentScreen(
    onBack: () -> Unit,
    viewModel: LabourAssignmentViewModel = viewModel()
) {

    val assignments by viewModel.assignments.collectAsState(
        initial = emptyList()
    )

    val labourers by viewModel.labourers.collectAsState(
        initial = emptyList()
    )

    val farms by viewModel.farms.collectAsState(
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

    var cropName by remember {
        mutableStateOf("")
    }

    var taskName by remember {
        mutableStateOf("")
    }

    var assignmentDate by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Labour Assignment"
        )

        // Labour Selection
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

        // Farm Selection
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

        OutlinedTextField(
            value = cropName,
            onValueChange = {
                cropName = it
            },
            label = {
                Text("Crop Name")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = taskName,
            onValueChange = {
                taskName = it
            },
            label = {
                Text("Agricultural Task")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = assignmentDate,
            onValueChange = {
                assignmentDate = it
            },
            label = {
                Text("Assignment Date")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {

                viewModel.addAssignment(
                    labourId = selectedLabourId,
                    labourName = selectedLabourName,
                    farmName = selectedFarmName,
                    cropName = cropName,
                    taskName = taskName,
                    assignmentDate = assignmentDate
                )

                selectedLabourId = 0
                selectedLabourName = ""
                selectedFarmName = ""
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