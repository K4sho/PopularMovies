package ru.skillbranch.searchmovie.data.repository

import ru.skillbranch.searchmovie.data.sources.categories.ICategoriesDataSource

class CategoriesModel(
    private val categoriesDataSource: ICategoriesDataSource
) {
    fun getCategories() = categoriesDataSource.getCategories()
}