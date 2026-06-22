# Compose 界面四大改造方向（对标《去月球》电影质感）

> 本文档梳理小镇 Compose 界面**从"农场感"升级到"电影叙事感"**的改造方案。
> 核心原则：**零底层架构重构，增量修改，贴合现有 NPC / 回忆分层**。

---

## 一、UI 全面收拢，根除"花架子平铺"

### 现状问题
- 底部铺满一排功能按钮
- 侧边长条状态栏
- 顶部四维数值常驻显示
- 各种永久悬浮文字提示、标签卡片

### 改造目标
**主画布全屏无常驻控件 + 弹窗仅交互触发**

### 1.1 状态信息收拢为悬浮迷你面板

**改造规则**：
- 四维状态、年份季节：右上角悬浮迷你半透明面板
  - 点击一键收起/展开
  - 默认折叠（点开才显示）
  - 半透明 + 圆角卡片 + 极小字号
- 纪念馆、设置、文字模拟器切换：收拢至右下角折叠圆形浮动菜单
  - 平时只显示一个小圆点
  - 点击展开 3 个扇形图标
  - 点击空白处自动收起

### 1.2 弹窗按需销毁

**改造规则**：
- NPC 对话、回忆弹窗、商店、认知手术刀
  - **只有点击对应物体才渲染**
  - 关闭后**完全从 Compose 树销毁**（设置 state = null）
  - 禁止任何永久悬浮浮窗
- 通过 `if (state != null) { Component(state, onDismiss = { state = null }) }` 模式

### 1.3 文字模拟器轻量化

**改造规则**：
- 文字模式不再铺满数值面板
- 仅底部窄条打字对话框
- 其余画面留白，保留电影感留白
- 顶部状态栏同地图页（迷你折叠面板）

### 1.4 Compose 极简示例（地图页面改造）

```kotlin
@Composable
fun NpcTownMapScreen(viewModel: NpcViewModel = viewModel()) {
    val npcList by viewModel.npcList.collectAsState()
    val selectedNpc by viewModel.selectedNpc.collectAsState()
    val currentMemory by viewModel.currentMemory.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // 第一层：像素地图画布（占满全屏）
        PixelMapCanvas(
            npcs = npcList,
            onNpcClick = { viewModel.selectNpc(it) }
        )

        // 第二层：昼夜/四季渐变遮罩
        AtmosphereOverlay(currentSeasonId, currentTimeOfDay)

        // 第三层：折叠悬浮控件
        MiniStatusPanel(seasonId, year)  // 右上角，默认折叠
        FloatingMenu(actions)             // 右下角，默认小圆点

        // 第四层：弹窗（按需渲染，关闭即销毁）
        selectedNpc?.let { npc ->
            NpcDialog(npc, onClose = { viewModel.closeDialog() })
        }
        currentMemory?.let { memory ->
            MemoryFragmentDialog(memory, onDismiss = { viewModel.closeMemory() })
        }
    }
}
```

---

## 二、Canvas 电影级镜头与光影系统

### 现状问题
- 固定俯视角度，画面廉价感
- 无镜头语言，无氛围
- 一成不变的固定底色

### 改造目标
**完全用 Compose Canvas 原生 API 实现，不新增第三方库**

### 2.1 动态昼夜/四季渐变遮罩

**实现方案**：
- 画面顶层叠加半透明渐变图层（用 `Brush.verticalGradient` / `Brush.radialGradient`）
- **清晨**（6-10）：浅暖黄径向柔光
- **白天**（10-18）：透明
- **傍晚**（18-20）：橙红渐变
- **深夜**（20-6）：深蓝径向柔光
- **春**：高饱和暖色
- **夏**：高对比强光
- **秋**：低饱和暖橙
- **冬**：低饱和冷灰
- 随游戏天数**自动平滑过渡**（用 `animateColorAsState`）

**核心代码骨架**：
```kotlin
@Composable
fun AtmosphereOverlay(seasonId: Int, timeOfDay: TimeOfDay) {
    val overlayColor = when (timeOfDay) {
        TimeOfDay.DAWN -> Color(0x33FFD700)   // 浅暖黄
        TimeOfDay.DAY -> Color.Transparent
        TimeOfDay.DUSK -> Color(0x33FF6347)   // 橙红
        TimeOfDay.NIGHT -> Color(0x55000080)  // 深蓝
    }
    val animatedColor by animateColorAsState(overlayColor, label = "atmosphere")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedColor)
    )
}
```

### 2.2 叙事专属运镜 Modifier（全局复用）

| 场景 | Modifier 效果 | 触发条件 |
|------|--------------|---------|
| 日常探索 | 固定居中远景 | 默认状态 |
| 回忆弹窗 | 缓慢放大推镜聚焦 | currentMemory != null |
| 关键剧情 | 全屏淡入淡出 | 剧情事件回调 |
| 生病/晚年 | 画面缩放 + 暗角滤镜 | playerAge > 60 |
| 回忆场景 | 低饱和度 + 颗粒噪点 | isMemoryScene = true |

**通用 Modifier 扩展**：
```kotlin
// 缓慢推镜 Modifier
fun Modifier.slowZoomIn(enabled: Boolean): Modifier = this.then(
    if (enabled) Modifier.scale(animateFloatAsState(if (enabled) 1.15f else 1f, label = "zoom").value)
    else Modifier
)

// 暗角滤镜
fun Modifier.vignetteEffect(enabled: Boolean): Modifier = this.then(
    if (enabled) Modifier.drawWithCache {
        val brush = Brush.radialGradient(
            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)),
            radius = size.minDimension / 1.2f
        )
        onDrawWithContent {
            drawContent()
            drawRect(brush)
        }
    } else Modifier
)

// 回忆褪色滤镜
fun Modifier.memoryFilter(enabled: Boolean): Modifier = this.then(
    if (enabled) Modifier
        .saturation(0.6f)  // 低饱和度
        .drawWithCache {
            // 颗粒噪点
            onDrawWithContent {
                drawContent()
                // ... 绘制像素噪点
            }
        }
    else Modifier
)
```

### 2.3 局部像素光影分层

**实现方案**：
- 室内窗户光源：`Brush.radialGradient` 径向光晕
- 路灯：圆形径向光
- 角色轮廓：`drawRect` 细描边光
- 区分人物与背景，解决农场平面纸片感

```kotlin
// 角色光晕
Canvas {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.3f),
                Color.Transparent
            ),
            radius = 40f,
            center = Offset(npc.x, npc.y)
        ),
        radius = 40f,
        center = Offset(npc.x, npc.y)
    )
    // 角色精灵
    drawImage(npcSprite, topLeft = Offset(npc.x - 16, npc.y - 16))
}
```

---

## 三、交互逻辑重构：抛弃"多点按钮操作"

### 现状问题
- 农场模式：点土地→点工具→点收取，多层操作弹窗
- 各种"一键快进"、"批量操作"醒目按钮
- 破坏沉浸感

### 改造目标
**全局单点交互：整张地图任意 NPC/建筑/路标统一点击触发**

### 3.1 单点交互规则

**改造规则**：
- ✅ 整张地图任意 NPC、建筑、路标**统一点击触发**
- ❌ 禁止多层弹窗、嵌套菜单
- ✅ 所有分支选择、认知解读、时间复盘**全部收纳进对话弹窗内**
- ❌ 禁止分散各处的选择按钮
- ✅ 移除所有"一键快进、批量操作"醒目按钮
- ✅ 快进功能**藏进折叠菜单**，避免破坏沉浸
- ✅ 点击音效、BGM 随场景、年龄动态切换

**点击层级设计**：
```
地图任意点 → 检测点击对象类型
    ↓
NPC  → 弹出对话弹窗（含支线选择）
建筑 → 弹出场景交互弹窗
路标 → 弹出回忆弹窗
空地 → 无操作（保留电影感留白）
```

### 3.2 弹窗内部选择收纳

**所有选择内嵌于对话弹窗**：
```kotlin
AlertDialog(
    title = { Text("${npc.name}（${npc.jobName}）") },
    text = {
        Column {
            Text(npc.currentDialog)
            // 选择按钮内嵌于此
            TextButton(onClick = { /* 选择 A */ }) { Text("我理解你") }
            TextButton(onClick = { /* 选择 B */ }) { Text("我觉得你想多了") }
        }
    },
    confirmButton = { TextButton(onClick = onClose) { Text("离开") } }
)
```

### 3.3 沉浸感设计

- **点击音效**：`View.playSoundEffect()` 轻量音效
- **BGM 联动**：进入回忆弹窗自动切换为低吟 BGM
- **触觉反馈**：长按 NPC 触发深层次对话（不弹按钮提示）

---

## 四、画面分层细节升级（区别休闲农场）

### 现状问题
- 农场只有单层地块
- 无远中近景层次
- 无环境动态

### 改造目标
**多层堆叠像素场景，强化"时间人生"质感**

### 4.1 三层场景结构

| 层级 | 内容 | 资源 |
|------|------|------|
| **远景** | 天空、远山、太阳/月亮 | 随季节切换背景图 |
| **中景** | 街道、房屋、NPC 独立生命周期像素动画 | NPC 行走/发呆/衰老外观 |
| **近景** | 树木、路灯、花草动态帧动画 | 风吹、落雪、落叶 |

### 4.2 NPC 动态像素动画

**实现方案**：
- 静态素材拆分帧动画（每 4-8 帧一循环）
- 状态机：行走 / 发呆 / 工作 / 衰老
- 衰老外观：`age > 60` 切换为白发动画帧
- Canvas 渲染，**不增加数据库负担**

```kotlin
// NPC 动画帧切换
val frameIndex = (System.currentTimeMillis() / 200 % spriteFrames.size).toInt()
drawImage(spriteFrames[frameIndex], topLeft = Offset(npc.x - 16, npc.y - 16))
```

### 4.3 四季环境动态

- **春**：花瓣飘落帧动画
- **夏**：树叶摇曳 + 蝉鸣音效
- **秋**：落叶帧动画
- **冬**：雪花飘落帧动画

### 4.4 回忆专属褪色滤镜

**效果**：
- 低饱和度（`saturation(0.6f)`）
- 轻微颗粒像素噪点
- 区分现实与过往记忆
- 复刻《去月球》回忆片段质感

```kotlin
fun Modifier.memoryFilter(): Modifier = this
    .saturation(0.6f)        // 低饱和度
    .drawWithCache {
        onDrawWithContent {
            drawContent()
            // 颗粒噪点叠加
            val noise = generateNoisePattern(size)  // 伪随机噪点
            drawImage(noise, blendMode = BlendMode.Overlay)
        }
    }
```

---

## 五、与现有底层架构的兼容性

### 数据层完全不动 ✅
- `NpcRepository` / `MemoryFragmentVo` / `TextAssetLoader` / `NpcLifecycleManager` 全部**复用**
- 画面只接收 ViewModel 组装好的状态
- **不碰任何数据库 / 静态文件**

### 分层规范不破坏 ✅
- UI 依旧纯展示
- 所有时间、NPC、光影计算逻辑收拢至 **ViewModel / 全局工具类**
- 页面无业务代码

### 资源复用 ✅
- 现有像素素材、回忆 JSON、BGM 音频**无需重做**
- 仅新增：
  - 渐变滤镜组件
  - 运镜 Modifier 通用组件
  - 折叠菜单组件
  - 弹窗容器组件

### 文字模拟器同步改造 ✅
- 共用同一套光影、弹窗、折叠菜单组件
- 两套界面视觉风格统一

---

## 六、落地优先级（由易到难）

### 短期 1（1 天，立刻改观）
- [ ] 收拢所有常驻 UI 为折叠悬浮组件
- [ ] 删除铺满屏幕的功能按钮
- [ ] 对话、回忆弹窗按需销毁
- [ ] 画布大面积留白

### 短期 2（2 天）
- [ ] 全局昼夜/四季渐变遮罩
- [ ] 淡入淡出转场 Modifier
- [ ] 基础光影氛围

### 中期（3 天）
- [ ] 剧情推镜 Modifier
- [ ] 晚年暗角滤镜
- [ ] 回忆褪色特效
- [ ] 动态时段 BGM 切换

### 长期迭代
- [ ] NPC 动态像素动画（行走/发呆/衰老）
- [ ] 场景分层远景近景
- [ ] 完整镜头语言系统
- [ ] 季节环境动态（花瓣/落雪/落叶）

---

## 七、改造后核心观感差异

| 维度 | 旧界面（农场感） | 改造后（去月球电影感） |
|------|----------------|---------------------|
| **画面** | 满屏按钮、数值、面板 | 全屏像素场景为主 |
| **UI** | 所有功能摊开 | UI 极简可隐藏 |
| **氛围** | 静态无氛围 | 光影、镜头、时间氛围联动 |
| **交互** | 多点按钮、嵌套菜单 | 单点交互、克制弹窗 |
| **沉浸** | 随时被打断 | 只在剧情节点弹出弹窗 |
| **主题** | 操作工具 | 服务"时间、人生、叙事"核心 |
| **观感** | 廉价休闲 | 电影级叙事 |

---

## 八、与小镇三大边界的契合度

### 边界一：永不加入焦虑驱动设计
✅ 极简 UI 减少视觉噪音  
✅ 弹窗按需弹出，不催促操作  

### 边界二：永不输出评判性结论
✅ 镜头语言中立呈现  
✅ 光影氛围客观展示时间流逝  

### 边界三：永不强迫用户行动
✅ 快进功能藏进折叠菜单  
✅ 单点交互，无强制操作路径  

### 方向校验
**"它是在增加'我要足够好才配'的焦虑，还是在强化'你这样就很好'的底气？"**  
→ 电影感叙事让玩家沉浸于**自己的人生故事**，而非被功能绑架。
