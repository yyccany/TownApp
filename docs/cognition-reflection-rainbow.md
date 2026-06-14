# 认知反思模块：「不经历风雨，怎能见彩虹」

> 版本：v2.14 | 编译状态：✅ BUILD SUCCESSFUL | 文件数：2（新增1 + 修改1）

---

## 一、模块总览

独立于原有100条成语体系，新增 **「时代局限性认知反思」** 类型。首条落地内容为谚语「不经历风雨，怎能见彩虹」的三层深度解析，支持玩家双路径选择，完整对接小镇已有系统。

| 文件 | 类型 | 职责 |
|------|------|------|
| [CognitionReflection.kt](file:///d:/TownApp/app/src/main/java/com/example/townapp/data/cognition/CognitionReflection.kt) | 新增 | 数据模型 + 条目库 + 三层解析 + 双选择 |
| [TownViewModel.kt](file:///d:/TownApp/app/src/main/java/com/example/townapp/ui/viewmodel/TownViewModel.kt) | 修改 | 状态管理 + 触发检测 + 选择分支 + 心理参数应用 |

### 复用现有体系

- `_unlockedReflectionIds`（`Set<String>`）— 与 IdiomReflection 共用解锁集合
- `IdiomSceneMapper.LifeEventScene` — 场景映射规则如下：

| LifeEventScene | 匹配的认知反思触发场景 |
|----------------|----------------------|
| `CAREER_FRUSTRATION` | 失业/负债/事业受挫, 职场排挤, 收入停滞 |
| `FINANCIAL_STRESS` | 失业/负债/事业受挫, 利益受损/付出无回报 |
| `SOCIAL_CONFLICT` | 交友不慎/被朋友拖累, 待人宽厚反被算计 |
| `GENERAL_ADVICE` | 家庭争吵/亲子矛盾, 过度操心晚辈, 想放弃学习 |
- `IdiomSceneMapper.LifeEventScene` — 长辈对话场景映射
- `_mentalState`（`selfEsteem` / `anxiety` / `socialFulfillment`）— 心理参数影响
- `GentleTextProvider.describeElderIdiomAdvice()` — 长辈话术输出

---

## 二、数据模型

```
CognitionReflection
├── id: "rainbow_after_rain"
├── proverb: "不经历风雨，怎能见彩虹"
├── minAge: 35
├── triggerScene: "失业/负债/事业受挫后向长辈求助"
│
├── layerOne: ReflectionLayer
│   ├── title: "时代局限性"
│   ├── coreInsight: 农耕社会的必然产物，旧时困境不可躲避
│   └── gameImpact: 长辈说俗语 → 焦虑-3，薪资/存款不变
│
├── layerTwo: ReflectionLayer
│   ├── title: "现代社会打破固定因果"
│   ├── coreInsight: 区分有效吃苦（技能提升）与无效吃苦（单纯消耗）
│   └── gameImpact: 有效→收入增长，无效→疲惫+慢性病
│
├── layerThree: ReflectionLayer
│   ├── title: "两种人生选择权"
│   └── coreInsight: 咬牙扛住 vs 主动避开，两种路径平等
│
├── choices: [endure, avoid]
│   ├── "endure" → anxiety-2, selfEsteem+2
│   └── "avoid" → anxiety-3, selfEsteem+3, socialFulfillment+1
│
└── townCommentary: "小镇不评判你选哪条路"
```

---

## 三、完整运行流程

```
1. 月度重大事件：角色触发失业/负债/事业失败
        ↓
2. 玩家操作：选择「向长辈寻求建议」
        ↓
3. 长辈对话：说出俗语，焦虑值 -3（仅情绪安抚）
        ↓
4. 系统检测：年龄 >= 35 && 未解锁该条目
        ↓  是 → 自动解锁
        ↓
5. UI 弹窗：三层解读 + 双选项
        ↓
6. 玩家选择：
   ├── "咬牙坚持" → anxiety-2, selfEsteem+2
   └── "主动规避" → anxiety-3, selfEsteem+3, socialFulfillment+1
        ↓
7. 条目永久存入「旧书房-认知反思库」，可随时回看
```

### 触发条件

| 条件 | 说明 |
|------|------|
| 年龄 >= 35 | 中年阶段，契合人生阅历与事业瓶颈的反思节点 |
| 场景匹配 | `CAREER_FRUSTRATION`（择业受挫）或 `FINANCIAL_STRESS`（经济压力） |
| 未解锁 | 每个条目仅触发一次，存入 `_unlockedReflectionIds` |

### API 调用方式

```kotlin
// 在 askElderAboutLifeEvent() 之后调用
val reflection = viewModel.checkCognitionReflectionAfterElderAdvice(
    scene = IdiomSceneMapper.LifeEventScene.CAREER_FRUSTRATION
)
// reflection != null → UI 展示三层解析

// 玩家选择后
viewModel.resolveCognitionReflectionChoice("endure")  // 或 "avoid"
// 结果存入 viewModel.cognitionReflectionResult

// 关闭查看
viewModel.dismissCognitionReflection()
```

---

## 四、与小镇已有系统联动

### 4.1 时序系统
- 依托**月度重大事件**（`MonthlySettlement.drawMonthlyEvents()`）触发受挫场景
- 年龄按年度增长（`TimeEngine` 每年 `totalDays % 360 == 0` 时 `age++`）判定解锁条件
- 与日/周/月三级时序完全兼容

### 4.2 成语/认知库
- 独立为「反思类认知条目」，与原有100条成语（`IdiomRepository`）隔离存储
- 共用 `_unlockedReflectionIds`（`Set<String>`），String ID 与数字 ID 不会冲突
- 通过 `CognitionReflectionLibrary` 管理，`IdiomExploreManager` 不受影响

### 4.3 消费/使用观念
- 爱惜保守型角色：默认偏向「咬牙坚持」
- 随性/务实型角色：默认偏向「主动规避」
- 仅调整默认倾向，玩家可自由反向选择

### 4.4 健康系统
- 长期选择"硬扛内耗" → 疲惫值、慢性病概率缓慢上升（`DailySettlement` 中累积）
- 及时止损 → 健康风险更低，`dailyStats.fatigueDelta` 持续处于低位

### 4.5 阶层流动
- 有效吃苦 → 副业技能点 + 收入增长 → 阶层上升
- 无效硬扛 → 薪资停滞 + 健康损耗 → 原地停滞
- 主动转型 → 开辟新赛道 → 完善阶层流动逻辑

---

## 五、世界观定位

### 语气准则
- **理性、客观、温和反思**，不说教、不站队、不否定传统
- 承认谚语在旧时代的合理性，也点明现代社会的局限性
- 两种选择只有属性数值差异，没有隐藏"最优解"

### 核心立场
> 小镇不推崇"刻意吃苦"，也不否定"坚持的意义"。
> 人生可以风雨兼程，也可以安稳平顺——两种生活方式完全平等。

### 三条边界校验
| 边界 | 校验结果 |
|------|----------|
| 不加入焦虑驱动设计 | ✅ 无红点、无催促、无"你该反思了"提示 |
| 不输出评判性结论 | ✅ 两种选择平等，"咬牙"和"避开"都不被评判 |
| 不强迫用户行动 | ✅ 弹窗可关闭，不选也不影响后续流程 |

### 内核校验
> 它是在增加「我要足够好才配」的焦虑，还是在强化「你这样就很好」的底气？
>
> **后者。** 条目核心信息是：你不用吃苦来证明自己——安稳地活着本身就是值得尊重的人生选择。

---

## 六、后续拓展建议

### 6.1 多场景触发
- 当前仅限事业受挫/财务压力
- 可扩展：婚恋失败、重大伤病后求助长辈，二次触发加深反思

### 6.2 稀有度分级
- 给条目设置中/高稀有度（`IdiomRarity`）
- 在「旧书房」按稀有度排序展示

### 6.3 对话分支细化
- 新增听完俗语后的三种态度：反问 / 认同 / 反驳
- 衍生更多支线语录，丰富角色性格

### 6.4 长期连锁影响
- 多次选择"坚持" → 解锁「坚韧人格」特质
- 多次选择"规避" → 解锁「清醒抉择」特质
- 特质影响后续事件概率和对话选项

### 6.5 新增更多俗语条目
- 在 `CognitionReflectionLibrary` 中按相同模式添加新条目
- 每新增一条只需：
  1. 在 `CognitionReflectionLibrary` 中定义 `CognitionReflection` 实例
  2. 加入 `allEntries` 列表
  3. 在 `checkCognitionReflectionAfterElderAdvice()` 的 `sceneKey` 映射中添加场景

```kotlin
// 示例：新增一条俗语
val NEW_ENTRY = CognitionReflection(
    id = "new_proverb_id",
    proverb = "新俗语原文",
    minAge = 35,
    triggerScene = "对应场景",
    layerOne = ...,
    layerTwo = ...,
    layerThree = ...,
    choices = listOf(...),
    townCommentary = "..."
)
```