package com.example.kisanmitra.ui.screens.assignment

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kisanmitra.data.LabourAssignment
import java.util.Calendar

private val FarmGreen = Color(0xFF2E7D32)
private val LightGreen = Color(0xFFE8F5E9)
private val DarkGreen = Color(0xFF1B5E20)
private val SoftBackground = Color(0xFFF7FAF7)
private val DeleteRed = Color(0xFFD32F2F)

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
            .background(SoftBackground)
    ) {

        // Header
        Surface(
            color = FarmGreen,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "‹",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .background(
                            Color.White.copy(alpha = 0.15f),
                            RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Labour Assignment",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Assign workers to farm activities",
                        color = Color.White.copy(alpha = 0.85f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Assignment form
            item {

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
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Create Assignment",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen
                        )

                        Text(
                            text = "Connect a labourer with a farm task",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 3.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Labour
                        ExposedDropdownMenuBox(
                            expanded = labourDropdownExpanded,
                            onExpandedChange = {
                                labourDropdownExpanded =
                                    !labourDropdownExpanded
                            },
                            modifier = Modifier.fillMaxWidth()
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
                                    .menuAnchor(),
                                shape = RoundedCornerShape(12.dp)
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

                                                selectedLabourId =
                                                    labour.id

                                                selectedLabourName =
                                                    labour.name

                                                labourDropdownExpanded =
                                                    false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Farm
                        ExposedDropdownMenuBox(
                            expanded = farmDropdownExpanded,
                            onExpandedChange = {
                                farmDropdownExpanded =
                                    !farmDropdownExpanded
                            },
                            modifier = Modifier.fillMaxWidth()
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
                                    .menuAnchor(),
                                shape = RoundedCornerShape(12.dp)
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

                                                selectedFarmName =
                                                    farm.farmName

                                                farmDropdownExpanded =
                                                    false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Crop
                        ExposedDropdownMenuBox(
                            expanded = cropDropdownExpanded,
                            onExpandedChange = {
                                cropDropdownExpanded =
                                    !cropDropdownExpanded
                            },
                            modifier = Modifier.fillMaxWidth()
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
                                    .menuAnchor(),
                                shape = RoundedCornerShape(12.dp)
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

                                                selectedCropName =
                                                    crop.cropName

                                                cropDropdownExpanded =
                                                    false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Agricultural Task
                        ExposedDropdownMenuBox(
                            expanded = taskDropdownExpanded,
                            onExpandedChange = {
                                taskDropdownExpanded =
                                    !taskDropdownExpanded
                            },
                            modifier = Modifier.fillMaxWidth()
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
                                    .menuAnchor(),
                                shape = RoundedCornerShape(12.dp)
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
                                            Text(
                                                "No agricultural tasks registered"
                                            )
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

                                                selectedTaskName =
                                                    task.taskName

                                                taskDropdownExpanded =
                                                    false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Date
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
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
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
                                .padding(top = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LightGreen,
                                contentColor = DarkGreen
                            )
                        ) {
                            Text(
                                text = "Select Assignment Date",
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

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
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = FarmGreen
                            )
                        ) {
                            Text(
                                text = "Assign Labour",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Current assignments
            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {

                    Text(
                        text = "Current Assignments",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${assignments.size} assignment${
                            if (assignments.size == 1) "" else "s"
                        } saved",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

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
private fun AssignmentCard(
    assignment: LabourAssignment,
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
                        text = assignment.labourName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Labour ID: ${assignment.labourId}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .background(
                            Color(0xFFFFEBEE),
                            RoundedCornerShape(12.dp)
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

            // Assignment details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        LightGreen,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
            ) {

                AssignmentDetail(
                    label = "Farm",
                    value = assignment.farmName
                )

                AssignmentDetail(
                    label = "Crop",
                    value = assignment.cropName
                )

                AssignmentDetail(
                    label = "Task",
                    value = assignment.taskName
                )

                AssignmentDetail(
                    label = "Date",
                    value = assignment.assignmentDate
                )
            }
        }
    }
}

@Composable
private fun AssignmentDetail(
    label: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "$label:",
            modifier = Modifier.width(70.dp),
            fontWeight = FontWeight.Bold,
            color = DarkGreen
        )

        Text(
            text = value,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}