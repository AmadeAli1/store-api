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
    @Id @Column("id") val id: Int,
    @NotNull @NotBlank @Length(max = 50, min = 5) @Column("name") val name: String,
    @NotNull @NotBlank @Min(1) @Column("price") var price: Float,
    @NotNull @NotBlank @Column("image") var image: String,
    @JsonIgnore @NotNull @NotBlank @Column("companyid") var companyId: Int,
    @JsonIgnore @NotNull @NotBlank @Column("categoryid") val categoryId: Int,
) {

    @Column("date")
    lateinit var date: Date
}