package com.bekarys.tech_assignment.thecats.features.common.cache

/***
 * To simplify cache logic created own simple cache data holder
 */
class KeyValueCacheHelper<K, T> : ListCacheHelper<KeyValueData<K, T>>() {

    override fun addToCache(element: KeyValueData<K, T>) {
        if (isUnique(element)) {
            super.addToCache(element)
            return
        }
        val foundElement = getElements().first { element.key == element.key }
        removeFromCache(foundElement)
        super.addToCache(element)
    }

    private fun isUnique(
        element: KeyValueData<K, T>
    ) = getElements().none {
        it.key == element.key
    }
}