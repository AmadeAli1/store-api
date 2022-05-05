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
    @Query("INSERT INTO itemimage (itemid,image) values(:?1,:?2)")
    suspend fun inserir_imagem(itemId: Int, image: String): Int

    @Query("SELECT * FROM itemimage where itemid=$1")
    fun obter_todas_imagens(id: Int): Flow<ItemImage>

    @Modifying
    @Query("DELETE FROM item where id=:id")
    suspend fun remover_item(id: Int): Int

    @Modifying
    @Query("INSERT INTO usuariocart (itemid,userid) values (:?1,:?2)")
    suspend fun adicionar_no_carrinho_de_compras(itemId: Int, userId: String): Int

    @Modifying
    @Query("DELETE FROM usuariocart where itemid=:itemId and userid=:userId")
    suspend fun remover_do_carrinho_de_compras(itemId: Int, userId: String): Int

    @Query("SELECT item.* FROM item inner join usuariocart on item.id = usuariocart.itemid and usuariocart.userid=:userId")
    fun obter_todos_items_do_usuario(userId: String): Flow<Item>

    @Modifying
    @Query("INSERT INTO usuarioitem (itemid,userid,status) values (:?1,:?2,:?3)")
    suspend fun adicionar_na_lista_de_desejos(itemId: Int, userId: String, status: String = "whitlist"): Int


    @Modifying
    @Query("DELETE FROM usuarioitem where itemid=:itemId and userid=:userId")
    suspend fun remover_da_lista_de_desejos(itemId: Int, userId: String): Int

    @Query("SELECT item.* FROM item inner join usuarioitem on item.id = usuarioitem.itemid and usuarioitem.userid=:userId")
    fun obter_todos_items_da_lista_de_desejos_do_usuario(userId: String): Flow<Item>

    @Query("select * from item where upper(item.name) like upper(concat($1,'%'))")
    fun pesquisar(query: String): Flow<Item>

    @Query("select exists(select * from usuarioitem where userid=$1 and itemid=$2)")
    suspend fun verificar_existencia_do_item_na_lista_de_desejos(userId: String, itemId: Int): Boolean

    @Query("select exists(select * from usuariocart where userid=$1 and itemid=$2)")
    suspend fun verificar_existencia_do_item_no_carrinho_de_compras(userId: String, itemId: Int): Boolean

    @Modifying
    @Query("INSERT INTO usuarioitemlike(userid,itemid) values (:?1,:?2)")
    suspend fun adicionar_like_do_usuario(userId: String, itemId: Int): Int

    @Modifying
    @Query("DELETE from usuarioitemlike where userid=$1 and itemid=$2")
    suspend fun remover_like_do_usuario(userId: String, itemId: Int): Int


    @Modifying
    @Query("update item set likes=likes+1 where id=$1")
    suspend fun adicionar_like_ao_item(itemId: Int): Int

    @Modifying
    @Query("update item set likes=likes-1 where id=$1")
    suspend fun remover_like_do_item(itemId: Int): Int

    @Query("select exists(select * from usuarioitemlike where userid=$1 and itemid=$2)")
    suspend fun verificar_existencia_de_like_do_usuario(userId: String, itemId: Int): Boolean

}