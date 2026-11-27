package com.example.androidsprints.app.di

interface Factory<T> {
    fun create(): T
}