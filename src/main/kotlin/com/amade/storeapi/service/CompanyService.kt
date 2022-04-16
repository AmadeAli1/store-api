package com.amade.storeapi.service

import com.amade.storeapi.model.Company
import com.amade.storeapi.repository.CompanyRepository
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
) {

    suspend fun insertOrUpdate(company: Company): Company = companyRepository.save(company)

    suspend fun delete(id: Int) = companyRepository.deleteById(id)

    suspend fun findCompany(id: Int) = companyRepository.findById(id)

    fun findAll() = companyRepository.findAll()

}