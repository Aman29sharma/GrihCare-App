package com.example.grihacare

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.BackEventCompat
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Work

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import com.example.grihacare.ai.ChatMessage
import com.example.grihacare.ai.ClaudeRepository
import com.example.grihacare.data.Property
import com.example.grihacare.data.Worker
import com.example.grihacare.data.properties
import com.example.grihacare.data.workers

import kotlinx.coroutines.launch

import kotlin.math.abs

/* =========================================================
   ACTIVITY
   ========================================================= */

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GrihaCareApp()
        }
    }
}

/* =========================================================
   NAVIGATION
   ========================================================= */

private enum class Screen {
    LANDING,
    LOGIN,
    OTP,
    HOME,
    SEARCH,
    WORKER,
    VERIFY,
    CHAT,
    BOOKING,
    BOOKINGS,
    PROFILE,
    SAFETY
}

@Composable
fun GrihaCareApp() {

    var screen by rememberSaveable {
        mutableStateOf(Screen.LANDING)
    }

    var darkMode by rememberSaveable {
        mutableStateOf(false)
    }

    var phone by rememberSaveable {
        mutableStateOf("")
    }

    var selectedWorker by remember {
        mutableStateOf<Worker?>(null)
    }

    val context =
        androidx.compose.ui.platform.LocalContext.current

    val profileStore = remember {
        UserProfileStore(context)
    }

    var userProfile by remember {
        mutableStateOf(
            profileStore.load()
        )
    }

    /*
     * Simple reliable navigation stack.
     */
    val backStack =
        remember {
            mutableListOf<Screen>()
        }

    fun navigate(next: Screen) {

        if (screen == next) return

        backStack.add(screen)
        screen = next
    }

    fun goBack() {

        if (backStack.isNotEmpty()) {

            screen =
                backStack.removeAt(
                    backStack.lastIndex
                )

        } else if (
            screen != Screen.HOME &&
            screen != Screen.LANDING
        ) {

            screen = Screen.HOME
        }
    }

    fun goHome() {

        backStack.clear()
        screen = Screen.HOME
    }

    BackHandler(
        enabled = screen != Screen.LANDING
    ) {
        goBack()
    }

    GrihaTheme(
        darkMode = darkMode
    ) {

        Crossfade(
            targetState = screen,
            animationSpec =
                tween(280),
            label = "screenChange"
        ) { currentScreen ->

            when (currentScreen) {

                Screen.LANDING -> {

                    LandingScreen(
                        onStart = {
                            navigate(
                                Screen.LOGIN
                            )
                        }
                    )
                }

                Screen.LOGIN -> {

                    LoginScreen(
                        phone = phone,

                        onPhoneChange = {
                            phone = it
                        },

                        onBack = {
                            goBack()
                        },

                        onContinue = {

                            if (
                                phone.length == 10
                            ) {
                                navigate(
                                    Screen.OTP
                                )
                            }
                        }
                    )
                }

                Screen.OTP -> {

                    OtpScreen(
                        phone = phone,

                        onBack = {
                            goBack()
                        },

                        onVerified = {

                            /*
                             * Mobile becomes the
                             * verified anchor
                             * for the profile.
                             */
                            userProfile =
                                userProfile.copy(
                                    mobile = phone
                                )

                            profileStore.save(
                                userProfile
                            )

                            goHome()
                        }
                    )
                }

                Screen.HOME -> {

                    HomeScreen(
                        profile = userProfile,
                        darkMode = darkMode,

                        onTheme = {
                            darkMode =
                                !darkMode
                        },

                        onSearch = {
                            navigate(
                                Screen.SEARCH
                            )
                        },

                        onChat = {
                            navigate(
                                Screen.CHAT
                            )
                        },

                        onBookings = {
                            navigate(
                                Screen.BOOKINGS
                            )
                        },

                        onProfile = {
                            navigate(
                                Screen.PROFILE
                            )
                        },

                        onSafety = {
                            navigate(
                                Screen.SAFETY
                            )
                        },

                        onVerify = {
                            navigate(
                                Screen.VERIFY
                            )
                        },

                        onWorker = {

                            selectedWorker = it

                            navigate(
                                Screen.WORKER
                            )
                        }
                    )
                }

                Screen.SEARCH -> {

                    SearchScreen(
                        onBack = {
                            goBack()
                        },

                        onOpen = {
                            navigate(
                                Screen.BOOKING
                            )
                        }
                    )
                }

                Screen.WORKER -> {

                    WorkerDetailScreen(
                        worker =
                            selectedWorker
                                ?: workers.first(),

                        onBack = {
                            goBack()
                        },

                        onHire = {
                            navigate(
                                Screen.VERIFY
                            )
                        }
                    )
                }

                Screen.VERIFY -> {

                    VerificationScreen(
                        onBack = {
                            goBack()
                        },

                        onVerified = {
                            navigate(
                                Screen.BOOKING
                            )
                        }
                    )
                }

                Screen.CHAT -> {

                    ChatScreen(
                        onBack = {
                            goBack()
                        }
                    )
                }

                Screen.BOOKING -> {

                    BookingScreen(
                        onBack = {
                            goBack()
                        },

                        onDone = {
                            navigate(
                                Screen.BOOKINGS
                            )
                        }
                    )
                }

                Screen.BOOKINGS -> {

                    BookingsScreen(
                        onBack = {
                            goBack()
                        }
                    )
                }

                Screen.PROFILE -> {

                    ProfileScreen(
                        profile = userProfile,
                        darkMode = darkMode,

                        onTheme = {
                            darkMode =
                                !darkMode
                        },

                        onBack = {
                            goBack()
                        },

                        onSave = { updated ->

                            userProfile =
                                updated

                            profileStore.save(
                                updated
                            )
                        }
                    )
                }

                Screen.SAFETY -> {

                    SafetyScreen(
                        onBack = {
                            goBack()
                        }
                    )
                }
            }
        }
    }
}

/* =========================================================
   THEME
   ========================================================= */

@Composable
private fun GrihaTheme(
    darkMode: Boolean,
    content: @Composable () -> Unit
) {

    val light = lightColorScheme(
        primary = Color(0xFF087F73),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFDDF8F3),
        secondary = Color(0xFF25B9A7),
        background = Color(0xFFF7FBFA),
        surface = Color.White,
        surfaceVariant = Color(0xFFEAF2F0)
    )

    val dark = darkColorScheme(
        primary = Color(0xFF48DDC5),
        onPrimary = Color(0xFF003731),
        primaryContainer = Color(0xFF123C37),
        secondary = Color(0xFF33C7B2),
        background = Color(0xFF061513),
        surface = Color(0xFF0C211E),
        surfaceVariant = Color(0xFF16302D)
    )

    MaterialTheme(
        colorScheme =
            if (darkMode)
                dark
            else
                light,
        content = content
    )
}

/* =========================================================
   LANDING
   ========================================================= */

@Composable
private fun LandingScreen(
    onStart: () -> Unit
) {

    val infinite =
        rememberInfiniteTransition(
            label = "landingMotion"
        )

    val floatY by infinite.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(2200),
                repeatMode =
                    RepeatMode.Reverse
            ),
        label = "float"
    )

    val orbScale by infinite.animateFloat(
        initialValue = .96f,
        targetValue = 1.04f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(1800),
                repeatMode =
                    RepeatMode.Reverse
            ),
        label = "orbScale"
    )

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFDDF9F4),
                            Color(0xFFF9FCFB),
                            Color(0xFFEFF7F5)
                        )
                    )
                )
    ) {

        Canvas(
            modifier =
                Modifier.fillMaxSize()
        ) {

            drawCircle(
                brush =
                    Brush.radialGradient(
                        listOf(
                            Color(
                                0xFF4DD8C5
                            ).copy(
                                alpha = .28f
                            ),
                            Color.Transparent
                        )
                    ),
                radius =
                    size.width * .60f,
                center =
                    androidx.compose.ui.geometry
                        .Offset(
                            size.width * .90f,
                            size.height * .10f
                        )
            )

            drawCircle(
                brush =
                    Brush.radialGradient(
                        listOf(
                            Color(
                                0xFF7DD3FC
                            ).copy(
                                alpha = .12f
                            ),
                            Color.Transparent
                        )
                    ),
                radius =
                    size.width * .55f,
                center =
                    androidx.compose.ui.geometry
                        .Offset(
                            size.width * .08f,
                            size.height * .82f
                        )
            )
        }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 24.dp
                    ),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Spacer(
                Modifier.height(52.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                BrandMark()

                Spacer(
                    Modifier.width(11.dp)
                )

                Column {

                    Text(
                        "GrihaCare",
                        style =
                            MaterialTheme
                                .typography
                                .titleLarge,
                        fontWeight =
                            FontWeight.ExtraBold
                    )

                    Text(
                        "HOME • TRUST • AI",
                        style =
                            MaterialTheme
                                .typography
                                .labelSmall,
                        color =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )
                }

                Spacer(
                    Modifier.weight(1f)
                )

                Surface(
                    shape = CircleShape,
                    color =
                        Color.White.copy(
                            alpha = .82f
                        ),
                    shadowElevation = 6.dp
                ) {

                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription =
                            null,
                        modifier =
                            Modifier.padding(
                                11.dp
                            ),
                        tint =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )
                }
            }

            Spacer(
                Modifier.height(38.dp)
            )

            AnimatedVisibility(
                visible = true,
                enter =
                    fadeIn(
                        tween(700)
                    ) +
                            slideInVertically(
                                initialOffsetY = {
                                    70
                                },
                                animationSpec =
                                    tween(700)
                            )
            ) {

                Column(
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Surface(
                        shape =
                            RoundedCornerShape(
                                50.dp
                            ),
                        color =
                            Color.White.copy(
                                alpha = .75f
                            )
                    ) {

                        Text(
                            "AI-powered living",
                            modifier =
                                Modifier.padding(
                                    horizontal = 14.dp,
                                    vertical = 8.dp
                                ),
                            fontWeight =
                                FontWeight.SemiBold
                        )
                    }

                    Spacer(
                        Modifier.height(16.dp)
                    )

                    Text(
                        "Find a home.\nFind your people.",
                        style =
                            MaterialTheme
                                .typography
                                .displaySmall,
                        fontWeight =
                            FontWeight.ExtraBold
                    )

                    Spacer(
                        Modifier.height(11.dp)
                    )

                    Text(
                        "PGs, private rooms and trusted home professionals — made for Indian living.",
                        style =
                            MaterialTheme
                                .typography
                                .bodyLarge,
                        color =
                            Color(0xFF53716C)
                    )
                }
            }

            Spacer(
                Modifier.height(22.dp)
            )

            Box(
                modifier =
                    Modifier.size(188.dp),
                contentAlignment =
                    Alignment.Center
            ) {

                Box(
                    modifier =
                        Modifier
                            .size(150.dp)
                            .graphicsLayer {
                                translationY =
                                    floatY
                                scaleX =
                                    orbScale
                                scaleY =
                                    orbScale
                                rotationY = 4f
                                cameraDistance =
                                    1400f
                            }
                            .clip(
                                CircleShape
                            )
                            .background(
                                Brush.radialGradient(
                                    listOf(
                                        Color(0xFFEFFFFC),
                                        Color(0xFF71E1D0),
                                        Color(0xFF087F73)
                                    )
                                )
                            ),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Box(
                        modifier =
                            Modifier
                                .size(92.dp)
                                .clip(
                                    CircleShape
                                )
                                .background(
                                    Color.White.copy(
                                        alpha = .16f
                                    )
                                )
                    )

                    Icon(
                        Icons.Default.AutoAwesome,
                        null,
                        modifier =
                            Modifier.size(44.dp),
                        tint = Color.White
                    )
                }

                SmallFloatingBadge(
                    modifier =
                        Modifier.align(
                            Alignment.TopStart
                        ),
                    icon =
                        Icons.Default.Home,
                    text = "Smart match"
                )

                SmallFloatingBadge(
                    modifier =
                        Modifier.align(
                            Alignment.BottomEnd
                        ),
                    icon =
                        Icons.Default.VerifiedUser,
                    text = "Trust verified"
                )
            }

            Spacer(
                Modifier.height(24.dp)
            )

            Button(
                onClick = onStart,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                shape =
                    RoundedCornerShape(18.dp)
            ) {

                Text(
                    "Enter GrihaCare",
                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    Modifier.width(7.dp)
                )

                Icon(
                    Icons.Default.ArrowForward,
                    null
                )
            }

            Spacer(
                Modifier.height(10.dp)
            )

            Text(
                "Simple • Safe • AI-assisted",
                style =
                    MaterialTheme
                        .typography
                        .bodySmall
            )
        }
    }
}

/* =========================================================
   BRAND
   ========================================================= */

@Composable
private fun BrandMark() {

    Box(
        modifier =
            Modifier
                .size(48.dp)
                .clip(
                    RoundedCornerShape(
                        16.dp
                    )
                )
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF087F73),
                            Color(0xFF2CC5AF)
                        )
                    )
                ),
        contentAlignment =
            Alignment.Center
    ) {

        Icon(
            Icons.Default.HomeWork,
            null,
            tint = Color.White
        )
    }
}

/* =========================================================
   LOGIN
   ========================================================= */

@Composable
private fun LoginScreen(
    phone: String,
    onPhoneChange: (String) -> Unit,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(24.dp),
        verticalArrangement =
            Arrangement.Center
    ) {

        TopBar(
            "Welcome",
            onBack
        )

        Spacer(
            Modifier.height(25.dp)
        )

        Text(
            "Welcome back 👋",
            style =
                MaterialTheme
                    .typography
                    .headlineLarge,
            fontWeight =
                FontWeight.ExtraBold
        )

        Spacer(
            Modifier.height(7.dp)
        )

        Text(
            "Let's get you into your next room, PG or home service."
        )

        Spacer(
            Modifier.height(24.dp)
        )

        Card(
            modifier =
                Modifier.fillMaxWidth(),
            shape =
                RoundedCornerShape(25.dp),
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 7.dp
                )
        ) {

            Column(
                modifier =
                    Modifier.padding(20.dp)
            ) {

                Text(
                    "Mobile number",
                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        if (
                            it.length <= 10 &&
                            it.all(
                                Char::isDigit
                            )
                        ) {
                            onPhoneChange(it)
                        }
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Text(
                            "+91",
                            fontWeight =
                                FontWeight.Bold
                        )
                    },
                    placeholder = {
                        Text(
                            "10 digit mobile number"
                        )
                    },
                    singleLine = true,
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Phone
                        ),
                    shape =
                        RoundedCornerShape(16.dp)
                )

                Spacer(
                    Modifier.height(14.dp)
                )

                Button(
                    onClick = onContinue,
                    enabled =
                        phone.length == 10,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                    shape =
                        RoundedCornerShape(16.dp)
                ) {

                    Text(
                        "Send OTP",
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }

        Spacer(
            Modifier.height(11.dp)
        )

        Text(
            "Demo OTP: 123456",
            style =
                MaterialTheme
                    .typography
                    .bodySmall
        )
    }
}

/* =========================================================
   OTP
   ========================================================= */

@Composable
private fun OtpScreen(
    phone: String,
    onBack: () -> Unit,
    onVerified: () -> Unit
) {

    var otp by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp)
    ) {

        TopBar(
            "Verify number",
            onBack
        )

        Spacer(
            Modifier.height(30.dp)
        )

        Icon(
            Icons.Default.Lock,
            null,
            modifier =
                Modifier.size(50.dp),
            tint =
                MaterialTheme
                    .colorScheme
                    .primary
        )

        Spacer(
            Modifier.height(14.dp)
        )

        Text(
            "Enter OTP",
            style =
                MaterialTheme
                    .typography
                    .headlineMedium,
            fontWeight =
                FontWeight.ExtraBold
        )

        Text(
            "OTP sent to +91 $phone"
        )

        Spacer(
            Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = otp,
            onValueChange = {
                if (
                    it.length <= 6 &&
                    it.all(
                        Char::isDigit
                    )
                ) {
                    otp = it
                }
            },
            modifier =
                Modifier.fillMaxWidth(),
            label = {
                Text("6 digit OTP")
            },
            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Number
                ),
            singleLine = true,
            shape =
                RoundedCornerShape(18.dp)
        )

        Spacer(
            Modifier.height(14.dp)
        )

        Button(
            onClick = onVerified,
            enabled =
                otp == "123456",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(54.dp),
            shape =
                RoundedCornerShape(18.dp)
        ) {

            Text(
                "Verify & Enter",
                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}

/* =========================================================
   HOME
   ========================================================= */

@Composable
private fun HomeScreen(
    profile: UserProfile,
    darkMode: Boolean,
    onTheme: () -> Unit,
    onSearch: () -> Unit,
    onChat: () -> Unit,
    onBookings: () -> Unit,
    onProfile: () -> Unit,
    onSafety: () -> Unit,
    onVerify: () -> Unit,
    onWorker: (Worker) -> Unit
) {

    val scroll =
        rememberScrollState()

    val scrollY =
        scroll.value

    val heroAlpha =
        (1f - scrollY / 420f)
            .coerceIn(
                .25f,
                1f
            )

    Scaffold(

        floatingActionButton = {
            AIButton(
                onClick = onChat
            )
        },

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            null
                        )
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onSearch,
                    icon = {
                        Icon(
                            Icons.Default.Search,
                            null
                        )
                    },
                    label = {
                        Text("Explore")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onBookings,
                    icon = {
                        Icon(
                            Icons.Default.Event,
                            null
                        )
                    },
                    label = {
                        Text("Bookings")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onProfile,
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            null
                        )
                    },
                    label = {
                        Text("Profile")
                    }
                )
            }
        }

    ) { padding ->

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
        ) {

            HomeHeader(
                profile = profile,
                darkMode = darkMode,
                onTheme = onTheme
            )

            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(
                            scroll
                        )
            ) {

                HomeHero(
                    alpha = heroAlpha,
                    profile = profile,
                    onAI = onChat
                )

                Spacer(
                    Modifier.height(18.dp)
                )

                SearchPill(
                    onClick = onSearch
                )

                Spacer(
                    Modifier.height(20.dp)
                )

                QuickActions(
                    onVerify = onVerify,
                    onSafety = onSafety
                )

                Spacer(
                    Modifier.height(27.dp)
                )

                SectionHeader(
                    "Made for Indian living",
                    "PGs, rooms and affordable rentals"
                )

                Spacer(
                    Modifier.height(11.dp)
                )

                AiMatchCard(
                    profile = profile
                )

                Spacer(
                    Modifier.height(28.dp)
                )

                SectionHeader(
                    "Rooms & PGs",
                    "Practical monthly options"
                )

                Spacer(
                    Modifier.height(7.dp)
                )

                properties.forEachIndexed {
                        index,
                        property ->

                    PropertyCard(
                        property = property,
                        index = index,
                        scrollY = scrollY
                    )
                }

                Spacer(
                    Modifier.height(25.dp)
                )

                SectionHeader(
                    "Trusted professionals",
                    "Identity + experience + reviews"
                )

                Spacer(
                    Modifier.height(7.dp)
                )

                workers.forEach {
                        worker ->

                    WorkerCard(
                        worker = worker,
                        onClick = {
                            onWorker(
                                worker
                            )
                        }
                    )
                }

                Spacer(
                    Modifier.height(110.dp)
                )
            }
        }
    }
}

/* =========================================================
   HOME HEADER
   ========================================================= */

@Composable
private fun HomeHeader(
    profile: UserProfile,
    darkMode: Boolean,
    onTheme: () -> Unit
) {

    Surface(
        tonalElevation = 3.dp,
        shadowElevation = 2.dp
    ) {

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 18.dp,
                        vertical = 11.dp
                    ),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    if (
                        profile.name.isBlank()
                    )
                        "GrihaCare"
                    else
                        "Hi, ${profile.name}",
                    style =
                        MaterialTheme
                            .typography
                            .titleLarge,
                    fontWeight =
                        FontWeight.ExtraBold
                )

                Text(
                    "Find your next place 👋",
                    style =
                        MaterialTheme
                            .typography
                            .bodySmall
                )
            }

            IconButton(
                onClick = onTheme
            ) {

                Icon(
                    if (darkMode)
                        Icons.Default.LightMode
                    else
                        Icons.Default.DarkMode,
                    contentDescription =
                        "Theme"
                )
            }
        }
    }
}

/* =========================================================
   HERO
   ========================================================= */

@Composable
private fun HomeHero(
    alpha: Float,
    profile: UserProfile,
    onAI: () -> Unit
) {

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp
                )
                .graphicsLayer {
                    this.alpha = alpha
                    translationY =
                        -(1f - alpha) * 35f
                },
        shape =
            RoundedCornerShape(29.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color(0xFF087F73)
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 14.dp
            )
    ) {

        Box {

            Canvas(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(225.dp)
            ) {

                drawCircle(
                    brush =
                        Brush.radialGradient(
                            listOf(
                                Color.White.copy(
                                    alpha = .17f
                                ),
                                Color.Transparent
                            )
                        ),
                    radius =
                        size.width * .55f,
                    center =
                        androidx.compose.ui.geometry
                            .Offset(
                                size.width * .85f,
                                size.height * .10f
                            )
                )
            }

            Column(
                modifier =
                    Modifier.padding(22.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.AutoAwesome,
                        null,
                        tint = Color.White
                    )

                    Spacer(
                        Modifier.width(6.dp)
                    )

                    Text(
                        "AI MATCHING",
                        color =
                            Color.White,
                        fontWeight =
                            FontWeight.Bold
                    )
                }

                Spacer(
                    Modifier.height(14.dp)
                )

                Text(
                    if (
                        profile.name.isBlank()
                    )
                        "A smarter way\nto find your space."
                    else
                        "A smarter way\nto find your space, ${profile.name}.",
                    style =
                        MaterialTheme
                            .typography
                            .headlineLarge,
                    color =
                        Color.White,
                    fontWeight =
                        FontWeight.ExtraBold
                )

                Spacer(
                    Modifier.height(7.dp)
                )

                Text(
                    "Tell us your budget, city and lifestyle.",
                    color =
                        Color.White.copy(
                            alpha = .82f
                        )
                )

                Spacer(
                    Modifier.height(16.dp)
                )

                Button(
                    onClick = onAI,
                    colors =
                        ButtonDefaults
                            .buttonColors(
                                containerColor =
                                    Color.White,
                                contentColor =
                                    Color(
                                        0xFF087F73
                                    )
                            ),
                    shape =
                        RoundedCornerShape(
                            15.dp
                        )
                ) {

                    Icon(
                        Icons.Default.AutoAwesome,
                        null
                    )

                    Spacer(
                        Modifier.width(6.dp)
                    )

                    Text(
                        "Ask GrihaCare AI",
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }
    }
}

/* =========================================================
   SEARCH
   ========================================================= */

@Composable
private fun SearchPill(
    onClick: () -> Unit
) {

    Surface(
        onClick = onClick,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp
                ),
        shape =
            RoundedCornerShape(18.dp),
        color =
            MaterialTheme
                .colorScheme
                .surface,
        shadowElevation = 5.dp
    ) {

        Row(
            modifier =
                Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 15.dp
                ),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                Icons.Default.Search,
                null,
                tint =
                    MaterialTheme
                        .colorScheme
                        .primary
            )

            Spacer(
                Modifier.width(9.dp)
            )

            Text(
                "Try “PG under ₹10k in Pune”",
                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }
    }
}

/* =========================================================
   QUICK ACTIONS
   ========================================================= */

@Composable
private fun QuickActions(
    onVerify: () -> Unit,
    onSafety: () -> Unit
) {

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )
                .padding(
                    horizontal = 18.dp
                ),
        horizontalArrangement =
            Arrangement.spacedBy(10.dp)
    ) {

        ActionCard(
            Icons.Default.VerifiedUser,
            "Verified",
            "Workers",
            onVerify
        )

        ActionCard(
            Icons.Default.Security,
            "First Visit",
            "Safety",
            onSafety
        )

        ActionCard(
            Icons.Default.AutoAwesome,
            "Smart Match",
            "AI",
            {}
        )

        ActionCard(
            Icons.Default.People,
            "Experts",
            "Home help",
            {}
        )
    }
}

@Composable
private fun ActionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {

    Surface(
        onClick = onClick,
        modifier =
            Modifier.width(128.dp),
        shape =
            RoundedCornerShape(19.dp),
        tonalElevation = 3.dp,
        shadowElevation = 3.dp
    ) {

        Column(
            modifier =
                Modifier.padding(15.dp)
        ) {

            Box(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(
                            RoundedCornerShape(
                                12.dp
                            )
                        )
                        .background(
                            MaterialTheme
                                .colorScheme
                                .primaryContainer
                        ),
                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    icon,
                    null,
                    tint =
                        MaterialTheme
                            .colorScheme
                            .primary
                )
            }

            Spacer(
                Modifier.height(8.dp)
            )

            Text(
                title,
                fontWeight =
                    FontWeight.Bold
            )

            Text(
                subtitle,
                style =
                    MaterialTheme
                        .typography
                        .bodySmall
            )
        }
    }
}

/* =========================================================
   AI MATCH
   ========================================================= */

@Composable
private fun AiMatchCard(
    profile: UserProfile
) {

    var progress by remember {
        mutableFloatStateOf(0f)
    }

    LaunchedEffect(Unit) {

        val animation =
            Animatable(0f)

        animation.animateTo(
            .96f,
            tween(1200)
        ) {

            progress =
                value
        }
    }

    val budgetText =
        if (
            profile.budget.isBlank()
        )
            "Set your budget in Profile"
        else
            "Your budget: ₹${profile.budget}/month"

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp
                ),
        shape =
            RoundedCornerShape(24.dp),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
    ) {

        Column(
            modifier =
                Modifier.padding(19.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Default.AutoAwesome,
                    null,
                    tint =
                        MaterialTheme
                            .colorScheme
                            .primary
                )

                Spacer(
                    Modifier.width(8.dp)
                )

                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(
                        "AI Match",
                        fontWeight =
                            FontWeight.ExtraBold
                    )

                    Text(
                        budgetText,
                        style =
                            MaterialTheme
                                .typography
                                .bodySmall
                    )
                }

                Text(
                    "${(progress * 100).toInt()}%",
                    fontWeight =
                        FontWeight.ExtraBold,
                    color =
                        MaterialTheme
                            .colorScheme
                            .primary
                )
            }

            Spacer(
                Modifier.height(12.dp)
            )

            LinearProgressIndicator(
                progress = progress,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(
                            RoundedCornerShape(
                                10.dp
                            )
                        )
            )

            Spacer(
                Modifier.height(12.dp)
            )

            Text(
                "Compact 1RK • HSR Layout",
                fontWeight =
                    FontWeight.Bold
            )

            Text(
                "₹15,500/month • Parking • Attached bathroom"
            )

            Spacer(
                Modifier.height(8.dp)
            )

            Text(
                "Why it matches",
                fontWeight =
                    FontWeight.Bold
            )

            Text(
                "✓ Practical price   ✓ Good commute   ✓ Verified listing"
            )
        }
    }
}

/* =========================================================
   PROPERTY
   ========================================================= */

@Composable
private fun PropertyCard(
    property: Property,
    index: Int,
    scrollY: Int
) {

    val normalized =
        (
                scrollY -
                        index * 270
                ) / 270f

    val tilt =
        normalized.coerceIn(
            -1f,
            1f
        )

    val scale =
        1f -
                abs(tilt) * .022f

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 8.dp
                )
                .graphicsLayer {
                    scaleX =
                        scale

                    scaleY =
                        scale

                    rotationY =
                        tilt * 2.3f

                    rotationX =
                        -tilt * 1.1f

                    translationY =
                        tilt * 10f

                    cameraDistance =
                        1500f
                },
        shape =
            RoundedCornerShape(24.dp),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
    ) {

        Column {

            PropertyVisual(
                index = index
            )

            Column(
                modifier =
                    Modifier.padding(17.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Column(
                        modifier =
                            Modifier.weight(1f)
                    ) {

                        Text(
                            property.name,
                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,
                            fontWeight =
                                FontWeight.ExtraBold
                        )

                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Icon(
                                Icons.Default.LocationOn,
                                null,
                                modifier =
                                    Modifier.size(
                                        15.dp
                                    )
                            )

                            Spacer(
                                Modifier.width(3.dp)
                            )

                            Text(
                                property.location,
                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall
                            )
                        }
                    }

                    Surface(
                        shape =
                            RoundedCornerShape(
                                11.dp
                            ),
                        color =
                            MaterialTheme
                                .colorScheme
                                .primaryContainer
                    ) {

                        Text(
                            "${property.match}%",
                            modifier =
                                Modifier.padding(
                                    8.dp
                                ),
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .primary,
                            fontWeight =
                                FontWeight.ExtraBold
                        )
                    }
                }

                Spacer(
                    Modifier.height(10.dp)
                )

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        property.rent,
                        color =
                            MaterialTheme
                                .colorScheme
                                .primary,
                        fontWeight =
                            FontWeight.ExtraBold
                    )

                    Spacer(
                        Modifier.width(5.dp)
                    )

                    Text(
                        "monthly"
                    )
                }

                Spacer(
                    Modifier.height(8.dp)
                )

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(7.dp)
                ) {

                    Tag(
                        property.type
                    )

                    Tag(
                        property.furnished
                    )

                    if (property.parking) {
                        Tag(
                            "Parking"
                        )
                    }
                }
            }
        }
    }
}

/* =========================================================
   PROPERTY VISUAL
   ========================================================= */

@Composable
private fun PropertyVisual(
    index: Int
) {

    val palettes =
        listOf(
            listOf(
                Color(0xFF9AE4D8),
                Color(0xFF248E81)
            ),
            listOf(
                Color(0xFFB9DFFF),
                Color(0xFF4D91CF)
            ),
            listOf(
                Color(0xFFFFDBAE),
                Color(0xFFE28C49)
            ),
            listOf(
                Color(0xFFDCCBFA),
                Color(0xFF8360BE)
            )
        )

    val palette =
        palettes[
            index %
                    palettes.size
        ]

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(
                    Brush.linearGradient(
                        palette
                    )
                ),
        contentAlignment =
            Alignment.Center
    ) {

        Canvas(
            modifier =
                Modifier.fillMaxSize()
        ) {

            val buildingW =
                size.width * .38f

            val buildingH =
                size.height * .53f

            val left =
                (
                        size.width -
                                buildingW
                        ) / 2f

            val top =
                size.height * .28f

            drawRoundRect(
                color =
                    Color.White.copy(
                        alpha = .80f
                    ),
                topLeft =
                    androidx.compose.ui.geometry
                        .Offset(
                            left,
                            top
                        ),
                size =
                    androidx.compose.ui.geometry
                        .Size(
                            buildingW,
                            buildingH
                        ),
                cornerRadius =
                    androidx.compose.ui.geometry
                        .CornerRadius(
                            22f,
                            22f
                        )
            )

            repeat(3) { row ->

                repeat(3) { col ->

                    drawRoundRect(
                        color =
                            Color(0xFF377D74)
                                .copy(
                                    alpha = .18f
                                ),
                        topLeft =
                            androidx.compose.ui.geometry
                                .Offset(
                                    left +
                                            22f +
                                            col * 43f,
                                    top +
                                            22f +
                                            row * 35f
                                ),
                        size =
                            androidx.compose.ui.geometry
                                .Size(
                                    19f,
                                    20f
                                ),
                        cornerRadius =
                            androidx.compose.ui.geometry
                                .CornerRadius(
                                    5f,
                                    5f
                                )
                    )
                }
            }
        }
    }
}

/* =========================================================
   WORKER
   ========================================================= */

@Composable
private fun WorkerCard(
    worker: Worker,
    onClick: () -> Unit
) {

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 7.dp
                )
                .clickable(
                    onClick = onClick
                ),
        shape =
            RoundedCornerShape(23.dp),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
    ) {

        Row(
            modifier =
                Modifier.padding(17.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier =
                    Modifier
                        .size(60.dp)
                        .clip(
                            CircleShape
                        )
                        .background(
                            MaterialTheme
                                .colorScheme
                                .primaryContainer
                        ),
                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    worker.name
                        .split(" ")
                        .take(2)
                        .map {
                            it.first()
                        }
                        .joinToString(""),
                    fontWeight =
                        FontWeight.ExtraBold,
                    color =
                        MaterialTheme
                            .colorScheme
                            .primary
                )
            }

            Spacer(
                Modifier.width(13.dp)
            )

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        worker.name,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        Modifier.width(4.dp)
                    )

                    Icon(
                        Icons.Default.Verified,
                        null,
                        modifier =
                            Modifier.size(
                                16.dp
                            ),
                        tint =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )
                }

                Text(
                    worker.role
                )

                Text(
                    "${worker.location} • ${worker.experience}",
                    style =
                        MaterialTheme
                            .typography
                            .bodySmall
                )

                Text(
                    "${worker.price} • ⭐ ${worker.rating}",
                    fontWeight =
                        FontWeight.SemiBold
                )
            }

            Surface(
                shape =
                    RoundedCornerShape(
                        13.dp
                    ),
                color =
                    MaterialTheme
                        .colorScheme
                        .primaryContainer
            ) {

                Column(
                    modifier =
                        Modifier.padding(
                            9.dp
                        ),
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        worker.trustScore
                            .toString(),
                        fontWeight =
                            FontWeight.ExtraBold,
                        color =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )

                    Text(
                        "TRUST",
                        style =
                            MaterialTheme
                                .typography
                                .labelSmall
                    )
                }
            }
        }
    }
}

/* =========================================================
   WORKER DETAIL
   ========================================================= */

@Composable
private fun WorkerDetailScreen(
    worker: Worker,
    onBack: () -> Unit,
    onHire: () -> Unit
) {

    Column(
        modifier =
            Modifier.fillMaxSize()
    ) {

        TopBar(
            "Professional",
            onBack
        )

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(20.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Box(
                modifier =
                    Modifier
                        .size(90.dp)
                        .clip(
                            CircleShape
                        )
                        .background(
                            MaterialTheme
                                .colorScheme
                                .primaryContainer
                        ),
                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    worker.name
                        .split(" ")
                        .take(2)
                        .map {
                            it.first()
                        }
                        .joinToString(""),
                    style =
                        MaterialTheme
                            .typography
                            .headlineMedium,
                    fontWeight =
                        FontWeight.ExtraBold
                )
            }

            Spacer(
                Modifier.height(11.dp)
            )

            Text(
                worker.name,
                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,
                fontWeight =
                    FontWeight.ExtraBold
            )

            Text(
                worker.role
            )

            Spacer(
                Modifier.height(18.dp)
            )

            TrustScoreCard(
                worker.trustScore
            )

            Spacer(
                Modifier.height(15.dp)
            )

            DetailRow(
                "Area",
                worker.location
            )

            DetailRow(
                "Experience",
                worker.experience
            )

            DetailRow(
                "Rating",
                "⭐ ${worker.rating}/5"
            )

            DetailRow(
                "Service",
                worker.price
            )

            DetailRow(
                "Identity",
                "Demo KYC verified ✓"
            )

            Spacer(
                Modifier.height(18.dp)
            )

            Button(
                onClick = onHire,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                shape =
                    RoundedCornerShape(17.dp)
            ) {

                Icon(
                    Icons.Default.VerifiedUser,
                    null
                )

                Spacer(
                    Modifier.width(7.dp)
                )

                Text(
                    "Verify & Hire",
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }
}

/* =========================================================
   TRUST
   ========================================================= */

@Composable
private fun TrustScoreCard(
    score: Int
) {

    var current by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(score) {

        val animation =
            Animatable(0f)

        animation.animateTo(
            score.toFloat(),
            tween(1000)
        ) {

            current =
                value.toInt()
        }
    }

    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(24.dp)
    ) {

        Column(
            modifier =
                Modifier.padding(20.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                "$current/100",
                style =
                    MaterialTheme
                        .typography
                        .displaySmall,
                fontWeight =
                    FontWeight.ExtraBold,
                color =
                    MaterialTheme
                        .colorScheme
                        .primary
            )

            Text(
                "TRUST SCORE",
                fontWeight =
                    FontWeight.Bold
            )

            Spacer(
                Modifier.height(10.dp)
            )

            LinearProgressIndicator(
                progress =
                    current / 100f,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(
                            RoundedCornerShape(
                                20.dp
                            )
                        )
            )

            Spacer(
                Modifier.height(11.dp)
            )

            Text(
                "Identity ✓   Mobile ✓   Reviews ✓"
            )
        }
    }
}

/* =========================================================
   VERIFICATION
   ========================================================= */

@Composable
private fun VerificationScreen(
    onBack: () -> Unit,
    onVerified: () -> Unit
) {

    var aadhaar by rememberSaveable {
        mutableStateOf("")
    }

    var otp by rememberSaveable {
        mutableStateOf("")
    }

    var sent by rememberSaveable {
        mutableStateOf(false)
    }

    var verified by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
    ) {

        TopBar(
            "Demo KYC",
            onBack
        )

        Spacer(
            Modifier.height(14.dp)
        )

        Text(
            "Worker identity verification",
            style =
                MaterialTheme
                    .typography
                    .headlineMedium,
            fontWeight =
                FontWeight.ExtraBold
        )

        Text(
            "Prototype only. Do not enter a real Aadhaar number."
        )

        Spacer(
            Modifier.height(18.dp)
        )

        Card(
            shape =
                RoundedCornerShape(24.dp)
        ) {

            Column(
                modifier =
                    Modifier.padding(20.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.Lock,
                        null
                    )

                    Spacer(
                        Modifier.width(7.dp)
                    )

                    Text(
                        "Aadhaar-style Demo Flow",
                        fontWeight =
                            FontWeight.Bold
                    )
                }

                Spacer(
                    Modifier.height(15.dp)
                )

                OutlinedTextField(
                    value = aadhaar,
                    onValueChange = {
                        if (
                            it.length <= 12 &&
                            it.all(
                                Char::isDigit
                            )
                        ) {
                            aadhaar = it
                        }
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            "Demo Aadhaar"
                        )
                    },
                    placeholder = {
                        Text(
                            "12 digit demo number"
                        )
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Number
                        ),
                    singleLine = true
                )

                Spacer(
                    Modifier.height(11.dp)
                )

                if (!sent) {

                    Button(
                        onClick = {
                            sent = true
                        },
                        enabled =
                            aadhaar.length == 12,
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Text(
                            "Send Demo OTP"
                        )
                    }

                } else {

                    Text(
                        "Demo OTP: 123456",
                        color =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )

                    Spacer(
                        Modifier.height(10.dp)
                    )

                    OutlinedTextField(
                        value = otp,
                        onValueChange = {
                            if (
                                it.length <= 6 &&
                                it.all(
                                    Char::isDigit
                                )
                            ) {
                                otp = it
                            }
                        },
                        modifier =
                            Modifier.fillMaxWidth(),
                        label = {
                            Text("OTP")
                        },
                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Number
                            ),
                        singleLine = true
                    )

                    Spacer(
                        Modifier.height(10.dp)
                    )

                    Button(
                        onClick = {
                            verified =
                                otp == "123456"
                        },
                        enabled =
                            otp.length == 6,
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Text(
                            "Verify Identity"
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = verified,
            enter =
                fadeIn() +
                        scaleIn()
        ) {

            Card(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 18.dp
                        ),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme
                                .colorScheme
                                .primaryContainer
                    ),
                shape =
                    RoundedCornerShape(24.dp)
            ) {

                Column(
                    modifier =
                        Modifier.padding(20.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Icon(
                        Icons.Default.CheckCircle,
                        null,
                        modifier =
                            Modifier.size(
                                52.dp
                            ),
                        tint =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )

                    Text(
                        "Identity Verified ✓",
                        style =
                            MaterialTheme
                                .typography
                                .titleLarge,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Text(
                        "Demo KYC completed"
                    )

                    Spacer(
                        Modifier.height(12.dp)
                    )

                    Button(
                        onClick = onVerified
                    ) {

                        Text(
                            "Continue"
                        )
                    }
                }
            }
        }
    }
}

/* =========================================================
   SEARCH
   ========================================================= */

@Composable
private fun SearchScreen(
    onBack: () -> Unit,
    onOpen: () -> Unit
) {

    var query by rememberSaveable {
        mutableStateOf("")
    }

    val filtered =
        properties.filter {

            query.isBlank() ||
                    it.name.contains(
                        query,
                        true
                    ) ||
                    it.location.contains(
                        query,
                        true
                    ) ||
                    it.type.contains(
                        query,
                        true
                    )
        }

    Column(
        modifier =
            Modifier.fillMaxSize()
    ) {

        TopBar(
            "Explore rooms & PGs",
            onBack
        )

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    null
                )
            },
            placeholder = {
                Text(
                    "Gwalior, PG, 1RK..."
                )
            },
            singleLine = true,
            shape =
                RoundedCornerShape(
                    18.dp
                )
        )

        LazyColumn(
            contentPadding =
                PaddingValues(
                    bottom = 30.dp
                )
        ) {

            items(filtered) { property ->

                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 18.dp,
                                vertical = 7.dp
                            )
                            .clickable(
                                onClick =
                                    onOpen
                            ),
                    shape =
                        RoundedCornerShape(
                            22.dp
                        )
                ) {

                    Column(
                        modifier =
                            Modifier.padding(
                                17.dp
                            )
                    ) {

                        Text(
                            property.name,
                            fontWeight =
                                FontWeight.ExtraBold
                        )

                        Text(
                            property.location
                        )

                        Text(
                            "${property.rent} • ${property.type}",
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .primary,
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

/* =========================================================
   CHAT
   ========================================================= */

@Composable
private fun ChatScreen(
    onBack: () -> Unit
) {

    val repository =
        remember {
            ClaudeRepository()
        }

    val scope =
        rememberCoroutineScope()

    var input by rememberSaveable {
        mutableStateOf("")
    }

    var loading by rememberSaveable {
        mutableStateOf(false)
    }

    var messages by remember {

        mutableStateOf(
            listOf(
                ChatMessage(
                    "assistant",
                    "Hi 👋 I'm GrihaCare AI.\n\n" +
                            "Tell me your city, budget or service need.\n\n" +
                            "Try: \"PG under ₹10k in Pune\""
                )
            )
        )
    }

    val scroll =
        rememberScrollState()

    LaunchedEffect(
        messages.size
    ) {

        scroll.animateScrollTo(
            scroll.maxValue
        )
    }

    Column(
        modifier =
            Modifier.fillMaxSize()
    ) {

        TopBar(
            "GrihaCare AI",
            onBack
        )

        Surface(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 15.dp,
                        vertical = 5.dp
                    ),
            shape =
                RoundedCornerShape(
                    50.dp
                ),
            color =
                MaterialTheme
                    .colorScheme
                    .primaryContainer
        ) {

            Row(
                modifier =
                    Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier =
                        Modifier
                            .size(8.dp)
                            .clip(
                                CircleShape
                            )
                            .background(
                                MaterialTheme
                                    .colorScheme
                                    .primary
                            )
                )

                Spacer(
                    Modifier.width(7.dp)
                )

                Text(
                    "GrihaCare AI • Ready",
                    style =
                        MaterialTheme
                            .typography
                            .bodySmall,
                    fontWeight =
                        FontWeight.SemiBold
                )
            }
        }

        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(
                        scroll
                    )
                    .padding(15.dp),
            verticalArrangement =
                Arrangement.spacedBy(9.dp)
        ) {

            messages.forEach { message ->

                ChatBubble(
                    message
                )
            }

            if (loading) {

                Surface(
                    shape =
                        RoundedCornerShape(
                            18.dp
                        ),
                    color =
                        MaterialTheme
                            .colorScheme
                            .surfaceVariant
                ) {

                    Text(
                        "GrihaCare AI is thinking...",
                        modifier =
                            Modifier.padding(
                                13.dp
                            )
                    )
                }
            }
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                },
                modifier =
                    Modifier.weight(1f),
                placeholder = {
                    Text(
                        "Ask GrihaCare..."
                    )
                },
                maxLines = 3,
                shape =
                    RoundedCornerShape(
                        20.dp
                    )
            )

            Spacer(
                Modifier.width(7.dp)
            )

            FloatingActionButton(
                onClick = {

                    if (
                        input.isBlank() ||
                        loading
                    ) return@FloatingActionButton

                    val question =
                        input.trim()

                    val oldMessages =
                        messages

                    messages =
                        messages +
                                ChatMessage(
                                    "user",
                                    question
                                )

                    input = ""
                    loading = true

                    scope.launch {

                        val result =
                            repository.sendMessage(
                                question,
                                oldMessages
                            )

                        result.onSuccess {
                                answer ->

                            messages =
                                messages +
                                        ChatMessage(
                                            "assistant",
                                            answer
                                        )

                        }.onFailure {

                            messages =
                                messages +
                                        ChatMessage(
                                            "assistant",
                                            "I couldn't connect right now. Please check that the GrihaCare backend is running."
                                        )
                        }

                        loading = false
                    }
                },
                containerColor =
                    MaterialTheme
                        .colorScheme
                        .primary
            ) {

                Icon(
                    Icons.Default.Send,
                    null,
                    tint = Color.White
                )
            }
        }
    }
}

/* =========================================================
   CHAT BUBBLE
   ========================================================= */

@Composable
private fun ChatBubble(
    message: ChatMessage
) {

    val user =
        message.role == "user"

    Row(
        modifier =
            Modifier.fillMaxWidth(),
        horizontalArrangement =
            if (user)
                Arrangement.End
            else
                Arrangement.Start
    ) {

        Surface(
            shape =
                RoundedCornerShape(
                    20.dp
                ),
            color =
                if (user)
                    MaterialTheme
                        .colorScheme
                        .primary
                else
                    MaterialTheme
                        .colorScheme
                        .surfaceVariant
        ) {

            Text(
                message.content,
                modifier =
                    Modifier.padding(
                        15.dp
                    ),
                color =
                    if (user)
                        Color.White
                    else
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
            )
        }
    }
}

/* =========================================================
   BOOKING
   ========================================================= */

@Composable
private fun BookingScreen(
    onBack: () -> Unit,
    onDone: () -> Unit
) {

    var confirmed by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(20.dp)
    ) {

        TopBar(
            "Confirm visit",
            onBack
        )

        Spacer(
            Modifier.height(15.dp)
        )

        Card(
            modifier =
                Modifier.fillMaxWidth(),
            shape =
                RoundedCornerShape(24.dp)
        ) {

            Column(
                modifier =
                    Modifier.padding(20.dp)
            ) {

                Text(
                    "Compact 1RK",
                    style =
                        MaterialTheme
                            .typography
                            .titleLarge,
                    fontWeight =
                        FontWeight.ExtraBold
                )

                Text(
                    "HSR Layout • Bengaluru"
                )

                Spacer(
                    Modifier.height(9.dp)
                )

                DetailRow(
                    "Rent",
                    "₹15,500/month"
                )

                DetailRow(
                    "Visit",
                    "Tomorrow • 11:30 AM"
                )

                DetailRow(
                    "Safety",
                    "First Visit Mode"
                )
            }
        }

        Spacer(
            Modifier.height(17.dp)
        )

        Button(
            onClick = {
                confirmed = true
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(54.dp),
            shape =
                RoundedCornerShape(
                    18.dp
                )
        ) {

            Text(
                if (confirmed)
                    "Visit confirmed ✓"
                else
                    "Confirm visit"
            )
        }

        if (confirmed) {

            Spacer(
                Modifier.height(10.dp)
            )

            OutlinedButton(
                onClick = onDone,
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text(
                    "View bookings"
                )
            }
        }
    }
}

/* =========================================================
   BOOKINGS
   ========================================================= */

@Composable
private fun BookingsScreen(
    onBack: () -> Unit
) {

    Column(
        modifier =
            Modifier.fillMaxSize()
    ) {

        TopBar(
            "My bookings",
            onBack
        )

        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
            shape =
                RoundedCornerShape(23.dp)
        ) {

            Column(
                modifier =
                    Modifier.padding(20.dp)
            ) {

                Text(
                    "Compact 1RK",
                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,
                    fontWeight =
                        FontWeight.Bold
                )

                Text(
                    "HSR Layout • ₹15,500/month"
                )

                Spacer(
                    Modifier.height(10.dp)
                )

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.CheckCircle,
                        null,
                        tint =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )

                    Spacer(
                        Modifier.width(6.dp)
                    )

                    Text(
                        "Visit confirmed"
                    )
                }
            }
        }
    }
}

/* =========================================================
   PROFILE
   ========================================================= */

@Composable
private fun ProfileScreen(
    profile: UserProfile,
    darkMode: Boolean,
    onTheme: () -> Unit,
    onBack: () -> Unit,
    onSave: (UserProfile) -> Unit
) {

    var editing by rememberSaveable {
        mutableStateOf(false)
    }

    var name by rememberSaveable(
        profile.name
    ) {
        mutableStateOf(
            profile.name
        )
    }

    var email by rememberSaveable(
        profile.email
    ) {
        mutableStateOf(
            profile.email
        )
    }

    var city by rememberSaveable(
        profile.city
    ) {
        mutableStateOf(
            profile.city
        )
    }

    var occupation by rememberSaveable(
        profile.occupation
    ) {
        mutableStateOf(
            profile.occupation
        )
    }

    var budget by rememberSaveable(
        profile.budget
    ) {
        mutableStateOf(
            profile.budget
        )
    }

    var preferredStay by rememberSaveable(
        profile.preferredStay
    ) {
        mutableStateOf(
            profile.preferredStay
        )
    }

    Column(
        modifier =
            Modifier.fillMaxSize()
    ) {

        TopBar(
            "My Profile",
            onBack
        )

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(20.dp)
        ) {

            Card(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(26.dp),
                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 7.dp
                    )
            ) {

                Row(
                    modifier =
                        Modifier.padding(20.dp),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Box(
                        modifier =
                            Modifier
                                .size(67.dp)
                                .clip(
                                    CircleShape
                                )
                                .background(
                                    MaterialTheme
                                        .colorScheme
                                        .primaryContainer
                                ),
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            if (name.isBlank())
                                "GC"
                            else
                                name
                                    .trim()
                                    .take(2)
                                    .uppercase(),
                            style =
                                MaterialTheme
                                    .typography
                                    .titleLarge,
                            fontWeight =
                                FontWeight.ExtraBold,
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .primary
                        )
                    }

                    Spacer(
                        Modifier.width(13.dp)
                    )

                    Column(
                        modifier =
                            Modifier.weight(1f)
                    ) {

                        Text(
                            if (name.isBlank())
                                "Complete your profile"
                            else
                                name,
                            style =
                                MaterialTheme
                                    .typography
                                    .titleLarge,
                            fontWeight =
                                FontWeight.ExtraBold
                        )

                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Icon(
                                Icons.Default.Verified,
                                null,
                                modifier =
                                    Modifier.size(
                                        16.dp
                                    ),
                                tint =
                                    MaterialTheme
                                        .colorScheme
                                        .primary
                            )

                            Spacer(
                                Modifier.width(4.dp)
                            )

                            Text(
                                "Mobile verified",
                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall
                            )
                        }
                    }

                    TextButton(
                        onClick = {
                            editing =
                                !editing
                        }
                    ) {

                        Text(
                            if (editing)
                                "Cancel"
                            else
                                "Edit"
                        )
                    }
                }
            }

            Spacer(
                Modifier.height(12.dp)
            )

            ProfileInfo(
                Icons.Default.Phone,
                "Mobile number",
                if (
                    profile.mobile.isBlank()
                )
                    "Not verified"
                else
                    "+91 ${profile.mobile}",
                verified =
                    profile.mobile.isNotBlank()
            )

            if (editing) {

                Spacer(
                    Modifier.height(12.dp)
                )

                ProfileField(
                    "Full name",
                    name,
                    {
                        name = it
                    },
                    Icons.Default.Person
                )

                ProfileField(
                    "Email",
                    email,
                    {
                        email = it
                    },
                    Icons.Default.Email,
                    KeyboardType.Email
                )

                ProfileField(
                    "City",
                    city,
                    {
                        city = it
                    },
                    Icons.Default.LocationOn
                )

                ProfileField(
                    "Occupation",
                    occupation,
                    {
                        occupation = it
                    },
                    Icons.Default.Work
                )

                ProfileField(
                    "Monthly budget",
                    budget,
                    {
                        budget = it
                    },
                    Icons.Default.AccountBalanceWallet,
                    KeyboardType.Number
                )

                ProfileField(
                    "Preferred stay",
                    preferredStay,
                    {
                        preferredStay = it
                    },
                    Icons.Default.Home
                )

                Spacer(
                    Modifier.height(10.dp)
                )

                Button(
                    onClick = {

                        onSave(
                            UserProfile(
                                name =
                                    name.trim(),
                                mobile =
                                    profile.mobile,
                                email =
                                    email.trim(),
                                city =
                                    city.trim(),
                                occupation =
                                    occupation.trim(),
                                budget =
                                    budget.trim(),
                                preferredStay =
                                    preferredStay.trim()
                            )
                        )

                        editing = false
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                    shape =
                        RoundedCornerShape(
                            17.dp
                        )
                ) {

                    Icon(
                        Icons.Default.Check,
                        null
                    )

                    Spacer(
                        Modifier.width(7.dp)
                    )

                    Text(
                        "Save Profile",
                        fontWeight =
                            FontWeight.Bold
                    )
                }

            } else {

                Spacer(
                    Modifier.height(8.dp)
                )

                ProfileInfo(
                    Icons.Default.Email,
                    "Email",
                    if (
                        email.isBlank()
                    )
                        "Add your email"
                    else
                        email
                )

                ProfileInfo(
                    Icons.Default.LocationOn,
                    "City",
                    if (
                        city.isBlank()
                    )
                        "Add your city"
                    else
                        city
                )

                ProfileInfo(
                    Icons.Default.Work,
                    "Occupation",
                    if (
                        occupation.isBlank()
                    )
                        "Add occupation"
                    else
                        occupation
                )

                ProfileInfo(
                    Icons.Default.AccountBalanceWallet,
                    "Monthly budget",
                    if (
                        budget.isBlank()
                    )
                        "Add your budget"
                    else
                        "₹$budget/month"
                )

                ProfileInfo(
                    Icons.Default.Home,
                    "Preferred stay",
                    if (
                        preferredStay.isBlank()
                    )
                        "PG / Private Room / 1RK"
                    else
                        preferredStay
                )

                Spacer(
                    Modifier.height(17.dp)
                )

                Text(
                    "Account preferences",
                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,
                    fontWeight =
                        FontWeight.ExtraBold
                )

                Spacer(
                    Modifier.height(8.dp)
                )

                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick =
                                    onTheme
                            ),
                    shape =
                        RoundedCornerShape(
                            19.dp
                        )
                ) {

                    Row(
                        modifier =
                            Modifier.padding(
                                17.dp
                            ),
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            if (darkMode)
                                Icons.Default.LightMode
                            else
                                Icons.Default.DarkMode,
                            null
                        )

                        Spacer(
                            Modifier.width(12.dp)
                        )

                        Text(
                            "Dark mode",
                            modifier =
                                Modifier.weight(
                                    1f
                                )
                        )

                        Text(
                            if (darkMode)
                                "ON"
                            else
                                "OFF",
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }

                Spacer(
                    Modifier.height(18.dp)
                )

                val completed =
                    listOf(
                        name,
                        email,
                        city,
                        occupation,
                        budget,
                        preferredStay
                    ).count {
                        it.isNotBlank()
                    }

                val completion =
                    completed / 6f

                Text(
                    "Profile completion",
                    fontWeight =
                        FontWeight.ExtraBold
                )

                Spacer(
                    Modifier.height(7.dp)
                )

                LinearProgressIndicator(
                    progress =
                        completion,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(
                                RoundedCornerShape(
                                    10.dp
                                )
                            )
                )

                Spacer(
                    Modifier.height(6.dp)
                )

                Text(
                    "${(completion * 100).toInt()}% complete"
                )
            }

            Spacer(
                Modifier.height(35.dp)
            )
        }
    }
}

/* =========================================================
   PROFILE COMPONENTS
   ========================================================= */

@Composable
private fun ProfileInfo(
    icon: ImageVector,
    title: String,
    value: String,
    verified: Boolean = false
) {

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 4.dp
                ),
        shape =
            RoundedCornerShape(19.dp)
    ) {

        Row(
            modifier =
                Modifier.padding(16.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(
                            RoundedCornerShape(
                                12.dp
                            )
                        )
                        .background(
                            MaterialTheme
                                .colorScheme
                                .primaryContainer
                        ),
                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    icon,
                    null,
                    tint =
                        MaterialTheme
                            .colorScheme
                            .primary
                )
            }

            Spacer(
                Modifier.width(12.dp)
            )

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    title,
                    style =
                        MaterialTheme
                            .typography
                            .bodySmall
                )

                Text(
                    value,
                    fontWeight =
                        FontWeight.SemiBold
                )
            }

            if (verified) {

                Icon(
                    Icons.Default.Verified,
                    null,
                    tint =
                        MaterialTheme
                            .colorScheme
                            .primary
                )
            }
        }
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    keyboardType: KeyboardType =
        KeyboardType.Text
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 5.dp
                ),
        label = {
            Text(label)
        },
        leadingIcon = {
            Icon(
                icon,
                null
            )
        },
        keyboardOptions =
            KeyboardOptions(
                keyboardType =
                    keyboardType
            ),
        singleLine = true,
        shape =
            RoundedCornerShape(
                17.dp
            )
    )
}

/* =========================================================
   SAFETY
   ========================================================= */

@Composable
private fun SafetyScreen(
    onBack: () -> Unit
) {

    var active by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(20.dp)
    ) {

        TopBar(
            "First Visit Mode",
            onBack
        )

        Card(
            modifier =
                Modifier.fillMaxWidth(),
            colors =
                CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme
                            .colorScheme
                            .primaryContainer
                ),
            shape =
                RoundedCornerShape(25.dp)
        ) {

            Column(
                modifier =
                    Modifier.padding(21.dp)
            ) {

                Icon(
                    Icons.Default.Security,
                    null,
                    modifier =
                        Modifier.size(47.dp),
                    tint =
                        MaterialTheme
                            .colorScheme
                            .primary
                )

                Spacer(
                    Modifier.height(9.dp)
                )

                Text(
                    "Visit safely.",
                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,
                    fontWeight =
                        FontWeight.ExtraBold
                )

                Text(
                    "A prototype safety layer for property visits."
                )

                Spacer(
                    Modifier.height(13.dp)
                )

                DetailRow(
                    "Meeting",
                    "HSR Layout"
                )

                DetailRow(
                    "Emergency contact",
                    "Configured"
                )

                DetailRow(
                    "Status",
                    if (active)
                        "Active"
                    else
                        "Not started"
                )
            }
        }

        Spacer(
            Modifier.height(18.dp)
        )

        Button(
            onClick = {
                active = !active
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(54.dp),
            shape =
                RoundedCornerShape(18.dp)
        ) {

            Text(
                if (active)
                    "I'm Safe ✓"
                else
                    "Start Safe Visit"
            )
        }
    }
}

/* =========================================================
   COMMON
   ========================================================= */

@Composable
private fun TopBar(
    title: String,
    onBack: () -> Unit
) {

    Surface(
        tonalElevation = 2.dp,
        shadowElevation = 1.dp
    ) {

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription =
                        "Back"
                )
            }

            Text(
                title,
                style =
                    MaterialTheme
                        .typography
                        .titleLarge,
                fontWeight =
                    FontWeight.ExtraBold
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String
) {

    Column(
        modifier =
            Modifier.padding(
                horizontal = 20.dp
            )
    ) {

        Text(
            title,
            style =
                MaterialTheme
                    .typography
                    .titleLarge,
            fontWeight =
                FontWeight.ExtraBold
        )

        Text(
            subtitle,
            style =
                MaterialTheme
                    .typography
                    .bodySmall,
            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
        )
    }
}

@Composable
private fun Tag(
    text: String
) {

    Surface(
        shape =
            RoundedCornerShape(10.dp),
        color =
            MaterialTheme
                .colorScheme
                .surfaceVariant
    ) {

        Text(
            text,
            modifier =
                Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 6.dp
                ),
            style =
                MaterialTheme
                    .typography
                    .labelSmall
        )
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 7.dp
                )
    ) {

        Text(
            label,
            modifier =
                Modifier.weight(1f),
            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
        )

        Text(
            value,
            fontWeight =
                FontWeight.SemiBold
        )
    }
}

@Composable
private fun SmallFloatingBadge(
    modifier: Modifier,
    icon: ImageVector,
    text: String
) {

    Surface(
        modifier = modifier,
        shape =
            RoundedCornerShape(14.dp),
        color =
            Color.White.copy(
                alpha = .93f
            ),
        shadowElevation = 8.dp
    ) {

        Row(
            modifier =
                Modifier.padding(
                    horizontal = 10.dp,
                    vertical = 8.dp
                ),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                icon,
                null,
                modifier =
                    Modifier.size(16.dp),
                tint =
                    Color(0xFF087F73)
            )

            Spacer(
                Modifier.width(5.dp)
            )

            Text(
                text,
                style =
                    MaterialTheme
                        .typography
                        .labelSmall,
                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}

@Composable
private fun AIButton(
    onClick: () -> Unit
) {

    val infinite =
        rememberInfiniteTransition(
            label = "ai"
        )

    val scale by infinite.animateFloat(
        initialValue = .94f,
        targetValue = 1.06f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(900),
                repeatMode =
                    RepeatMode.Reverse
            ),
        label = "aiPulse"
    )

    FloatingActionButton(
        onClick = onClick,
        modifier =
            Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        containerColor =
            Color(0xFF087F73)
    ) {

        Icon(
            Icons.Default.AutoAwesome,
            contentDescription =
                "GrihaCare AI",
            tint = Color.White
        )
    }
}