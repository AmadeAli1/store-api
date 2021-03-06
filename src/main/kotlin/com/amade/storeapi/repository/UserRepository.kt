package com.amade.storeapi.repository

import com.amade.storeapi.model.User
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@SuppressWarnings
@Repository
interface UserRepository : CoroutineCrudRepository<User, String> {

    @Modifying
    @Query("INSERT INTO usuario values(:?1,:?2,:?3,:?4)")
    suspend fun insert(id: String, email: String, name: String, image: String?): Int

    @Modifying
    @Query("UPDATE usuario SET image=:image where id=:id")
    suspend fun update(id: String, image: String?): Int

    @Modifying
    @Query("DELETE FROM usuario where id=:id")
    suspend fun delete(id: String): Int
}