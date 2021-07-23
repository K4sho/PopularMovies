package ru.skillbranch.searchmovie.data.sources.categories

import ru.skillbranch.searchmovie.data.dto.CategoryDto

interface ICategoriesDataSource {
    fun getCategories(): List<CategoryDto>
}