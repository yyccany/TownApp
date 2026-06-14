package com.example.townapp.ui.design

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object TownAestheticDesign {

    object Composition {
        // 黄金分割比例
        const val GOLDEN_RATIO = 1.618f
        const val GOLDEN_RATIO_RECIPROCAL = 0.618f
        
        // 三分法比例
        const val THIRD_RATIO = 0.333f
        const val TWO_THIRDS_RATIO = 0.666f
        
        // 构图类型
        enum class CompositionType {
            CENTER,
            RULE_OF_THIRDS,
            GOLDEN_SPIRAL,
            TRIANGLE,
            DIAGONAL,
            FOREGOUND,
            SYMMETRIC
        }
    }

    object LightShadow {
        // 阴影强度
        enum class ShadowIntensity(val elevation: Float, val blur: Float) {
            SOFT(2f, 8f),
            MEDIUM(4f, 12f),
            HARD(6f, 16f),
            DRAMATIC(10f, 24f)
        }
        
        // 光线方向
        enum class LightDirection {
            TOP_LEFT,
            TOP,
            TOP_RIGHT,
            LEFT,
            CENTER,
            RIGHT,
            BOTTOM_LEFT,
            BOTTOM,
            BOTTOM_RIGHT
        }
        
        // 环境光颜色
        val ambientLight = Color(0x1AFFFFFF)
        val shadowColor = Color(0x40000000)
        val highlightColor = Color(0x80FFFFFF)
    }

    object ColorPalette {
        val primary = Color(0xFF2C3E50)
        val primaryLight = Color(0xFF34495E)
        val primaryDark = Color(0xFF1A252F)
        
        val accent = Color(0xFF3498DB)
        val accentLight = Color(0xFF5DADE2)
        val accentDark = Color(0xFF2980B9)
        
        val success = Color(0xFF27AE60)
        val warning = Color(0xFFF39C12)
        val danger = Color(0xFFE74C3C)
        val info = Color(0xFF17A2B8)
        
        val background = Color(0xFFF8FAFC)
        val surface = Color(0xFFFFFFFF)
        val surfaceElevated = Color(0xFFFEFFFF)
        
        val textPrimary = Color(0xFF1A202C)
        val textSecondary = Color(0xFF4A5568)
        val textTertiary = Color(0xFF718096)
        val textDisabled = Color(0xFFA0AEC0)
        
        val border = Color(0xFFE2E8F0)
        val borderLight = Color(0xFFEDF2F7)
        
        val gold = Color(0xFFD4AF37)
        val silver = Color(0xFFC0C0C0)
        val bronze = Color(0xFFCD7F32)
        
        // 渐变背景色
        val gradientStart = Color(0xFFFAFBFC)
        val gradientEnd = Color(0xFFF2F4F7)
        
        // 光影效果色
        val spotlight = Color(0x40FFD700)
        val vignette = Color(0x60000000)

        fun getBuildingColor(buildingType: BuildingType): Color {
            return when (buildingType) {
                BuildingType.FOOD_NEGATIVE -> danger
                BuildingType.FOOD_POSITIVE -> success
                BuildingType.CLOTHING_NEGATIVE -> warning
                BuildingType.CLOTHING_POSITIVE -> accent
                BuildingType.HOUSING_NEGATIVE -> Color(0xFF7B2CBF)
                BuildingType.HOUSING_POSITIVE -> Color(0xFF10B981)
                BuildingType.MENTAL_NEGATIVE -> Color(0xFF8B5CF6)
                BuildingType.MENTAL_POSITIVE -> Color(0xFF06B6D4)
            }
        }

        fun getDistrictColor(district: TownDistrict): Color {
            return when (district) {
                TownDistrict.DIGESTIVE_TRACT -> Color(0xFFF97316)
                TownDistrict.SKIN_KINGDOM -> Color(0xFFEC4899)
                TownDistrict.DWELLING_REALM -> Color(0xFF84CC16)
                TownDistrict.SPIRIT_DOMAIN -> Color(0xFF8B5CF6)
            }
        }
    }

    object Typography {
        const val fontFamily = "PingFang SC"
        
        object Size {
            val xs = 10.sp
            val sm = 12.sp
            val base = 14.sp
            val lg = 16.sp
            val xl = 18.sp
            val xxl = 20.sp
            val xxxl = 24.sp
            val xxxxl = 30.sp
            val xxxxxl = 36.sp
            val xxxxxxl = 48.sp

            // 以下别名兼容现有代码中的引用
            val `2xl` get() = xxl
            val `3xl` get() = xxxl
            val `4xl` get() = xxxxl
            val `5xl` get() = xxxxxl
            val `6xl` get() = xxxxxxl
        }
        
        object Weight {
            const val light = "300"
            const val normal = "400"
            const val medium = "500"
            const val semibold = "600"
            const val bold = "700"
            const val extraBold = "800"
        }
    }

    object Spacing {
        val none = 0.dp
        val xs = 4.dp
        val sm = 8.dp
        val base = 12.dp
        val md = 16.dp
        val lg = 24.dp
        val xl = 32.dp
        val xxl = 48.dp
        val xxxl = 64.dp
    }

    object Elevation {
        val none = 0.dp
        val sm = 2.dp
        val md = 4.dp
        val lg = 8.dp
        val xl = 12.dp
        val xxl = 16.dp
        val xxxl = 24.dp
    }

    object CornerRadius {
        val none = 0.dp
        val sm = 4.dp
        val base = 8.dp
        val md = 12.dp
        val lg = 16.dp
        val xl = 24.dp
        val full = 9999.dp
    }

    object Animation {
        const val durationFast = 150
        const val durationNormal = 300
        const val durationSlow = 500
        const val durationExtraSlow = 700
        
        const val springStiffness = 300f
        const val springDamping = 30f
    }

    object Icons {
        const val home = "🏠"
        const val food = "🍽️"
        const val clothing = "👕"
        const val housing = "🏢"
        const val mental = "🧠"
        const val aesthetic = "🎨"
        const val awakening = "✨"
        const val trash = "🗑️"
        const val compare = "⚖️"
        const val book = "📚"
        const val award = "🏆"
        const val arrowUp = "↑"
        const val arrowDown = "↓"
    }

    object BuildingDesign {
        data class VisualStyle(
            val color: Color,
            val icon: String,
            val glowEffect: Boolean = false,
            val animationEnabled: Boolean = true
        )

        fun getBuildingStyle(buildingId: String): VisualStyle {
            return when {
                buildingId.contains("pos") -> VisualStyle(
                    color = ColorPalette.success,
                    icon = "🌟",
                    glowEffect = true
                )
                buildingId.contains("neg") -> VisualStyle(
                    color = ColorPalette.danger,
                    icon = "💀",
                    glowEffect = false
                )
                else -> VisualStyle(
                    color = ColorPalette.accent,
                    icon = "🏛️"
                )
            }
        }
    }

    object DistrictTheme {
        data class Theme(
            val name: String,
            val primaryColor: Color,
            val secondaryColor: Color,
            val backgroundGradient: List<Color>,
            val icon: String,
            val atmosphere: String
        )

        fun getDistrictTheme(district: TownDistrict): Theme {
            return when (district) {
                TownDistrict.DIGESTIVE_TRACT -> Theme(
                    name = "消化道",
                    primaryColor = Color(0xFFF97316),
                    secondaryColor = Color(0xFFFB923C),
                    backgroundGradient = listOf(Color(0xFFFFFBF5), Color(0xFFFFFAF0)),
                    icon = "🍽️",
                    atmosphere = "温暖、活力"
                )
                TownDistrict.SKIN_KINGDOM -> Theme(
                    name = "皮肤国",
                    primaryColor = Color(0xFFEC4899),
                    secondaryColor = Color(0xFFF472B6),
                    backgroundGradient = listOf(Color(0xFFFDF2F8), Color(0xFFFCE7F3)),
                    icon = "👗",
                    atmosphere = "优雅、精致"
                )
                TownDistrict.DWELLING_REALM -> Theme(
                    name = "栖息界",
                    primaryColor = Color(0xFF84CC16),
                    secondaryColor = Color(0xFFA3E635),
                    backgroundGradient = listOf(Color(0xFFF0FDF4), Color(0xFFECFDF5)),
                    icon = "🏠",
                    atmosphere = "舒适、宁静"
                )
                TownDistrict.SPIRIT_DOMAIN -> Theme(
                    name = "精神域",
                    primaryColor = Color(0xFF8B5CF6),
                    secondaryColor = Color(0xFFA78BFA),
                    backgroundGradient = listOf(Color(0xFFFAF5FF), Color(0xFFF5F3FF)),
                    icon = "🧠",
                    atmosphere = "深邃、智慧"
                )
            }
        }
    }

    object WeatherEffects {
        data class Effect(
            val overlayColor: Color,
            val opacity: Float,
            val particleEffect: String,
            val ambientSound: String? = null
        )

        fun getWeatherEffect(weather: WeatherState): Effect {
            return when (weather) {
                WeatherState.SUNNY -> Effect(
                    overlayColor = Color(0x33FFD700),
                    opacity = 0.1f,
                    particleEffect = "sunshine"
                )
                WeatherState.CLOUDY -> Effect(
                    overlayColor = Color(0x449CA3AF),
                    opacity = 0.15f,
                    particleEffect = "clouds"
                )
                WeatherState.OVERCAST -> Effect(
                    overlayColor = Color(0x556B7280),
                    opacity = 0.2f,
                    particleEffect = "overcast"
                )
                WeatherState.HAZY -> Effect(
                    overlayColor = Color(0x66D1D5DB),
                    opacity = 0.3f,
                    particleEffect = "smog"
                )
                WeatherState.RAINY -> Effect(
                    overlayColor = Color(0x443B82F6),
                    opacity = 0.25f,
                    particleEffect = "rain",
                    ambientSound = "rain"
                )
                WeatherState.STORM -> Effect(
                    overlayColor = Color(0x551E1B4B),
                    opacity = 0.4f,
                    particleEffect = "storm",
                    ambientSound = "thunder"
                )
            }
        }
    }

    enum class BuildingType {
        FOOD_NEGATIVE,
        FOOD_POSITIVE,
        CLOTHING_NEGATIVE,
        CLOTHING_POSITIVE,
        HOUSING_NEGATIVE,
        HOUSING_POSITIVE,
        MENTAL_NEGATIVE,
        MENTAL_POSITIVE
    }

    enum class TownDistrict {
        DIGESTIVE_TRACT,
        SKIN_KINGDOM,
        DWELLING_REALM,
        SPIRIT_DOMAIN
    }

    enum class WeatherState {
        SUNNY,
        CLOUDY,
        OVERCAST,
        HAZY,
        RAINY,
        STORM
    }
}