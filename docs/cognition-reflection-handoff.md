# 认知反思模块 · 收尾交付文档

> 版本：v2.14 | 编译状态：✅ BUILD SUCCESSFUL | 后端完成度：100% | 待办：Compose UI

---

## 一、当前整体状态

### 1.1 后端全完成

| 交付项 | 文件 | 状态 |
|--------|------|------|
| 数据模型 + 条目库 | [CognitionReflection.kt](file:///d:/TownApp/app/src/main/java/com/example/townapp/data/cognition/CognitionReflection.kt) | ✅ |
| 触发检测 + 选择分支 | [TownViewModel.kt](file:///d:/TownApp/app/src/main/java/com/example/townapp/ui/viewmodel/TownViewModel.kt) | ✅ |
| 设计文档 | [cognition-reflection-rainbow.md](file:///d:/TownApp/docs/cognition-reflection-rainbow.md) | ✅ |
| 编译验证 | `./gradlew assembleDebug` | ✅ |

### 1.2 依赖与兼容

- 对接 `_unlockedReflectionIds`（`Set<String>`），与 IdiomReflection 共用，不冲突
- 对接 `_mentalState`（`selfEsteem` / `anxiety` / `socialFulfillment`）
- 对接 `IdiomSceneMapper.LifeEventScene`（`CAREER_FRUSTRATION` / `FINANCIAL_STRESS`）
- 对接 `TimeEngine` 年龄判定（`age >= 35`）
- 对接 `MonthlySettlement` 月度负面事件
- **不改动原有成语/事件核心代码**

### 1.3 唯一待办

> 基于 Compose 开发**认知反思弹窗 UI**，读取已就绪的状态流做展示与交互。

---

## 二、UI 开发指引（Compose）

### 2.1 触发时机

当 `activeCognitionReflection` 不为 `null` 时，全局弹窗置顶展示，阻断下层操作。完成选择后自动关闭、清空状态。

### 2.2 状态流读取

```kotlin
// 在任意 Composable 中观察
val currentReflection by viewModel.activeCognitionReflection.collectAsStateWithLifecycle()
val choiceResult by viewModel.cognitionReflectionResult.collectAsStateWithLifecycle()
```

### 2.3 弹窗页面结构

```
┌─────────────────────────────────────┐
│                                     │
│     认知反思 · 不经历风雨，怎能见彩虹     │  ← 标题区（居中、稍大）
│                                     │
│  ── 时代局限性 ──                     │
│  这句老话属于农耕社会的产物...           │  ← 第一层
│                                     │
│  ── 现代社会打破固定因果 ──             │
│  吃苦分两种情况：有效吃苦 vs 无效吃苦...  │  ← 第二层（含滚动）
│                                     │
│  ── 两种人生选择权 ──                  │
│  小镇给你两条路，没有哪条更"正确"...     │  ← 第三层
│                                     │
│  ┌──────────┐  ┌──────────┐        │
│  │ 咬牙坚持  │  │ 主动规避  │        │  ← 双按钮并排、无主次
│  └──────────┘  └──────────┘        │
│                                     │
└─────────────────────────────────────┘
```

### 2.4 三层正文数据映射

```kotlin
currentReflection?.let { entry ->
    // 第一层
    Text(entry.layerOne.title)       // "时代局限性"
    Text(entry.layerOne.coreInsight) // 核心观点
    Text(entry.layerOne.detailText)  // 详细展开
    Text(entry.layerOne.gameImpact)  // 游戏内影响

    // 第二层
    Text(entry.layerTwo.title)       // "现代社会打破固定因果"
    Text(entry.layerTwo.coreInsight)
    Text(entry.layerTwo.detailText)
    Text(entry.layerTwo.gameImpact)

    // 第三层
    Text(entry.layerThree.title)     // "两种人生选择权"
    Text(entry.layerThree.coreInsight)
    Text(entry.layerThree.detailText)
}
```

### 2.5 选择按钮交互

```kotlin
Row(horizontalArrangement = Arrangement.SpaceEvenly) {
    entry.choices.forEach { choice ->
        Button(
            onClick = {
                viewModel.resolveCognitionReflectionChoice(choice.id)
            },
            // 无主次、无颜色强弱区分（体现选择平等）
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column {
                Text(choice.label)        // "咬牙坚持" / "主动规避"
                Text(choice.description)  // 简短说明
            }
        }
    }
}
```

### 2.6 交互规则

| 规则 | 说明 |
|------|------|
| 单次触发 | 每个条目仅弹窗 1 次，已解锁不再重复 |
| 选择后关闭 | 点击任意按钮 → 执行数值变更 → 自动关闭弹窗 |
| 可关闭 | 弹窗支持点外部关闭（`dismissCognitionReflection()`） |
| 主题适配 | 跟随 App 全局浅色/深色模式 |

### 2.7 选择结果展示

```kotlin
// 选择完成后的结果文本（可选展示 Snackbar 或底部提示）
choiceResult?.let { result ->
    Snackbar { Text(result) }
    // 阅读完毕后清空
    viewModel.cognitionReflectionResult.value = null  // 或由 ViewModel 内部处理
}
```

---

## 三、配套入口：旧书房-认知库

在原有「成语探索/认知反思」页面中新增该条目：

| 属性 | 值 |
|------|-----|
| 条目名称 | 不经历风雨，怎能见彩虹 |
| 标签 | 中年感悟 / 人生选择 |
| 点击行为 | 回看完整三层解读文案（仅阅览，不触发选择分支） |

### 判断是否已解锁

```kotlin
val isUnlocked = viewModel.unlockedReflectionIds.value.contains("rainbow_after_rain")
if (isUnlocked) {
    // 展示条目，点击进入只读模式
    val entry = CognitionReflectionLibrary.getById("rainbow_after_rain")
}
```

---

## 四、功能测试用例

### 4.1 基础触发

| # | 场景 | 预期 | 状态 |
|---|------|------|------|
| 1 | 年龄 < 35，失业→求助长辈 | 仅出现俗语对话，不解锁、不弹反思弹窗 | ⬜ |
| 2 | 年龄 >= 35，首次触发场景 | 正常解锁 + 弹出完整弹窗 | ⬜ |
| 3 | 已解锁后重复触发相同场景 | 仅长辈对话，不再弹窗 | ⬜ |
| 4 | 年龄 >= 35，不同场景（财务压力） | 如该场景在映射中，正常触发 | ⬜ |

### 4.2 分支数值

| # | 操作 | 预期数值变化 | 状态 |
|---|------|-------------|------|
| 5 | 选择「咬牙坚持」 | `anxiety -2`，`selfEsteem +2` | ⬜ |
| 6 | 选择「主动规避」 | `anxiety -3`，`selfEsteem +3`，`socialFulfillment +1` | ⬜ |
| 7 | 边界测试：anxiety=0 时选择 | 不会溢出为负数，`coerceIn(0, 100)` 保护 | ⬜ |
| 8 | 边界测试：selfEsteem=100 时选择 | 不会溢出超过 100 | ⬜ |

### 4.3 存储与回溯

| # | 场景 | 预期 | 状态 |
|---|------|------|------|
| 9 | 解锁后进入旧书房 | 条目正常显示，可查看全文 | ⬜ |
| 10 | 重启 App | 解锁状态持久保留（取决于 `_unlockedReflectionIds` 持久化方案） | ⬜ |

### 4.4 联动

| # | 场景 | 预期 | 状态 |
|---|------|------|------|
| 11 | 爱惜保守型角色触发 | 默认倾向「咬牙坚持」，但可自由反向选择 | ⬜ |
| 12 | 随性/务实型角色触发 | 默认倾向「主动规避」，但可自由反向选择 | ⬜ |
| 13 | 长期多次选择"硬扛" | 后续疲惫、慢性病概率缓慢上升 | ⬜ |

### 4.5 UI

| # | 场景 | 预期 | 状态 |
|---|------|------|------|
| 14 | 弹窗展示 | 三层文本完整展示，可滚动 | ⬜ |
| 15 | 双按钮并排 | 无主次颜色区分，点击响应正常 | ⬜ |
| 16 | 选择后关闭 | 弹窗自动关闭，状态清空 | ⬜ |
| 17 | 深色模式 | 弹窗跟随主题适配 | ⬜ |

---

## 五、后续迭代规划

### 短期（UI 完成后）

1. 优化长辈对话话术，搭配该谚语增加多版口语文案
2. 统一所有认知反思弹窗 UI 样式，形成全局 `CognitionReflectionDialog` 组件

### 中期

1. 拓展触发场景：婚恋受挫、重大伤病后求助长辈，复用同一反思条目
2. 新增人格特质联动：累计选择倾向 → 解锁「坚韧」/「清醒抉择」标签

### 长期

使用 `CognitionReflection` 模板批量新增同类型俗语反思模块：

```kotlin
// 新增一条只需在 CognitionReflectionLibrary 中添加：
val NEW_ENTRY = CognitionReflection(
    id = "new_proverb_id",
    proverb = "俗语原文",
    minAge = 35,
    triggerScene = "对应场景",
    layerOne = ReflectionLayer(...),
    layerTwo = ReflectionLayer(...),
    layerThree = ReflectionLayer(...),
    choices = listOf(...),
    townCommentary = "..."
)
// 然后加入 allEntries 列表，在 checkCognitionReflectionAfterElderAdvice 中添加场景映射
```

---

## 六、总结

| 维度 | 状态 |
|------|------|
| 后端业务逻辑 | ✅ 100% 完成 |
| 数据模型 | ✅ 三层解析 + 双选择 + 心理参数联动 |
| 编译 | ✅ BUILD SUCCESSFUL |
| 设计文档 | ✅ 完整交付 |
| 前端 UI | ⬜ 待开发（状态流已就绪，可直接读取） |
| 测试 | ⬜ 待 UI 完成后执行 17 条用例 |

**卡点：仅前端 UI 实现。** 完成后即可正式上线，完整融入小镇人生模拟体系。