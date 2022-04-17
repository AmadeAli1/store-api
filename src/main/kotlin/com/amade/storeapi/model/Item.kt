package com.amade.storeapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
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
    @NotNull @NotBlank @Length(max = 50, min = 5) @Column("name") val name: String,
    @NotNull @NotBlank @Min(1) @Column("price") var price: Float,
    @NotNull @NotBlank @Column("image") var image: String,
    @NotNull @NotBlank @Column("companyid") var company: Int,
    @NotNull @NotBlank @Column("categoryid") var category: Int,
) {
    @Id
    @Column("id")
    var id: Int = 0

    @Column("date")
    @org.springframework.data.annotation.Transient
    var date: Date = Date(System.currentTimeMillis())

}