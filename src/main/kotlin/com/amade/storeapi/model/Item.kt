package com.amade.storeapi.model

import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Table("item")
data class Item(
    @Id @Column("id") val id: Int,
    @NotNull @NotBlank @Length(max = 50, min = 5) @Column("name") val name: String,
    @NotNull @NotBlank @Column("category") val category: String,
    @NotNull @NotBlank @Min(1) @Column("price") var price: Float,
    @NotNull @NotBlank @Column("image") var image: String,
    @NotNull @NotBlank @Column("companyid") val companyId: Int,
    @NotNull @NotBlank @Column("categoryid") val categoryId: Int,
) {
    @NotNull
    @NotBlank
    @Column("date")
    lateinit var date: Date
}