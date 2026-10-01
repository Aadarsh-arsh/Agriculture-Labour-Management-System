package com.example.kisanmitra.ui.screens.marketplace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kisanmitra.data.SupabaseHarvestedCrop

@Composable
fun HarvestMarketplaceScreen(
    onBack: () -> Unit,
    onPostHarvest: () -> Unit,
    viewModel: HarvestedCropViewModel =
        viewModel()
) {

    val crops by viewModel.availableCrops.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCF9))
    ) {

        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp
                ),
            verticalAlignment = Alignment.CenterVertically
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
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = "Crop Marketplace",
                fontSize = 22.sp,
                color = Color(0xFF1B5E20)
            )
        }

        // Intro section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E9)
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "🌾 Harvested Crops",
                    fontSize = 20.sp,
                    color = Color(0xFF1B5E20)
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text =
                        "Browse crops available directly from farmers.",
                    fontSize = 13.sp,
                    color = Color(0xFF455A64)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(
                    onClick = onPostHarvest,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Post Harvested Crop"
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        when {

            isLoading -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = errorMessage
                            ?: "Unable to load crops",
                        color = Color(0xFFC62828),
                        fontSize = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Button(
                        onClick = {
                            viewModel.clearError()
                            viewModel.loadAvailableCrops()
                        }
                    ) {
                        Text("Retry")
                    }
                }
            }

            crops.isEmpty() -> {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text =
                            "No harvested crops are available yet.",
                        fontSize = 15.sp,
                        color = Color(0xFF607D8B)
                    )
                }
            }

            else -> {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout
                        .PaddingValues(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    items(
                        items = crops,
                        key = {
                            it.id ?: 0L
                        }
                    ) { crop ->

                        HarvestedCropCard(
                            crop = crop
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HarvestedCropCard(
    crop: SupabaseHarvestedCrop
) {

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
            modifier = Modifier.padding(16.dp)
        ) {

            // Photo placeholder for now
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        color = Color(0xFFE8F5E9),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "🌾",
                    fontSize = 55.sp
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = crop.crop_name,
                fontSize = 21.sp,
                color = Color(0xFF1B5E20)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text =
                    "${crop.quantity} ${crop.unit}",
                fontSize = 14.sp,
                color = Color(0xFF455A64)
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text =
                    "Expected price: ₹${crop.expected_price}",
                fontSize = 15.sp,
                color = Color(0xFF2E7D32)
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text =
                    "📍 ${crop.location}",
                fontSize = 13.sp,
                color = Color(0xFF607D8B)
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text =
                    "Harvested: ${crop.harvest_date}",
                fontSize = 13.sp,
                color = Color(0xFF607D8B)
            )

            if (crop.description.isNotBlank()) {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = crop.description,
                    fontSize = 13.sp,
                    color = Color(0xFF37474F)
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Farmer: ${crop.farmer_name}",
                fontSize = 14.sp,
                color = Color(0xFF263238)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Button(
                onClick = {
                    // Contact action will be added later.
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "📞 Contact Farmer"
                )
            }
        }
    }
}