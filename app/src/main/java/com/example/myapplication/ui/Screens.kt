package com.example.myapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Guru
import com.example.myapplication.data.Session
import com.example.myapplication.data.Appreciation

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Spiritual Saffron & Gold Gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFB74D), // Saffron
                            Color(0xFFFFF3E0), // Cream
                            Color(0xFFFFE0B2)  // Gold
                        )
                    )
                )
        )

        // Subtle Decorative Mandala Pattern
        Canvas(modifier = Modifier.fillMaxSize().alpha(0.1f)) {
            val center = Offset(size.width / 2, size.height / 3)
            for (i in 1..15) {
                drawCircle(
                    color = Color(0xFFE65100),
                    radius = i * 60f,
                    center = center,
                    style = Stroke(width = 1.5f)
                )
            }
        }

        content()
    }
}

@Composable
fun HomeScreen(
    viewModel: NimmaGuruViewModel,
    onFindGuru: () -> Unit,
    onCalendar: () -> Unit,
    onProfile: () -> Unit
) {
    val topGurus by viewModel.topGurus.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Welcome Header with Spiritual Touch
        Text(
            text = "Namaste!",
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold),
            color = Color(0xFFBF360C) // Burnt Orange
        )
        Text(
            text = "Sharing Wisdom, Building Futures",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF5D4037).copy(alpha = 0.8f),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Wall of Fame Section
        Text(
            text = stringResource(R.string.wall_of_fame),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E2723),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(topGurus) { guru ->
                WallOfFameItem(guru)
            }
        }

        Spacer(Modifier.height(24.dp))

        // Quick Actions
        Text(
            text = "Explore & Learn",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E2723),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ActionButton(
                text = stringResource(R.string.find_guru),
                icon = Icons.Default.Search,
                onClick = onFindGuru,
                modifier = Modifier.weight(1f),
                containerColor = Color(0xFFE65100)
            )
            ActionButton(
                text = stringResource(R.string.class_calendar),
                icon = Icons.Default.DateRange,
                onClick = onCalendar,
                modifier = Modifier.weight(1f),
                containerColor = Color(0xFFF57C00)
            )
        }
        
        Spacer(Modifier.height(12.dp))
        
        OutlinedButton(
            onClick = onProfile,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(width = 2.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFE65100))
        ) {
            Icon(Icons.Default.Person, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.my_profile), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun WallOfFameItem(guru: Guru) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.85f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFFE65100), Color(0xFFFFB74D))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = guru.name.take(1).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = guru.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2723)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color(0xFFD84315),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "${guru.thankYouCount} Blessings",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF5D4037)
                    )
                }
            }
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(32.dp), tint = Color.White)
            Spacer(Modifier.height(8.dp))
            Text(text, style = MaterialTheme.typography.labelLarge, color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: NimmaGuruViewModel, onGuruClick: (String) -> Unit) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedSkill by viewModel.selectedSkill.collectAsState()
    val gurus by viewModel.filteredGurus.collectAsState()
    
    val skills = listOf(
        stringResource(R.string.math),
        stringResource(R.string.science),
        stringResource(R.string.carpentry),
        stringResource(R.string.english)
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            label = { Text(stringResource(R.string.search_hint)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFFE65100)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE65100),
                unfocusedBorderColor = Color(0xFFE65100).copy(alpha = 0.4f),
                focusedLabelColor = Color(0xFFE65100)
            )
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            skills.forEach { skill ->
                FilterChip(
                    selected = selectedSkill == skill,
                    onClick = { viewModel.onSkillSelected(skill) },
                    label = { Text(skill) },
                    shape = RoundedCornerShape(12.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFE65100),
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(gurus) { guru ->
                GuruCard(guru, onClick = { onGuruClick(guru.id) })
            }
        }
    }
}

@Composable
fun GuruCard(guru: Guru, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.85f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFFFF3E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFFE65100))
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(guru.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF3E2723))
                    Text(guru.village, style = MaterialTheme.typography.bodyMedium, color = Color(0xFFE65100))
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                guru.skills.take(3).forEach { skill ->
                    SuggestionChip(
                        onClick = { },
                        label = { Text(skill, fontSize = 12.sp) },
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarScreen(viewModel: NimmaGuruViewModel) {
    val sessions by viewModel.sessions.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = stringResource(R.string.upcoming_sessions),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E2723),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        if (sessions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.no_sessions),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF5D4037).copy(alpha = 0.6f)
                )
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(sessions) { session ->
                    SessionCard(session)
                }
            }
        }
    }
}

@Composable
fun SessionCard(session: Session) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE0B2).copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(session.subject, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = Color(0xFF3E2723))
                Icon(Icons.Default.Event, contentDescription = null, tint = Color(0xFFE65100))
            }
            Spacer(Modifier.height(8.dp))
            Text("with Guru ${session.guruName}", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF5D4037))
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFF3E2723).copy(alpha = 0.1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFFE65100))
                Spacer(Modifier.width(4.dp))
                Text("${session.date} | ${session.time}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF5D4037))
                Spacer(Modifier.width(16.dp))
                Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFFE65100))
                Spacer(Modifier.width(4.dp))
                Text(session.location, style = MaterialTheme.typography.bodySmall, color = Color(0xFF5D4037))
            }
        }
    }
}

@Composable
fun ProfileScreen(viewModel: NimmaGuruViewModel, guruId: String? = null) {
    var name by remember { mutableStateOf("") }
    var village by remember { mutableStateOf("") }
    var skillsText by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    val gurus by viewModel.gurus.collectAsState()
    val existingGuru = gurus.find { it.id == guruId }

    LaunchedEffect(existingGuru) {
        existingGuru?.let {
            name = it.name
            village = it.village
            skillsText = it.skills.joinToString(", ")
            hours = it.availableHours
            bio = it.bio
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = if (guruId == null) stringResource(R.string.my_profile) else "Guru Profile",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E2723)
        )
        Spacer(Modifier.height(24.dp))
        
        ProfileInputField("Full Name", name, { name = it }, Icons.Default.Badge)
        ProfileInputField(stringResource(R.string.location_label), village, { village = it }, Icons.Default.Place)
        ProfileInputField(stringResource(R.string.skills_label), skillsText, { skillsText = it }, Icons.Default.HistoryEdu, "e.g. Math, Science")
        ProfileInputField(stringResource(R.string.available_hours), hours, { hours = it }, Icons.Default.Timer)
        ProfileInputField("Bio", bio, { bio = it }, Icons.Default.Info, "Tell students about yourself", true)

        Spacer(Modifier.height(32.dp))
        
        Button(
            onClick = {
                viewModel.saveGuruProfile(
                    Guru(
                        id = guruId ?: java.util.UUID.randomUUID().toString(),
                        name = name,
                        village = village,
                        skills = skillsText.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                        availableHours = hours,
                        bio = bio
                    )
                )
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(stringResource(R.string.save_profile), style = MaterialTheme.typography.titleMedium, color = Color.White)
        }
        
        if (guruId != null) {
            Spacer(Modifier.height(48.dp))
            Text(
                stringResource(R.string.thank_you_notes),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3E2723)
            )
            Spacer(Modifier.height(16.dp))
            
            val appreciations by viewModel.getAppreciations(guruId).collectAsState(emptyList())
            
            if (appreciations.isEmpty()) {
                Text("No notes yet. Be the first to express gratitude!", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF5D4037))
            }

            appreciations.forEach { appreciation ->
                AppreciationCard(appreciation)
            }
            
            Spacer(Modifier.height(32.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Express Gratitude", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF3E2723))
                    Spacer(Modifier.height(16.dp))
                    
                    var note by remember { mutableStateOf("") }
                    var studentName by remember { mutableStateOf("") }
                    
                    OutlinedTextField(
                        value = studentName,
                        onValueChange = { studentName = it },
                        label = { Text("Your Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text(stringResource(R.string.thank_you_hint)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        minLines = 2
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (note.isNotEmpty() && studentName.isNotEmpty()) {
                                viewModel.postAppreciation(Appreciation(guruId = guruId, studentName = studentName, message = note))
                                note = ""
                                studentName = ""
                            }
                        },
                        modifier = Modifier.align(Alignment.End),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))
                    ) {
                        Text(stringResource(R.string.submit), color = Color.White)
                    }
                }
            }
        }
        Spacer(Modifier.height(40.dp))
    }
}

@Composable
fun ProfileInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    placeholder: String = "",
    isLarge: Boolean = false
) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = Color(0xFFE65100), fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(icon, contentDescription = null, tint = Color(0xFFE65100)) },
            placeholder = { Text(placeholder) },
            minLines = if (isLarge) 4 else 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFE65100),
                unfocusedBorderColor = Color(0xFFE65100).copy(alpha = 0.3f)
            )
        )
    }
}

@Composable
fun AppreciationCard(appreciation: Appreciation) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "\"${appreciation.message}\"",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3E2723)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "— ${appreciation.studentName}",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFFE65100),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
