package com.sadri.universitypanel.infrastructure.utils

import android.annotation.SuppressLint
import androidx.arch.core.internal.SafeIterableMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

@SuppressLint("RestrictedApi")
class MultiSourceBooleanMediatorLiveData : MediatorLiveData<Boolean>() {

  private val liveDataToObserverMap = SafeIterableMap<LiveData<*>, Source<*>>()

  private val sourceChangeObserver = Observer<Any?> {
    updateValue()
  }

  override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<in S>) {
    throw IllegalAccessException("Use addBooleanSource Instead")
  }

  fun <S : Any?> addBooleanSource(
    source: LiveData<S>,
    onChanged: (value: S?) -> Boolean
  ) {
    val old = liveDataToObserverMap.putIfAbsent(
      source,
      Source(
        source,
        onChanged
      )
    )
    if (old != null) {
      throw IllegalStateException("Duplicate Source is added, call removeBooleanSource first")
    }

    super.addSource(source, sourceChangeObserver)
  }

  override fun <S : Any?> removeSource(source: LiveData<S>) {
    liveDataToObserverMap.remove(source)

    super.removeSource(source)
  }

  private fun updateValue() {
    var res = false
    for (liveDataToObserverItem in liveDataToObserverMap) {
      if (liveDataToObserverItem.value.getResult()) {
        res = true
        break
      }
    }
    value = res
  }

  private class Source<V> internal constructor(
    val liveData: LiveData<V>,
    val onChanged: (value: V?) -> Boolean
  ) {
    fun getResult(): Boolean {
      return onChanged(liveData.value)
    }
  }
}