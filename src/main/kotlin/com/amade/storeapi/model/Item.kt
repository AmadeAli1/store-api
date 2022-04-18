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
    @field:NotNull @field:NotBlank @field:Length(max = 50, min = 5) @Column("name") val name: String,
    @field:NotNull @field:NotBlank @field:Min(1) @Column("price") var price: Float,
    @field:NotNull @field:NotBlank @Column("image") var image: String,
    @field:NotNull @field:NotBlank @Column("companyid") var company: Int,
    @field:NotNull @field:NotBlank @Column("categoryid") var category: Int,
) {
    @Id
    @Column("id")
    var id: Int = 0

    @Column("date")
    @org.springframework.data.annotation.Transient
    var date: Date = Date(System.currentTimeMillis())

}