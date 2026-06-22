package com.example.townapp.npc.model

/**
 * NPC 基础信息实体
 *
 * 严格遵循小镇项目铁律：
 * 1. Entity 只存数字 / ID / 状态
 * 2. 严禁存 name、desc、dialog 等长文本
 * 3. 长文本一律走 TextAssetLoader 读取 assets 静态 JSON
 *
 * 字段分组（按用途）：
 * - 基础标识：id / nameTextId / jobId / seasonId
 * - 地图定位：x / y / districtId
 * - 资源索引：portraitId / dialogGroupId
 * - 视觉氛围（仅3个数字，对应 NpcMoodOverlay）：
 *   - tonePaletteId：专属色调组 ID
 *   - vignetteStrength：专属暗角强度
 *   - saturationBias：饱和度偏移
 */
data class NpcBase(
    /** NPC 唯一ID */
    val id: Int,

    /** 姓名文本ID（对应 assets/text/npc/name.json 中的 name_xxx） */
    val nameTextId: Int,

    /** 职业ID（对标职业配置表：1=教师、2=医生、3=工人、4=农民、5=店主） */
    val jobId: Int,

    /** 当前所在季节ID（1=春、2=夏、3=秋、4=冬） */
    val seasonId: Int,

    /** 像素地图X坐标 */
    val x: Int,

    /** 像素地图Y坐标 */
    val y: Int,

    /** 头像资源ID（drawable 资源索引） */
    val portraitId: Int,

    /** 对话组ID（决定用哪一组对话 JSON） */
    val dialogGroupId: Int,

    /** 所在区域ID（用于按区域筛选） */
    val districtId: Int = 0,

    // ================== 视觉氛围字段（3个数字，零文本压力） ==================

    /**
     * 专属色调组 ID（对应 assets/text/npc/tone_palette.json）
     * 1=教师温柔米白、2=务工质朴灰黄、3=医生冷静浅蓝、4=创业者亮金高光
     * 5=晚年老人暖棕褪色、6=失意压抑暗调、7=奋斗通透高光、8=治愈邻居淡绿柔光
     */
    val tonePaletteId: Int = 1,

    /**
     * 专属暗角强度 0f ~ 1f（失意人暗角更深、奋斗者暗角更浅）
     * 范围 0.0~0.6
     */
    val vignetteStrength: Float = 0.20f,

    /**
     * 饱和度偏移 0.5f ~ 1.3f（老人低饱和、年轻人高通透）
     * 范围 0.5~1.3
     */
    val saturationBias: Float = 1.0f
)
