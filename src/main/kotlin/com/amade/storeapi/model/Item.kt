package com.amade.storeapi.model

import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date
import java.time.LocalDate
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Table("item")
data class Item(
    @field:NotNull @field:NotBlank @field:Length(max = 50, min = 5) @Column("name") val name: String,
    @field:NotNull @field:Min(1) @Column("price") var price: Float,
    @field:NotNull @field:Min(1) @Column("quantity") var quantity: Int,
    @field:NotNull @Column("categoryid") var category: Int,
    @Column("likes") var likes : Int = 0,
    @Column("date") var date: LocalDate = Date(System.currentTimeMillis()).toLocalDate()
) {
    @Id
    @Column("id")
    var id: Int = 0

    @org.springframework.data.annotation.Transient
    var images: List<String> = listOf("")


}