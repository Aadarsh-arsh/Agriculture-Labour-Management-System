package com.example.kisanmitra.ui.screens.attendance

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.kisanmitra.data.Attendance
import com.example.kisanmitra.data.Labour

import java.util.Calendar
import java.util.Locale

private val FarmGreen = Color(0xFF2E7D32)
private val LightGreen = Color(0xFFE8F5E9)
private val DarkGreen = Color(0xFF1B5E20)
private val SoftBackground = Color(0xFFF7FAF7)
private val PresentGreen = Color(0xFF2E7D32)
private val AbsentRed = Color(0xFFC62828)
private val LeaveOrange = Color(0xFFEF6C00)
private val DeleteRed = Color(0xFFD32F2F)

@Composable
fun AttendanceScreen(
    onBack: () -> Unit,
    viewModel: AttendanceViewModel = viewModel()
) {

    val context = LocalContext.current

    val calendar = remember {
        Calendar.getInstance()
    }

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

    // ---------------------------------------------------------
    // ATTENDANCE MODE
    // ---------------------------------------------------------

    var attendanceMode by remember {
        mutableStateOf("Manual")
    }

    // ---------------------------------------------------------
    // VOICE COMMAND STATE
    // ---------------------------------------------------------

    var isListening by remember {
        mutableStateOf(false)
    }

    var voiceCommandMessage by remember {
        mutableStateOf("")
    }

    // ---------------------------------------------------------
    // MICROPHONE PERMISSION
    // ---------------------------------------------------------

    val microphonePermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {
                voiceCommandMessage =
                    "Microphone permission granted"
            } else {
                voiceCommandMessage =
                    "Microphone permission denied"
            }
        }

    // ---------------------------------------------------------
    // TEXT TO SPEECH
    // ---------------------------------------------------------

    val textToSpeech = remember {

        TextToSpeech(context) { result ->

            if (result == TextToSpeech.SUCCESS) {
                // TTS initialized
            }
        }
    }

    // ---------------------------------------------------------
    // SPEECH RECOGNIZER
    // ---------------------------------------------------------

    val speechRecognizer = remember {

        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            SpeechRecognizer.createSpeechRecognizer(context)
        } else {
            null
        }
    }

    // ---------------------------------------------------------
    // SPEECH RECOGNITION LISTENER
    // ---------------------------------------------------------

    DisposableEffect(speechRecognizer) {

        if (speechRecognizer == null) {
            onDispose { }
        } else {

            val listener = object : RecognitionListener {

                override fun onReadyForSpeech(
                    params: android.os.Bundle?
                ) {
                    isListening = true
                    voiceCommandMessage =
                        "Listening... Say Present, Absent or Leave"
                }

                override fun onBeginningOfSpeech() {
                    isListening = true
                    voiceCommandMessage =
                        "Listening..."
                }

                override fun onRmsChanged(
                    rmsdB: Float
                ) {
                    // Not required
                }

                override fun onBufferReceived(
                    buffer: ByteArray?
                ) {
                    // Not required
                }

                override fun onEndOfSpeech() {
                    isListening = false
                }

                override fun onError(
                    error: Int
                ) {

                    isListening = false

                    voiceCommandMessage =
                        when (error) {

                            SpeechRecognizer.ERROR_NO_MATCH ->
                                "I couldn't understand. Please try again."

                            SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                                "No speech detected. Please try again."

                            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS ->
                                "Microphone permission is required."

                            else ->
                                "Voice recognition failed. Please try again."
                        }
                }

                override fun onResults(
                    results: android.os.Bundle?
                ) {

                    isListening = false

                    val matches =
                        results?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )

                    val spokenText =
                        matches?.firstOrNull()
                            ?.trim()
                            ?.lowercase(Locale.getDefault())

                    if (spokenText.isNullOrBlank()) {

                        voiceCommandMessage =
                            "No command detected."

                        return
                    }

                    when {

                        spokenText.contains("present") -> {

                            status = "Present"

                            voiceCommandMessage =
                                "Voice command: Present"
                        }

                        spokenText.contains("absent") -> {

                            status = "Absent"

                            voiceCommandMessage =
                                "Voice command: Absent"
                        }

                        spokenText.contains("leave") -> {

                            status = "Leave"

                            voiceCommandMessage =
                                "Voice command: Leave"
                        }

                        else -> {

                            voiceCommandMessage =
                                "Command not recognized. Say Present, Absent or Leave."
                        }
                    }
                }

                override fun onPartialResults(
                    partialResults: android.os.Bundle?
                ) {
                    // Not required
                }

                override fun onEvent(
                    eventType: Int,
                    params: android.os.Bundle?
                ) {
                    // Not required
                }
            }

            speechRecognizer.setRecognitionListener(listener)

            onDispose {

                speechRecognizer.stopListening()
                speechRecognizer.cancel()
                speechRecognizer.destroy()
            }
        }
    }

    // ---------------------------------------------------------
    // CLEAN UP TTS
    // ---------------------------------------------------------

    DisposableEffect(Unit) {

        onDispose {

            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }

    // ---------------------------------------------------------
    // CURRENT LABOUR
    // ---------------------------------------------------------

    val currentLabour: Labour? =
        if (
            labourers.isNotEmpty() &&
            currentLabourIndex < labourers.size
        ) {
            labourers[currentLabourIndex]
        } else {
            null
        }

    // ---------------------------------------------------------
    // AUTOMATIC LABOUR NAME SPEECH
    // ---------------------------------------------------------

    LaunchedEffect(
        currentLabour?.id,
        attendanceMode
    ) {

        if (attendanceMode == "Voice") {

            currentLabour?.let { labour ->

                textToSpeech.language =
                    Locale.ENGLISH

                textToSpeech.speak(
                    labour.name,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "automatic_labour_name"
                )
            }
        }
    }

    // ---------------------------------------------------------
    // START VOICE COMMAND
    // ---------------------------------------------------------

    fun startVoiceCommand() {

        if (speechRecognizer == null) {

            voiceCommandMessage =
                "Speech recognition is not available on this device."

            return
        }

        val permissionGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED

        if (!permissionGranted) {

            microphonePermissionLauncher.launch(
                Manifest.permission.RECORD_AUDIO
            )

            return
        }

        val intent =
            Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
            ).apply {

                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )

                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault()
                )

                putExtra(
                    RecognizerIntent.EXTRA_PROMPT,
                    "Say Present, Absent or Leave"
                )
            }

        voiceCommandMessage =
            "Starting voice recognition..."

        speechRecognizer.startListening(intent)
    }

    // ---------------------------------------------------------
    // MAIN SCREEN
    // ---------------------------------------------------------

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBackground)
    ) {

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

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
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = "‹",
                    color = Color.White,
                    style =
                        MaterialTheme.typography.headlineMedium,
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
                        text = "Voice Attendance",
                        color = Color.White,
                        style =
                            MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text =
                            "Quick and simple labour attendance",
                        color =
                            Color.White.copy(alpha = 0.85f),
                        style =
                            MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            item {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

            // ---------------------------------------------------------
            // ATTENDANCE MODE
            // ---------------------------------------------------------

            item {

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

                        Text(
                            text = "Attendance Mode",
                            style =
                                MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            // MANUAL
                            Button(
                                onClick = {

                                    attendanceMode =
                                        "Manual"

                                    textToSpeech.stop()

                                    speechRecognizer?.stopListening()

                                    isListening = false

                                    voiceCommandMessage = ""
                                },
                                modifier =
                                    Modifier.weight(1f),
                                shape =
                                    RoundedCornerShape(12.dp),
                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            if (
                                                attendanceMode ==
                                                "Manual"
                                            ) {
                                                FarmGreen
                                            } else {
                                                LightGreen
                                            },
                                        contentColor =
                                            if (
                                                attendanceMode ==
                                                "Manual"
                                            ) {
                                                Color.White
                                            } else {
                                                DarkGreen
                                            }
                                    )
                            ) {

                                Text(
                                    text = "✍️ Manual",
                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }

                            // VOICE
                            Button(
                                onClick = {

                                    attendanceMode =
                                        "Voice"

                                },
                                modifier =
                                    Modifier.weight(1f),
                                shape =
                                    RoundedCornerShape(12.dp),
                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            if (
                                                attendanceMode ==
                                                "Voice"
                                            ) {
                                                FarmGreen
                                            } else {
                                                LightGreen
                                            },
                                        contentColor =
                                            if (
                                                attendanceMode ==
                                                "Voice"
                                            ) {
                                                Color.White
                                            } else {
                                                DarkGreen
                                            }
                                    )
                            ) {

                                Text(
                                    text = "🔊 Voice",
                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }
                        }

                        Text(
                            text =
                                if (
                                    attendanceMode ==
                                    "Manual"
                                ) {
                                    "Use the buttons below to mark attendance."
                                } else {
                                    "The app will speak each labourer's name."
                                },
                            style =
                                MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier =
                                Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            if (currentLabour != null) {

                // ---------------------------------------------------------
                // PROGRESS
                // ---------------------------------------------------------

                item {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Column(
                            modifier =
                                Modifier.weight(1f)
                        ) {

                            Text(
                                text =
                                    "Today's Attendance",
                                style =
                                    MaterialTheme.typography.titleMedium,
                                fontWeight =
                                    FontWeight.Bold
                            )

                            Text(
                                text =
                                    "Labour ${currentLabourIndex + 1} of ${labourers.size}",
                                style =
                                    MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier =
                                    Modifier.padding(top = 2.dp)
                            )
                        }

                        Text(
                            text =
                                "${currentLabourIndex + 1}/${labourers.size}",
                            color = FarmGreen,
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }

                // ---------------------------------------------------------
                // CURRENT LABOUR
                // ---------------------------------------------------------

                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(20.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            ),
                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 3.dp
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(18.dp)
                        ) {

                            Text(
                                text = "CURRENT LABOUR",
                                style =
                                    MaterialTheme.typography.labelMedium,
                                color = Color.Gray,
                                fontWeight =
                                    FontWeight.Bold
                            )

                            Text(
                                text =
                                    currentLabour.name,
                                style =
                                    MaterialTheme.typography.headlineSmall,
                                fontWeight =
                                    FontWeight.Bold,
                                color = DarkGreen,
                                modifier =
                                    Modifier.padding(top = 6.dp)
                            )

                            Text(
                                text =
                                    "Labour ID: ${currentLabour.id}",
                                style =
                                    MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier =
                                    Modifier.padding(top = 3.dp)
                            )

                            // SPEAK NAME
                            if (
                                attendanceMode ==
                                "Voice"
                            ) {

                                Spacer(
                                    modifier =
                                        Modifier.height(14.dp)
                                )

                                Button(
                                    onClick = {

                                        textToSpeech.language =
                                            Locale.ENGLISH

                                        textToSpeech.speak(
                                            currentLabour.name,
                                            TextToSpeech.QUEUE_FLUSH,
                                            null,
                                            "current_labour"
                                        )
                                    },
                                    modifier =
                                        Modifier.fillMaxWidth(),
                                    shape =
                                        RoundedCornerShape(12.dp),
                                    colors =
                                        ButtonDefaults.buttonColors(
                                            containerColor =
                                                LightGreen,
                                            contentColor =
                                                DarkGreen
                                        )
                                ) {

                                    Text(
                                        text =
                                            "🔊  Speak Labour Name",
                                        fontWeight =
                                            FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                // ---------------------------------------------------------
                // ATTENDANCE DETAILS
                // ---------------------------------------------------------

                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(20.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            ),
                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(16.dp)
                        ) {

                            Text(
                                text =
                                    "Attendance Details",
                                style =
                                    MaterialTheme.typography.titleMedium,
                                fontWeight =
                                    FontWeight.Bold,
                                color = DarkGreen
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            // DATE
                            OutlinedTextField(
                                value = date,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text(
                                        "Attendance Date"
                                    )
                                },
                                placeholder = {
                                    Text(
                                        "DD/MM/YYYY"
                                    )
                                },
                                modifier =
                                    Modifier.fillMaxWidth(),
                                shape =
                                    RoundedCornerShape(12.dp)
                            )

                            Button(
                                onClick = {

                                    val datePicker =
                                        DatePickerDialog(
                                            context,
                                            { _, year,
                                              month,
                                              dayOfMonth ->

                                                date =
                                                    String.format(
                                                        "%02d/%02d/%04d",
                                                        dayOfMonth,
                                                        month + 1,
                                                        year
                                                    )
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
                                shape =
                                    RoundedCornerShape(12.dp),
                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            LightGreen,
                                        contentColor =
                                            DarkGreen
                                    )
                            ) {

                                Text(
                                    text =
                                        "Select Attendance Date",
                                    fontWeight =
                                        FontWeight.SemiBold
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(10.dp)
                            )

                            // TASK
                            OutlinedTextField(
                                value = taskName,
                                onValueChange = {
                                    taskName = it
                                },
                                label = {
                                    Text(
                                        "Agricultural Task"
                                    )
                                },
                                placeholder = {
                                    Text(
                                        "e.g. Irrigation"
                                    )
                                },
                                singleLine = true,
                                modifier =
                                    Modifier.fillMaxWidth(),
                                shape =
                                    RoundedCornerShape(12.dp)
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(14.dp)
                            )

                            Text(
                                text =
                                    "Attendance Status",
                                style =
                                    MaterialTheme.typography.titleSmall,
                                fontWeight =
                                    FontWeight.Bold
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(8.dp)
                            )

                            // STATUS BUTTONS
                            Row(
                                modifier =
                                    Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.spacedBy(8.dp)
                            ) {

                                AttendanceStatusButton(
                                    text = "Present",
                                    selected =
                                        status ==
                                                "Present",
                                    selectedColor =
                                        PresentGreen,
                                    onClick = {
                                        status =
                                            "Present"
                                    },
                                    modifier =
                                        Modifier.weight(1f)
                                )

                                AttendanceStatusButton(
                                    text = "Absent",
                                    selected =
                                        status ==
                                                "Absent",
                                    selectedColor =
                                        AbsentRed,
                                    onClick = {
                                        status =
                                            "Absent"
                                    },
                                    modifier =
                                        Modifier.weight(1f)
                                )

                                AttendanceStatusButton(
                                    text = "Leave",
                                    selected =
                                        status ==
                                                "Leave",
                                    selectedColor =
                                        LeaveOrange,
                                    onClick = {
                                        status =
                                            "Leave"
                                    },
                                    modifier =
                                        Modifier.weight(1f)
                                )
                            }

                            // ---------------------------------------------------------
                            // VOICE COMMAND BUTTON
                            // ---------------------------------------------------------

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            Button(
                                onClick = {
                                    startVoiceCommand()
                                },
                                modifier =
                                    Modifier.fillMaxWidth(),
                                shape =
                                    RoundedCornerShape(12.dp),
                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            if (isListening) {
                                                AbsentRed
                                            } else {
                                                LightGreen
                                            },
                                        contentColor =
                                            if (isListening) {
                                                Color.White
                                            } else {
                                                DarkGreen
                                            }
                                    )
                            ) {

                                Text(
                                    text =
                                        if (isListening) {
                                            "🎙️ Listening..."
                                        } else {
                                            "🎙️ Voice Command"
                                        },
                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }

                            if (
                                voiceCommandMessage
                                    .isNotBlank()
                            ) {

                                Text(
                                    text =
                                        voiceCommandMessage,
                                    style =
                                        MaterialTheme.typography.bodySmall,
                                    color =
                                        if (
                                            voiceCommandMessage
                                                .contains(
                                                    "Present",
                                                    ignoreCase = true
                                                )
                                        ) {
                                            PresentGreen
                                        } else if (
                                            voiceCommandMessage
                                                .contains(
                                                    "Absent",
                                                    ignoreCase = true
                                                )
                                        ) {
                                            AbsentRed
                                        } else {
                                            DarkGreen
                                        },
                                    fontWeight =
                                        FontWeight.SemiBold,
                                    modifier =
                                        Modifier.padding(
                                            top = 8.dp
                                        )
                                )
                            }

                            // SELECTED STATUS
                            if (status.isNotBlank()) {

                                Text(
                                    text =
                                        "Selected: $status",
                                    color =
                                        when (status) {

                                            "Present" ->
                                                PresentGreen

                                            "Absent" ->
                                                AbsentRed

                                            else ->
                                                LeaveOrange
                                        },
                                    fontWeight =
                                        FontWeight.Bold,
                                    modifier =
                                        Modifier.padding(
                                            top = 10.dp
                                        )
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(14.dp)
                            )

                            // ---------------------------------------------------------
                            // SAVE
                            // ---------------------------------------------------------

                            Button(
                                onClick = {

                                    if (
                                        date.isNotBlank() &&
                                        taskName.isNotBlank() &&
                                        status.isNotBlank()
                                    ) {

                                        viewModel.markAttendance(
                                            labourId =
                                                currentLabour.id,
                                            labourName =
                                                currentLabour.name,
                                            date = date,
                                            status =
                                                status,
                                            taskName =
                                                taskName
                                        )

                                        status = ""

                                        voiceCommandMessage =
                                            ""

                                        if (
                                            currentLabourIndex <
                                            labourers.lastIndex
                                        ) {
                                            currentLabourIndex++
                                        }
                                    }
                                },
                                modifier =
                                    Modifier.fillMaxWidth(),
                                shape =
                                    RoundedCornerShape(12.dp),
                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            FarmGreen
                                    )
                            ) {

                                Text(
                                    text =
                                        "Save & Next Labour",
                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }
                        }
                    }
                }

            } else {

                // ---------------------------------------------------------
                // NO LABOURERS
                // ---------------------------------------------------------

                item {

                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(18.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(18.dp)
                        ) {

                            Text(
                                text =
                                    "No Labourers Found",
                                style =
                                    MaterialTheme.typography.titleMedium,
                                fontWeight =
                                    FontWeight.Bold,
                                color = DarkGreen
                            )

                            Text(
                                text =
                                    "Please add labourers from Labour Management.",
                                style =
                                    MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                modifier =
                                    Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                }
            }

            // ---------------------------------------------------------
            // ATTENDANCE HISTORY
            // ---------------------------------------------------------

            item {

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                ) {

                    Text(
                        text =
                            "Attendance History",
                        style =
                            MaterialTheme.typography.titleLarge,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Text(
                        text =
                            "${attendanceList.size} record${
                                if (
                                    attendanceList.size == 1
                                ) {
                                    ""
                                } else {
                                    "s"
                                }
                            } saved",
                        style =
                            MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier =
                            Modifier.padding(top = 2.dp)
                    )
                }
            }

            items(
                items = attendanceList,
                key = { it.id }
            ) { attendance ->

                AttendanceCard(
                    attendance = attendance,
                    onDelete = {
                        viewModel.deleteAttendance(
                            attendance
                        )
                    }
                )
            }

            // ---------------------------------------------------------
            // BACK
            // ---------------------------------------------------------

            item {

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                Button(
                    onClick = onBack,
                    modifier =
                        Modifier.fillMaxWidth(),
                    shape =
                        RoundedCornerShape(12.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color.Gray
                        )
                ) {

                    Text(
                        text = "Back",
                        fontWeight =
                            FontWeight.Bold
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )
            }
        }
    }
}

// =============================================================
// ATTENDANCE STATUS BUTTON
// =============================================================

@Composable
private fun AttendanceStatusButton(
    text: String,
    selected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        shape =
            RoundedCornerShape(12.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor =
                    if (selected) {
                        selectedColor
                    } else {
                        LightGreen
                    },
                contentColor =
                    if (selected) {
                        Color.White
                    } else {
                        DarkGreen
                    }
            )
    ) {

        Text(
            text = text,
            fontWeight =
                FontWeight.Bold
        )
    }
}

// =============================================================
// ATTENDANCE CARD
// =============================================================

@Composable
private fun AttendanceCard(
    attendance: Attendance,
    onDelete: () -> Unit
) {

    val statusColor =
        when (
            attendance.status
                .trim()
                .lowercase()
        ) {

            "present" ->
                PresentGreen

            "absent" ->
                AbsentRed

            "leave" ->
                LeaveOrange

            else ->
                Color.Gray
        }

    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(18.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color.White
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(
                        text =
                            attendance.labourName,
                        style =
                            MaterialTheme.typography.titleMedium,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Text(
                        text =
                            attendance.taskName,
                        style =
                            MaterialTheme.typography.bodyMedium,
                        color =
                            FarmGreen,
                        fontWeight =
                            FontWeight.SemiBold,
                        modifier =
                            Modifier.padding(
                                top = 3.dp
                            )
                    )
                }

                IconButton(
                    onClick =
                        onDelete,
                    modifier =
                        Modifier.background(
                            Color(0xFFFFEBEE),
                            RoundedCornerShape(12.dp)
                        )
                ) {

                    Text(
                        text = "×",
                        color =
                            DeleteRed,
                        style =
                            MaterialTheme.typography.titleLarge,
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(
                            LightGreen,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "DATE",
                        style =
                            MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Text(
                        text =
                            attendance.date,
                        fontWeight =
                            FontWeight.SemiBold,
                        modifier =
                            Modifier.padding(
                                top = 3.dp
                            )
                    )
                }

                Text(
                    text =
                        attendance.status,
                    color =
                        statusColor,
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }
}