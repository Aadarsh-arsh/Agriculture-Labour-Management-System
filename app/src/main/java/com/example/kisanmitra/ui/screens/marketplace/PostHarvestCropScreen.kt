package com.example.kisanmitra.ui.screens.marketplace

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PostHarvestCropScreen(
    onBack: () -> Unit,
    viewModel: HarvestedCropViewModel =
        viewModel()
) {

    var farmerName by remember {
        mutableStateOf("")
    }

    var cropName by remember {
        mutableStateOf("")
    }

    var quantity by remember {
        mutableStateOf("")
    }

    var unit by remember {
        mutableStateOf("kg")
    }

    var expectedPrice by remember {
        mutableStateOf("")
    }

    var location by remember {
        mutableStateOf("")
    }

    var harvestDate by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var contactNumber by remember {
        mutableStateOf("")
    }

    var validationMessage by remember {
        mutableStateOf<String?>(null)
    }

    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCF9))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // =========================
        // HEADER
        // =========================

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            TextButton(
                onClick = onBack
            ) {
                Text(
                    text = "← Back",
                    fontSize = 15.sp
                )
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = "Post Harvested Crop",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20),
            modifier = Modifier.padding(
                top = 4.dp,
                bottom = 6.dp
            )
        )

        Text(
            text =
                "Publish your harvested crop so buyers and wholesalers can contact you.",
            fontSize = 14.sp,
            color = Color(0xFF607D8B),
            modifier = Modifier.padding(
                bottom = 16.dp
            )
        )

        // =========================
        // FORM CARD
        // =========================

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
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = farmerName,
                    onValueChange = {
                        farmerName = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Farmer Name")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = cropName,
                    onValueChange = {
                        cropName = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Crop Name")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = quantity,
                    onValueChange = {
                        quantity = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Quantity")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = unit,
                    onValueChange = {
                        unit = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Unit")
                    },
                    placeholder = {
                        Text("kg / quintal / tonne")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = expectedPrice,
                    onValueChange = {
                        expectedPrice = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Expected Price")
                    },
                    placeholder = {
                        Text("₹ per unit")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = {
                        location = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Location")
                    },
                    placeholder = {
                        Text("Village / City / District")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = harvestDate,
                    onValueChange = {
                        harvestDate = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Harvest Date")
                    },
                    placeholder = {
                        Text("YYYY-MM-DD")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    label = {
                        Text("Description")
                    },
                    placeholder = {
                        Text("Describe quality, variety, condition, etc.")
                    }
                )

                OutlinedTextField(
                    value = contactNumber,
                    onValueChange = {
                        contactNumber = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Contact Number")
                    },
                    singleLine = true
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // =========================
        // PHOTO PLACEHOLDER
        // =========================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E9)
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "📷 Crop Photo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text =
                        "Photo upload will be connected next.",
                    fontSize = 13.sp,
                    color = Color(0xFF607D8B)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // =========================
        // VALIDATION / ERROR
        // =========================

        validationMessage?.let { message ->

            Text(
                text = message,
                color = Color(0xFFC62828),
                fontSize = 14.sp,
                modifier = Modifier.padding(
                    bottom = 8.dp
                )
            )
        }

        errorMessage?.let { message ->

            Text(
                text = message,
                color = Color(0xFFC62828),
                fontSize = 14.sp,
                modifier = Modifier.padding(
                    bottom = 8.dp
                )
            )
        }

        // =========================
        // PUBLISH BUTTON
        // =========================

        Button(
            onClick = {

                val cleanFarmerName =
                    farmerName.trim()

                val cleanCropName =
                    cropName.trim()

                val cleanUnit =
                    unit.trim()

                val cleanLocation =
                    location.trim()

                val cleanHarvestDate =
                    harvestDate.trim()

                val cleanDescription =
                    description.trim()

                val cleanContact =
                    contactNumber.trim()

                val parsedQuantity =
                    quantity.trim().toDoubleOrNull()

                val parsedPrice =
                    expectedPrice.trim().toDoubleOrNull()

                validationMessage = when {

                    cleanFarmerName.isBlank() ->
                        "Please enter farmer name"

                    cleanCropName.isBlank() ->
                        "Please enter crop name"

                    parsedQuantity == null ->
                        "Please enter a valid quantity"

                    parsedQuantity <= 0 ->
                        "Quantity must be greater than 0"

                    cleanUnit.isBlank() ->
                        "Please enter unit"

                    parsedPrice == null ->
                        "Please enter a valid expected price"

                    parsedPrice <= 0 ->
                        "Expected price must be greater than 0"

                    cleanLocation.isBlank() ->
                        "Please enter location"

                    cleanHarvestDate.isBlank() ->
                        "Please enter harvest date"

                    cleanContact.isBlank() ->
                        "Please enter contact number"

                    else ->
                        null
                }

                if (validationMessage == null) {

                    viewModel.addHarvestedCrop(
                        farmerName = cleanFarmerName,
                        cropName = cleanCropName,
                        quantity = parsedQuantity!!,
                        unit = cleanUnit,
                        expectedPrice = parsedPrice!!,
                        location = cleanLocation,
                        harvestDate = cleanHarvestDate,
                        description = cleanDescription,
                        contactNumber = cleanContact,
                        photoUrl = null,
                        onSuccess = {
                            onBack()
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {

            Text(
                text = "Publish Harvested Crop",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}