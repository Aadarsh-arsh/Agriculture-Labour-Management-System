package com.example.kisanmitra.ui.screens.task

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
import com.example.kisanmitra.data.AgriculturalTask
import java.util.Calendar

private val FarmGreen = Color(0xFF2E7D32)
private val LightGreen = Color(0xFFE8F5E9)
private val DarkGreen = Color(0xFF1B5E20)
private val SoftBackground = Color(0xFFF7FAF7)
private val DeleteRed = Color(0xFFD32F2F)

@Composable
fun AgriculturalTaskScreen(
    onBack: () -> Unit,
    onAssignLabour: () -> Unit,
    viewModel: AgriculturalTaskViewModel = viewModel()
) {

    val context = LocalContext.current

    val tasks by viewModel.tasks.collectAsState(
        initial = emptyList()
    )

    var taskName by remember {
        mutableStateOf("")
    }

    var cropName by remember {
        mutableStateOf("")
    }

    var taskDate by remember {
        mutableStateOf("")
    }

    var status by remember {
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
                        text = "Agricultural Tasks",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Plan and manage daily farm activities",
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

            // Add Task Card
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
                            text = "Add Agricultural Task",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen
                        )

                        Text(
                            text = "Create a task for your crop",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 3.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = taskName,
                            onValueChange = {
                                taskName = it
                            },
                            label = {
                                Text("Task Name")
                            },
                            placeholder = {
                                Text("e.g. Irrigation")
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = cropName,
                            onValueChange = {
                                cropName = it
                            },
                            label = {
                                Text("Crop Name")
                            },
                            placeholder = {
                                Text("e.g. Tomato")
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = taskDate,
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text("Task Date")
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

                                        taskDate = String.format(
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
                                text = "Select Task Date",
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = status,
                            onValueChange = {
                                status = it
                            },
                            label = {
                                Text("Status")
                            },
                            placeholder = {
                                Text("Pending / In Progress / Completed")
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = {

                                viewModel.addTask(
                                    taskName = taskName,
                                    cropName = cropName,
                                    taskDate = taskDate,
                                    status = status
                                )

                                taskName = ""
                                cropName = ""
                                taskDate = ""
                                status = ""
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = FarmGreen
                            )
                        ) {
                            Text(
                                text = "Add Agricultural Task",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Task List Header
            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {

                    Text(
                        text = "Agricultural Tasks",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${tasks.size} task${if (tasks.size == 1) "" else "s"} saved",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Task Records
            items(
                items = tasks,
                key = {
                    it.id
                }
            ) { task ->

                AgriculturalTaskCard(
                    task = task,
                    onDelete = {
                        viewModel.deleteTask(task)
                    }
                )
            }

            // Navigation buttons
            item {

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onAssignLabour,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen
                    )
                ) {
                    Text(
                        text = "Assign Labour",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

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
private fun AgriculturalTaskCard(
    task: AgriculturalTask,
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
                        text = task.taskName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = task.cropName,
                        style = MaterialTheme.typography.bodyMedium,
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

            // Task information
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
                        text = "DATE",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = task.taskDate,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = "STATUS",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = task.status,
                        color = FarmGreen,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
            }
        }
    }
}