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
    @NotNull @NotBlank @Id val id: Int,
    @NotNull @NotBlank @Length(max = 50, min = 5) val name: String,
    @NotNull @NotBlank val category: String,
    @NotNull @NotBlank @Min(1) var price: Float,
    @NotNull @NotBlank var image: String,
    @NotNull @NotBlank val companyId: Int,
    @NotNull @NotBlank val categoryId: Int
) {
    @NotNull
    @NotBlank
    @Column("date")
    lateinit var date: Date
}