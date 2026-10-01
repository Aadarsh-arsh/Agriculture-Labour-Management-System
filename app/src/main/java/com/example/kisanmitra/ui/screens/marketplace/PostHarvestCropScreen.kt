package com.example.kisanmitra.ui.screens.marketplace

import io.github.jan.supabase.auth.auth
import com.example.kisanmitra.data.SupabaseClientProvider
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

private suspend fun readImageBytes(
    context: android.content.Context,
    uri: Uri
): ByteArray? {
    return context.contentResolver
        .openInputStream(uri)
        ?.use { it.readBytes() }
}
@Composable
fun PostHarvestCropScreen(
    onBack: () -> Unit,
    viewModel: HarvestedCropViewModel =
        viewModel()
) {

    val context = androidx.compose.ui.platform.LocalContext.current

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
    var selectedPhotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            selectedPhotoUri = uri

            if (uri != null) {
                validationMessage = null
                viewModel.clearError()
            }
        }

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
// CROP PHOTO
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
                    modifier = Modifier.height(8.dp)
                )

                if (selectedPhotoUri != null) {

                    Text(
                        text = "Photo selected successfully.",
                        fontSize = 13.sp,
                        color = Color(0xFF2E7D32)
                    )

                } else {

                    Text(
                        text = "Add a clear photo of your harvested crop.",
                        fontSize = 13.sp,
                        color = Color(0xFF607D8B)
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(
                    onClick = {
                        photoPickerLauncher.launch("image/*")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (selectedPhotoUri != null) {
                            "Change Photo"
                        } else {
                            "Select Crop Photo"
                        }
                    )
                }
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

                    val selectedUri = selectedPhotoUri

                    if (selectedUri == null) {

                        validationMessage =
                            "Please select a crop photo"

                    } else {

                        val fileBytes =
                            kotlinx.coroutines.runBlocking {
                                readImageBytes(
                                    context = context,
                                    uri = selectedUri
                                )
                            }

                        if (fileBytes == null) {

                            validationMessage =
                                "Unable to read selected photo"

                        } else {

                            val user =
                                SupabaseClientProvider
                                    .client
                                    .auth
                                    .currentUserOrNull()

                            if (user == null) {

                                validationMessage =
                                    "User is not logged in"

                            } else {

                                val fileName =
                                    "harvest_${System.currentTimeMillis()}.jpg"

                                viewModel.uploadCropPhoto(
                                    userId = user.id,
                                    fileName = fileName,
                                    fileBytes = fileBytes,
                                    onSuccess = { photoPath ->

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
                                            photoUrl = photoPath,
                                            onSuccess = {
                                                onBack()
                                            }
                                        )
                                    }
                                )
                            }
                        }
                    }
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