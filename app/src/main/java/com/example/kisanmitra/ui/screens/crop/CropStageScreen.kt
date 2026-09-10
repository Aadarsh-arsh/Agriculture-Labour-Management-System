package com.example.kisanmitra.ui.screens.crop

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
import com.example.kisanmitra.data.CropStage

@Composable
fun CropStageScreen(
    onBack: () -> Unit,
    viewModel: CropStageViewModel = viewModel()
) {
    var cropName by remember { mutableStateOf("") }
    var stageName by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val cropStages by viewModel.cropStages.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Crop Stage Management"
        )

        Text(
            text = "Track different stages of crop growth",
            modifier = Modifier.padding(top = 8.dp)
        )

        OutlinedTextField(
            value = cropName,
            onValueChange = { cropName = it },
            label = { Text("Crop Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        OutlinedTextField(
            value = stageName,
            onValueChange = { stageName = it },
            label = { Text("Stage Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text("Start Date") },
            placeholder = { Text("DD/MM/YYYY") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = endDate,
            onValueChange = { endDate = it },
            label = { Text("End Date") },
            placeholder = { Text("DD/MM/YYYY") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {

                viewModel.addCropStage(
                    cropName = cropName,
                    stageName = stageName,
                    startDate = startDate,
                    endDate = endDate,
                    notes = notes
                )

                cropName = ""
                stageName = ""
                startDate = ""
                endDate = ""
                notes = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text("Add Crop Stage")
        }

        Text(
            text = "Crop Stage Records",
            modifier = Modifier.padding(top = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(
                items = cropStages,
                key = { it.id }
            ) { cropStage ->

                CropStageCard(
                    cropStage = cropStage,
                    onDelete = {
                        viewModel.deleteCropStage(cropStage)
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
private fun CropStageCard(
    cropStage: CropStage,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Crop: ${cropStage.cropName}"
            )

            Text(
                text = "Stage: ${cropStage.stageName}",
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "Start Date: ${cropStage.startDate}",
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "End Date: ${cropStage.endDate}",
                modifier = Modifier.padding(top = 4.dp)
            )

            if (cropStage.notes.isNotBlank()) {
                Text(
                    text = "Notes: ${cropStage.notes}",
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

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