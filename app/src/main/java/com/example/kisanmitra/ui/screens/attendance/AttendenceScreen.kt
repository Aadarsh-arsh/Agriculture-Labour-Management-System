package com.example.kisanmitra.ui.screens.attendance

import android.speech.tts.TextToSpeech
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kisanmitra.data.Attendance
import com.example.kisanmitra.data.Labour
import java.util.Locale

@Composable
fun AttendanceScreen(
    onBack: () -> Unit,
    viewModel: AttendanceViewModel = viewModel()
) {

    val context = LocalContext.current

    val labourers by viewModel.labourers.collectAsState(
        initial = emptyList()
    )

    val attendanceList by viewModel.attendance.collectAsState(
        initial = emptyList()
    )

    var currentLabourIndex by remember {
        mutableIntStateOf(0)
    }

    var date by remember {
        mutableStateOf("")
    }

    var taskName by remember {
        mutableStateOf("")
    }

    var status by remember {
        mutableStateOf("")
    }

    val textToSpeech = remember {
        TextToSpeech(context) { result ->
            if (result == TextToSpeech.SUCCESS) {
                // Text-to-Speech ready
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }

    val currentLabour: Labour? =
        if (
            labourers.isNotEmpty() &&
            currentLabourIndex < labourers.size
        ) {
            labourers[currentLabourIndex]
        } else {
            null
        }

    // Automatically speak the current labourer's name
    LaunchedEffect(currentLabour?.id) {
        currentLabour?.let { labour ->

            textToSpeech.language = Locale.ENGLISH

            textToSpeech.speak(
                labour.name,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "automatic_labour_name"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Voice Attendance"
        )

        if (currentLabour != null) {

            Text(
                text = "Labour ${currentLabourIndex + 1} of ${labourers.size}",
                modifier = Modifier.padding(top = 12.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Current Labour"
                    )

                    Text(
                        text = currentLabour.name,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = "Labour ID: ${currentLabour.id}",
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Button(
                onClick = {
                    textToSpeech.language = Locale.ENGLISH

                    textToSpeech.speak(
                        currentLabour.name,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "current_labour"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("🔊 Speak Labour Name")
            }

            OutlinedTextField(
                value = date,
                onValueChange = {
                    date = it
                },
                label = {
                    Text("Date")
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

            Text(
                text = "Attendance Status",
                modifier = Modifier.padding(top = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = {
                        status = "Present"
                    }
                ) {
                    Text("Present")
                }

                Button(
                    onClick = {
                        status = "Absent"
                    }
                ) {
                    Text("Absent")
                }

                Button(
                    onClick = {
                        status = "Leave"
                    }
                ) {
                    Text("Leave")
                }
            }

            Text(
                text = "Selected Status: $status",
                modifier = Modifier.padding(top = 8.dp)
            )

            Button(
                onClick = {

                    if (
                        date.isNotBlank() &&
                        taskName.isNotBlank() &&
                        status.isNotBlank()
                    ) {

                        viewModel.markAttendance(
                            labourId = currentLabour.id,
                            labourName = currentLabour.name,
                            date = date,
                            status = status,
                            taskName = taskName
                        )

                        status = ""

                        if (currentLabourIndex < labourers.lastIndex) {
                            currentLabourIndex++
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("Save & Next Labour")
            }

        } else {

            Text(
                text = "No labourers found.",
                modifier = Modifier.padding(top = 20.dp)
            )

            Text(
                text = "Please add labourers from Labour Management.",
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Text(
            text = "Attendance History",
            modifier = Modifier.padding(top = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(
                items = attendanceList,
                key = { it.id }
            ) { attendance ->

                AttendanceCard(
                    attendance = attendance,
                    onDelete = {
                        viewModel.deleteAttendance(attendance)
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
private fun AttendanceCard(
    attendance: Attendance,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = attendance.labourName
            )

            Text(
                text = "Date: ${attendance.date}"
            )

            Text(
                text = "Task: ${attendance.taskName}"
            )

            Text(
                text = "Status: ${attendance.status}"
            )

            Button(
                onClick = onDelete,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Delete")
            }
        }
    }
}