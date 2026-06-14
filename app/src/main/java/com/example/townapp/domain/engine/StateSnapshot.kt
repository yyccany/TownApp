package com.example.townapp.domain.engine

import com.example.townapp.domain.spacemodel.SpaceState

/**
 * 全局状态快照。
 *
 * 聚合某一时刻的完整游戏状态：时间、玩家、空间。
 * 用于状态存档、状态回滚或跨层数据传输。
 */
data class StateSnapshot(
    val gameTime: GameTime,
    val playerState: PlayerState,
    val spaceState: SpaceState
)
