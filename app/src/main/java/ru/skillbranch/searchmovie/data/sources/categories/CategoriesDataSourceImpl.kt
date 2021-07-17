package ru.skillbranch.searchmovie.data.sources.categories

import ru.skillbranch.searchmovie.data.dto.CategoryDto

class CategoriesDataSourceImpl: ICategoriesDataSource {
    override fun getCategories() = listOf(
        CategoryDto(
            name = "боевики"
        ),
        CategoryDto(
            name = "драмы"
        ),
        CategoryDto(
            name = "комедии"
        ),
        CategoryDto(
            name = "артхаус"
        ),
        CategoryDto(
            name = "мелодрамы"
        ),
        CategoryDto(
            name = "вестерны"
        ),
        CategoryDto(
            name = "военные"
        ),
        CategoryDto(
            name = "детективы"
        ),
        CategoryDto(
            name = "истории"
        ),
        CategoryDto(
            name = "криминал"
        ),
        CategoryDto(
            name = "мультфильмы"
        ),
        CategoryDto(
            name = "ужасы"
        )
    )
}