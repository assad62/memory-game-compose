# Memory Game 🧠

A modern Android memory game built with Jetpack Compose, featuring smooth flip animations, timer-based gameplay, and a clean, maintainable architecture.

## 🎮 Game Features

### Core Gameplay
- **Card Matching**: Match pairs of identical icons to score points
- **Timer Challenge**: Complete the game within 2 minutes (120 seconds)
- **Score System**: Earn 10 points for each successful match
- **Progressive Difficulty**: Cards are removed from the board when matched, reducing visual clutter
- **Win/Lose Conditions**: Win by matching all pairs, lose if time runs out

### Visual Features
- **Smooth Flip Animation**: Cards flip with a 3D rotation effect
- **Material Design 3**: Modern UI following Material Design guidelines
- **Responsive Grid**: 3x4 grid layout that adapts to different screen sizes
- **Visual Feedback**: Different colors for matched cards and time warnings
- **Card States**: Clear visual distinction between hidden and revealed cards

## 🏗️ Architecture Overview

The project follows a clean, modular architecture with clear separation of concerns:

```
app/src/main/java/com/mohammadassad/memorygame/
├── MainActivity.kt              # Main Activity (21 lines)
├── GameConstants.kt             # Configuration constants
├── GameModels.kt               # Data models (CardData, GameStatus)
├── MemoryGameState.kt          # Game logic and state management
└── ui/
    ├── MemoryGameScreen.kt     # Main game screen composable
    ├── GameUIComponents.kt     # Reusable UI components
    └── FlipAnimation.kt        # Card flip animation logic
```

## 🎯 Key Implementation Details

### 1. Flip Animation System

The flip animation is implemented using Jetpack Compose's `animateFloatAsState` with a custom `FlipAnimation` composable:

```kotlin
@Composable
fun FlipAnimation(
    isFlipped: Boolean,
    onFlip: () -> Unit,
    modifier: Modifier = Modifier,
    durationMillis: Int = 600,
    frontSide: @Composable () -> Unit,
    backSide: @Composable () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing
        ),
        label = "flip"
    )
    // 3D rotation implementation...
}
```

**How it works:**
- Uses `graphicsLayer` with `rotationY` for 3D rotation effect
- `cameraDistance` creates perspective for realistic 3D appearance
- Smooth transition between front and back sides at 90° rotation
- Customizable duration and easing for different feel

### 2. Timer System

The game implements a countdown timer using `LaunchedEffect` and coroutines:

```kotlin
LaunchedEffect(gameState.gameStatus) {
    while (gameState.gameStatus == GameStatus.PLAYING) {
        delay(GameConstants.TIMER_INTERVAL_MS) // 1000ms
        gameState.decrementTime()
    }
}
```

**Features:**
- Updates every second using `delay(1000)`
- Automatically stops when game ends
- Visual warning when time drops below 30 seconds
- Time format: MM:SS display

### 3. Game State Management

The `MemoryGameState` class manages all game logic using Compose state:

```kotlin
class MemoryGameState {
    var cards by mutableStateOf<List<CardData>>(emptyList())
    var selectedCards by mutableStateOf<List<CardData>>(emptyList())
    var score by mutableStateOf(0)
    var timeLeft by mutableStateOf(GameConstants.GAME_TIME_SECONDS)
    var gameStatus by mutableStateOf(GameStatus.PLAYING)
    var isCheckingMatch by mutableStateOf(false)
}
```

**State Management Features:**
- Reactive UI updates through `mutableStateOf`
- Immutable state updates for consistency
- Private setters with controlled mutation
- Clear separation of game logic from UI

### 4. Card Matching Logic

The matching system includes sophisticated validation:

```kotlin
suspend fun checkMatch() {
    if (selectedCards.size != 2) return
    
    isCheckingMatch = true
    val (first, second) = selectedCards
    
    delay(GameConstants.MATCH_DELAY_MS) // 800ms
    
    if (isMatch(first, second)) {
        handleMatch(first, second)
    }
    
    clearSelection()
}
```

**Matching Features:**
- Prevents multiple simultaneous matches
- Visual delay for better UX
- Removes matched cards from the board
- Automatic win detection when all cards matched

## ⚙️ Customization Options

### 1. Game Configuration (`GameConstants.kt`)

Easily modify game parameters:

```kotlin
object GameConstants {
    // Game timing
    const val GAME_TIME_SECONDS = 120        // Total game time
    const val MATCH_DELAY_MS = 800L          // Delay before match check
    const val TIMER_INTERVAL_MS = 1000L      // Timer update frequency
    
    // Scoring
    const val MATCH_SCORE = 10               // Points per match
    
    // UI Layout
    const val GRID_COLUMNS = 3               // Number of grid columns
    const val CARD_ICON_SIZE_DP = 48         // Card icon size
    const val GRID_SPACING_DP = 8            // Space between cards
    
    // Visual feedback
    const val TIME_WARNING_THRESHOLD = 30    // Time warning threshold
    const val FLIP_ANIMATION_DURATION_MS = 400 // Animation speed
}
```

### 2. Game Icons

Customize the card icons by modifying `GAME_ICONS`:

```kotlin
val GAME_ICONS = listOf(
    Icons.Filled.Star,
    Icons.Filled.Favorite,
    Icons.Filled.Home,
    Icons.Filled.Face,
    Icons.Filled.Person,
    Icons.Filled.Email
    // Add more icons here
)
```

### 3. Visual Styling

Customize colors, sizes, and animations:

```kotlin
// In GameUIComponents.kt
@Composable
fun GameTimer(timeLeft: Int) {
    val timeColor = if (timeLeft <= GameConstants.TIME_WARNING_THRESHOLD) 
        MaterialTheme.colorScheme.error  // Red when time is low
    else 
        MaterialTheme.colorScheme.onSurface  // Normal color
    // ...
}
```

### 4. Grid Layout

Modify the grid structure:

```kotlin
// In GameUIComponents.kt
LazyVerticalGrid(
    columns = GridCells.Fixed(GameConstants.GRID_COLUMNS), // Change to 4 for 4x3 grid
    // ...
)
```

### 5. Animation Customization

Adjust flip animation properties:

```kotlin
// In FlipAnimation.kt
val rotation by animateFloatAsState(
    targetValue = if (isFlipped) 180f else 0f,
    animationSpec = tween(
        durationMillis = durationMillis, // Customizable duration
        easing = FastOutSlowInEasing     // Change easing function
    ),
    label = "flip"
)
```

---

**Happy Gaming! 🎮**

For questions or suggestions, please open an issue or create a pull request.
# memory-game-compose
