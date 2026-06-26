$ErrorActionPreference = "Stop"
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

$sourceDir = "d:\TownApp\docs_development"
$docsDir = "d:\TownApp\docs"
$targetBase = "d:\TownApp\app\src\main\assets\docs"

$systemDirs = @(
    "01-核心人生系统", "02-社交心理系统", "03-职业经济系统", "04-物质生活系统",
    "05-饮食健康系统", "06-医疗健康系统", "07-文化娱乐系统", "08-认知反思系统",
    "09-互动陪伴系统", "10-人生事件系统", "11-产品分类数据", "12-众生时间档案馆",
    "13-多周目认知继承"
)

$systemKeywords = @{
    "01-核心人生系统" = "人生|生活|存在|意义|价值|幸福|快乐|平凡|普通|日子|世界|时间|岁月|生命|活着|自己|自我|接纳|温柔|和解"
    "02-社交心理系统" = "朋友|社交|关系|别人|他人|孤独|陪伴|聊天|对话|邻居|陌生人|人群|说话|沟通|心理|情绪|感受|心情|难过|开心|焦虑|平静"
    "03-职业经济系统" = "工作|上班|工资|钱|收入|存款|消费|花|买|职业|职场|加班|老板|同事|客户|升职|失业|经济|财务|房租|贷款|债务|贫穷|富有"
    "04-物质生活系统" = "家|房间|东西|物品|衣服|家具|装饰|房子|家居|物件|鞋子|包|家电|厨房|阳台|窗外|街道|市场|超市"
    "05-饮食健康系统" = "吃|饭|食物|菜|肉|水果|蔬菜|喝|茶|咖啡|奶茶|胃|饱|饿|味道|好吃|营养|口味|三餐|早餐|午餐|晚餐|零食|外卖|做饭"
    "06-医疗健康系统" = "身体|健康|病|医院|医生|药|痛|舒服|疲惫|睡眠|休息|体检|症状|感冒|发烧|头疼|老年|慢性|血压|血糖|养生|调理"
    "07-文化娱乐系统" = "玩|游戏|电影|音乐|书|阅读|逛街|公园|散步|旅行|景点|假期|节日|庆祝|活动|爱好|兴趣|娱乐|休闲|放松|综艺|剧"
    "08-认知反思系统" = "想|思考|明白|发现|意识到|原来|其实|也许|或许|反思|觉醒|认知|观念|道理|真相|规律|社会|规则|束缚|打破|重新|选择"
    "09-互动陪伴系统" = "陪伴|陪你|在你身边|支持你|没关系|不用怕|有我在|温暖|抱抱|安慰|鼓励|理解|懂你|倾听|守护|小家伙|毛孩子|宠物|猫|狗"
    "10-人生事件系统" = "结婚|生子|毕业|离职|搬家|旅行|生病|告别|重逢|相遇|离开|回来|开始|结束|决定|改变|转折|意外|惊喜|遗憾|成长"
    "11-产品分类数据" = "商品|产品|品牌|价格|质量|包装|广告|营销|网红|推荐|测评|性价比|奢华|平价|实用|花哨|质感|设计|款式|流行"
    "12-众生时间档案馆" = "回忆|过去|小时候|当年|以前|曾经|老了|岁月|时光|故事|往事|记忆|相册|照片|老人|年代|时代|历史|归档|记录|回顾"
    "13-多周目认知继承" = "轮回|重生|再来|周目|记忆残留|似曾相识|前世|今生|宿命|注定|熟悉感|既视感|觉醒|传承|继承|闭环|圆|循环"
}

function Get-ValidNarrativeLines($filePath) {
    $lines = @()
    try {
        $content = [System.IO.File]::ReadAllText($filePath, [System.Text.UTF8Encoding]::new($false))
        $pattern = '(?m)^-\s+(.+)$'
        $matches = [regex]::Matches($content, $pattern)
        foreach ($m in $matches) {
            $line = $m.Groups[1].Value.Trim()
            $line = $line -replace '\*\*[^*]+\*\*', ''
            $line = $line -replace '^[^：:]+[：:]\s*', ''
            $line = $line -replace '^note_\d+\s*[:：]\s*', ''
            $line = $line.Trim()
            if ($line.Length -ge 8 -and $line.Length -le 200 `
                -and -not $line.StartsWith("来源") `
                -and -not $line.StartsWith("状态") `
                -and -not $line.StartsWith("说明") `
                -and -not $line.StartsWith("对应成语") `
                -and -not $line.StartsWith("触发") `
                -and -not $line.StartsWith("体感") `
                -and -not $line.StartsWith("闪光点") `
                -and -not $line.StartsWith("小镇评述") `
                -and -not $line.StartsWith("副标题") `
                -and -not $line.StartsWith("场景") `
                -and -not $line.StartsWith("物品") `
                -and -not $line.Contains("：已上线") `
                -and -not $line.Contains("：已废弃") `
                -and -not $line.Contains("|") `
                -and -not $line.StartsWith("**") `
                -and $line -notmatch '^\d+\.' `
                -and $line -notmatch '^[A-Za-z_]+$' `
                -and $line -notmatch '^key:' `
                -and $line -notmatch '^type:' `
                -and $line -notmatch '^score_' `
                -and $line -notmatch '^season:' `
                -and $line -notmatch '^emotion:' `
                -and $line -notmatch '^identity:' `
                -and $line -notmatch '^category:' `
                -and $line -notmatch '^sleep_status:' `
                -and $line -notmatch '^dream_type:' `
                -and $line -notmatch '^tendency:' `
                -and -not $line.StartsWith("key:") `
                -and -not $line.StartsWith("type:") `
                -and -not $line.StartsWith("<!--")) {
                $lines += $line
            }
        }
    } catch {
        Write-Host "Error reading $filePath : $_"
    }
    return $lines
}

$sourceFiles = @()
Get-ChildItem -Path $sourceDir -Filter "*.md" -Recurse | ForEach-Object { $sourceFiles += $_.FullName }
Get-ChildItem -Path $docsDir -Filter "*.md" | ForEach-Object { $sourceFiles += $_.FullName }

Write-Host "Found $($sourceFiles.Count) source files to process"

$systemLines = @{}
foreach ($dir in $systemDirs) { $systemLines[$dir] = [System.Collections.ArrayList]::new() }
$totalExtracted = 0

foreach ($file in $sourceFiles) {
    $lines = Get-ValidNarrativeLines $file
    foreach ($line in $lines) {
        $bestSys = $null
        $bestScore = 0
        foreach ($sys in $systemDirs) {
            $keywords = $systemKeywords[$sys]
            if ($keywords) {
                $score = 0
                foreach ($kw in $keywords.Split('|')) {
                    if ($line.Contains($kw)) { $score++ }
                }
                if ($score -gt $bestScore) {
                    $bestScore = $score
                    $bestSys = $sys
                }
            }
        }
        if (-not $bestSys) { $bestSys = $systemDirs[$totalExtracted % $systemDirs.Count] }
        [void]$systemLines[$bestSys].Add($line)
        $totalExtracted++
    }
}

Write-Host "Extracted $totalExtracted valid narrative lines total"

$crlf = "`r`n"
$totalAppended = 0

foreach ($sys in $systemDirs) {
    $targetFile = Join-Path $targetBase "$sys\基础设定.md"
    if (-not (Test-Path $targetFile)) {
        Write-Host "WARNING: Target not found: $targetFile"
        continue
    }
    
    $newLines = $systemLines[$sys]
    if ($newLines.Count -eq 0) { continue }
    
    $sb = [System.Text.StringBuilder]::new()
    [void]$sb.Append($crlf)
    $appendCount = 0
    foreach ($line in $newLines) {
        [void]$sb.Append("- $line$crlf")
        $appendCount++
    }
    
    $contentToAppend = $sb.ToString()
    [System.IO.File]::AppendAllText($targetFile, $contentToAppend, [System.Text.UTF8Encoding]::new($false))
    Write-Host "  $sys : +$appendCount lines"
    $totalAppended += $appendCount
}

Write-Host "Done! Total appended: $totalAppended lines"
