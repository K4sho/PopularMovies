package ru.skillbranch.searchmovie.presentation.view_models

import ru.skillbranch.searchmovie.data.sources.categories.ICategoriesDataSource

class CategoriesViewModel(
    private val categoriesDataSource: ICategoriesDataSource
) {
    fun getCategories() = categoriesDataSource.getCategories()
}