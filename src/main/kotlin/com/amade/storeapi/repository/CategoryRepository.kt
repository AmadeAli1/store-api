package com.amade.storeapi.repository

import com.amade.storeapi.model.Category
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository:CoroutineCrudRepository<Category,Int> {

    @Modifying
    @Query("DELETE FROM category where id=:id")
    suspend fun delete(id:Int):Int
}