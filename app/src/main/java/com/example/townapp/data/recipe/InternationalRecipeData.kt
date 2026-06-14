package com.example.townapp.data.recipe

import com.example.townapp.data.database.entity.RecipeEntity

/**
 * 🌍 国际化食谱数据
 * 确保国内食谱与国外食谱各占50%
 * 按世界地理区域系统分类
 */
object InternationalRecipeData {
    
    // ============================================
    // 🇨🇳 中式食谱（20条）- 涵盖八大菜系
    // ============================================
    private val chineseRecipes = listOf(
        RecipeEntity(
            name = "宫保鸡丁",
            nameEn = "Kung Pao Chicken",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 3,
            cookTimeMinutes = 25,
            servings = 4,
            description = "经典川菜，鸡肉鲜嫩，花生酥脆，麻辣鲜香",
            descriptionEn = "Classic Sichuan dish with tender chicken, crispy peanuts, spicy and numbing flavor",
            ingredients = "[{\"name\":\"鸡肉\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"花生\",\"quantity\":50,\"unit\":\"g\"},{\"name\":\"干辣椒\",\"quantity\":10,\"unit\":\"g\"},{\"name\":\"花椒\",\"quantity\":5,\"unit\":\"g\"}]",
            steps = "[\"鸡肉切丁，用盐、料酒、淀粉腌制15分钟\",\"花生米炸熟备用\",\"葱姜蒜爆香，加入干辣椒和花椒\",\"加入鸡丁翻炒至变色\",\"加入生抽、料酒、糖、醋调味\",\"最后加入花生翻炒均匀\"]",
            tips = "花生最后放可以保持酥脆口感",
            calories = 450.0,
            protein = 35.0,
            fat = 28.0,
            carbs = 18.0,
            rating = 4.8,
            ratingCount = 2580,
            tags = "川菜,麻辣,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "糖醋排骨",
            nameEn = "Sweet and Sour Ribs",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 3,
            cookTimeMinutes = 40,
            servings = 4,
            description = "外酥里嫩，酸甜适口，色泽红亮",
            descriptionEn = "Crispy outside, tender inside, sweet and sour taste, bright red color",
            ingredients = "[{\"name\":\"排骨\",\"quantity\":500,\"unit\":\"g\"},{\"name\":\"白糖\",\"quantity\":80,\"unit\":\"g\"},{\"name\":\"白醋\",\"quantity\":60,\"unit\":\"ml\"},{\"name\":\"番茄酱\",\"quantity\":30,\"unit\":\"g\"}]",
            steps = "[\"排骨焯水去血沫\",\"热油炒糖色，加入排骨翻炒上色\",\"加入葱姜蒜爆香\",\"加入料酒、生抽、醋、糖调味\",\"加水焖煮30分钟\",\"大火收汁至浓稠\"]",
            tips = "炒糖色时火候要控制好，避免炒糊",
            calories = 520.0,
            protein = 28.0,
            fat = 35.0,
            carbs = 25.0,
            rating = 4.9,
            ratingCount = 3120,
            tags = "沪菜,酸甜,家常",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "麻婆豆腐",
            nameEn = "Mapo Tofu",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 2,
            cookTimeMinutes = 15,
            servings = 3,
            description = "麻辣鲜香，豆腐嫩滑，川菜代表",
            descriptionEn = "Spicy and numbing Sichuan classic with silky tofu",
            ingredients = "[{\"name\":\"豆腐\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"猪肉末\",\"quantity\":50,\"unit\":\"g\"},{\"name\":\"郫县豆瓣\",\"quantity\":30,\"unit\":\"g\"},{\"name\":\"花椒\",\"quantity\":5,\"unit\":\"g\"}]",
            steps = "[\"豆腐切块焯水\",\"热油炒香郫县豆瓣和肉末\",\"加入葱姜蒜爆香\",\"加水煮开，加入豆腐\",\"小火煮5分钟\",\"勾芡出锅，撒花椒粉\"]",
            tips = "用嫩豆腐口感更好",
            calories = 280.0,
            protein = 18.0,
            fat = 18.0,
            carbs = 12.0,
            rating = 4.7,
            ratingCount = 2150,
            tags = "川菜,麻辣,下饭",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "清蒸鲈鱼",
            nameEn = "Steamed Sea Bass",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 2,
            cookTimeMinutes = 15,
            servings = 2,
            description = "鱼肉鲜嫩，原汁原味，清淡鲜美",
            descriptionEn = "Tender fish with original flavor, light and delicious",
            ingredients = "[{\"name\":\"鲈鱼\",\"quantity\":1,\"unit\":\"条\"},{\"name\":\"葱\",\"quantity\":2,\"unit\":\"根\"},{\"name\":\"姜\",\"quantity\":10,\"unit\":\"g\"},{\"name\":\"蒸鱼豉油\",\"quantity\":30,\"unit\":\"ml\"}]",
            steps = "[\"鲈鱼处理干净，两面划刀\",\"葱姜切丝铺在鱼身上\",\"水烧开后蒸8-10分钟\",\"倒掉蒸出的水，淋上蒸鱼豉油\",\"热油浇在葱姜丝上\"]",
            tips = "蒸的时间不要太长，否则鱼肉会老",
            calories = 180.0,
            protein = 28.0,
            fat = 6.0,
            carbs = 2.0,
            rating = 4.6,
            ratingCount = 1890,
            tags = "粤菜,清蒸,清淡",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "红烧肉",
            nameEn = "Braised Pork Belly",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 3,
            cookTimeMinutes = 60,
            servings = 4,
            description = "肥而不腻，入口即化，色泽红亮",
            descriptionEn = "Fat but not greasy, melts in mouth, glossy red color",
            ingredients = "[{\"name\":\"五花肉\",\"quantity\":500,\"unit\":\"g\"},{\"name\":\"冰糖\",\"quantity\":40,\"unit\":\"g\"},{\"name\":\"八角\",\"quantity\":2,\"unit\":\"个\"},{\"name\":\"桂皮\",\"quantity\":1,\"unit\":\"小段\"}]",
            steps = "[\"五花肉切块焯水\",\"炒糖色，加入肉块上色\",\"加入葱姜蒜和香料爆香\",\"加入料酒、生抽、老抽\",\"加水焖煮40分钟\",\"大火收汁\"]",
            tips = "选择层次分明的五花肉口感更好",
            calories = 680.0,
            protein = 22.0,
            fat = 58.0,
            carbs = 15.0,
            rating = 4.9,
            ratingCount = 4200,
            tags = "苏菜,红烧,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "蒜蓉西兰花",
            nameEn = "Garlic Broccoli",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 1,
            cookTimeMinutes = 8,
            servings = 2,
            description = "蒜香浓郁，清淡健康，营养丰富",
            descriptionEn = "Strong garlic flavor, light and healthy, nutritious",
            ingredients = "[{\"name\":\"西兰花\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"蒜\",\"quantity\":20,\"unit\":\"g\"},{\"name\":\"盐\",\"quantity\":3,\"unit\":\"g\"}]",
            steps = "[\"西兰花切小朵，焯水备用\",\"热油爆香蒜末\",\"加入西兰花翻炒\",\"加盐调味，翻炒均匀出锅\"]",
            tips = "焯水时间不要太长，保持翠绿",
            calories = 85.0,
            protein = 5.0,
            fat = 4.0,
            carbs = 10.0,
            rating = 4.3,
            ratingCount = 1200,
            tags = "素菜,清淡,健康",
            isVegetarian = true
        ),
        RecipeEntity(
            name = "酸辣土豆丝",
            nameEn = "Shredded Potato with Vinegar",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 1,
            cookTimeMinutes = 10,
            servings = 3,
            description = "酸辣爽口，家常必备，下饭神器",
            descriptionEn = "Sour and spicy, refreshing, perfect with rice",
            ingredients = "[{\"name\":\"土豆\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"干辣椒\",\"quantity\":5,\"unit\":\"g\"},{\"name\":\"醋\",\"quantity\":20,\"unit\":\"ml\"},{\"name\":\"蒜\",\"quantity\":10,\"unit\":\"g\"}]",
            steps = "[\"土豆切丝泡水去淀粉\",\"干辣椒切段，蒜切末\",\"热油爆香辣椒和蒜末\",\"加入土豆丝快速翻炒\",\"加醋和盐调味\",\"翻炒均匀出锅\"]",
            tips = "土豆丝泡水可以保持脆爽口感",
            calories = 120.0,
            protein = 3.0,
            fat = 5.0,
            carbs = 20.0,
            rating = 4.5,
            ratingCount = 2800,
            tags = "家常菜,酸辣,快手",
            isVegetarian = true
        ),
        RecipeEntity(
            name = "鱼香肉丝",
            nameEn = "Yu-Shiang Shredded Pork",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 3,
            cookTimeMinutes = 20,
            servings = 4,
            description = "酸甜微辣，鱼香味浓，经典下饭",
            descriptionEn = "Sweet and sour with fish flavor, classic rice dish",
            ingredients = "[{\"name\":\"猪肉丝\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"胡萝卜\",\"quantity\":50,\"unit\":\"g\"},{\"name\":\"木耳\",\"quantity\":30,\"unit\":\"g\"},{\"name\":\"泡椒\",\"quantity\":20,\"unit\":\"g\"}]",
            steps = "[\"肉丝用淀粉腌制10分钟\",\"胡萝卜和木耳切丝\",\"调鱼香汁：糖、醋、生抽、淀粉、水\",\"热油炒肉丝至变色盛出\",\"炒香泡椒和葱姜蒜\",\"加入配菜翻炒\",\"加入肉丝和鱼香汁\",\"翻炒均匀出锅\"]",
            tips = "鱼香汁的比例很重要，酸甜要平衡",
            calories = 320.0,
            protein = 22.0,
            fat = 18.0,
            carbs = 15.0,
            rating = 4.7,
            ratingCount = 2340,
            tags = "川菜,鱼香,下饭",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "蛋炒饭",
            nameEn = "Egg Fried Rice",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 2,
            cookTimeMinutes = 12,
            servings = 2,
            description = "粒粒分明，蛋香浓郁，家常美味",
            descriptionEn = "Separate grains, rich egg flavor, home-style",
            ingredients = "[{\"name\":\"米饭\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"鸡蛋\",\"quantity\":2,\"unit\":\"个\"},{\"name\":\"葱\",\"quantity\":10,\"unit\":\"g\"},{\"name\":\"盐\",\"quantity\":3,\"unit\":\"g\"}]",
            steps = "[\"鸡蛋打散炒熟盛出\",\"热油炒香葱花\",\"加入米饭翻炒至粒粒分开\",\"加入鸡蛋翻炒\",\"加盐调味\"]",
            tips = "用隔夜饭炒出来更粒粒分明",
            calories = 380.0,
            protein = 12.0,
            fat = 12.0,
            carbs = 60.0,
            rating = 4.4,
            ratingCount = 3500,
            tags = "家常菜,主食,简单",
            isVegetarian = true
        ),
        RecipeEntity(
            name = "番茄炒蛋",
            nameEn = "Scrambled Eggs with Tomato",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 1,
            cookTimeMinutes = 10,
            servings = 2,
            description = "酸甜可口，营养均衡，国民家常菜",
            descriptionEn = "Sweet and sour, balanced nutrition, national home dish",
            ingredients = "[{\"name\":\"番茄\",\"quantity\":2,\"unit\":\"个\"},{\"name\":\"鸡蛋\",\"quantity\":3,\"unit\":\"个\"},{\"name\":\"糖\",\"quantity\":5,\"unit\":\"g\"},{\"name\":\"盐\",\"quantity\":3,\"unit\":\"g\"}]",
            steps = "[\"鸡蛋打散炒熟盛出\",\"番茄切块炒软出汁\",\"加入鸡蛋翻炒\",\"加糖和盐调味\"]",
            tips = "番茄选择熟透的更甜",
            calories = 180.0,
            protein = 14.0,
            fat = 10.0,
            carbs = 10.0,
            rating = 4.5,
            ratingCount = 4100,
            tags = "家常菜,酸甜,简单",
            isVegetarian = true
        ),
        // 新增10条中式食谱
        RecipeEntity(
            name = "北京烤鸭",
            nameEn = "Peking Duck",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 5,
            cookTimeMinutes = 120,
            servings = 6,
            description = "北京特色，皮脆肉嫩，肥而不腻",
            descriptionEn = "Beijing specialty, crispy skin, tender meat",
            ingredients = "[{\"name\":\"北京鸭\",\"quantity\":1,\"unit\":\"只\"},{\"name\":\"荷叶饼\",\"quantity\":20,\"unit\":\"张\"},{\"name\":\"甜面酱\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"葱丝\",\"quantity\":100,\"unit\":\"g\"}]",
            steps = "[\"鸭子处理干净，打气\",\"烫皮后晾干\",\"麦芽糖兑水刷匀\",\"风干4小时\",\"烤200度60分钟\",\"片鸭装盘\"]",
            tips = "风干步骤很重要，影响皮脆度",
            calories = 480.0,
            protein = 35.0,
            fat = 35.0,
            carbs = 10.0,
            rating = 4.9,
            ratingCount = 3800,
            tags = "京菜,烤鸭,特色",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "西湖醋鱼",
            nameEn = "West Lake Vinegar Fish",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 3,
            cookTimeMinutes = 25,
            servings = 2,
            description = "浙菜经典，鱼肉鲜嫩，酸甜适口",
            descriptionEn = "Zhejiang classic, tender fish, sweet and sour",
            ingredients = "[{\"name\":\"草鱼\",\"quantity\":600,\"unit\":\"g\"},{\"name\":\"醋\",\"quantity\":40,\"unit\":\"ml\"},{\"name\":\"糖\",\"quantity\":30,\"unit\":\"g\"},{\"name\":\"淀粉\",\"quantity\":10,\"unit\":\"g\"}]",
            steps = "[\"草鱼处理干净，切两段\",\"开水焯熟，装盘\",\"炒糖醋汁，勾芡\",\"浇在鱼上\"]",
            tips = "焯水时间要短，保持鱼肉嫩",
            calories = 220.0,
            protein = 30.0,
            fat = 6.0,
            carbs = 15.0,
            rating = 4.5,
            ratingCount = 1600,
            tags = "浙菜,酸甜,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "佛跳墙",
            nameEn = "Buddha Jumps Over the Wall",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 5,
            cookTimeMinutes = 180,
            servings = 6,
            description = "闽菜代表，食材丰富，汤鲜味美",
            descriptionEn = "Fujian classic, rich ingredients, delicious soup",
            ingredients = "[{\"name\":\"鲍鱼\",\"quantity\":6,\"unit\":\"只\"},{\"name\":\"海参\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"鱼翅\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"鸡汤\",\"quantity\":1000,\"unit\":\"ml\"}]",
            steps = "[\"各种食材分别处理好\",\"按层次放入坛中\",\"加入鸡汤\",\"密封蒸3小时\"]",
            tips = "食材要新鲜，火候要足",
            calories = 420.0,
            protein = 35.0,
            fat = 20.0,
            carbs = 15.0,
            rating = 4.8,
            ratingCount = 2100,
            tags = "闽菜,海鲜,豪华",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "东坡肉",
            nameEn = "Dongpo Pork",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 3,
            cookTimeMinutes = 90,
            servings = 6,
            description = "浙菜经典，色泽红亮，酥烂入味",
            descriptionEn = "Zhejiang classic, glossy red, tender and flavorful",
            ingredients = "[{\"name\":\"五花肉\",\"quantity\":600,\"unit\":\"g\"},{\"name\":\"黄酒\",\"quantity\":200,\"unit\":\"ml\"},{\"name\":\"冰糖\",\"quantity\":50,\"unit\":\"g\"},{\"name\":\"葱\",\"quantity\":30,\"unit\":\"g\"}]",
            steps = "[\"五花肉切块焯水\",\"砂锅垫葱姜，放肉\",\"加黄酒、冰糖、酱油\",\"小火焖1小时\",\"大火收汁\"]",
            tips = "用黄酒焖煮更香",
            calories = 580.0,
            protein = 25.0,
            fat = 45.0,
            carbs = 15.0,
            rating = 4.8,
            ratingCount = 2800,
            tags = "浙菜,红烧,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "水煮牛肉",
            nameEn = "Sichuan Boiled Beef",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 3,
            cookTimeMinutes = 20,
            servings = 4,
            description = "川菜经典，麻辣鲜香，牛肉嫩滑",
            descriptionEn = "Sichuan classic, spicy and numbing, tender beef",
            ingredients = "[{\"name\":\"牛肉\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"豆芽\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"干辣椒\",\"quantity\":20,\"unit\":\"g\"},{\"name\":\"花椒\",\"quantity\":10,\"unit\":\"g\"}]",
            steps = "[\"牛肉切片腌制\",\"豆芽焯水铺碗底\",\"煮麻辣汤，滑入牛肉片\",\"牛肉变色后倒入碗中\",\"热油爆香辣椒花椒，淋上\"]",
            tips = "牛肉要逆纹切才嫩",
            calories = 380.0,
            protein = 30.0,
            fat = 22.0,
            carbs = 10.0,
            rating = 4.7,
            ratingCount = 2400,
            tags = "川菜,麻辣,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "蚝油生菜",
            nameEn = "Lettuce with Oyster Sauce",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 1,
            cookTimeMinutes = 8,
            servings = 2,
            description = "粤菜经典，清淡爽口，蚝油香浓",
            descriptionEn = "Cantonese classic, light and refreshing, rich oyster sauce",
            ingredients = "[{\"name\":\"生菜\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"蚝油\",\"quantity\":20,\"unit\":\"g\"},{\"name\":\"蒜\",\"quantity\":10,\"unit\":\"g\"},{\"name\":\"淀粉\",\"quantity\":5,\"unit\":\"g\"}]",
            steps = "[\"生菜洗净焯水\",\"热油爆香蒜末\",\"加水和蚝油\",\"勾芡后淋在生菜上\"]",
            tips = "生菜焯水时间不要太长",
            calories = 120.0,
            protein = 5.0,
            fat = 8.0,
            carbs = 8.0,
            rating = 4.3,
            ratingCount = 1500,
            tags = "粤菜,清淡,素菜",
            isVegetarian = true
        ),
        RecipeEntity(
            name = "小笼包",
            nameEn = "Xiao Long Bao",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 4,
            cookTimeMinutes = 15,
            servings = 4,
            description = "苏菜经典，皮薄汁多，鲜美可口",
            descriptionEn = "Jiangsu classic, thin skin, juicy filling",
            ingredients = "[{\"name\":\"面粉\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"猪肉馅\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"皮冻\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"葱姜水\",\"quantity\":50,\"unit\":\"ml\"}]",
            steps = "[\"面团醒发后擀皮\",\"肉馅加皮冻和调料拌匀\",\"包入馅料\",\"蒸10分钟\"]",
            tips = "皮冻是小笼包多汁的关键",
            calories = 350.0,
            protein = 18.0,
            fat = 15.0,
            carbs = 40.0,
            rating = 4.8,
            ratingCount = 3200,
            tags = "苏菜,点心,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "炸酱面",
            nameEn = "Zhajiang Mian",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 2,
            cookTimeMinutes = 25,
            servings = 2,
            description = "京味经典，酱香浓郁，筋道爽滑",
            descriptionEn = "Beijing classic, rich soybean paste, chewy noodles",
            ingredients = "[{\"name\":\"面条\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"猪肉末\",\"quantity\":150,\"unit\":\"g\"},{\"name\":\"黄酱\",\"quantity\":50,\"unit\":\"g\"},{\"name\":\"黄瓜\",\"quantity\":100,\"unit\":\"g\"}]",
            steps = "[\"炒香猪肉末\",\"加入黄酱翻炒\",\"加水熬5分钟\",\"面条煮熟过凉\",\"拌炸酱，加黄瓜丝\"]",
            tips = "黄酱太咸可以加甜面酱调和",
            calories = 420.0,
            protein = 20.0,
            fat = 15.0,
            carbs = 60.0,
            rating = 4.6,
            ratingCount = 2600,
            tags = "京菜,面食,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "红烧狮子头",
            nameEn = "Braised Lion's Head",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 3,
            cookTimeMinutes = 50,
            servings = 4,
            description = "淮扬菜代表，肉质鲜嫩，造型美观",
            descriptionEn = "Huaiyang classic, tender meatballs, beautiful shape",
            ingredients = "[{\"name\":\"猪肉末\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"荸荠\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"青菜\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"淀粉\",\"quantity\":30,\"unit\":\"g\"}]",
            steps = "[\"肉末加荸荠、淀粉拌匀\",\"搓成大肉丸子\",\"油炸定型\",\"加葱姜和调料焖煮30分钟\",\"青菜焯水垫底\"]",
            tips = "加荸荠增加口感层次",
            calories = 480.0,
            protein = 25.0,
            fat = 32.0,
            carbs = 20.0,
            rating = 4.7,
            ratingCount = 2100,
            tags = "淮扬菜,丸子,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "凉拌黄瓜",
            nameEn = "Cold Cucumber Salad",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = true,
            category = RecipeEntity.CATEGORY_CHINESE,
            difficulty = 1,
            cookTimeMinutes = 10,
            servings = 4,
            description = "清爽开胃，酸辣可口，家常凉菜",
            descriptionEn = "Refreshing appetizer, sour and spicy, home-style",
            ingredients = "[{\"name\":\"黄瓜\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"蒜\",\"quantity\":20,\"unit\":\"g\"},{\"name\":\"醋\",\"quantity\":30,\"unit\":\"ml\"},{\"name\":\"香油\",\"quantity\":5,\"unit\":\"ml\"}]",
            steps = "[\"黄瓜拍碎切块\",\"加入蒜末、醋、盐、糖\",\"滴香油拌匀\",\"冷藏10分钟\"]",
            tips = "拍碎的黄瓜更入味",
            calories = 60.0,
            protein = 2.0,
            fat = 3.0,
            carbs = 8.0,
            rating = 4.2,
            ratingCount = 1800,
            tags = "凉菜,清爽,简单",
            isVegetarian = true
        )
    )
    
    // ============================================
    // 🇯🇵 日式食谱（4条）
    // ============================================
    private val japaneseRecipes = listOf(
        RecipeEntity(
            name = "寿司",
            nameEn = "Sushi",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_JAPANESE,
            difficulty = 3,
            cookTimeMinutes = 30,
            servings = 2,
            description = "日式经典，米饭软糯，海鲜新鲜",
            descriptionEn = "Japanese classic, soft rice, fresh seafood",
            ingredients = "[{\"name\":\"寿司饭\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"三文鱼\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"海苔\",\"quantity\":2,\"unit\":\"张\"},{\"name\":\"黄瓜\",\"quantity\":50,\"unit\":\"g\"}]",
            steps = "[\"寿司饭放凉备用\",\"海苔铺在寿司帘上\",\"铺一层寿司饭\",\"放上三文鱼和黄瓜条\",\"卷紧后切块\"]",
            tips = "寿司醋的比例：米醋3:糖2:盐1",
            calories = 320.0,
            protein = 18.0,
            fat = 8.0,
            carbs = 45.0,
            rating = 4.7,
            ratingCount = 2600,
            tags = "日式,生食,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "味噌汤",
            nameEn = "Miso Soup",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_JAPANESE,
            difficulty = 1,
            cookTimeMinutes = 8,
            servings = 2,
            description = "日式传统汤品，味噌浓郁，清淡鲜美",
            descriptionEn = "Traditional Japanese soup, rich miso flavor",
            ingredients = "[{\"name\":\"味噌\",\"quantity\":30,\"unit\":\"g\"},{\"name\":\"豆腐\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"海带\",\"quantity\":10,\"unit\":\"g\"},{\"name\":\"葱花\",\"quantity\":10,\"unit\":\"g\"}]",
            steps = "[\"水烧开，加入海带煮2分钟\",\"加入豆腐块煮1分钟\",\"关火后加入味噌搅拌均匀\",\"撒葱花\"]",
            tips = "味噌不要长时间煮，否则会破坏风味",
            calories = 80.0,
            protein = 5.0,
            fat = 3.0,
            carbs = 10.0,
            rating = 4.3,
            ratingCount = 1800,
            tags = "日式,清淡,汤品",
            isVegetarian = true
        ),
        RecipeEntity(
            name = "天妇罗",
            nameEn = "Tempura",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_JAPANESE,
            difficulty = 3,
            cookTimeMinutes = 20,
            servings = 2,
            description = "外酥里嫩，日式油炸料理的代表",
            descriptionEn = "Crispy outside, tender inside, Japanese fried classic",
            ingredients = "[{\"name\":\"虾\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"蔬菜\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"天妇罗粉\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"冰水\",\"quantity\":150,\"unit\":\"ml\"}]",
            steps = "[\"食材洗净沥干\",\"天妇罗粉加冰水调成面糊\",\"食材裹上面糊\",\"油温180度炸至金黄\",\"控油后搭配蘸料\"]",
            tips = "面糊要稀一些，用冰水调更酥脆",
            calories = 420.0,
            protein = 18.0,
            fat = 25.0,
            carbs = 30.0,
            rating = 4.6,
            ratingCount = 2100,
            tags = "日式,油炸,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "照烧鸡腿",
            nameEn = "Teriyaki Chicken",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_JAPANESE,
            difficulty = 2,
            cookTimeMinutes = 30,
            servings = 2,
            description = "甜咸适口，酱汁浓郁，鸡肉鲜嫩",
            descriptionEn = "Sweet and salty, rich sauce, tender chicken",
            ingredients = "[{\"name\":\"鸡腿\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"酱油\",\"quantity\":30,\"unit\":\"ml\"},{\"name\":\"味醂\",\"quantity\":30,\"unit\":\"ml\"},{\"name\":\"糖\",\"quantity\":20,\"unit\":\"g\"}]",
            steps = "[\"鸡腿去骨，用酱油腌10分钟\",\"调照烧汁：酱油+味醂+糖+水\",\"煎鸡腿至两面金黄\",\"倒入照烧汁焖煮至浓稠\",\"切块装盘\"]",
            tips = "味醂可以用料酒+糖代替",
            calories = 450.0,
            protein = 32.0,
            fat = 28.0,
            carbs = 15.0,
            rating = 4.7,
            ratingCount = 2400,
            tags = "日式,照烧,家常",
            isVegetarian = false
        )
    )
    
    // ============================================
    // 🇰🇷 韩式食谱（3条）
    // ============================================
    private val koreanRecipes = listOf(
        RecipeEntity(
            name = "石锅拌饭",
            nameEn = "Bibimbap",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_KOREAN,
            difficulty = 2,
            cookTimeMinutes = 25,
            servings = 1,
            description = "韩式经典，营养丰富，拌饭酱香",
            descriptionEn = "Korean classic, nutritious, spicy sauce",
            ingredients = "[{\"name\":\"米饭\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"牛肉\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"蔬菜\",\"quantity\":150,\"unit\":\"g\"},{\"name\":\"鸡蛋\",\"quantity\":1,\"unit\":\"个\"}]",
            steps = "[\"牛肉炒熟，蔬菜焯水\",\"米饭放入石锅\",\"铺上食材和煎蛋\",\"加入拌饭酱\",\"搅拌均匀\"]",
            tips = "石锅底部的锅巴是精华",
            calories = 520.0,
            protein = 22.0,
            fat = 18.0,
            carbs = 65.0,
            rating = 4.8,
            ratingCount = 2200,
            tags = "韩式,拌饭,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "部队火锅",
            nameEn = "Budae Jjigae",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_KOREAN,
            difficulty = 2,
            cookTimeMinutes = 30,
            servings = 4,
            description = "韩式部队锅，食材丰富，香辣过瘾",
            descriptionEn = "Korean army stew, rich ingredients, spicy",
            ingredients = "[{\"name\":\"泡菜\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"火腿\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"方便面\",\"quantity\":1,\"unit\":\"包\"},{\"name\":\"芝士\",\"quantity\":50,\"unit\":\"g\"}]",
            steps = "[\"泡菜炒香，加水煮开\",\"加入火腿、午餐肉等食材\",\"煮10分钟后加入方便面\",\"最后加入芝士融化\"]",
            tips = "泡面最后放保持口感",
            calories = 680.0,
            protein = 28.0,
            fat = 35.0,
            carbs = 60.0,
            rating = 4.5,
            ratingCount = 1900,
            tags = "韩式,火锅,香辣",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "辣炒年糕",
            nameEn = "Tteokbokki",
            region = RecipeEntity.REGION_EAST_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_KOREAN,
            difficulty = 2,
            cookTimeMinutes = 15,
            servings = 2,
            description = "韩式辣炒年糕，甜辣软糯，街头小吃",
            descriptionEn = "Korean spicy rice cake, sweet and spicy, street food",
            ingredients = "[{\"name\":\"年糕\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"韩式辣酱\",\"quantity\":30,\"unit\":\"g\"},{\"name\":\"鱼饼\",\"quantity\":50,\"unit\":\"g\"},{\"name\":\"糖\",\"quantity\":10,\"unit\":\"g\"}]",
            steps = "[\"年糕焯水2分钟\",\"辣酱加少量水化开\",\"加入年糕和鱼饼\",\"煮至酱汁浓稠\"]",
            tips = "加一点糖可以平衡辣味",
            calories = 350.0,
            protein = 8.0,
            fat = 8.0,
            carbs = 70.0,
            rating = 4.6,
            ratingCount = 2000,
            tags = "韩式,甜辣,小吃",
            isVegetarian = true
        )
    )
    
    // ============================================
    // 🇹🇭 泰式食谱（4条）
    // ============================================
    private val thaiRecipes = listOf(
        RecipeEntity(
            name = "冬阴功汤",
            nameEn = "Tom Yum Goong",
            region = RecipeEntity.REGION_SOUTHEAST_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_THAI,
            difficulty = 3,
            cookTimeMinutes = 20,
            servings = 4,
            description = "泰式经典，酸辣鲜香，开胃过瘾",
            descriptionEn = "Thai classic, sour and spicy, appetizing",
            ingredients = "[{\"name\":\"虾\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"冬阴功酱\",\"quantity\":50,\"unit\":\"g\"},{\"name\":\"椰奶\",\"quantity\":100,\"unit\":\"ml\"},{\"name\":\"柠檬叶\",\"quantity\":5,\"unit\":\"片\"}]",
            steps = "[\"热油炒香冬阴功酱和香料\",\"加入虾翻炒至变色\",\"加水煮开\",\"加入椰奶和鱼露调味\"]",
            tips = "加入椰奶可以中和辣味",
            calories = 280.0,
            protein = 25.0,
            fat = 15.0,
            carbs = 8.0,
            rating = 4.7,
            ratingCount = 2300,
            tags = "泰式,酸辣,汤品",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "绿咖喱鸡",
            nameEn = "Green Curry Chicken",
            region = RecipeEntity.REGION_SOUTHEAST_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_THAI,
            difficulty = 3,
            cookTimeMinutes = 25,
            servings = 4,
            description = "泰式绿咖喱，椰香浓郁，辣度适中",
            descriptionEn = "Thai green curry, rich coconut flavor, medium spicy",
            ingredients = "[{\"name\":\"鸡肉\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"绿咖喱酱\",\"quantity\":40,\"unit\":\"g\"},{\"name\":\"椰奶\",\"quantity\":200,\"unit\":\"ml\"},{\"name\":\"豆角\",\"quantity\":100,\"unit\":\"g\"}]",
            steps = "[\"椰奶加热，加入咖喱酱炒香\",\"加入鸡肉翻炒\",\"加水焖煮10分钟\",\"加入豆角煮软\"]",
            tips = "用椰奶做基底更浓郁",
            calories = 420.0,
            protein = 22.0,
            fat = 30.0,
            carbs = 15.0,
            rating = 4.6,
            ratingCount = 2100,
            tags = "泰式,咖喱,椰香",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "菠萝炒饭",
            nameEn = "Pineapple Fried Rice",
            region = RecipeEntity.REGION_SOUTHEAST_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_THAI,
            difficulty = 2,
            cookTimeMinutes = 20,
            servings = 4,
            description = "酸甜可口，果香浓郁，泰式经典",
            descriptionEn = "Sweet and sour, fruity, Thai classic",
            ingredients = "[{\"name\":\"菠萝\",\"quantity\":1,\"unit\":\"个\"},{\"name\":\"米饭\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"虾仁\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"腰果\",\"quantity\":30,\"unit\":\"g\"}]",
            steps = "[\"菠萝切丁，果肉挖出备用\",\"虾仁炒熟盛出\",\"炒香咖喱粉，加入米饭\",\"加入菠萝和虾仁\",\"加腰果翻炒\"]",
            tips = "用菠萝壳当容器更有仪式感",
            calories = 450.0,
            protein = 18.0,
            fat = 12.0,
            carbs = 70.0,
            rating = 4.5,
            ratingCount = 1800,
            tags = "泰式,酸甜,主食",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "芒果糯米饭",
            nameEn = "Mango Sticky Rice",
            region = RecipeEntity.REGION_SOUTHEAST_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_THAI,
            difficulty = 2,
            cookTimeMinutes = 25,
            servings = 2,
            description = "泰式甜品，椰香浓郁，香甜软糯",
            descriptionEn = "Thai dessert, coconut flavor, sweet and sticky",
            ingredients = "[{\"name\":\"糯米\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"芒果\",\"quantity\":1,\"unit\":\"个\"},{\"name\":\"椰奶\",\"quantity\":200,\"unit\":\"ml\"},{\"name\":\"糖\",\"quantity\":30,\"unit\":\"g\"}]",
            steps = "[\"糯米泡2小时后蒸熟\",\"椰奶加糖煮成椰浆\",\"芒果切块\",\"糯米装盘，放上芒果\",\"淋上椰浆撒芝麻\"]",
            tips = "糯米一定要泡够时间才软糯",
            calories = 520.0,
            protein = 6.0,
            fat = 12.0,
            carbs = 100.0,
            rating = 4.8,
            ratingCount = 2600,
            tags = "泰式,甜品,椰香",
            isVegetarian = true
        )
    )
    
    // ============================================
    // 🇮🇳 印度食谱（3条）
    // ============================================
    private val indianRecipes = listOf(
        RecipeEntity(
            name = "黄油鸡",
            nameEn = "Butter Chicken",
            region = RecipeEntity.REGION_SOUTH_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_INDIAN,
            difficulty = 3,
            cookTimeMinutes = 40,
            servings = 4,
            description = "印度经典，奶油浓郁，香料丰富",
            descriptionEn = "Indian classic, creamy, rich spices",
            ingredients = "[{\"name\":\"鸡肉\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"黄油\",\"quantity\":30,\"unit\":\"g\"},{\"name\":\"番茄\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"咖喱粉\",\"quantity\":20,\"unit\":\"g\"}]",
            steps = "[\"鸡肉用酸奶和香料腌制2小时\",\"炒香洋葱和番茄\",\"加入咖喱粉和黄油\",\"加入鸡肉焖煮20分钟\",\"加鲜奶油\"]",
            tips = "酸奶腌制让鸡肉更嫩滑",
            calories = 580.0,
            protein = 28.0,
            fat = 40.0,
            carbs = 20.0,
            rating = 4.7,
            ratingCount = 2400,
            tags = "印度,咖喱,奶油",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "印度咖喱饭",
            nameEn = "Chana Masala",
            region = RecipeEntity.REGION_SOUTH_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_INDIAN,
            difficulty = 2,
            cookTimeMinutes = 30,
            servings = 4,
            description = "印度鹰嘴豆咖喱，香料浓郁，素食经典",
            descriptionEn = "Indian chickpea curry, rich spices, vegetarian classic",
            ingredients = "[{\"name\":\"鹰嘴豆\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"番茄\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"印度香料\",\"quantity\":15,\"unit\":\"g\"},{\"name\":\"洋葱\",\"quantity\":100,\"unit\":\"g\"}]",
            steps = "[\"鹰嘴豆提前泡发煮熟\",\"炒香洋葱和番茄\",\"加入香料炒香\",\"加入鹰嘴豆和水\",\"焖煮15分钟\"]",
            tips = "搭配馕饼吃更地道",
            calories = 320.0,
            protein = 15.0,
            fat = 8.0,
            carbs = 50.0,
            rating = 4.5,
            ratingCount = 1900,
            tags = "印度,咖喱,素食",
            isVegetarian = true
        ),
        RecipeEntity(
            name = "馕饼",
            nameEn = "Naan",
            region = RecipeEntity.REGION_SOUTH_ASIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_INDIAN,
            difficulty = 2,
            cookTimeMinutes = 20,
            servings = 4,
            description = "印度传统烤饼，外酥内软，香气扑鼻",
            descriptionEn = "Indian traditional flatbread, crispy outside, soft inside",
            ingredients = "[{\"name\":\"面粉\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"酸奶\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"酵母\",\"quantity\":5,\"unit\":\"g\"},{\"name\":\"黄油\",\"quantity\":20,\"unit\":\"g\"}]",
            steps = "[\"面团发酵1小时\",\"分成小面团擀薄\",\"烤箱220度烤5-7分钟\",\"出炉后刷黄油\"]",
            tips = "用烤箱或平底锅都可以制作",
            calories = 420.0,
            protein = 10.0,
            fat = 12.0,
            carbs = 70.0,
            rating = 4.4,
            ratingCount = 1700,
            tags = "印度,主食,烤饼",
            isVegetarian = true
        )
    )
    
    // ============================================
    // 🇮🇹 意式食谱（4条）
    // ============================================
    private val italianRecipes = listOf(
        RecipeEntity(
            name = "番茄肉酱意面",
            nameEn = "Spaghetti Bolognese",
            region = RecipeEntity.REGION_EUROPE,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_ITALIAN,
            difficulty = 3,
            cookTimeMinutes = 45,
            servings = 4,
            description = "意式经典，肉酱浓郁，面条劲道",
            descriptionEn = "Italian classic, rich meat sauce, al dente pasta",
            ingredients = "[{\"name\":\"意面\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"牛肉末\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"番茄\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"洋葱\",\"quantity\":100,\"unit\":\"g\"}]",
            steps = "[\"炒香洋葱和蒜末\",\"加入牛肉末炒至变色\",\"加入番茄炒软\",\"加红酒和香料\",\"小火熬30分钟\",\"煮意面拌酱\"]",
            tips = "肉酱要慢熬才香浓",
            calories = 520.0,
            protein = 25.0,
            fat = 18.0,
            carbs = 70.0,
            rating = 4.8,
            ratingCount = 3200,
            tags = "意式,意面,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "披萨",
            nameEn = "Pizza",
            region = RecipeEntity.REGION_EUROPE,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_ITALIAN,
            difficulty = 3,
            cookTimeMinutes = 30,
            servings = 4,
            description = "意式披萨，薄脆底，芝士香浓",
            descriptionEn = "Italian pizza, thin crispy crust, rich cheese",
            ingredients = "[{\"name\":\"披萨面团\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"番茄酱\",\"quantity\":150,\"unit\":\"g\"},{\"name\":\"马苏里拉芝士\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"配料\",\"quantity\":200,\"unit\":\"g\"}]",
            steps = "[\"面团发酵后擀成圆形\",\"涂上番茄酱\",\"铺上芝士和配料\",\"烤箱220度烤12-15分钟\"]",
            tips = "高温快烤让饼底更脆",
            calories = 620.0,
            protein = 25.0,
            fat = 30.0,
            carbs = 60.0,
            rating = 4.7,
            ratingCount = 3500,
            tags = "意式,披萨,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "提拉米苏",
            nameEn = "Tiramisu",
            region = RecipeEntity.REGION_EUROPE,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_ITALIAN,
            difficulty = 3,
            cookTimeMinutes = 60,
            servings = 6,
            description = "意式甜品，咖啡酒香，口感细腻",
            descriptionEn = "Italian dessert, coffee and wine flavor, creamy",
            ingredients = "[{\"name\":\"马斯卡彭芝士\",\"quantity\":250,\"unit\":\"g\"},{\"name\":\"手指饼干\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"咖啡\",\"quantity\":200,\"unit\":\"ml\"},{\"name\":\"可可粉\",\"quantity\":20,\"unit\":\"g\"}]",
            steps = "[\"马斯卡彭芝士打发\",\"手指饼干蘸咖啡酒\",\"一层饼干一层芝士糊\",\"冷藏4小时\",\"撒可可粉\"]",
            tips = "咖啡酒是提拉米苏的灵魂",
            calories = 450.0,
            protein = 8.0,
            fat = 30.0,
            carbs = 40.0,
            rating = 4.9,
            ratingCount = 2800,
            tags = "意式,甜品,咖啡",
            isVegetarian = true
        ),
        RecipeEntity(
            name = "奶油蘑菇汤",
            nameEn = "Cream of Mushroom Soup",
            region = RecipeEntity.REGION_EUROPE,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_ITALIAN,
            difficulty = 2,
            cookTimeMinutes = 25,
            servings = 4,
            description = "香浓顺滑，蘑菇鲜美，西式经典",
            descriptionEn = "Creamy and smooth, mushroom flavor, Western classic",
            ingredients = "[{\"name\":\"蘑菇\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"奶油\",\"quantity\":100,\"unit\":\"ml\"},{\"name\":\"洋葱\",\"quantity\":50,\"unit\":\"g\"},{\"name\":\"面粉\",\"quantity\":20,\"unit\":\"g\"}]",
            steps = "[\"炒香洋葱和蘑菇\",\"加面粉炒香\",\"加水煮开\",\"加奶油搅拌\",\"打碎成浓汤\"]",
            tips = "用料理机打成细腻的浓汤",
            calories = 280.0,
            protein = 8.0,
            fat = 20.0,
            carbs = 15.0,
            rating = 4.4,
            ratingCount = 1600,
            tags = "意式,汤品,奶油",
            isVegetarian = true
        )
    )
    
    // ============================================
    // 🇫🇷 法式食谱（3条）
    // ============================================
    private val frenchRecipes = listOf(
        RecipeEntity(
            name = "法式洋葱汤",
            nameEn = "French Onion Soup",
            region = RecipeEntity.REGION_EUROPE,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_FRENCH,
            difficulty = 3,
            cookTimeMinutes = 45,
            servings = 4,
            description = "法式经典，洋葱香甜，芝士浓郁",
            descriptionEn = "French classic, sweet onions, rich cheese",
            ingredients = "[{\"name\":\"洋葱\",\"quantity\":500,\"unit\":\"g\"},{\"name\":\"牛肉汤\",\"quantity\":1000,\"unit\":\"ml\"},{\"name\":\"面包\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"格鲁耶尔芝士\",\"quantity\":100,\"unit\":\"g\"}]",
            steps = "[\"洋葱慢炒至焦糖色\",\"加入牛肉汤煮开\",\"面包烤至金黄\",\"放入汤中，撒芝士\",\"烤箱烤至芝士融化\"]",
            tips = "洋葱要炒到深金黄色才香甜",
            calories = 380.0,
            protein = 15.0,
            fat = 20.0,
            carbs = 30.0,
            rating = 4.6,
            ratingCount = 1800,
            tags = "法式,汤品,洋葱",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "香煎牛排",
            nameEn = "French Steak",
            region = RecipeEntity.REGION_EUROPE,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_FRENCH,
            difficulty = 3,
            cookTimeMinutes = 15,
            servings = 2,
            description = "法式煎牛排，外焦里嫩，肉香四溢",
            descriptionEn = "French steak, seared outside, tender inside",
            ingredients = "[{\"name\":\"牛排\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"黄油\",\"quantity\":20,\"unit\":\"g\"},{\"name\":\"迷迭香\",\"quantity\":5,\"unit\":\"g\"},{\"name\":\"蒜\",\"quantity\":10,\"unit\":\"g\"}]",
            steps = "[\"牛排吸干水分，撒盐和黑胡椒\",\"热锅煎两面各2分钟\",\"加入黄油、迷迭香、蒜\",\"淋油30秒\",\"静置5分钟再切\"]",
            tips = "静置让肉汁回流更嫩",
            calories = 520.0,
            protein = 40.0,
            fat = 35.0,
            carbs = 2.0,
            rating = 4.8,
            ratingCount = 2500,
            tags = "法式,牛排,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "可颂",
            nameEn = "Croissant",
            region = RecipeEntity.REGION_EUROPE,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_FRENCH,
            difficulty = 4,
            cookTimeMinutes = 180,
            servings = 8,
            description = "法式可颂，层次分明，黄油香浓",
            descriptionEn = "French croissant, flaky layers, rich butter flavor",
            ingredients = "[{\"name\":\"高筋面粉\",\"quantity\":500,\"unit\":\"g\"},{\"name\":\"黄油\",\"quantity\":250,\"unit\":\"g\"},{\"name\":\"酵母\",\"quantity\":10,\"unit\":\"g\"},{\"name\":\"牛奶\",\"quantity\":250,\"unit\":\"ml\"}]",
            steps = "[\"面团制作冷藏松弛\",\"黄油裹入折叠擀压\",\"切割成型发酵\",\"烤箱180度烤15-20分钟\"]",
            tips = "黄油和面团温度要控制好",
            calories = 350.0,
            protein = 6.0,
            fat = 22.0,
            carbs = 35.0,
            rating = 4.7,
            ratingCount = 2200,
            tags = "法式,面包,黄油",
            isVegetarian = true
        )
    )
    
    // ============================================
    // 🇺🇸 美式食谱（3条）
    // ============================================
    private val americanRecipes = listOf(
        RecipeEntity(
            name = "汉堡",
            nameEn = "Hamburger",
            region = RecipeEntity.REGION_NORTH_AMERICA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_AMERICAN,
            difficulty = 2,
            cookTimeMinutes = 20,
            servings = 2,
            description = "美式经典，肉饼多汁，配料丰富",
            descriptionEn = "American classic, juicy patty, rich toppings",
            ingredients = "[{\"name\":\"牛肉末\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"汉堡面包\",\"quantity\":2,\"unit\":\"个\"},{\"name\":\"生菜\",\"quantity\":50,\"unit\":\"g\"},{\"name\":\"番茄\",\"quantity\":100,\"unit\":\"g\"}]",
            steps = "[\"牛肉末调味捏成肉饼\",\"煎肉饼至两面金黄\",\"面包烤热\",\"组装汉堡：生菜、番茄、肉饼、酱料\"]",
            tips = "肉饼不要过度按压保持多汁",
            calories = 680.0,
            protein = 35.0,
            fat = 40.0,
            carbs = 40.0,
            rating = 4.5,
            ratingCount = 2800,
            tags = "美式,汉堡,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "炸鸡",
            nameEn = "Fried Chicken",
            region = RecipeEntity.REGION_NORTH_AMERICA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_AMERICAN,
            difficulty = 3,
            cookTimeMinutes = 40,
            servings = 4,
            description = "美式炸鸡，外酥里嫩，香气扑鼻",
            descriptionEn = "American fried chicken, crispy outside, tender inside",
            ingredients = "[{\"name\":\"鸡肉\",\"quantity\":800,\"unit\":\"g\"},{\"name\":\"面粉\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"香料\",\"quantity\":10,\"unit\":\"g\"},{\"name\":\"植物油\",\"quantity\":500,\"unit\":\"ml\"}]",
            steps = "[\"鸡肉用盐和香料腌制1小时\",\"面粉加香料调成面糊\",\"鸡肉裹面糊\",\"180度油炸12-15分钟\"]",
            tips = "油温要够高才酥脆",
            calories = 580.0,
            protein = 30.0,
            fat = 45.0,
            carbs = 15.0,
            rating = 4.6,
            ratingCount = 3100,
            tags = "美式,炸鸡,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "苹果派",
            nameEn = "Apple Pie",
            region = RecipeEntity.REGION_NORTH_AMERICA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_AMERICAN,
            difficulty = 3,
            cookTimeMinutes = 60,
            servings = 6,
            description = "美式甜点，苹果香甜，派皮酥脆",
            descriptionEn = "American dessert, sweet apples, flaky crust",
            ingredients = "[{\"name\":\"苹果\",\"quantity\":600,\"unit\":\"g\"},{\"name\":\"派皮\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"糖\",\"quantity\":80,\"unit\":\"g\"},{\"name\":\"肉桂粉\",\"quantity\":5,\"unit\":\"g\"}]",
            steps = "[\"派皮分成两份擀平\",\"苹果切丁加糖和肉桂粉拌匀\",\"放入派皮中盖上盖子\",\"烤200度35-40分钟\"]",
            tips = "苹果要稍微煮软再烤",
            calories = 420.0,
            protein = 4.0,
            fat = 20.0,
            carbs = 65.0,
            rating = 4.7,
            ratingCount = 2400,
            tags = "美式,甜点,苹果",
            isVegetarian = true
        )
    )
    
    // ============================================
    // 🇲🇽 墨西哥食谱（3条）
    // ============================================
    private val mexicanRecipes = listOf(
        RecipeEntity(
            name = "塔可",
            nameEn = "Taco",
            region = RecipeEntity.REGION_NORTH_AMERICA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_MEXICAN,
            difficulty = 2,
            cookTimeMinutes = 25,
            servings = 4,
            description = "墨西哥经典，玉米饼夹肉，酸辣可口",
            descriptionEn = "Mexican classic, corn tortilla with meat, spicy",
            ingredients = "[{\"name\":\"玉米饼\",\"quantity\":8,\"unit\":\"张\"},{\"name\":\"牛肉末\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"莎莎酱\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"生菜\",\"quantity\":100,\"unit\":\"g\"}]",
            steps = "[\"牛肉末炒香加墨西哥香料\",\"玉米饼加热\",\"夹入牛肉、莎莎酱、生菜\"]",
            tips = "自制莎莎酱更美味",
            calories = 380.0,
            protein = 20.0,
            fat = 18.0,
            carbs = 35.0,
            rating = 4.6,
            ratingCount = 2100,
            tags = "墨西哥,塔可,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "墨西哥卷饼",
            nameEn = "Burrito",
            region = RecipeEntity.REGION_NORTH_AMERICA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_MEXICAN,
            difficulty = 2,
            cookTimeMinutes = 30,
            servings = 2,
            description = "墨西哥卷饼，馅料丰富，味道浓郁",
            descriptionEn = "Mexican burrito, rich filling, bold flavor",
            ingredients = "[{\"name\":\"面粉饼\",\"quantity\":2,\"unit\":\"张\"},{\"name\":\"鸡肉\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"米饭\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"豆类\",\"quantity\":100,\"unit\":\"g\"}]",
            steps = "[\"鸡肉炒香加香料\",\"米饭和豆类备好\",\"面饼加热铺馅料\",\"卷起来即可\"]",
            tips = "馅料不要太多以免散开",
            calories = 520.0,
            protein = 25.0,
            fat = 18.0,
            carbs = 65.0,
            rating = 4.5,
            ratingCount = 1900,
            tags = "墨西哥,卷饼,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "莎莎酱",
            nameEn = "Salsa",
            region = RecipeEntity.REGION_NORTH_AMERICA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_MEXICAN,
            difficulty = 1,
            cookTimeMinutes = 10,
            servings = 4,
            description = "墨西哥莎莎酱，酸甜辣爽，百搭酱料",
            descriptionEn = "Mexican salsa, sweet sour spicy, versatile sauce",
            ingredients = "[{\"name\":\"番茄\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"洋葱\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"辣椒\",\"quantity\":50,\"unit\":\"g\"},{\"name\":\"柠檬汁\",\"quantity\":30,\"unit\":\"ml\"}]",
            steps = "[\"所有食材切碎\",\"加入柠檬汁和盐\",\"搅拌均匀冷藏30分钟\"]",
            tips = "搭配玉米片吃最经典",
            calories = 60.0,
            protein = 2.0,
            fat = 1.0,
            carbs = 12.0,
            rating = 4.4,
            ratingCount = 1600,
            tags = "墨西哥,酱料,开胃",
            isVegetarian = true
        )
    )
    
    // ============================================
    // 🇬🇷 希腊食谱（2条）
    // ============================================
    private val greekRecipes = listOf(
        RecipeEntity(
            name = "希腊沙拉",
            nameEn = "Greek Salad",
            region = RecipeEntity.REGION_EUROPE,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_GREEK,
            difficulty = 1,
            cookTimeMinutes = 10,
            servings = 4,
            description = "希腊经典，新鲜蔬菜，橄榄油香浓",
            descriptionEn = "Greek classic, fresh vegetables, rich olive oil",
            ingredients = "[{\"name\":\"番茄\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"黄瓜\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"羊奶酪\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"橄榄油\",\"quantity\":30,\"unit\":\"ml\"}]",
            steps = "[\"蔬菜切块\",\"加入羊奶酪和橄榄\",\"淋橄榄油和醋\",\"撒盐和黑胡椒\"]",
            tips = "用优质橄榄油更香浓",
            calories = 280.0,
            protein = 10.0,
            fat = 22.0,
            carbs = 12.0,
            rating = 4.3,
            ratingCount = 1500,
            tags = "希腊,沙拉,健康",
            isVegetarian = true
        ),
        RecipeEntity(
            name = "穆萨卡",
            nameEn = "Moussaka",
            region = RecipeEntity.REGION_EUROPE,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_GREEK,
            difficulty = 4,
            cookTimeMinutes = 90,
            servings = 6,
            description = "希腊传统菜，茄子层叠，肉酱香浓",
            descriptionEn = "Greek traditional, layered eggplant, rich meat sauce",
            ingredients = "[{\"name\":\"茄子\",\"quantity\":500,\"unit\":\"g\"},{\"name\":\"牛肉末\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"番茄\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"奶油酱\",\"quantity\":200,\"unit\":\"g\"}]",
            steps = "[\"茄子切片煎熟\",\"炒牛肉末加番茄熬酱\",\"一层茄子一层肉酱\",\"铺上奶油酱\",\"烤200度40分钟\"]",
            tips = "茄子要煎到金黄才香",
            calories = 480.0,
            protein = 25.0,
            fat = 28.0,
            carbs = 30.0,
            rating = 4.6,
            ratingCount = 1800,
            tags = "希腊,传统,层叠",
            isVegetarian = false
        )
    )
    
    // ============================================
    // 🇧🇷 巴西食谱（2条）
    // ============================================
    private val brazilianRecipes = listOf(
        RecipeEntity(
            name = "巴西烤肉",
            nameEn = "Churrasco",
            region = RecipeEntity.REGION_SOUTH_AMERICA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_BRAZILIAN,
            difficulty = 3,
            cookTimeMinutes = 120,
            servings = 8,
            description = "巴西烤肉，炭火烤制，肉香四溢",
            descriptionEn = "Brazilian barbecue, charcoal grilled, rich meat flavor",
            ingredients = "[{\"name\":\"牛肉\",\"quantity\":1000,\"unit\":\"g\"},{\"name\":\"鸡腿\",\"quantity\":800,\"unit\":\"g\"},{\"name\":\"香肠\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"粗盐\",\"quantity\":50,\"unit\":\"g\"}]",
            steps = "[\"肉用粗盐腌制30分钟\",\"炭火预热到高温\",\"串肉放在烤架上\",\"旋转烤制40-60分钟\"]",
            tips = "只用盐调味保持原味",
            calories = 620.0,
            protein = 45.0,
            fat = 45.0,
            carbs = 5.0,
            rating = 4.8,
            ratingCount = 2200,
            tags = "巴西,烤肉,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "黑豆饭",
            nameEn = "Feijoada",
            region = RecipeEntity.REGION_SOUTH_AMERICA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_BRAZILIAN,
            difficulty = 3,
            cookTimeMinutes = 180,
            servings = 6,
            description = "巴西国菜，黑豆炖煮，风味浓郁",
            descriptionEn = "Brazilian national dish, black beans stew, rich flavor",
            ingredients = "[{\"name\":\"黑豆\",\"quantity\":500,\"unit\":\"g\"},{\"name\":\"五花肉\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"香肠\",\"quantity\":200,\"unit\":\"g\"},{\"name\":\"米饭\",\"quantity\":400,\"unit\":\"g\"}]",
            steps = "[\"黑豆提前泡发\",\"肉类焯水\",\"一起炖煮2-3小时\",\"搭配米饭和橙片\"]",
            tips = "炖煮时间越长越香浓",
            calories = 420.0,
            protein = 20.0,
            fat = 20.0,
            carbs = 45.0,
            rating = 4.5,
            ratingCount = 1700,
            tags = "巴西,国菜,黑豆",
            isVegetarian = false
        )
    )
    
    // ============================================
    // 🇦🇺 澳洲食谱（2条）
    // ============================================
    private val australianRecipes = listOf(
        RecipeEntity(
            name = "澳洲肉派",
            nameEn = "Australian Meat Pie",
            region = RecipeEntity.REGION_OCEANIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_AUSTRALIAN,
            difficulty = 3,
            cookTimeMinutes = 60,
            servings = 4,
            description = "澳洲经典，酥皮包裹，肉馅浓郁",
            descriptionEn = "Australian classic, flaky pastry, rich meat filling",
            ingredients = "[{\"name\":\"酥皮\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"牛肉末\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"洋葱\",\"quantity\":100,\"unit\":\"g\"},{\"name\":\"番茄酱\",\"quantity\":50,\"unit\":\"g\"}]",
            steps = "[\"牛肉末炒香加洋葱和酱料\",\"酥皮分成小块包馅\",\"刷蛋液\",\"烤200度25分钟\"]",
            tips = "酥皮要烤到金黄酥脆",
            calories = 520.0,
            protein = 20.0,
            fat = 30.0,
            carbs = 45.0,
            rating = 4.4,
            ratingCount = 1600,
            tags = "澳洲,肉派,经典",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "袋鼠肉排",
            nameEn = "Kangaroo Steak",
            region = RecipeEntity.REGION_OCEANIA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_AUSTRALIAN,
            difficulty = 2,
            cookTimeMinutes = 12,
            servings = 2,
            description = "澳洲特色，袋鼠肉嫩，低脂健康",
            descriptionEn = "Australian specialty, tender kangaroo meat, low fat",
            ingredients = "[{\"name\":\"袋鼠肉排\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"橄榄油\",\"quantity\":20,\"unit\":\"ml\"},{\"name\":\"迷迭香\",\"quantity\":5,\"unit\":\"g\"},{\"name\":\"盐\",\"quantity\":5,\"unit\":\"g\"}]",
            steps = "[\"肉排用盐和迷迭香腌制10分钟\",\"热锅煎两面各2-3分钟\",\"静置5分钟\"]",
            tips = "袋鼠肉低脂，不要煎太老",
            calories = 280.0,
            protein = 40.0,
            fat = 8.0,
            carbs = 2.0,
            rating = 4.3,
            ratingCount = 1200,
            tags = "澳洲,特色,健康",
            isVegetarian = false
        )
    )
    
    // ============================================
    // 🇲🇦 北非食谱（2条）
    // ============================================
    private val northAfricanRecipes = listOf(
        RecipeEntity(
            name = "摩洛哥塔吉锅",
            nameEn = "Moroccan Tagine",
            region = RecipeEntity.REGION_AFRICA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_NORTH_AFRICAN,
            difficulty = 3,
            cookTimeMinutes = 90,
            servings = 4,
            description = "摩洛哥特色，塔吉锅慢炖，香料丰富",
            descriptionEn = "Moroccan specialty, tagine slow cooked, rich spices",
            ingredients = "[{\"name\":\"鸡肉\",\"quantity\":500,\"unit\":\"g\"},{\"name\":\"蔬菜\",\"quantity\":400,\"unit\":\"g\"},{\"name\":\"摩洛哥香料\",\"quantity\":15,\"unit\":\"g\"},{\"name\":\"干果\",\"quantity\":50,\"unit\":\"g\"}]",
            steps = "[\"香料炒香，加入鸡肉上色\",\"加水和蔬菜\",\"放入塔吉锅小火炖60分钟\",\"加入干果\"]",
            tips = "用传统塔吉锅风味更正宗",
            calories = 380.0,
            protein = 28.0,
            fat = 15.0,
            carbs = 25.0,
            rating = 4.6,
            ratingCount = 1800,
            tags = "北非,摩洛哥,慢炖",
            isVegetarian = false
        ),
        RecipeEntity(
            name = "古斯米",
            nameEn = "Couscous",
            region = RecipeEntity.REGION_AFRICA,
            isDomestic = false,
            category = RecipeEntity.CATEGORY_NORTH_AFRICAN,
            difficulty = 2,
            cookTimeMinutes = 20,
            servings = 4,
            description = "北非主食，颗粒分明，搭配炖菜",
            descriptionEn = "North African staple, fluffy grains, served with stew",
            ingredients = "[{\"name\":\"古斯米\",\"quantity\":300,\"unit\":\"g\"},{\"name\":\"水\",\"quantity\":450,\"unit\":\"ml\"},{\"name\":\"橄榄油\",\"quantity\":20,\"unit\":\"ml\"},{\"name\":\"盐\",\"quantity\":5,\"unit\":\"g\"}]",
            steps = "[\"水烧开加盐和橄榄油\",\"加入古斯米拌匀\",\"盖盖焖10分钟\",\"用叉子打散\"]",
            tips = "焖好后用叉子打散更蓬松",
            calories = 350.0,
            protein = 8.0,
            fat = 5.0,
            carbs = 70.0,
            rating = 4.2,
            ratingCount = 1400,
            tags = "北非,主食,简单",
            isVegetarian = true
        )
    )
    
    // ============================================
    // 获取所有食谱（确保国内国外各50%）
    // ============================================
    
    /**
     * 获取所有国际化食谱
     * 基础食谱：50条（20条中式 + 30条国外）
     * 扩展食谱：25+条（中式扩展 + 日式扩展 + 韩式扩展 + 东南亚扩展 + 印度扩展）
     * 总计：75+条
     */
    fun getAllRecipes(): List<RecipeEntity> {
        return chineseRecipes + 
               japaneseRecipes + 
               koreanRecipes + 
               thaiRecipes + 
               indianRecipes + 
               italianRecipes + 
               frenchRecipes + 
               americanRecipes + 
               mexicanRecipes + 
               greekRecipes + 
               brazilianRecipes + 
               australianRecipes + 
               northAfricanRecipes +
               ExpandedRecipeData.chineseExpandedRecipes +
               ExpandedRecipeData.japaneseExpandedRecipes +
               ExpandedRecipeData.koreanExpandedRecipes +
               ExpandedRecipeData.southeastAsiaExpandedRecipes +
               ExpandedRecipeData.indianExpandedRecipes
    }
    
    /**
     * 获取均衡食谱（国内国外各50%）
     * 国内30条（含扩展） + 国外30条（含扩展） = 60条均衡食谱
     */
    fun getBalancedRecipes(): List<RecipeEntity> {
        val domesticRecipes = (chineseRecipes + ExpandedRecipeData.chineseExpandedRecipes)
            .shuffled()
            .take(30)
        val internationalRecipes = (japaneseRecipes + koreanRecipes + thaiRecipes + 
                                   indianRecipes + italianRecipes + frenchRecipes + 
                                   americanRecipes + mexicanRecipes + greekRecipes +
                                   brazilianRecipes + australianRecipes + northAfricanRecipes +
                                   ExpandedRecipeData.japaneseExpandedRecipes +
                                   ExpandedRecipeData.koreanExpandedRecipes +
                                   ExpandedRecipeData.southeastAsiaExpandedRecipes +
                                   ExpandedRecipeData.indianExpandedRecipes)
            .shuffled()
            .take(30)
        
        return (domesticRecipes + internationalRecipes).shuffled()
    }
    
    /**
     * 获取按区域分类的食谱
     */
    fun getRecipesByRegion(region: String): List<RecipeEntity> {
        return getAllRecipes().filter { it.region == region }
    }
    
    /**
     * 获取按菜系分类的食谱
     */
    fun getRecipesByCategory(category: String): List<RecipeEntity> {
        return getAllRecipes().filter { it.category == category }
    }
    
    /**
     * 获取统计信息
     */
    fun getStatistics(): RecipeStatistics {
        val allRecipes = getAllRecipes()
        val domesticCount = allRecipes.count { it.isDomestic }
        val internationalCount = allRecipes.size - domesticCount
        val regionCounts = allRecipes.groupBy { it.region }.mapValues { it.value.size }
        val categoryCounts = allRecipes.groupBy { it.category }.mapValues { it.value.size }
        val vegetarianCount = allRecipes.count { it.isVegetarian }
        
        return RecipeStatistics(
            totalRecipes = allRecipes.size,
            domesticRecipes = domesticCount,
            internationalRecipes = internationalCount,
            regionDistribution = regionCounts,
            categoryDistribution = categoryCounts,
            vegetarianRecipes = vegetarianCount
        )
    }
    
    data class RecipeStatistics(
        val totalRecipes: Int,
        val domesticRecipes: Int,
        val internationalRecipes: Int,
        val regionDistribution: Map<String, Int>,
        val categoryDistribution: Map<String, Int>,
        val vegetarianRecipes: Int
    )
}