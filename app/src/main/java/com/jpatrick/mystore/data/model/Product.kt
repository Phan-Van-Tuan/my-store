package com.jpatrick.mystore.data.model

data class Product (
    val _id: String,
    val storeId: String,
    val name: String,
    val image: String,
    val description: String,
    val price: Number,
    val quantity: Number,
//    val rate: Number,
    val sold: Number,
    val categories: Array<String>,
    val createdAt: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (_id != other._id) return false
        if (storeId != other.storeId) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (price != other.price) return false
        if (quantity != other.quantity) return false
        if (sold != other.sold) return false
        if (!categories.contentEquals(other.categories)) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _id.hashCode()
        result = 31 * result + storeId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + quantity.hashCode()
        result = 31 * result + sold.hashCode()
        result = 31 * result + categories.contentHashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }
}