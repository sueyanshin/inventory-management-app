package com.sys.a9store.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.sys.a9store.model.ProductModel

class ProductViewModel : ViewModel() {
    private val firestore = Firebase.firestore

    fun createProduct(productModel: ProductModel, onResult: (Boolean, String?) -> Unit) {
        firestore.collection("products")
            .add(productModel.toMap())
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    onResult(true,null)
                } else{
                    onResult(false,task.exception?.message?:"ကုန်ပစ္စည်းထည့်သွင်းခြင်း မအောင်မြင်ပါ။")
                }
            }
    }

}