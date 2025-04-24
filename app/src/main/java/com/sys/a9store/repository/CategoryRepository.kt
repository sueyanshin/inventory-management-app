import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import com.sys.a9store.model.CategoryModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Locale.Category

class CategoryRepositoryImpl : CategoryRepository {
    private val db = Firebase.firestore
    private val categoriesRef = db.collection("categories")
    override fun getAllCategories(): Flow<List<CategoryModel>> = callbackFlow {
        val snapshotListener = categoriesRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val categories = snapshot?.toObjects<CategoryModel>() ?: emptyList()
            trySend(categories)
        }
        awaitClose { snapshotListener.remove() }
    }

    override suspend fun addCategory(category: CategoryModel) {
        if (category.id.isBlank()) {
            categoriesRef.add(category).await()
        } else {
            categoriesRef.document(category.id).set(category).await()
        }
    }

    override suspend fun deleteCategory(categoryId: String) {
        categoriesRef.document(categoryId).delete().await()
    }


}

interface CategoryRepository {
    fun getAllCategories(): Flow<List<CategoryModel>>
    suspend fun addCategory(category: CategoryModel)
    suspend fun deleteCategory(categoryId: String)
}