package com.amade.storeapi.repository

import com.amade.storeapi.model.Item
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : CoroutineCrudRepository<Item, Int> {

    @Modifying
    @Query("INSERT INTO item (name,category,price,image,companyId,categoryId) values(:?1,:?2,:?3,:?4,:?5,:?6)")
    suspend fun insert(
        name: String,
        category: String,
        price: Float,
        image: String,
        companyId: Int,
        categoryId: Int,
    ): Int

    @Modifying
    @Query("UPDATE item set name=:name,image=:image where id=:id")
    suspend fun update(id: Int, name: String, image: String): Int

    @Modifying
    @Query("DELETE FROM item where id=:id")
    suspend fun delete(id: Int): Int

    @Modifying
    @Query("INSERT INTO useritem (itemid,userid,status) values (:?1,:?2,:?3)")
    suspend fun selectItem(itemId: Int, userId: String, status: String = "whitlist"): Int

    @Modifying
    @Query("DELETE FROM useritem where itemid=:itemId and userid=:userId")
    suspend fun removeSelectItem(itemId: Int, userId: String): Int

    @Query("SELECT * FROM item where companyid=:companyId")
    fun findItemsByCompany(companyId: String): Flow<Item>

    @Query("SELECT item.* FROM item inner join useritem on item.id = useritem.itemid and useritem.userid=:userId")
    fun findWhitlistItems(userId: String): Flow<Item>

}