package com.example.townapp.performance

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

object ThreadPoolManager {

    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()

    private val DB_MAX_POOL_SIZE = 4
    private val BUSINESS_MAX_POOL_SIZE = 4
    private val CALCULATION_MAX_POOL_SIZE = if (CPU_COUNT > 2) CPU_COUNT * 2 else 4
    private val RENDER_MAX_POOL_SIZE = 2

    private val dbThreadFactory = NamedThreadFactory("DB-Worker")
    private val businessThreadFactory = NamedThreadFactory("Business-Worker")
    private val calculationThreadFactory = NamedThreadFactory("Calculation-Worker")
    private val renderThreadFactory = NamedThreadFactory("Render-Worker")

    val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
    val dbDispatcher: CoroutineDispatcher = Executors.newFixedThreadPool(DB_MAX_POOL_SIZE, dbThreadFactory).asCoroutineDispatcher()
    val businessDispatcher: CoroutineDispatcher = Executors.newFixedThreadPool(BUSINESS_MAX_POOL_SIZE, businessThreadFactory).asCoroutineDispatcher()
    val calculationDispatcher: CoroutineDispatcher = Executors.newFixedThreadPool(CALCULATION_MAX_POOL_SIZE, calculationThreadFactory).asCoroutineDispatcher()
    val renderDispatcher: CoroutineDispatcher = Executors.newFixedThreadPool(RENDER_MAX_POOL_SIZE, renderThreadFactory).asCoroutineDispatcher()

    private class NamedThreadFactory(private val prefix: String) : ThreadFactory {
        private val counter = AtomicInteger(0)
        override fun newThread(runnable: Runnable): Thread {
            val thread = Thread(runnable, "$prefix-${counter.incrementAndGet()}")
            thread.isDaemon = true
            thread.priority = Thread.NORM_PRIORITY
            return thread
        }
    }

    fun shutdownAll() {
        listOf(dbDispatcher, businessDispatcher, calculationDispatcher, renderDispatcher)
            .forEach { (it as? ExecutorService)?.shutdown() }
    }
}