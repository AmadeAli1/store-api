package com.amade.storeapi.service

import com.amade.storeapi.model.Category
import com.amade.storeapi.repository.CategoryRepository
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
class CategoryService(
    private val categoryRepository: CategoryRepository,
) {
    suspend fun insertOrUpdate(category: Category) = categoryRepository.save(category)

    suspend fun findCategory(id: Int) = categoryRepository.findById(id)

    suspend fun findAll() = categoryRepository.findAll()
}