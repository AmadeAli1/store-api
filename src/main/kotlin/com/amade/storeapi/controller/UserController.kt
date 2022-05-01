package com.amade.storeapi.controller

import com.amade.storeapi.model.Constants.SUCCESS
import com.amade.storeapi.model.User
import com.amade.storeapi.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/users")
@RestController
class
UserController(
    private val userService: UserService,
) {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    suspend fun findAll(): Flow<User> {
        return withContext(Dispatchers.IO) {
            userService.findAll()
        }
    }

    @GetMapping("/{id}")
    suspend fun findUser(@PathVariable("id") id: String): ResponseEntity<User> {
        return withContext(Dispatchers.IO) {
            val response = userService.findUser(id)
            if (response != null) {
                return@withContext ResponseEntity(response, HttpStatus.OK)
            }
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping
    suspend fun save(@Valid @RequestBody user: User): ResponseEntity<User> {
        return withContext(Dispatchers.IO) {
            val response: User?
            try {
                response = userService.save(user)
            } catch (e: Exception) {
                return@withContext ResponseEntity<User>(HttpStatus.BAD_REQUEST)
            }
            ResponseEntity(response, HttpStatus.CREATED)
        }
    }

    @PutMapping
    suspend fun update(@Valid @RequestBody user: User): ResponseEntity<User> {
        return withContext(Dispatchers.IO) {
            val response = userService.update(user)
            if (response != null) {
                return@withContext ResponseEntity<User>(user, HttpStatus.OK)
            }

            ResponseEntity<User>(HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable("id") id: String): ResponseEntity<String> {
        return withContext(Dispatchers.IO) {
            val status = userService.delete(id)
            if (status == SUCCESS) {
                return@withContext ResponseEntity("Usuario Removido", HttpStatus.OK)
            }
            ResponseEntity("Usuario nao encontrado.", HttpStatus.NOT_FOUND)
        }
    }

}