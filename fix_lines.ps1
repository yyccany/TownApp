$path = 'app\src\main\java\com\example\townapp\ui\screens\CognitiveScalpelScreen.kt'
$lines = [System.IO.File]::ReadAllLines($path, [System.Text.Encoding]::UTF8)

# Map of line numbers (1-based) to fixed content
$fixes = @{
    120 = '        "【结论】只要结果是好的，动机是否\"纯粹\"没那么重要。"'
    230 = '        "【成本】你被恩情绑架，做了许多自己不愿做的事来\"还人情\"。",'
    250 = '        "【结论】用\"世态炎凉\"当借口封闭自己，是另一种偷懒。"'
    256 = '        "【时代】现代人社交边界更清晰，\"冷\"可能只是尊重距离，不是冷漠。",'
    258 = '        "【结论】把别人的\"冷\"当成自己的错，是一种不必要的自我攻击。"'
    288 = '        "【替代】\"厚颜\"可能只是对方的生存策略，理解比批判更有力量。",'
    291 = '        "【结论】少一点道德审判，多一点\"他为什么会这样\"。"'
    295 = '        "【成本】你被\"耻感文化\"绑架，任何拒绝都感到愧疚。",'
    299 = '        "【结论】健康的边界感，有时看起来就像\"不知耻\"。"'
    306 = '        "【内核】不是所有的事都值得羞耻，很多\"耻\"只是文化灌输的。",'
    321 = '        "【时代】媒体喜欢用\"丧心病狂\"博眼球，但人性很少非黑即白。",'
    322 = '        "【内核】当一个人被贴上\"不是人\"的标签，我们就放弃了理解人性的机会。",'
    327 = '        "【成本】你接受了\"人性本善\"的叙事，对真实的复杂性缺乏准备。",'
    336 = '        "【替代】严重错误需要承担后果，但\"不赦\"的权利不应该被随意使用。",'
}

foreach ($lineNum in $fixes.Keys) {
    $lines[$lineNum - 1] = $fixes[$lineNum]
}

[System.IO.File]::WriteAllLines($path, $lines, [System.Text.Encoding]::UTF8)
Write-Host 'Done'