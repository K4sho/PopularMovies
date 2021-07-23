package ru.skillbranch.searchmovie.data.repository

import ru.skillbranch.searchmovie.data.sources.categories.ICategoriesDataSource

class CategoriesRepository(
    private val categoriesDataSource: ICategoriesDataSource
) {
    fun getCategories() = categoriesDataSource.getCategories()
}