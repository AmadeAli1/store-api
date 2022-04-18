package com.amade.storeapi.controller

import com.amade.storeapi.model.Company
import com.amade.storeapi.model.Constants.SUCCESS
import com.amade.storeapi.service.CompanyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/company")
@RestController
class CompanyController(
    private val companyService: CompanyService,
) {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    suspend fun findAll() = withContext(Dispatchers.IO) {
        companyService.findAll()
    }

    @PostMapping
    suspend fun insert(@Valid @RequestBody company: Company) = withContext(Dispatchers.IO) {
        companyService.insertOrUpdate(company)
    }

    @PutMapping
    suspend fun update(@Valid @RequestBody company: Company) = withContext(Dispatchers.IO) {
        companyService.insertOrUpdate(company)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable("id") id: Int) = withContext(Dispatchers.IO) {
        val status = companyService.delete(id)
        if (status == SUCCESS) {
            return@withContext ResponseEntity("Company Removido com sucesso", HttpStatus.OK)
        }
        ResponseEntity("Company nao encontrado.", HttpStatus.NOT_FOUND)
    }

    @GetMapping("/{id}")
    suspend fun findCompany(@PathVariable("id") id: Int) = withContext(Dispatchers.IO) {
        companyService.findCompany(id)!!
    }
}