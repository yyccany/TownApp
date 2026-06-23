package com.example.townapp.data.repository

import android.content.Context
import com.example.townapp.data.*
import com.example.townapp.data.database.entity.UserBodyState
import com.example.townapp.data.database.entity.UserMentalState
import com.example.townapp.data.database.entity.UserSpaceState
import com.example.townapp.domain.util.TimeState
import com.example.townapp.ui.viewmodel.AutoLifeTier
import com.example.townapp.ui.viewmodel.ShoppingMode
import org.json.JSONArray
import org.json.JSONObject

/**
 * 游戏存档仓库（轻量级本地文件 JSON 序列化）
 *
 * 设计原则：
 * - 与 SimulationRepository 解耦，独立负责持久化
 * - 仅保存核心玩法状态，不保存 UI 瞬态
 * - 使用 org.json（Android 内置），不引入额外依赖
 */
class GameSaveRepository(private val context: Context) {

    companion object {
        private const val SAVE_FILE_NAME = "town_game_save.json"
        private const val SAVE_VERSION = 1
    }

    /**
     * 游戏状态快照（仅包含核心持久化字段）
     */
    data class GameSnapshot(
        val version: Int = SAVE_VERSION,
        val timestamp: Long = System.currentTimeMillis(),
        val gameDay: Int = 1,
        val currentWeek: Int = 1,
        val playerAge: Int = 19,
        val timeState: TimeState = TimeState(),
        val spaceState: UserSpaceState = UserSpaceState(userId = 1),
        val bodyState: UserBodyState = UserBodyState(userId = 1),
        val mentalState: UserMentalState = UserMentalState(userId = 1),
        val shoppingMode: String = ShoppingMode.BALANCED.name,
        val autoLifeTier: String = AutoLifeTier.NORMAL.name,
        val medicalState: MedicalRuntimeState = MedicalRuntimeState(),
        val genderState: GenderRuntimeState = GenderRuntimeState.forSex(BiologicalSex.MALE),
        val loveState: LoveRelationshipState = LoveRelationshipState(),
        val reformerState: ReformerUnlockState = ReformerUnlockState(),
        val eventLog: List<String> = emptyList(),
        val weeklySocialScore: Int = 0
    )

    /** 保存快照到本地文件 */
    fun save(snapshot: GameSnapshot) {
        try {
            val json = JSONObject().apply {
                put("version", snapshot.version)
                put("timestamp", snapshot.timestamp)
                put("gameDay", snapshot.gameDay)
                put("currentWeek", snapshot.currentWeek)
                put("playerAge", snapshot.playerAge)
                put("timeState", timeStateToJson(snapshot.timeState))
                put("spaceState", spaceStateToJson(snapshot.spaceState))
                put("bodyState", bodyStateToJson(snapshot.bodyState))
                put("mentalState", mentalStateToJson(snapshot.mentalState))
                put("shoppingMode", snapshot.shoppingMode)
                put("autoLifeTier", snapshot.autoLifeTier)
                put("medicalState", medicalStateToJson(snapshot.medicalState))
                put("genderState", genderStateToJson(snapshot.genderState))
                put("loveState", loveStateToJson(snapshot.loveState))
                put("reformerState", reformerStateToJson(snapshot.reformerState))
                put("eventLog", JSONArray(snapshot.eventLog))
                put("weeklySocialScore", snapshot.weeklySocialScore)
            }
            context.openFileOutput(SAVE_FILE_NAME, Context.MODE_PRIVATE).use {
                it.write(json.toString().toByteArray(Charsets.UTF_8))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /** 从本地文件读取快照，失败或不存在返回 null */
    fun load(): GameSnapshot? {
        return try {
            val file = context.getFileStreamPath(SAVE_FILE_NAME)
            if (!file.exists()) return null
            val content = context.openFileInput(SAVE_FILE_NAME).bufferedReader(Charsets.UTF_8).use { it.readText() }
            val json = JSONObject(content)
            if (json.optInt("version", 0) != SAVE_VERSION) return null

            GameSnapshot(
                version = json.getInt("version"),
                timestamp = json.getLong("timestamp"),
                gameDay = json.getInt("gameDay"),
                currentWeek = json.getInt("currentWeek"),
                playerAge = json.getInt("playerAge"),
                timeState = jsonToTimeState(json.getJSONObject("timeState")),
                spaceState = jsonToSpaceState(json.getJSONObject("spaceState")),
                bodyState = jsonToBodyState(json.getJSONObject("bodyState")),
                mentalState = jsonToMentalState(json.getJSONObject("mentalState")),
                shoppingMode = json.getString("shoppingMode"),
                autoLifeTier = json.getString("autoLifeTier"),
                medicalState = jsonToMedicalState(json.getJSONObject("medicalState")),
                genderState = jsonToGenderState(json.getJSONObject("genderState")),
                loveState = jsonToLoveState(json.getJSONObject("loveState")),
                reformerState = jsonToReformerState(json.getJSONObject("reformerState")),
                eventLog = jsonArrayToStringList(json.getJSONArray("eventLog")),
                weeklySocialScore = json.getInt("weeklySocialScore")
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /** 是否存在有效存档 */
    fun hasSave(): Boolean = context.getFileStreamPath(SAVE_FILE_NAME).exists()

    /** 删除存档 */
    fun deleteSave() {
        context.deleteFile(SAVE_FILE_NAME)
    }

    // ============================================================
    // 序列化辅助：TimeState
    // ============================================================
    private fun timeStateToJson(state: TimeState) = JSONObject().apply {
        put("hour", state.hour)
        put("day", state.day)
        put("weekOfMonth", state.weekOfMonth)
        put("weekDay", state.weekDay)
        put("month", state.month)
        put("year", state.year)
        put("totalDays", state.totalDays)
        put("totalWeeks", state.totalWeeks)
        put("age", state.age)
        put("lifeStage", state.lifeStage.name)
        put("season", state.season.name)
    }

    private fun jsonToTimeState(json: JSONObject) = TimeState(
        hour = json.getInt("hour"),
        day = json.getInt("day"),
        weekOfMonth = json.getInt("weekOfMonth"),
        weekDay = json.getInt("weekDay"),
        month = json.getInt("month"),
        year = json.getInt("year"),
        totalDays = json.getInt("totalDays"),
        totalWeeks = json.getInt("totalWeeks"),
        age = json.getInt("age"),
        lifeStage = LifeStage.valueOf(json.getString("lifeStage")),
        season = Season.valueOf(json.getString("season"))
    )

    // ============================================================
    // 序列化辅助：UserSpaceState
    // ============================================================
    private fun spaceStateToJson(state: UserSpaceState) = JSONObject().apply {
        put("addressName", state.addressName)
        put("cityTier", state.cityTier)
        put("monthlyRent", state.monthlyRent)
        put("monthlyIncome", state.monthlyIncome)
        put("areaSqm", state.areaSqm)
        put("light", state.light)
        put("noise", state.noise)
        put("cleanliness", state.cleanliness)
        put("ventilation", state.ventilation)
        put("avgTemperatureCelsius", state.avgTemperatureCelsius)
        put("furnitureLevel", state.furnitureLevel)
        put("safety", state.safety)
        put("privacy", state.privacy)
        put("neighborhoodQuality", state.neighborhoodQuality)
        put("commuteMinutesOneWay", state.commuteMinutesOneWay)
        put("workHoursPerDay", state.workHoursPerDay)
        put("workDaysPerWeek", state.workDaysPerWeek)
        put("currentSavings", state.currentSavings)
        put("hasDebt", state.hasDebt)
        put("debtAmount", state.debtAmount)
        put("daysLivedHere", state.daysLivedHere)
    }

    private fun jsonToSpaceState(json: JSONObject) = UserSpaceState(
        userId = 1,
        addressName = json.getString("addressName"),
        cityTier = json.getInt("cityTier"),
        monthlyRent = json.getDouble("monthlyRent"),
        monthlyIncome = json.getDouble("monthlyIncome"),
        areaSqm = json.getInt("areaSqm"),
        light = json.getInt("light"),
        noise = json.getInt("noise"),
        cleanliness = json.getInt("cleanliness"),
        ventilation = json.getInt("ventilation"),
        avgTemperatureCelsius = json.getInt("avgTemperatureCelsius"),
        furnitureLevel = json.getInt("furnitureLevel"),
        safety = json.getInt("safety"),
        privacy = json.getInt("privacy"),
        neighborhoodQuality = json.getInt("neighborhoodQuality"),
        commuteMinutesOneWay = json.getInt("commuteMinutesOneWay"),
        workHoursPerDay = json.getInt("workHoursPerDay"),
        workDaysPerWeek = json.getInt("workDaysPerWeek"),
        currentSavings = json.getDouble("currentSavings"),
        hasDebt = json.getBoolean("hasDebt"),
        debtAmount = json.getDouble("debtAmount"),
        daysLivedHere = json.getInt("daysLivedHere")
    )

    // ============================================================
    // 序列化辅助：UserBodyState
    // ============================================================
    private fun bodyStateToJson(state: UserBodyState) = JSONObject().apply {
        put("satiety", state.satiety)
        put("nutritionBalance", state.nutritionBalance)
        put("gastroBurden", state.gastroBurden)
        put("bloodSugar", state.bloodSugar)
        put("bloodPressure", state.bloodPressure)
        put("heartRate", state.heartRate)
        put("bodyTemperature", state.bodyTemperature)
        put("bodyFatPercent", state.bodyFatPercent)
        put("cholesterolMg", state.cholesterolMg)
        put("comfortLevel", state.comfortLevel)
        put("skinStatus", state.skinStatus)
        put("toxinLevel", state.toxinLevel)
        put("heavyMetalAccum", state.heavyMetalAccum)
        put("immuneLevel", state.immuneLevel)
        put("energy", state.energy)
        put("healthScore", state.healthScore)
        put("mood", state.mood)
        put("dailyWorkHours", state.dailyWorkHours)
        put("fatigueLevel", state.fatigueLevel)
        put("overtimeStreakDays", state.overtimeStreakDays)
        put("weeklyWorkHours", state.weeklyWorkHours)
        put("totalOvertimeHours", state.totalOvertimeHours)
    }

    private fun jsonToBodyState(json: JSONObject) = UserBodyState(
        userId = 1,
        satiety = json.getInt("satiety"),
        nutritionBalance = json.getInt("nutritionBalance"),
        gastroBurden = json.getInt("gastroBurden"),
        bloodSugar = json.getDouble("bloodSugar"),
        bloodPressure = json.getInt("bloodPressure"),
        heartRate = json.getInt("heartRate"),
        bodyTemperature = json.getDouble("bodyTemperature"),
        bodyFatPercent = json.getDouble("bodyFatPercent"),
        cholesterolMg = json.getDouble("cholesterolMg"),
        comfortLevel = json.getInt("comfortLevel"),
        skinStatus = json.getInt("skinStatus"),
        toxinLevel = json.getDouble("toxinLevel"),
        heavyMetalAccum = json.getDouble("heavyMetalAccum"),
        immuneLevel = json.getInt("immuneLevel"),
        energy = json.getInt("energy"),
        healthScore = json.getInt("healthScore"),
        mood = json.getInt("mood"),
        dailyWorkHours = json.getDouble("dailyWorkHours"),
        fatigueLevel = json.getInt("fatigueLevel"),
        overtimeStreakDays = json.getInt("overtimeStreakDays"),
        weeklyWorkHours = json.getDouble("weeklyWorkHours"),
        totalOvertimeHours = json.getDouble("totalOvertimeHours")
    )

    // ============================================================
    // 序列化辅助：UserMentalState
    // ============================================================
    private fun mentalStateToJson(state: UserMentalState) = JSONObject().apply {
        put("happiness", state.happiness)
        put("anxiety", state.anxiety)
        put("loneliness", state.loneliness)
        put("senseOfControl", state.senseOfControl)
        put("selfEsteem", state.selfEsteem)
        put("meaning", state.meaning)
        put("energyLevel", state.energyLevel)
        put("sleepQuality", state.sleepQuality)
        put("socialFulfillment", state.socialFulfillment)
        put("creativeFlow", state.creativeFlow)
        put("daysSinceSocialContact", state.daysSinceSocialContact)
        put("daysSinceAchievement", state.daysSinceAchievement)
        put("daysInNegativeState", state.daysInNegativeState)
        put("workStress", state.workStress)
        put("lifePathType", state.lifePathType)
        put("workSatisfaction", state.workSatisfaction)
        put("burnoutRisk", state.burnoutRisk)
    }

    private fun jsonToMentalState(json: JSONObject) = UserMentalState(
        userId = 1,
        happiness = json.getInt("happiness"),
        anxiety = json.getInt("anxiety"),
        loneliness = json.getInt("loneliness"),
        senseOfControl = json.getInt("senseOfControl"),
        selfEsteem = json.getInt("selfEsteem"),
        meaning = json.getInt("meaning"),
        energyLevel = json.getInt("energyLevel"),
        sleepQuality = json.getInt("sleepQuality"),
        socialFulfillment = json.getInt("socialFulfillment"),
        creativeFlow = json.getInt("creativeFlow"),
        daysSinceSocialContact = json.getInt("daysSinceSocialContact"),
        daysSinceAchievement = json.getInt("daysSinceAchievement"),
        daysInNegativeState = json.getInt("daysInNegativeState"),
        workStress = json.getInt("workStress"),
        lifePathType = json.getString("lifePathType"),
        workSatisfaction = json.getInt("workSatisfaction"),
        burnoutRisk = json.getInt("burnoutRisk")
    )

    // ============================================================
    // 序列化辅助：MedicalRuntimeState / ActiveDisease
    // ============================================================
    private fun medicalStateToJson(state: MedicalRuntimeState) = JSONObject().apply {
        put("constitutionScore", state.constitutionScore)
        put("lifetimeMedicalCost", state.lifetimeMedicalCost)
        put("defaultTreatmentRoute", state.defaultTreatmentRoute.name)
        val diseases = JSONArray()
        state.activeDiseases.forEach { d ->
            diseases.put(JSONObject().apply {
                put("type", d.type.name)
                put("currentSeverity", d.currentSeverity)
                put("daysActive", d.daysActive)
                put("isChronic", d.isChronic)
                put("hasPermanentSequela", d.hasPermanentSequela)
                put("activeTreatment", d.activeTreatment?.name)
                put("activeDrugTier", d.activeDrugTier?.name)
                put("treatmentDays", d.treatmentDays)
                put("onsetAge", d.onsetAge)
            })
        }
        put("activeDiseases", diseases)
        val sequelae = JSONArray()
        state.permanentSequelae.forEach { sequelae.put(it.name) }
        put("permanentSequelae", sequelae)
    }

    private fun jsonToMedicalState(json: JSONObject): MedicalRuntimeState {
        val diseases = mutableListOf<ActiveDisease>()
        val diseaseArray = json.getJSONArray("activeDiseases")
        for (i in 0 until diseaseArray.length()) {
            val obj = diseaseArray.getJSONObject(i)
            diseases.add(ActiveDisease(
                type = DiseaseType.valueOf(obj.getString("type")),
                currentSeverity = obj.getInt("currentSeverity"),
                daysActive = obj.getInt("daysActive"),
                isChronic = obj.getBoolean("isChronic"),
                hasPermanentSequela = obj.getBoolean("hasPermanentSequela"),
                activeTreatment = if (obj.isNull("activeTreatment")) null else TreatmentRoute.valueOf(obj.getString("activeTreatment")),
                activeDrugTier = if (obj.isNull("activeDrugTier")) null else DrugTier.valueOf(obj.getString("activeDrugTier")),
                treatmentDays = obj.getInt("treatmentDays"),
                onsetAge = obj.getInt("onsetAge")
            ))
        }
        val sequelae = mutableListOf<DiseaseType>()
        val seqArray = json.getJSONArray("permanentSequelae")
        for (i in 0 until seqArray.length()) {
            sequelae.add(DiseaseType.valueOf(seqArray.getString(i)))
        }
        return MedicalRuntimeState(
            activeDiseases = diseases,
            permanentSequelae = sequelae,
            defaultTreatmentRoute = TreatmentRoute.valueOf(json.getString("defaultTreatmentRoute")),
            constitutionScore = json.getInt("constitutionScore"),
            lifetimeMedicalCost = json.getDouble("lifetimeMedicalCost")
        )
    }

    // ============================================================
    // 序列化辅助：GenderRuntimeState / MenstrualCycleState
    // ============================================================
    private fun genderStateToJson(state: GenderRuntimeState) = JSONObject().apply {
        put("sex", state.sex.name)
        state.menstrualCycle?.let { cycle ->
            put("menstrualCurrentDay", cycle.currentDay)
            put("menstrualCycleLength", cycle.cycleLength)
            put("menstrualBleedingDays", cycle.bleedingDays)
            put("menstrualPainLevel", cycle.painLevel.name)
            put("menstrualIsMenstruating", cycle.isMenstruating)
            put("menstrualIsOvulating", cycle.isOvulating)
            put("menstrualUsedPainkiller", cycle.usedPainkiller)
        }
        put("childbirthPhase", state.childbirth.phase.name)
        put("childbirthPregnancyDaysRemaining", state.childbirth.pregnancyDaysRemaining)
        put("childbirthPostpartumDaysRemaining", state.childbirth.postpartumDaysRemaining)
        put("childbirthHasPostpartumSupplies", state.childbirth.hasPostpartumSupplies)
        put("childbirthHasChronicWeakness", state.childbirth.hasChronicWeakness)
        put("hasChosenCelibacy", state.hasChosenCelibacy)
        put("hasChosenDINK", state.hasChosenDINK)
        put("hasChosenLateMarriage", state.hasChosenLateMarriage)
        put("accumulatedSuppression", state.accumulatedSuppression)
    }

    private fun jsonToGenderState(json: JSONObject): GenderRuntimeState {
        val sex = BiologicalSex.valueOf(json.getString("sex"))
        val base = GenderRuntimeState.forSex(sex)
        val cycle = if (json.has("menstrualCurrentDay")) {
            MenstrualCycleState(
                currentDay = json.getInt("menstrualCurrentDay"),
                cycleLength = json.getInt("menstrualCycleLength"),
                bleedingDays = json.getInt("menstrualBleedingDays"),
                painLevel = PeriodPainLevel.valueOf(json.getString("menstrualPainLevel")),
                isMenstruating = json.getBoolean("menstrualIsMenstruating"),
                isOvulating = json.getBoolean("menstrualIsOvulating"),
                usedPainkiller = json.getBoolean("menstrualUsedPainkiller")
            )
        } else null
        return base.copy(
            sex = sex,
            menstrualCycle = cycle,
            childbirth = ChildbirthState(
                phase = ChildbirthPhase.valueOf(json.getString("childbirthPhase")),
                pregnancyDaysRemaining = json.getInt("childbirthPregnancyDaysRemaining"),
                postpartumDaysRemaining = json.getInt("childbirthPostpartumDaysRemaining"),
                hasPostpartumSupplies = json.getBoolean("childbirthHasPostpartumSupplies"),
                hasChronicWeakness = json.getBoolean("childbirthHasChronicWeakness")
            ),
            hasChosenCelibacy = json.getBoolean("hasChosenCelibacy"),
            hasChosenDINK = json.getBoolean("hasChosenDINK"),
            hasChosenLateMarriage = json.getBoolean("hasChosenLateMarriage"),
            accumulatedSuppression = json.getInt("accumulatedSuppression")
        )
    }

    // ============================================================
    // 序列化辅助：LoveRelationshipState
    // ============================================================
    private fun loveStateToJson(state: LoveRelationshipState) = JSONObject().apply {
        put("status", state.status.name)
        put("partnerDescription", state.partnerDescription)
        put("loveDays", state.loveDays)
        put("marriageDays", state.marriageDays)
        put("isLongDistance", state.isLongDistance)
        put("longDistanceDays", state.longDistanceDays)
        put("maritalConflictDays", state.maritalConflictDays)
        put("happinessBonus", state.happinessBonus)
        put("relationshipStress", state.relationshipStress)
        put("hasCareerImpact", state.hasCareerImpact)
        put("hasAbandonedIdeal", state.hasAbandonedIdeal)
        put("partnerSupportsReform", state.partnerSupportsReform)
        put("triggeredEventIds", JSONArray(state.triggeredEventIds))
        put("hasExperiencedHeartbreak", state.hasExperiencedHeartbreak)
        put("heartbreakRecoveryPhase", state.heartbreakRecoveryPhase.name)
        put("hasChildren", state.hasChildren)
        put("childrenCount", state.childrenCount)
    }

    private fun jsonToLoveState(json: JSONObject) = LoveRelationshipState(
        status = LoveStatus.valueOf(json.getString("status")),
        partnerDescription = json.getString("partnerDescription"),
        loveDays = json.getInt("loveDays"),
        marriageDays = json.getInt("marriageDays"),
        isLongDistance = json.getBoolean("isLongDistance"),
        longDistanceDays = json.getInt("longDistanceDays"),
        maritalConflictDays = json.getInt("maritalConflictDays"),
        happinessBonus = json.getInt("happinessBonus"),
        relationshipStress = json.getInt("relationshipStress"),
        hasCareerImpact = json.getBoolean("hasCareerImpact"),
        hasAbandonedIdeal = json.getBoolean("hasAbandonedIdeal"),
        partnerSupportsReform = json.getBoolean("partnerSupportsReform"),
        triggeredEventIds = jsonArrayToStringList(json.getJSONArray("triggeredEventIds")),
        hasExperiencedHeartbreak = json.getBoolean("hasExperiencedHeartbreak"),
        heartbreakRecoveryPhase = HeartbreakPhase.valueOf(json.getString("heartbreakRecoveryPhase")),
        hasChildren = json.getBoolean("hasChildren"),
        childrenCount = json.getInt("childrenCount")
    )

    // ============================================================
    // 序列化辅助：ReformerUnlockState
    // ============================================================
    private fun reformerStateToJson(state: ReformerUnlockState) = JSONObject().apply {
        put("isReformer", state.isReformer)
        put("form", state.form?.name)
        put("cognitiveMet", state.cognitiveMet)
        put("beliefDurable", state.beliefDurable)
        put("eraWindowOpen", state.eraWindowOpen)
        put("branchClosed", state.branchClosed)
        put("closureReason", state.closureReason)
        put("completedActions", JSONArray(state.completedActions))
        put("failedAttempts", state.failedAttempts)
        put("isLegacySeed", state.isLegacySeed)
        put("legacyArchive", state.legacyArchive)
        put("publicSupport", state.publicSupport)
        put("resistanceIntensity", state.resistanceIntensity)
    }

    private fun jsonToReformerState(json: JSONObject) = ReformerUnlockState(
        isReformer = json.getBoolean("isReformer"),
        form = if (json.isNull("form")) null else ReformerForm.valueOf(json.getString("form")),
        cognitiveMet = json.getBoolean("cognitiveMet"),
        beliefDurable = json.getBoolean("beliefDurable"),
        eraWindowOpen = json.getBoolean("eraWindowOpen"),
        branchClosed = json.getBoolean("branchClosed"),
        closureReason = json.getString("closureReason"),
        completedActions = jsonArrayToStringList(json.getJSONArray("completedActions")),
        failedAttempts = json.getInt("failedAttempts"),
        isLegacySeed = json.getBoolean("isLegacySeed"),
        legacyArchive = json.getString("legacyArchive"),
        publicSupport = json.getInt("publicSupport"),
        resistanceIntensity = json.getInt("resistanceIntensity")
    )

    // ============================================================
    // 通用工具
    // ============================================================
    private fun jsonArrayToStringList(array: JSONArray): List<String> =
        (0 until array.length()).map { array.getString(it) }
}
