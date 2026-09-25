package com.example.kisanmitra.ui.screens.farm

import androidx.compose.material3.AlertDialog
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kisanmitra.data.Farm

@Composable
fun FarmScreen(
    onBack: () -> Unit,
    viewModel: FarmViewModel = viewModel()
) {

    val farms by viewModel.farms.collectAsState(
        initial = emptyList()
    )

    val errorMessage by viewModel.errorMessage.collectAsState(
        initial = null
    )

    var farmName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var landArea by remember { mutableStateOf("") }
    var farmToDelete by remember { mutableStateOf<Farm?>(null) }

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
            text = "🚜 Farm Management",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = darkGreen
        )

        Text(
            text = "Manage your farms and agricultural land",
            fontSize = 14.sp,
            color = Color(0xFF607D8B),
            modifier = Modifier.padding(top = 3.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add Farm Card
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
                    text = "Add New Farm",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkGreen
                )

                Text(
                    text = "Enter your farm details below",
                    fontSize = 12.sp,
                    color = Color(0xFF78909C),
                    modifier = Modifier.padding(top = 3.dp)
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
                        .padding(top = 12.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = {
                        location = it
                        if (errorMessage != null) {
                            viewModel.clearError()
                        }
                    },
                    label = {
                        Text("Location")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                OutlinedTextField(
                    value = landArea,
                    onValueChange = {
                        landArea = it
                        if (errorMessage != null) {
                            viewModel.clearError()
                        }
                    },
                    label = {
                        Text("Land Area (acres)")
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

                        val beforeError = errorMessage

                        viewModel.addFarm(
                            farmName = farmName,
                            location = location,
                            landArea = landArea
                        )

                        // Clear fields only when the input itself
                        // was valid.
                        val area =
                            landArea.trim().toDoubleOrNull()

                        if (
                            farmName.trim().isNotBlank() &&
                            location.trim().isNotBlank() &&
                            area != null &&
                            area > 0 &&
                            beforeError == null
                        ) {
                            farmName = ""
                            location = ""
                            landArea = ""
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
                        text = "➕  Add Farm",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Farm section
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
                    text = "My Farms",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF263238)
                )

                Text(
                    text = "${farms.size} farm(s) registered",
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
                    text = "🚜 ${farms.size}",
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

        // Farm list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(
                items = farms,
                key = { it.id }
            ) { farm ->

                FarmCard(
                    farm = farm,
                    onDelete = {
                        farmToDelete = farm
                    }
                )
            }
        }

        // Back button
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
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
    if (farmToDelete != null) {

        AlertDialog(
            onDismissRequest = {
                farmToDelete = null
            },
            title = {
                Text(
                    text = "Delete Farm?"
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete \"${farmToDelete?.farmName}\"?"
                )
            },
            confirmButton = {

                Button(
                    onClick = {

                        farmToDelete?.let {
                            viewModel.deleteFarm(it)
                        }

                        farmToDelete = null
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
                        farmToDelete = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun FarmCard(
    farm: Farm,
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
                        text = "🚜",
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
                        text = farm.farmName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )

                    Text(
                        text = "Agricultural Farm",
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
                        text = "📍  Location: ${farm.location}",
                        fontSize = 13.sp,
                        color = Color(0xFF37474F)
                    )

                    Text(
                        text = "🌾  Land Area: ${farm.landArea} acres",
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
                    text = "🗑  Delete Farm",
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }

}
