package com.example.townapp.data.repository

import com.example.townapp.domain.engine.PlayerState

/**
 * 🏠 空间数据适配器
 * 
 * 将空间配置数据转换为可接入模拟引擎的格式
 * 空间不再是背景板，而是真正影响玩家状态
 */
data class SpaceConfig(
    val id: String,
    val name: String,
    val price: Double,
    val area: Int,
    // 状态影响系数
    val stateEffect: SpaceStateEffect = SpaceStateEffect(),
    // 事件触发权重
    val eventWeight: SpaceEventWeight = SpaceEventWeight()
)

/**
 * 空间状态影响
 */
data class SpaceStateEffect(
    val spiritLight: Double = 1.0,      // 采光对精神的影响 (0.5-1.5)
    val spiritNoise: Double = 0.0,      // 噪音对精神的影响 (-10 到 0)
    val bodySleepQuality: Double = 1.0  // 睡眠质量系数 (0.5-1.5)
)

/**
 * 空间事件触发权重
 */
data class SpaceEventWeight(
    val insomnia: Double = 0.1,         // 失眠事件触发权重
    val lowMood: Double = 0.1,          // 情绪低落事件触发权重
    val healthIssue: Double = 0.05      // 健康问题事件触发权重
)

/**
 * 空间配置仓库
 */
object SpaceConfigRepository {
    private val spaces = listOf(
        // 900元暗房
        SpaceConfig(
            id = "room_900",
            name = "900元暗房",
            price = 900.0,
            area = 15,
            stateEffect = SpaceStateEffect(
                spiritLight = 0.6,      // 采光差
                spiritNoise = -5.0,     // 噪音大
                bodySleepQuality = 0.7  // 睡眠质量低
            ),
            eventWeight = SpaceEventWeight(
                insomnia = 0.3,
                lowMood = 0.2,
                healthIssue = 0.1
            )
        ),
        // 1200元普通单间
        SpaceConfig(
            id = "room_1200",
            name = "1200元普通单间",
            price = 1200.0,
            area = 20,
            stateEffect = SpaceStateEffect(
                spiritLight = 0.85,
                spiritNoise = -2.0,
                bodySleepQuality = 0.9
            ),
            eventWeight = SpaceEventWeight(
                insomnia = 0.15,
                lowMood = 0.1,
                healthIssue = 0.05
            )
        ),
        // 1500元朝南单间
        SpaceConfig(
            id = "room_1500",
            name = "1500元朝南单间",
            price = 1500.0,
            area = 25,
            stateEffect = SpaceStateEffect(
                spiritLight = 1.1,       // 采光好
                spiritNoise = -1.0,      // 安静
                bodySleepQuality = 1.15  // 睡眠质量高
            ),
            eventWeight = SpaceEventWeight(
                insomnia = 0.05,
                lowMood = 0.05,
                healthIssue = 0.02
            )
        ),
        // 2000元一居室
        SpaceConfig(
            id = "room_2000",
            name = "2000元一居室",
            price = 2000.0,
            area = 35,
            stateEffect = SpaceStateEffect(
                spiritLight = 1.25,
                spiritNoise = -0.5,
                bodySleepQuality = 1.3
            ),
            eventWeight = SpaceEventWeight(
                insomnia = 0.02,
                lowMood = 0.02,
                healthIssue = 0.01
            )
        ),
        // 3000元两居室
        SpaceConfig(
            id = "room_3000",
            name = "3000元两居室",
            price = 3000.0,
            area = 50,
            stateEffect = SpaceStateEffect(
                spiritLight = 1.4,
                spiritNoise = 0.0,
                bodySleepQuality = 1.45
            ),
            eventWeight = SpaceEventWeight(
                insomnia = 0.01,
                lowMood = 0.01,
                healthIssue = 0.005
            )
        )
    )

    fun getAllSpaces(): List<SpaceConfig> = spaces
    
    fun getSpaceById(id: String): SpaceConfig? = spaces.find { it.id == id }
    
    fun getSpaceByPrice(price: Double): SpaceConfig? {
        return spaces.minByOrNull { kotlin.math.abs(it.price - price) }
    }
}

/**
 * 空间数据适配器 - 实现ISimulatableData接口
 */
class SpaceDataAdapter(private val space: SpaceConfig) : ISimulatableData {
    override val id: String = space.id
    override val name: String = space.name

    override fun canTrigger(currentState: PlayerState): Boolean {
        // 空间影响持续生效
        return SimulationDataSwitch.enableSpaceEffects
    }

    override fun getStateEffect(): StateEffect {
        val effect = space.stateEffect
        
        // 计算空间对状态的影响
        return StateEffect(
            // 采光影响精神状态
            happiness = effect.spiritLight * 2 - 2,  // -1 到 1
            // 噪音增加焦虑
            anxiety = effect.spiritNoise * 0.1,
            // 睡眠质量影响精力恢复（在睡眠时应用）
            // 这里返回基础影响，实际睡眠时会乘以这个系数
            energy = effect.bodySleepQuality * 5 - 5  // -2.5 到 2.5
        )
    }

    override fun getDisplayText(): String {
        val effect = space.stateEffect
        val lightDesc = when {
            effect.spiritLight < 0.7 -> "采光较差"
            effect.spiritLight < 0.9 -> "采光一般"
            effect.spiritLight >= 1.1 -> "采光很好"
            else -> "采光适中"
        }
        val noiseDesc = when {
            effect.spiritNoise < -3 -> "噪音较大"
            effect.spiritNoise < -1 -> "略有噪音"
            effect.spiritNoise >= 0 -> "非常安静"
            else -> "环境安静"
        }
        return "$lightDesc，$noiseDesc，适合休息"
    }

    override fun getTriggerWeight(): Int {
        return 100  // 空间影响权重最高
    }
}
