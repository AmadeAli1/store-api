package com.amade.storeapi.service

import com.amade.storeapi.model.User
import com.amade.storeapi.repository.UserRepository
import kotlinx.coroutines.flow.*
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    suspend fun save(user: User): User {
        val status = userRepository.insert(user.id, user.email, user.name, user.image)
        if (status == 1) {
            return findUser(user.id)!!
        }
        throw RuntimeException("Erro ao gravar o usuario")
    }

    suspend fun findAll(): Flow<User> {
        return userRepository.findAll()
    }

    suspend fun delete(id: String): Int {
        return userRepository.delete(id)
    }

    suspend fun update(user: User): User? {
        val status = userRepository.update(id = user.id, image = user.image)
        return if (status == 1) findUser(id = user.id) else null
    }

    suspend fun findUser(id: String): User? {
        return userRepository.findById(id)
    }


}