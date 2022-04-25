package com.amade.storeapi.repository

import com.amade.storeapi.model.Item
import com.amade.storeapi.model.ItemImage
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : CoroutineCrudRepository<Item, Int> {

    @Modifying
    @Query("INSERT INTO item (name,price,image,companyid,categoryid) values(:?1,:?2,:?3,:?4,:?5)")
    suspend fun insert(
        name: String,
        price: Float,
        image: String,
        companyId: Int,
        categoryId: Int,
    ): Int

    @Modifying
    @Query("INSERT INTO itemimage (itemid,image) values(:?1,:?2)")
    suspend fun insertImages(itemId: Int, image: String): Int

    @Query("SELECT * FROM itemimage where itemid=:id")
    fun findImages(id: Int): Flow<ItemImage>

    @Modifying
    @Query("UPDATE item set name=:name,image=:image where id=:id")
    suspend fun update(id: Int, name: String, image: String): Int

    @Modifying
    @Query("DELETE FROM item where id=:id")
    suspend fun delete(id: Int): Int

    @Modifying
    @Query("INSERT INTO usuarioitem (itemid,userid,status) values (:?1,:?2,:?3)")
    suspend fun selectItem(itemId: Int, userId: String, status: String = "whitlist"): Int

    @Modifying
    @Query("DELETE FROM usuarioitem where itemid=:itemId and userid=:userId")
    suspend fun removeSelectItem(itemId: Int, userId: String): Int

    @Query("SELECT * FROM item where companyid=:companyId")
    fun findItemsByCompany(companyId: String): Flow<Item>

    @Query("SELECT item.* FROM item inner join usuarioitem on item.id = usuarioitem.itemid and usuarioitem.userid=:userId")
    fun findWhitlistItems(userId: String): Flow<Item>

}