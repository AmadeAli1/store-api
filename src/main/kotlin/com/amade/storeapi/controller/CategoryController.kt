package com.amade.storeapi.controller

import com.amade.storeapi.model.Category
import com.amade.storeapi.model.constantes.Constants.SUCCESS
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
            return@withContext ResponseEntity(category, HttpStatus.OK)
        }
        ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    suspend fun insert(@Valid @RequestBody category: Category) = withContext(Dispatchers.IO) {
        categoryService.insertOrUpdate(category)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    suspend fun update(@Valid @RequestBody category: Category) = withContext(Dispatchers.IO) {
        categoryService.insertOrUpdate(category)
    }

    @DeleteMapping("/delete/{id}")
    suspend fun delete(@PathVariable("id") id: Int): ResponseEntity<String> {
        return withContext(Dispatchers.IO) {
            val status = categoryService.delete(id)
            if (status == SUCCESS) {
                return@withContext ResponseEntity("Categoria Removida com sucesso", HttpStatus.OK)
            }
            ResponseEntity("Categoria nao encontrada.", HttpStatus.NOT_FOUND)
        }
    }
}