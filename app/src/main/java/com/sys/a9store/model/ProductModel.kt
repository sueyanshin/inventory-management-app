package com.sys.a9store.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class ProductModel(
    @DocumentId val id: String = "",
    val productCode: String = "",
    val name: String = "",
    val purchasedPrice: Double = 0.0,
    val sellingPrice: Double = 0.0,
    val stockQuantity: Int = 0,
    val category: String = "",
    val image: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
) {

    // Helper function to convert to Map for Firestore
    fun toMap(): Map<String, Any> = mapOf(
        "productCode" to productCode,
        "name" to name,
        "purchasedPrice" to purchasedPrice,
        "sellingPrice" to sellingPrice,
        "stockQuantity" to stockQuantity,
        "category" to category,
        "image" to image,
        "createdAt" to createdAt,
        "updatedAt" to updatedAt,
//        "isActive" to isActive
    )
}