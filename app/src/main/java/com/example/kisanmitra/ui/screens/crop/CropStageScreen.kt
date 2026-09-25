package com.example.kisanmitra.ui.screens.crop

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.kisanmitra.data.CropStage
import java.util.Calendar

private val FarmGreen = Color(0xFF2E7D32)
private val LightGreen = Color(0xFFE8F5E9)
private val DarkGreen = Color(0xFF1B5E20)
private val SoftBackground = Color(0xFFF7FAF7)
private val DeleteRed = Color(0xFFD32F2F)

@Composable
fun CropStageScreen(
    onBack: () -> Unit,
    viewModel: CropStageViewModel = viewModel()
) {
    val context = LocalContext.current

    var cropName by remember { mutableStateOf("") }
    var stageName by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var cropStageToDelete by remember {
        mutableStateOf<CropStage?>(null)
    }

    val cropStages by viewModel.cropStages.collectAsState(
        initial = emptyList()
    )

    val errorMessage by viewModel.errorMessage.collectAsState(
        initial = null
    )

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
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    ),
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
                        .padding(
                            horizontal = 10.dp,
                            vertical = 2.dp
                        )
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Crop Stage Management",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Plan and track crop growth stages",
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

            // Add Crop Stage Card
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
                            text = "Add Crop Stage",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen
                        )

                        Text(
                            text = "Create a farming plan for your crop",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 3.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = cropName,
                            onValueChange = {
                                cropName = it

                                if (errorMessage != null) {
                                    viewModel.clearError()
                                }
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
                            value = stageName,
                            onValueChange = {
                                stageName = it

                                if (errorMessage != null) {
                                    viewModel.clearError()
                                }
                            },
                            label = {
                                Text("Stage Name")
                            },
                            placeholder = {
                                Text("e.g. Seedling")
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = startDate,
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text("Start Date")
                            },
                            placeholder = {
                                Text("DD/MM/YYYY")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Button(
                            onClick = {

                                val datePicker =
                                    DatePickerDialog(
                                        context,
                                        { _, year, month, dayOfMonth ->

                                            startDate =
                                                String.format(
                                                    "%02d/%02d/%04d",
                                                    dayOfMonth,
                                                    month + 1,
                                                    year
                                                )

                                            if (errorMessage != null) {
                                                viewModel.clearError()
                                            }
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
                                text = "Select Start Date",
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = endDate,
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text("End Date")
                            },
                            placeholder = {
                                Text("DD/MM/YYYY")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Button(
                            onClick = {

                                val datePicker =
                                    DatePickerDialog(
                                        context,
                                        { _, year, month, dayOfMonth ->

                                            endDate =
                                                String.format(
                                                    "%02d/%02d/%04d",
                                                    dayOfMonth,
                                                    month + 1,
                                                    year
                                                )

                                            if (errorMessage != null) {
                                                viewModel.clearError()
                                            }
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
                                text = "Select End Date",
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = notes,
                            onValueChange = {
                                notes = it

                                if (errorMessage != null) {
                                    viewModel.clearError()
                                }
                            },
                            label = {
                                Text("Notes")
                            },
                            placeholder = {
                                Text("Add farming notes...")
                            },
                            minLines = 3,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Validation error
                        if (errorMessage != null) {
                            Text(
                                text = errorMessage ?: "",
                                color = Color(0xFFC62828),
                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(
                                    top = 8.dp,
                                    start = 4.dp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = {

                                val validInput =
                                    cropName.trim().isNotBlank() &&
                                            stageName.trim().isNotBlank() &&
                                            startDate.trim().isNotBlank() &&
                                            endDate.trim().isNotBlank()

                                viewModel.addCropStage(
                                    cropName = cropName,
                                    stageName = stageName,
                                    startDate = startDate,
                                    endDate = endDate,
                                    notes = notes
                                )

                                if (validInput) {
                                    cropName = ""
                                    stageName = ""
                                    startDate = ""
                                    endDate = ""
                                    notes = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = FarmGreen
                            )
                        ) {
                            Text(
                                text = "Add Crop Stage",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Records Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    Text(
                        text = "Crop Stage Records",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${cropStages.size} stage${
                            if (cropStages.size == 1) ""
                            else "s"
                        } saved",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Crop Stage Records
            items(
                items = cropStages,
                key = { it.id }
            ) { cropStage ->

                CropStageCard(
                    cropStage = cropStage,
                    onDelete = {
                        cropStageToDelete = cropStage
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen
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

        // Delete confirmation dialog
        if (cropStageToDelete != null) {

            AlertDialog(
                onDismissRequest = {
                    cropStageToDelete = null
                },
                title = {
                    Text(
                        text = "Delete Crop Stage?"
                    )
                },
                text = {
                    Text(
                        text = "Are you sure you want to delete \"${cropStageToDelete?.stageName}\"?"
                    )
                },
                confirmButton = {

                    Button(
                        onClick = {

                            cropStageToDelete?.let {
                                viewModel.deleteCropStage(it)
                            }

                            cropStageToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DeleteRed
                        )
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {

                    Button(
                        onClick = {
                            cropStageToDelete = null
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
private fun CropStageCard(
    cropStage: CropStage,
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
                        text = cropStage.cropName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = cropStage.stageName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = FarmGreen,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(44.dp)
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
                        text = "START",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = cropStage.startDate,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "END",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = cropStage.endDate,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
            }

            if (cropStage.notes.isNotBlank()) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Notes",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )

                Text(
                    text = cropStage.notes,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}