package com.example.townapp.domain

import com.example.townapp.data.ClothingItem
import com.example.townapp.data.OutfitSet

// ============================================
// 穿搭套装计算器
// ============================================
object OutfitCalculator {

    /**
     * 计算套装总价
     */
    fun calculateTotalPrice(outfit: OutfitSet, allClothes: List<ClothingItem>): Double {
        return allClothes
            .filter { it.id in outfit.clothIdList }
            .sumOf { it.price }
    }

    /**
     * 获取套装中的衣物列表
     */
    fun getOutfitItems(outfit: OutfitSet, allClothes: List<ClothingItem>): List<ClothingItem> {
        return allClothes.filter { it.id in outfit.clothIdList }
    }
}