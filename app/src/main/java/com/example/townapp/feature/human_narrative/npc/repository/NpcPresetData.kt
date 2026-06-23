package com.example.townapp.feature.human_narrative.npc.repository

import com.example.townapp.feature.human_narrative.npc.model.NpcBase

/**
 * NPC 预设数据
 *
 * 对应 assets/text/npc/name.json 和 job.json
 * 共30位小镇居民，涵盖各行各业
 */
object NpcPresetData {

    fun getAllPresetNpcs(): List<NpcBase> = listOf(
        // 1-10: 稳定职业群体
        NpcBase(id = 1, nameTextId = 1, jobId = 1, seasonId = 1, x = 120, y = 80, portraitId = 1, dialogGroupId = 1, districtId = 1, tonePaletteId = 1, vignetteStrength = 0.15f, saturationBias = 0.9f),
        NpcBase(id = 2, nameTextId = 2, jobId = 2, seasonId = 1, x = 200, y = 150, portraitId = 2, dialogGroupId = 1, districtId = 2, tonePaletteId = 3, vignetteStrength = 0.10f, saturationBias = 0.85f),
        NpcBase(id = 3, nameTextId = 3, jobId = 3, seasonId = 1, x = 300, y = 200, portraitId = 3, dialogGroupId = 2, districtId = 3, tonePaletteId = 2, vignetteStrength = 0.25f, saturationBias = 0.80f),
        NpcBase(id = 4, nameTextId = 4, jobId = 4, seasonId = 1, x = 80, y = 300, portraitId = 4, dialogGroupId = 2, districtId = 4, tonePaletteId = 2, vignetteStrength = 0.20f, saturationBias = 0.85f),
        NpcBase(id = 5, nameTextId = 5, jobId = 5, seasonId = 1, x = 180, y = 250, portraitId = 5, dialogGroupId = 3, districtId = 1, tonePaletteId = 4, vignetteStrength = 0.12f, saturationBias = 1.0f),
        NpcBase(id = 6, nameTextId = 6, jobId = 6, seasonId = 1, x = 250, y = 180, portraitId = 6, dialogGroupId = 4, districtId = 2, tonePaletteId = 7, vignetteStrength = 0.18f, saturationBias = 1.1f),
        NpcBase(id = 7, nameTextId = 7, jobId = 7, seasonId = 1, x = 350, y = 280, portraitId = 7, dialogGroupId = 5, districtId = 3, tonePaletteId = 2, vignetteStrength = 0.22f, saturationBias = 0.90f),
        NpcBase(id = 8, nameTextId = 8, jobId = 8, seasonId = 1, x = 150, y = 350, portraitId = 8, dialogGroupId = 6, districtId = 1, tonePaletteId = 2, vignetteStrength = 0.20f, saturationBias = 0.80f),
        NpcBase(id = 9, nameTextId = 9, jobId = 9, seasonId = 1, x = 400, y = 150, portraitId = 9, dialogGroupId = 5, districtId = 3, tonePaletteId = 2, vignetteStrength = 0.25f, saturationBias = 0.95f),
        NpcBase(id = 10, nameTextId = 10, jobId = 10, seasonId = 1, x = 100, y = 200, portraitId = 10, dialogGroupId = 3, districtId = 4, tonePaletteId = 4, vignetteStrength = 0.15f, saturationBias = 1.0f),

        // 11-20: 弹性职业与过渡期
        NpcBase(id = 11, nameTextId = 11, jobId = 11, seasonId = 1, x = 280, y = 320, portraitId = 11, dialogGroupId = 7, districtId = 2, tonePaletteId = 7, vignetteStrength = 0.18f, saturationBias = 1.1f),
        NpcBase(id = 12, nameTextId = 12, jobId = 12, seasonId = 1, x = 60, y = 400, portraitId = 12, dialogGroupId = 8, districtId = 1, tonePaletteId = 5, vignetteStrength = 0.30f, saturationBias = 0.70f),
        NpcBase(id = 13, nameTextId = 13, jobId = 13, seasonId = 1, x = 220, y = 100, portraitId = 13, dialogGroupId = 9, districtId = 1, tonePaletteId = 8, vignetteStrength = 0.15f, saturationBias = 0.95f),
        NpcBase(id = 14, nameTextId = 14, jobId = 14, seasonId = 1, x = 320, y = 350, portraitId = 14, dialogGroupId = 4, districtId = 2, tonePaletteId = 7, vignetteStrength = 0.12f, saturationBias = 1.2f),
        NpcBase(id = 15, nameTextId = 15, jobId = 15, seasonId = 1, x = 420, y = 220, portraitId = 15, dialogGroupId = 5, districtId = 3, tonePaletteId = 2, vignetteStrength = 0.22f, saturationBias = 0.90f),
        NpcBase(id = 16, nameTextId = 16, jobId = 16, seasonId = 1, x = 90, y = 280, portraitId = 16, dialogGroupId = 6, districtId = 4, tonePaletteId = 2, vignetteStrength = 0.25f, saturationBias = 0.75f),
        NpcBase(id = 17, nameTextId = 17, jobId = 17, seasonId = 1, x = 240, y = 130, portraitId = 17, dialogGroupId = 1, districtId = 2, tonePaletteId = 3, vignetteStrength = 0.10f, saturationBias = 0.90f),
        NpcBase(id = 18, nameTextId = 18, jobId = 18, seasonId = 1, x = 170, y = 180, portraitId = 18, dialogGroupId = 3, districtId = 1, tonePaletteId = 4, vignetteStrength = 0.15f, saturationBias = 1.0f),
        NpcBase(id = 19, nameTextId = 19, jobId = 19, seasonId = 1, x = 310, y = 90, portraitId = 19, dialogGroupId = 10, districtId = 2, tonePaletteId = 1, vignetteStrength = 0.12f, saturationBias = 0.85f),
        NpcBase(id = 20, nameTextId = 20, jobId = 20, seasonId = 1, x = 130, y = 150, portraitId = 20, dialogGroupId = 3, districtId = 1, tonePaletteId = 8, vignetteStrength = 0.15f, saturationBias = 1.05f),

        // 21-30: 自雇与小微经营
        NpcBase(id = 21, nameTextId = 21, jobId = 21, seasonId = 1, x = 200, y = 220, portraitId = 21, dialogGroupId = 3, districtId = 1, tonePaletteId = 4, vignetteStrength = 0.14f, saturationBias = 1.0f),
        NpcBase(id = 22, nameTextId = 22, jobId = 22, seasonId = 1, x = 380, y = 300, portraitId = 22, dialogGroupId = 6, districtId = 3, tonePaletteId = 2, vignetteStrength = 0.22f, saturationBias = 0.85f),
        NpcBase(id = 23, nameTextId = 23, jobId = 23, seasonId = 1, x = 260, y = 140, portraitId = 23, dialogGroupId = 7, districtId = 2, tonePaletteId = 7, vignetteStrength = 0.18f, saturationBias = 1.0f),
        NpcBase(id = 24, nameTextId = 24, jobId = 24, seasonId = 1, x = 110, y = 320, portraitId = 24, dialogGroupId = 9, districtId = 1, tonePaletteId = 8, vignetteStrength = 0.15f, saturationBias = 0.90f),
        NpcBase(id = 25, nameTextId = 25, jobId = 25, seasonId = 1, x = 340, y = 250, portraitId = 25, dialogGroupId = 3, districtId = 3, tonePaletteId = 4, vignetteStrength = 0.15f, saturationBias = 1.0f),
        NpcBase(id = 26, nameTextId = 26, jobId = 26, seasonId = 1, x = 450, y = 180, portraitId = 26, dialogGroupId = 5, districtId = 3, tonePaletteId = 2, vignetteStrength = 0.20f, saturationBias = 0.95f),
        NpcBase(id = 27, nameTextId = 27, jobId = 27, seasonId = 1, x = 370, y = 350, portraitId = 27, dialogGroupId = 2, districtId = 4, tonePaletteId = 2, vignetteStrength = 0.28f, saturationBias = 0.80f),
        NpcBase(id = 28, nameTextId = 28, jobId = 28, seasonId = 1, x = 190, y = 380, portraitId = 28, dialogGroupId = 5, districtId = 3, tonePaletteId = 4, vignetteStrength = 0.16f, saturationBias = 0.95f),
        NpcBase(id = 29, nameTextId = 29, jobId = 29, seasonId = 1, x = 80, y = 250, portraitId = 29, dialogGroupId = 3, districtId = 4, tonePaletteId = 4, vignetteStrength = 0.14f, saturationBias = 1.0f),
        NpcBase(id = 30, nameTextId = 30, jobId = 30, seasonId = 1, x = 410, y = 320, portraitId = 30, dialogGroupId = 6, districtId = 3, tonePaletteId = 2, vignetteStrength = 0.24f, saturationBias = 0.85f)
    )

    fun getPresetCount(): Int = 30
}
