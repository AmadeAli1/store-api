package com.amade.storeapi.controller

import com.amade.storeapi.model.Category
import com.amade.storeapi.service.CategoryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/category")
@RestController
class CategoryController(
    private val categoryService: CategoryService,
) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    suspend fun findAll() = withContext(Dispatchers.IO) {
        categoryService.findAll()
    }

    @GetMapping("/{id}")
    suspend fun findCategory(@PathVariable("id") id: Int) = withContext(Dispatchers.IO) {
        val category = categoryService.findCategory(id)
        if (category != null) {
            return@withContext ResponseEntity(category, HttpStatus.FOUND)
        }
        ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping
    suspend fun insertOrUpdate(@Valid @RequestBody category: Category) = withContext(Dispatchers.IO) {
        categoryService.insertOrUpdate(category)
    }

}