package com.example.kisanmitra.ui.screens.crop

import androidx.compose.material3.AlertDialog
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
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

    val errorMessage by viewModel.errorMessage.collectAsState(
        initial = null
    )

    var cropName by remember { mutableStateOf("") }
    var farmName by remember { mutableStateOf("") }
    var sowingDate by remember { mutableStateOf("") }
    var cropStage by remember { mutableStateOf("") }
    var cropToDelete by remember { mutableStateOf<Crop?>(null) }

    val calendar = remember {
        Calendar.getInstance()
    }

    val darkGreen = Color(0xFF1B5E20)
    val green = Color(0xFF2E7D32)
    val lightGreen = Color(0xFFE8F5E9)
    val background = Color(0xFFFAFCF9)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp)
    ) {

        // Header
        Text(
            text = "🌱 Crop Management",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = darkGreen
        )

        Text(
            text = "Manage crops and monitor their growth",
            fontSize = 14.sp,
            color = Color(0xFF607D8B),
            modifier = Modifier.padding(top = 3.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add Crop Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "Add New Crop",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkGreen
                )

                Text(
                    text = "Enter crop information below",
                    fontSize = 12.sp,
                    color = Color(0xFF78909C),
                    modifier = Modifier.padding(top = 3.dp)
                )

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                OutlinedTextField(
                    value = farmName,
                    onValueChange = {
                        farmName = it

                        if (errorMessage != null) {
                            viewModel.clearError()
                        }
                    },
                    label = {
                        Text("Farm Name")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                OutlinedTextField(
                    value = sowingDate,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("Sowing Date")
                    },
                    placeholder = {
                        Text("DD/MM/YYYY")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(14.dp),
                    trailingIcon = {
                        Text(
                            text = "📅",
                            fontSize = 18.sp,
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

                                if (errorMessage != null) {
                                    viewModel.clearError()
                                }
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
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF558B2F)
                    )
                ) {
                    Text(
                        text = "📅  Select Sowing Date",
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedTextField(
                    value = cropStage,
                    onValueChange = {
                        cropStage = it

                        if (errorMessage != null) {
                            viewModel.clearError()
                        }
                    },
                    label = {
                        Text("Crop Stage")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                // Validation error
                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFC62828),
                        modifier = Modifier.padding(
                            top = 8.dp,
                            start = 4.dp
                        )
                    )
                }

                Button(
                    onClick = {

                        val cleanCropName =
                            cropName.trim()

                        val cleanFarmName =
                            farmName.trim()

                        val cleanSowingDate =
                            sowingDate.trim()

                        val cleanCropStage =
                            cropStage.trim()

                        val validInput =
                            cleanCropName.isNotBlank() &&
                                    cleanFarmName.isNotBlank() &&
                                    cleanSowingDate.isNotBlank() &&
                                    cleanCropStage.isNotBlank()

                        viewModel.addCrop(
                            cropName = cropName,
                            farmName = farmName,
                            sowingDate = sowingDate,
                            cropStage = cropStage
                        )

                        if (validInput) {
                            cropName = ""
                            farmName = ""
                            sowingDate = ""
                            cropStage = ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = green
                    )
                ) {
                    Text(
                        text = "➕  Add Crop",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (cropToDelete != null) {

                AlertDialog(
                    onDismissRequest = {
                        cropToDelete = null
                    },
                    title = {
                        Text(
                            text = "Delete Crop?"
                        )
                    },
                    text = {
                        Text(
                            text = "Are you sure you want to delete \"${cropToDelete?.cropName}\"?"
                        )
                    },
                    confirmButton = {

                        Button(
                            onClick = {

                                cropToDelete?.let {
                                    viewModel.deleteCrop(it)
                                }

                                cropToDelete = null
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFC62828)
                            )
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {

                        Button(
                            onClick = {
                                cropToDelete = null
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }

        // Crop section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 20.dp,
                    bottom = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "My Crops",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF263238)
                )

                Text(
                    text = "${crops.size} crop(s) registered",
                    fontSize = 12.sp,
                    color = Color(0xFF78909C),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = lightGreen
                )
            ) {
                Text(
                    text = "🌱 ${crops.size}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkGreen,
                    modifier = Modifier.padding(
                        horizontal = 10.dp,
                        vertical = 7.dp
                    )
                )
            }
        }

        // Crop list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(
                items = crops,
                key = { it.id }
            ) { crop ->

                CropCard(
                    crop = crop,
                    onDelete = {
                        cropToDelete = crop
                    }
                )
            }
        }

        // Navigation buttons
        Button(
            onClick = onCropStage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF689F38)
            )
        ) {
            Text(
                text = "📅  Crop Stage Management",
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = onAgriculturalTask,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7CB342)
            )
        ) {
            Text(
                text = "📋  Agricultural Tasks",
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF455A64)
            )
        ) {
            Text(
                text = "←  Back to Dashboard",
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun CropCard(
    crop: Crop,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
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

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE8F5E9)
                    )
                ) {
                    Text(
                        text = "🌱",
                        fontSize = 23.sp,
                        modifier = Modifier.padding(9.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {

                    Text(
                        text = crop.cropName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )

                    Text(
                        text = "Crop on ${crop.farmName}",
                        fontSize = 13.sp,
                        color = Color(0xFF607D8B),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F9F4)
                )
            ) {

                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Text(
                        text = "📅  Sowing Date: ${crop.sowingDate}",
                        fontSize = 13.sp,
                        color = Color(0xFF37474F)
                    )

                    Text(
                        text = "🌿  Stage: ${crop.cropStage}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }

            Button(
                onClick = onDelete,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFEBEE),
                    contentColor = Color(0xFFC62828)
                )
            ) {
                Text(
                    text = "🗑  Delete Crop",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}