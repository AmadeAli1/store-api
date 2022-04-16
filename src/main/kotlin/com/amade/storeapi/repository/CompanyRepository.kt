package com.amade.storeapi.repository

import com.amade.storeapi.model.Company
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository:CoroutineCrudRepository<Company,Int> {
}