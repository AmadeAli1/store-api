package com.amade.storeapi.repository

import com.amade.storeapi.model.Category
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository:CoroutineCrudRepository<Category,Int> {
}