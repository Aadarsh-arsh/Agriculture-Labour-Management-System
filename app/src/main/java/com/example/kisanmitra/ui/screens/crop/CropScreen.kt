package com.example.kisanmitra.ui.screens.crop

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
import com.example.kisanmitra.data.Crop
import java.util.Calendar

@Composable
fun CropScreen(
    onBack: () -> Unit,
    onAgriculturalTask: () -> Unit,
    onCropStage: () -> Unit,
    viewModel: CropViewModel = viewModel()
) {

    val context = LocalContext.current

    val crops by viewModel.crops.collectAsState(
        initial = emptyList()
    )

    var cropName by remember { mutableStateOf("") }
    var farmName by remember { mutableStateOf("") }
    var sowingDate by remember { mutableStateOf("") }
    var cropStage by remember { mutableStateOf("") }

    val calendar = remember {
        Calendar.getInstance()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Crop Management"
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
            value = farmName,
            onValueChange = { farmName = it },
            label = { Text("Farm Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = sowingDate,
            onValueChange = {},
            readOnly = true,
            label = { Text("Sowing Date") },
            placeholder = { Text("DD/MM/YYYY") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            trailingIcon = {
                Text(
                    text = "📅",
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        )

        Button(
            onClick = {

                val datePicker = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->

                        sowingDate =
                            String.format(
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
            Text("Select Sowing Date")
        }

        OutlinedTextField(
            value = cropStage,
            onValueChange = { cropStage = it },
            label = { Text("Crop Stage") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {

                viewModel.addCrop(
                    cropName = cropName,
                    farmName = farmName,
                    sowingDate = sowingDate,
                    cropStage = cropStage
                )

                cropName = ""
                farmName = ""
                sowingDate = ""
                cropStage = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text("Add Crop")
        }

        Text(
            text = "My Crops",
            modifier = Modifier.padding(top = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(
                items = crops,
                key = { it.id }
            ) { crop ->

                CropCard(
                    crop = crop,
                    onDelete = {
                        viewModel.deleteCrop(crop)
                    }
                )
            }
        }

        Button(
            onClick = onCropStage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Crop Stage Management")
        }

        Button(
            onClick = onAgriculturalTask,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Agricultural Tasks")
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
private fun CropCard(
    crop: Crop,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = crop.cropName
            )

            Text(
                text = "Farm: ${crop.farmName}"
            )

            Text(
                text = "Sowing Date: ${crop.sowingDate}"
            )

            Text(
                text = "Stage: ${crop.cropStage}"
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