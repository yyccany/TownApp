# 万物薪俸小镇 - 项目进度总结

## 开发时间线

**开始时间：** 2026-06-09
**当前进度：** V2.0 人文公益载体完成

---

## 核心架构完成 ✅

### 1. 数据持久化层

#### 数据库实体（22个）

**核心系统（14个已使用）**
- `UserEntity` - 用户基本信息
- `BuildingEntity` - 建筑信息
- `NpcEntity` - NPC 基础数据
- `FoodNutritionEntity` - 食物营养
- `FoodRiskEntity` - 食物风险
- `UserBodyState` - 身体状态
- `MaterialEntity` - 原材料
- `ProductEntity` - 成品
- `FormulaEntity` - 配方
- `AppConfigEntity` - 应用配置
- `LifeEventLogEntity` - 事件日志
- `LifeArchiveEntity` - 人生存档
- `RecipeEntity` - 食谱
- `RecipeIngredientEntity` - 食谱原料

**待接入系统（8个未使用但保留）**
- `ActionEntity` - 行为记录（用于人生复盘）
- `EventHistoryEntity` - 事件历史（用于晚年统计）
- `QuoteEntity` - 语录库（用于 NPC 随机引用）
- `QuoteCacheEntity` - 语录缓存
- `ResidentEntity` - 居民健康状态
- `FeedingRecordEntity` - 喂食记录
- `UserSpaceState` - 居住空间状态
- `UserMentalState` - 精神状态

#### 已删除实体（违反小镇宪章）
- `DailyTaskEntity` ❌ 每日任务系统
- `UnlockEntity` ❌ 解锁/收集系统
- `CuteCharacterEntity` ❌ 角色陪伴系统
- `CuteEmotionQuoteEntity` ❌ 角色情绪语录
- `CuteSceneQuoteEntity` ❌ 角色场景语录（含召回机制）

---

### 2. 核心功能模块

#### P0 NPC 专属色调系统 ✅
- `NpcMoodPalette.kt` - 氛围色数据模型
- `NpcRepository.getMoodPalette(npcId)` - 配色查询
- `NpcMoodOverlay` - 专属光影渲染组件
- 每个职业、性格、年龄有独立氛围色

#### P1 众生时间档案馆 ✅
- `NpcArchiveScreen.kt` - 档案馆主页面
- `NpcTimelineScreen.kt` - NPC 时间线详情页
- `NpcTimelineVo.kt` - 时间线数据模型
- 分类检索：片区/年龄段/职业（无排行榜）
- 认知分层注解：高认知自动追加中立解读
- 静默纯画面浏览模式

#### P2 多周目认知记忆继承 ✅
- `CognitionState.kt` - 认知状态管理
- `ResetSimulationUseCase.kt` - 周目重置逻辑
- 认知碎片永久保留，财富/NPC好感重置
- 二周目直接解锁智慧者对话视角

#### NPC 人物库扩充 ✅
- 30 个职业覆盖
- 60 个 NPC 预设数据
- 360 条四季对话素材
- 50 条回忆碎片
- `NpcTextDispatchManager.kt` - 统一素材调度器

---

### 3. 小镇宪章合规性

#### 三条边界（永久不可违背）

1. **永不加入焦虑驱动设计** ✅
   - 签到、打卡、红点提醒、每日任务、排行榜、成就体系 —— **永久禁用**
   - 已删除 `DailyTaskEntity`、`UnlockEntity` 等违反宪章的模块

2. **永不输出评判性结论** ✅
   - 任何人生选择、任何生活状态，不分好坏优劣
   - 认知系统只呈现事实，不输出"应该如何活"

3. **永不强迫用户行动** ✅
   - 永远不推送召回、不催促上线、不引导"你该做什么"
   - 已删除角色陪伴系统中的召回机制

---

## 项目文件总览 📁

```
d:\TownApp\app\src\main\java\com\example\townapp\
│
├── MainActivity.kt - 主入口
│
├── npc/ - NPC 系统（核心人文载体）
│   ├── model/
│   │   ├── NpcBase.kt - NPC 基础数据
│   │   ├── NpcStatus.kt - NPC 状态
│   │   ├── NpcMoodPalette.kt - 专属色调
│   │   ├── NpcTimelineVo.kt - 时间线数据
│   │   ├── CognitionState.kt - 认知状态
│   │   └── TonePaletteVo.kt - 色调配置
│   │
│   ├── repository/
│   │   └── NpcRepository.kt - NPC 数据仓库
│   │
│   ├── ui/
│   │   ├── NpcTownMapScreen.kt - 小镇地图
│   │   ├── NpcArchiveScreen.kt - 档案馆主页
│   │   ├── NpcTimelineScreen.kt - 时间线详情
│   │   └── NpcMoodOverlay.kt - 专属光影组件
│   │
│   ├── viewmodel/
│   │   └── NpcViewModel.kt - NPC 状态管理
│   │
│   └── NpcTextDispatchManager.kt - 素材调度器
│
├── data/
│   ├── database/
│   │   ├── TownDatabase.kt - Room 数据库（version=11）
│   │   ├── entity/ - 数据库实体（22个）
│   │   └── dao/ - 数据库 DAO
│   │
│   └── text/ - 静态素材
│       ├── npc/
│       │   ├── mood_palette.json - 色调配置
│       │   ├── dialogue_*.json - 四季对话
│       │   └── memory_*.json - 回忆碎片
│       └── cognition/
│           └── reflection_*.json - 认知反思
│
├── domain/
│   └── usecase/
│       └── ResetSimulationUseCase.kt - 周目重置
│
└── ui/
    ├── theme/ - 主题系统
    └── components/ - UI 组件
```

---

## 数据库状态

```
TownDatabase (version = 11)
├── 已使用（14个表）
│   ├── UserEntity, BuildingEntity, NpcEntity
│   ├── FoodNutritionEntity, FoodRiskEntity, UserBodyState
│   ├── MaterialEntity, ProductEntity, FormulaEntity
│   ├── AppConfigEntity, LifeEventLogEntity, LifeArchiveEntity
│   ├── RecipeEntity, RecipeIngredientEntity
│
├── 未使用但可接入（8个表）
│   ├── ActionEntity, EventHistoryEntity
│   ├── QuoteEntity, QuoteCacheEntity
│   ├── ResidentEntity, FeedingRecordEntity
│   ├── UserSpaceState, UserMentalState
│
└── 已删除（违反宪章）
    ├── DailyTaskEntity ❌ 每日任务
    ├── UnlockEntity ❌ 解锁收集
    └── 角色陪伴系统全套 ❌ 召回机制
```

---

## 下一步开发建议 🚀

### 阶段一：接入未使用表（1-2周）
1. `ActionEntity` → 记录玩家行为日志，用于人生复盘
2. `EventHistoryEntity` → 记录事件历史，用于晚年统计
3. `QuoteEntity` → NPC 随机引用语录库

### 阶段二：环境视觉迭代（2-3周）
1. 四季植被变化
2. 晨昏光影动态
3. 远景场景细化

### 阶段三：NPC 自主日常动画（3-4周）
1. 通勤、居家、就医、散步动态行为
2. 强化小镇自主流动的鲜活感

---

## 成就解锁 🏆

✅ 完整的数据库架构（精简合规）
✅ NPC 专属色调系统
✅ 众生时间档案馆（人文公益载体）
✅ 多周目认知记忆继承
✅ NPC 人物库扩充（30职业/60NPC）
✅ 小镇宪章底层合规（删除所有焦虑驱动设计）

---

## 核心文件链接

[小镇开发宪章](.trae/rules/project_rules.md)
[产品终极定位](app/src/main/assets/docs/00-项目根目录/产品终极定位.md)
[认知反思系统](app/src/main/assets/docs/08-认知反思系统/基础设定.md)