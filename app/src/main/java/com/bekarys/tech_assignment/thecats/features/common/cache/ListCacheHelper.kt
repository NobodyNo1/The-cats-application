package com.bekarys.tech_assignment.thecats.features.common.cache

/***
 * To simplify cache logic created own simple cache data holder
 */
open class ListCacheHelper<T> {

    private var isActual = false

    private val cacheItems: MutableList<T> = mutableListOf()

    fun getElements(): List<T> {
        return cacheItems
    }

    fun getElements(filter: (T) -> Boolean): List<T> {
        return cacheItems.filter {
            filter(it)
        }
    }

    open fun addToCache(element: T) {
        cacheItems.add(element)
    }

    open fun removeFromCache(element: T) {
        cacheItems.remove(element)
    }

    fun updateCache(elements: List<T>) {
        clearCache()
        cacheItems.addAll(elements)
        isActual = true
    }

    fun actual() = isActual

    fun clearCache() {
        cacheItems.clear()
    }
}