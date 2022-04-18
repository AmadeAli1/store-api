package com.amade.storeapi.repository

import com.amade.storeapi.model.Company
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository : CoroutineCrudRepository<Company, Int> {

    @Modifying
    @Query("DELETE FROM company where id=:id")
    suspend fun delete(id: Int): Int

}