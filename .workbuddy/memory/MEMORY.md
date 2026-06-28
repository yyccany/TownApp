# TownApp 项目记忆

## 项目概述
- Android 认知字典应用，品牌名 **Vera**（原"万物薪俸小镇"），Jetpack Compose + Material3
- 英文副标题：Cognition Dictionary · Train Your Judgment
- 包名 `com.example.townapp`，minSdk 24，targetSdk 36
- Kotlin 1.9.25，Compose BOM 2024.09.00，Gson 解析 JSON
- 词条数据在 `app/src/main/assets/idioms/idioms.json`，当前350条（5种entryType：MODERN_BELIEF 145 / IDIOM 121 / ACADEMIC 42 / COINED 22 / DILEMMA 20），共350条（2026-06-28累计扩充：165→208→229→271→350，同日完成全量数据质量审计修复）
- **JSON格式规范**（代码端约束，新增词条必须遵守）：townPerspective/distortedTruth=string（`\n\n`分段）；examples=string[]；spotlight={category,text,weight}；category/toxicityLevel=英文枚举名（非中文displayName）；cognitiveBiasTags=string[]

## 设计系统
- 活跃设计Token：`DictionaryTokens`（Theme.kt底部）—— 16dp圆角卡片、4dp阴影、混米白基底 #F8F5EB
- 废弃Token：`TownDesignTokens`（像素风2dp圆角）、`AppColors`/`BrandColors`/`AppDimens`（兼容层）
- 四层文字规范：`DictionaryTokens.Type`（title 18sp Medium深黑 / heading 17sp Bold深灰 / body 14sp常规浅灰 / caption 13sp中灰）
- **已读置灰**：textTitleUnread=#121212 / textTitleRead=#8C8C8C / textBodyUnread=#707070 / textBodyRead=#8C8C8C
- Tab栏：下划线高亮模式（选中Bold深色+底部2dp分类色条 / 未选中Normal浅灰，白底极简无填充无阴影）
- Tab分类图标：10个Canvas线性图标（**2f stroke**），选中深色/未选中灰色，含compass指南针
- 色系对应：毒性等级→标签色（认知误区淡粉/焦虑陷阱浅橙/片面认知浅黄/底层认知浅蓝）；分类→竖条&序号色
- 全图标线条统一：**2f density stroke**
- 卡片无左侧竖条，仅靠右上角标签区分词条类型（极简白底黑线风格）
- 卡片内边距：16dp水平/12-14dp垂直；标题-简介间距4dp
- 标签规范：12sp Medium，横向 8dp/纵向 3dp，8dp 圆角

## 架构（2026-06-28统一引擎后）
- 无底部导航栏，`ModalNavigationDrawer` 侧边抽屉收纳低频功能
- `StandardTopBar` 统一顶部Header（左汉堡/中标题/右视图切换+搜索）
- 首页：横向Tab栏（11个，含「主体性工具」Tab）+ 全屏词条列表，视图可切换卡片/紧凑
- 详情页：统一 `IdiomCriticScreen`，底部连续阅读（上/下一篇同分类循环 + 随机全库词条）
- **统一分析引擎**：所有内容（成语/自造概念/学术名词/现代观念/社交困境）共用同一套三层唯物辩证结构（唯物史观→社会结构批判→认知心理学），通过 `entryType` 字段标记入口类型，不另建独立数据模型
- 搜索：右上角图标 → 全屏覆盖面板（MainActivity层级渲染）
- 导航：状态驱动（currentScreen + selectedIdiomId），未用Navigation Compose库

## 关键文件
- `MainActivity.kt` — TownApp() 主入口，DrawerState + 搜索覆盖层 + 已读状态管理 + About路由
- `ui/components/StandardTopBar.kt` — 标准化顶栏组件
- `ui/components/AppDrawer.kt` — 侧边抽屉（深色横幅Vera品牌区+图标右对齐菜单，about已激活）
- `ui/screens/DictionaryHomeScreen.kt` — 首页（Tab+分类图标+三视图统一卡片+搜索面板+clickableWithScale）
- `ui/screens/IdiomCriticScreen.kt` — 详情页（连续阅读+折叠模块+可展开文本，所有entryType统一渲染）
- `ui/screens/SettingsScreen.kt` — 设置页
- `ui/screens/AboutVeraScreen.kt` — 关于Vera页面（用户可见轻量化版，五层通俗化+三大核心功能）
- `ui/Theme.kt` — DictionaryTokens 设计系统
- `data/idiom/IdiomCritiqueLibrary.kt` — IdiomCritiqueLibrary 单例 + EntryType 枚举 + IdiomCritique 数据模型
- `data/idiom/IdiomAssetLoader.kt` — JSON解析（含entryType字段）
- `assets/idioms/idioms.json` — 165条词条数据（IDIOM 103 / COINED 16 / ACADEMIC 13 / MODERN_BELIEF 32 / DILEMMA 1）

## 内容规范
- `docs/VERA_CONTENT_SPEC.md` — **内容撰写唯一标准手册**（v3.0），含五层理论框架、7字段模板、写作红线与禁用话术、查重校验标准、JSON数据模型。面向全体开发/内容撰写者。

## 构建命令
- `./gradlew assembleDebug --no-daemon -q`（Windows bash环境）
- SDK路径：`C:\Users\limkz\AppData\Local\Android\Sdk`
