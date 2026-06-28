---
name: vera-add-content-type
description: >-
  This skill guides the process of adding a new content type (independent Tab) to the
  Vera Cognition Dictionary Android app. It covers the full workflow: creating a new
  data model + library singleton, creating the JSON data file, building a detail screen
  with VeraColorScheme-compatible icons, adding a HomeTab entry, wiring navigation in
  MainActivity, and adding tab bar colors. Use this skill when the user asks to add a
  new section, category, or content type to Vera that is NOT a traditional idiom entry
  (e.g., "主体性工具", "情感工具箱", "职场工具").
agent_created: true
---

# Vera Add Content Type

## Overview

This skill provides the step-by-step workflow for adding a new content type as an
independent Tab in the Vera Cognition Dictionary app. Each content type has its own
data model, JSON data file, detail screen, and home tab entry — but shares the
existing design system (DictionaryTokens, VeraColorScheme, StandardTopBar).

## When to Use

- User requests adding a new section/category to Vera that is NOT an idiom entry
- User wants a new Tab with a different data structure than `IdiomCritique`
- User asks to create "工具"/"模块"/"板块" with custom fields and rendering

## Prerequisites

- Vera project at `D:\TownApp` with Jetpack Compose + Material3
- Existing patterns: `IdiomCritiqueLibrary` (singleton), `IdiomAssetLoader` (JSON parser),
  `DictionaryHomeScreen` (tab + list), `IdiomCriticScreen` (detail page)
- `VeraColorScheme.kt` for theme-aware colors
- `DictionaryTokens` in `Theme.kt` for typography/spacing

## Workflow

### Step 1: Create Data Model + Library

Create `app/src/main/java/com/example/townapp/data/<type>/<Type>.kt`:

1. Define a `data class` with fields specific to the content type
2. Create a `Library` singleton class following the `IdiomCritiqueLibrary` pattern:
   - `@Volatile private var instance`
   - `fun initialize(context: Context)` — call from `MainActivity.onCreate()`
   - `fun getAll(): List<T>` and `fun getById(id: String): T?`
   - Private `loadTools()` method reading from `assets/<type>/data.json` using `org.json.JSONArray`

### Step 2: Create JSON Data File

Create `app/src/main/assets/<type>/data.json`:

- Top-level JSON array of objects
- Each object must have an `id` field (unique snake_case string)
- Fields must match the data class properties
- Use `\n\n` for paragraph separation within text fields

### Step 3: Create Detail Screen

Create `app/src/main/java/com/example/townapp/ui/screens/<Type>Screen.kt`:

1. `@Composable fun <Type>Screen(toolId: String, onBack, onMenuClick, onSearchClick)`
2. Use `StandardTopBar` with `menuIcon = "back"`
3. `LazyColumn` with `Card` sections, each using `DictionaryTokens` for spacing/typography
4. Icons: create a private `@Composable` Canvas icon function with `1.8f * density` stroke
5. Colors: read from `LocalVeraColors.current.iconLine` / `.iconCheck` / `.iconWarning` etc.
6. Handle null tool with a fallback error state

### Step 4: Add HomeTab Entry

In `DictionaryHomeScreen.kt`:

1. Add `is<Type>: Boolean = false` flag to `HomeTab` data class
2. Add `HomeTab("<type_id>", "<label>", is<Type> = true, iconType = "<icon_name>")` to `homeTabs` list
3. Add a new `when` branch in the content rendering:
   - When `currentTab.is<Type>` is true, load from the new library
   - Render `<Type>CardItem` instead of `IdiomCardItem`
4. Add a `@Composable fun <Type>CardItem(item: <Type>, onClick: () -> Unit)` with same visual specs as `IdiomCardItem` (16dp radius, 4dp elevation, surface color)
5. Add the new icon case to `TabCategoryIcon` `when` block (2f stroke)

### Step 5: Wire Navigation in MainActivity

In `MainActivity.kt`:

1. `onCreate()`: call `<Type>Library.initialize(this)` alongside `IdiomCritiqueLibrary.initialize()`
2. Add `var selected<Type>Id by remember { mutableStateOf<String?>(null) }`
3. Add navigation callback: `val onNavigateTo<Type>: (String) -> Unit = { id -> selectedIdiomId = null; selected<Type>Id = id }`
4. In the `when (currentScreen)` → `"home"` block, add priority check:
   ```kotlin
   when {
       selected<Type>Id != null -> {
           val id = selected<Type>Id!!
           <Type>Screen(toolId = id, onBack = { selected<Type>Id = null }, ...)
       }
       selectedIdiomId != null -> { ... }  // existing
       else -> { ... }  // existing
   }
   ```
5. Update `gesturesEnabled` to also check `selected<Type>Id == null`
6. Update `onDrawerItemClick` to also clear `selected<Type>Id = null`
7. Pass `onNavigateTo<Type>` to `DictionaryHomeScreen`

### Step 6: Add Tab Bar Color

In `Theme.kt` → `DictionaryTokens.tabBarColor()`:
- Add `"<type_id>" -> TabBar.grayBlue` (or another color from `TabBar` object)

### Step 7: Build and Verify

```bash
cd /d/TownApp && ./gradlew assembleDebug --no-daemon -q 2>&1 | tail -20
```

Common compilation issues:
- **Smart cast error**: `var` properties can't be smart-cast. Use `val id = selectedId!!`
- **ExperimentalLayoutApi**: Add `@OptIn(ExperimentalLayoutApi::class)` for `FlowRow`
- **Missing imports**: `Size`, `Path`, `StrokeJoin`, `StrokeCap` from `androidx.compose.ui.graphics.*`

## Key Design Rules

- All icons use `1.8f * density` stroke width (consistent with IdiomCriticScreen)
- All colors come from `LocalVeraColors.current.*` — never hardcode `Color(0xFF...)`
- Card sections use `DictionaryTokens.radiusCard` (16dp), `DictionaryTokens.elevationCard` (4dp)
- Text hierarchy: `DictionaryTokens.Type.titleSizeLarge` (28sp) / `headingSize` (17sp Bold) / `bodySize` (14sp) / `captionSize` (13sp)
- Section spacing: `DictionaryTokens.sectionSpacing` (16dp) between cards
- Page padding: `DictionaryTokens.pagePadding` (24dp) horizontal
- Bottom safe padding: `DictionaryTokens.bottomSafePadding` (40dp)
