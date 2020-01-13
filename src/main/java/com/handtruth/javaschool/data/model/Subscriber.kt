package com.handtruth.javaschool.data.model

interface Subscriber<T> {

    fun insert(obj: T)
    fun delete(id: Int)

}