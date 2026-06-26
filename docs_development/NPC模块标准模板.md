# NPC 模块标准架构模板（基于认知觉醒样板 1:1 对标）

> 本文件是 NPC 模块落地的**完整标准模板**，严格对标认知觉醒模块（CognitionAwakeningScreen / UserCognitiveCard / CognitiveTipRepository / DataIntegrationManager），按此模板实现即可保证全项目规范统一。

---

## 一、模块文件结构

```
app/src/main/java/com/example/townapp/npc/
├── ui/
│   ├── NpcTownMapScreen.kt          # 像素小镇NPC地图（对标 CognitionAwakeningScreen）
│   ├── NpcDialogComponent.kt        # NPC对话弹窗组件（对标 CognitiveScalpel）
│   └── NpcStatusBar.kt              # NPC状态条（好感/职业/季节标签）
├── model/
│   ├── NpcBase.kt                   # NPC基础信息实体（对标 UserCognitiveCard）
│   ├── NpcStatus.kt                 # NPC动态状态实体（对标 CognitiveBias）
│   └── NpcDialogTrigger.kt          # NPC对话触发条件
├── repository/
│   ├── NpcRepository.kt             # NPC仓库，所有查询收口（对标 CognitiveTipRepository）
│   └── NpcTextLoader.kt             # NPC文本读取，复用 TextAssetLoader
├── viewmodel/
│   └── NpcViewModel.kt              # 业务逻辑，不碰数据库
└── NpcDataLayer.kt                  # 对外暴露入口（对标 DataIntegrationManager 集成方式）
```

```
app/src/main/assets/text/npc/
├── dialog/
│   ├── spring.json                  # 春季对话
│   ├── summer.json                  # 夏季对话
│   ├── autumn.json                  # 秋季对话
│   ├── winter.json                  # 冬季对话
│   └── universal.json               # 通用对话（跨季节）
├── job/
│   ├── teacher.json                 # 教师职业对话
│   ├── doctor.json                  # 医生职业对话
│   ├── worker.json                  # 工人职业对话
│   ├── farmer.json                  # 农民职业对话
│   └── shopkeeper.json              # 店主职业对话
└── favor/
    ├── low_favor.json               # 低好感对话（< 30）
    ├── mid_favor.json               # 中好感对话（30-70）
    └── high_favor.json              # 高好感对话（> 70）
```

---

## 二、四层结构完整代码模板

### 1. UI 层（只画画、只接收状态）

**NpcTownMapScreen.kt**
```kotlin
package com.example.townapp.npc.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.townapp.npc.viewmodel.NpcViewModel

/**
 * 像素小镇NPC地图主屏幕
 * 严格遵循：UI层只拿数据、只渲染，不碰数据库、不写查询条件
 */
@Composable
fun NpcTownMapScreen(
    viewModel: NpcViewModel = viewModel()
) {
    val npcList = viewModel.visibleNpcList.collectAsState()
    val selectedNpc = viewModel.selectedNpc.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // 顶部：NPC列表（默认隐藏，交互才展开）
        if (viewModel.isListVisible.collectAsState().value) {
            NpcListPanel(
                npcs = npcList.value,
                onSelect = { viewModel.selectNpc(it) }
            )
        }

        // 主体：当前选中NPC的对话区
        selectedNpc.value?.let { npc ->
            NpcDialogComponent(
                npc = npc,
                dialogText = viewModel.currentDialogText.collectAsState().value,
                onClose = { viewModel.closeDialog() }
            )
        }
    }
}
```

**NpcDialogComponent.kt**
```kotlin
package com.example.townapp.npc.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.townapp.npc.model.NpcBase
import com.example.townapp.npc.model.NpcStatus

/**
 * NPC对话弹窗组件
 * 纯UI组件，不做任何业务逻辑
 */
@Composable
fun NpcDialogComponent(
    npc: NpcBase,
    dialogText: String,
    onClose: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("${npc.name}（${npc.jobName}）") },
        text = { Text(dialogText) },
        confirmButton = { TextButton(onClick = onClose) { Text("知道了") } }
    )
}
```

---

### 2. ViewModel 层（只做业务逻辑，不碰数据库）

**NpcViewModel.kt**
```kotlin
package com.example.townapp.npc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.townapp.npc.model.NpcBase
import com.example.townapp.npc.model.NpcStatus
import com.example.townapp.npc.repository.NpcRepository
import com.example.townapp.npc.NpcDataLayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * NPC业务逻辑层
 * 严格遵循：
 * - 不导入任何 Dao
 * - 不写 SQL 筛选条件
 * - 所有数据需求通过 NpcDataLayer（统一入口）获取
 */
class NpcViewModel(
    private val dataLayer: NpcDataLayer = NpcDataLayer.instance
) : ViewModel() {

    private val repo: NpcRepository = dataLayer.npcRepository

    // ============== 状态输出（仅供UI订阅） ==============
    private val _visibleNpcList = MutableStateFlow<List<NpcBase>>(emptyList())
    val visibleNpcList: StateFlow<List<NpcBase>> = _visibleNpcList

    private val _selectedNpc = MutableStateFlow<NpcBase?>(null)
    val selectedNpc: StateFlow<NpcBase?> = _selectedNpc

    private val _currentDialogText = MutableStateFlow("")
    val currentDialogText: StateFlow<String> = _currentDialogText

    private val _isListVisible = MutableStateFlow(false)
    val isListVisible: StateFlow<Boolean> = _isListVisible

    // ============== 业务逻辑（计算消耗、判断流程） ==============
    fun loadTownNpcs(seasonId: Int) {
        viewModelScope.launch {
            // 业务逻辑：过滤当前季节可见NPC
            _visibleNpcList.value = repo.getNpcsBySeason(seasonId)
        }
    }

    fun selectNpc(npc: NpcBase) {
        _selectedNpc.value = npc
        loadDialogForNpc(npc)
    }

    private fun loadDialogForNpc(npc: NpcBase) {
        viewModelScope.launch {
            // 业务逻辑：综合季节+职业+好感 → 生成对话key → 读取文本
            val status = repo.getNpcStatus(npc.id)
            val dialogKey = buildDialogKey(npc.jobId, status.currentSeasonId, status.favor)
            _currentDialogText.value = repo.getDialogText(dialogKey)
        }
    }

    private fun buildDialogKey(jobId: Int, seasonId: Int, favor: Int): String {
        // 业务逻辑：好感分级，决定对话深度
        val favorTier = when {
            favor < 30 -> "low"
            favor < 70 -> "mid"
            else -> "high"
        }
        return "job_${jobId}_season_${seasonId}_${favorTier}"
    }

    fun closeDialog() {
        _selectedNpc.value = null
        _currentDialogText.value = ""
    }

    fun toggleList() {
        _isListVisible.value = !_isListVisible.value
    }
}
```

---

### 3. Repository 层（所有查询、条件收口在此）

**NpcRepository.kt**
```kotlin
package com.example.townapp.npc.repository

import com.example.townapp.npc.model.NpcBase
import com.example.townapp.npc.model.NpcStatus
import com.example.townapp.npc.repository.NpcTextLoader

/**
 * NPC仓库
 * 严格遵循：
 * - 唯一负责拼接所有多条件查询
 * - 唯一负责调用 Dao
 * - 唯一负责读取静态文本
 * - 上层（ViewModel/UI）一律不准再写 where 条件
 */
class NpcRepository(
    private val dao: NpcDao,                  // 注入Dao，但只有Repo能碰
    private val textLoader: NpcTextLoader     // 注入文本加载器
) {
    // ============== 业务查询（所有条件只在这里写一次） ==============

    /**
     * 按季节筛选当前可见NPC
     * 【唯一入口】其他地方不准再写 seasonId 筛选
     */
    suspend fun getNpcsBySeason(seasonId: Int): List<NpcBase> {
        return dao.queryBySeason(seasonId)
    }

    /**
     * 按职业+季节组合查询（应对复合筛选场景）
     */
    suspend fun getNpcsByJobAndSeason(jobId: Int, seasonId: Int): List<NpcBase> {
        return dao.queryByJobAndSeason(jobId, seasonId)
    }

    /**
     * 获取NPC动态状态（好感、亲密度、当前情绪）
     */
    suspend fun getNpcStatus(npcId: Int): NpcStatus {
        return dao.queryStatus(npcId) ?: NpcStatus.default(npcId)
    }

    /**
     * 更新好感度
     */
    suspend fun updateFavor(npcId: Int, delta: Int) {
        val current = getNpcStatus(npcId)
        dao.updateStatus(current.copy(favor = (current.favor + delta).coerceIn(0, 100)))
    }

    // ============== 文本读取（静态资源） ==============

    /**
     * 根据对话key读取静态文本
     */
    fun getDialogText(dialogKey: String): String {
        return textLoader.getTextByKey(dialogKey)
    }
}
```

**NpcDao.kt**（仅最底层单字段查询）
```kotlin
package com.example.townapp.npc.repository

import androidx.room.*
import com.example.townapp.npc.model.NpcBase
import com.example.townapp.npc.model.NpcStatus

/**
 * NPC底层Dao
 * 严格遵循：
 * - 只做最基础的单字段/单表查询
 * - 复杂多条件联合查询全部交给 Repository 组合
 */
@Dao
interface NpcDao {
    // 基础单字段查询
    @Query("SELECT * FROM npc_base WHERE season_id = :seasonId")
    suspend fun queryBySeason(seasonId: Int): List<NpcBase>

    @Query("SELECT * FROM npc_base WHERE job_id = :jobId AND season_id = :seasonId")
    suspend fun queryByJobAndSeason(jobId: Int, seasonId: Int): List<NpcBase>

    @Query("SELECT * FROM npc_state WHERE npc_id = :npcId")
    suspend fun queryStatus(npcId: Int): NpcStatus?

    @Update
    suspend fun updateStatus(status: NpcStatus)
}
```

---

### 4. Entity 层（只存数字/ID/状态，严禁存长文本）

**NpcBase.kt**
```kotlin
package com.example.townapp.npc.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * NPC基础信息实体
 * 严格遵循：
 * - 只存数字ID、状态、坐标
 * - 严禁存 name、desc、dialog 等长文本
 * - 长文本一律走 TextAssetLoader 读取静态JSON
 */
@Entity(tableName = "npc_base")
data class NpcBase(
    @PrimaryKey val id: Int,
    val nameTextId: Int,       // 姓名文本ID（assets/npc/name.json）
    val jobId: Int,            // 职业ID（对标职业配置表）
    val seasonId: Int,         // 当前所在季节
    val x: Int,                // 像素地图X坐标
    val y: Int,                // 像素地图Y坐标
    val portraitId: Int,       // 头像资源ID
    val dialogGroupId: Int     // 对话组ID（决定用哪一组对话JSON）
)
```

**NpcStatus.kt**
```kotlin
package com.example.townapp.npc.model

import androidx.room.Entity

/**
 * NPC动态状态实体
 * 严格遵循：只存运行时变化的数字状态
 */
@Entity(tableName = "npc_state")
data class NpcStatus(
    val npcId: Int,
    val favor: Int,                // 好感度 0-100
    val intimacy: Int,             // 亲密度 0-100
    val currentSeasonId: Int,      // 当前季节
    val lastInteractionTime: Long  // 上次互动时间戳
) {
    companion object {
        fun default(npcId: Int) = NpcStatus(
            npcId = npcId,
            favor = 30,
            intimacy = 0,
            currentSeasonId = 1,
            lastInteractionTime = 0L
        )
    }
}
```

---

### 5. 文本加载器（复刻认知模块规范）

**NpcTextLoader.kt**
```kotlin
package com.example.townapp.npc.repository

import android.content.Context
import com.example.townapp.data.asset.TextAssetLoader

/**
 * NPC静态文本加载器
 * 严格遵循：
 * - 全局复用 TextAssetLoader
 * - 不在 Entity 存任何长文本
 * - 全部从 assets/text/npc/ 下按 key 读取
 */
class NpcTextLoader(context: Context) {
    private val loader = TextAssetLoader(context, basePath = "text/npc")

    fun getTextByKey(key: String): String {
        return loader.getTextByKey(key)
    }

    fun getNpcName(nameTextId: Int): String {
        return loader.getTextByKey("name_$nameTextId")
    }

    fun getJobName(jobId: Int): String {
        return loader.getTextByKey("job_$jobId")
    }
}
```

---

### 6. 全局统一入口（对标 DataIntegrationManager）

**NpcDataLayer.kt**
```kotlin
package com.example.townapp.npc

import android.content.Context
import com.example.townapp.npc.repository.NpcDao
import com.example.townapp.npc.repository.NpcRepository
import com.example.townapp.npc.repository.NpcTextLoader
import com.example.townapp.data.AppDatabase

/**
 * NPC模块全局统一入口
 * 严格遵循：
 * - 全项目唯一获取 NPC 数据的入口
 * - 禁止在 UI / ViewModel 中独立 new Dao、独立访问数据库
 * - 禁止在 NpcDataLayer 之外的任何地方直接 new NpcRepository
 */
object NpcDataLayer {
    @Volatile private var initialized = false
    lateinit var npcRepository: NpcRepository
        private set

    val instance: NpcDataLayer
        get() = this

    /**
     * 必须在 Application.onCreate 中调用一次
     */
    fun init(context: Context, database: AppDatabase) {
        if (initialized) return
        synchronized(this) {
            if (initialized) return
            val dao = database.npcDao()
            val textLoader = NpcTextLoader(context.applicationContext)
            npcRepository = NpcRepository(dao, textLoader)
            initialized = true
        }
    }
}
```

**AppDatabase.kt 中暴露 NpcDao**
```kotlin
@Database(
    entities = [
        NpcBase::class,
        NpcStatus::class,
        UserCognitiveCard::class,    // 认知模块（已有）
        // ... 其他模块
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun npcDao(): NpcDao
    abstract fun cognitiveDao(): CognitiveDao  // 认知模块（已有）
    // ... 其他模块
}
```

**在 Application 中初始化**
```kotlin
class TownApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 1. 初始化数据库（全局唯一）
        val database = Room.databaseBuilder(
            this, AppDatabase::class.java, "town_app.db"
        ).build()

        // 2. 初始化各模块统一入口
        NpcDataLayer.init(this, database)
        CognitionDataLayer.init(this, database)
        // ... 其他模块
    }
}
```

---

## 三、静态JSON文本样板

**assets/text/npc/job/teacher.json**
```json
{
  "job_1": "教师",
  "name_1": "李老师",
  "name_2": "王老师",
  "job_1_season_1_low": "教书不容易啊，希望孩子们能懂我的苦心。",
  "job_1_season_1_mid": "你来啦，今天天气真好，春天就该多出来走走。",
  "job_1_season_1_high": "能和你聊聊天真开心，有些话只有对懂的人才能说。",
  "job_1_season_2_low": "夏天上课太困了，粉笔灰飘得满头都是。",
  "job_1_season_2_mid": "夏天嘛，热的难受的时候想想冰西瓜就开心了。",
  "job_1_season_2_high": "谢谢你愿意听我唠叨，这年头愿意听真话的人不多了。"
}
```

---

## 四、模板对标速查表

| 认知模块（样板） | NPC 模块（对标） | 职责 |
|---|---|---|
| `CognitionAwakeningScreen` | `NpcTownMapScreen` | UI主屏 |
| `CognitiveScalpel` | `NpcDialogComponent` | UI复用组件 |
| `UserCognitiveCard` | `NpcBase` | 基础信息实体 |
| `CognitiveBias` | `NpcStatus` | 动态状态实体 |
| `CognitiveItem` | `NpcDialogTrigger` | 业务实体 |
| `CognitiveTipRepository` | `NpcRepository` | 数据仓库 |
| `DataIntegrationManager` | `NpcDataLayer` | 全局统一入口 |
| 认知语录静态JSON | NPC对话静态JSON | 静态文本资源 |
| `UserCognitiveCard.textId:Int` | `NpcBase.nameTextId:Int` | 文本ID化 |

---

## 五、强制硬性规则

1. **禁止 UI/ViewModel 直接 new Dao** —— 一律走 `NpcDataLayer.npcRepository`
2. **禁止 Entity 存长文本** —— 全部走 `TextAssetLoader` + 静态JSON
3. **禁止 Repository 之外的任何地方写 where 条件** —— 多条件查询只许在 `NpcRepository` 内写
4. **禁止独立数据库** —— 所有模块共享 `AppDatabase`
5. **禁止直调 Dao 跳过 Repository** —— UI/ViewModel 不准导入任何 Dao

---

## 六、落地检查清单

- [ ] UI 层文件已创建，未导入任何 Dao
- [ ] ViewModel 层文件已创建，只通过 `NpcDataLayer` 取数据
- [ ] Repository 层文件已创建，所有多条件查询已收口
- [ ] Entity 字段全部为数字/ID/状态，无长文本
- [ ] 静态JSON文件已建立，路径遵循 `assets/text/npc/`
- [ ] `NpcDataLayer.init` 已在 `Application.onCreate` 调用
- [ ] `AppDatabase.npcDao()` 已暴露
- [ ] 编译通过 `.\gradlew assembleDebug --no-daemon`
