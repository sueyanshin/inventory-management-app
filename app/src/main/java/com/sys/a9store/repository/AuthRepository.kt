import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.sys.a9store.model.UserModel
import kotlinx.coroutines.tasks.await

class  AuthRepositoryImpl : AuthRepository{
    private val auth = Firebase.auth
    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun login(email: String, password: String): FirebaseUser {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user?: throw  Exception("Login failed")
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun getCurrentUser(): UserModel? {
        return auth.currentUser?.let { firebaseUser ->
            UserModel(
                uid = firebaseUser.uid,
                name = firebaseUser.displayName.toString(),
                email = firebaseUser.email.toString()
            )
        }
    }

}

interface AuthRepository{
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): FirebaseUser
    suspend fun logout()
    suspend fun getCurrentUser(): UserModel?
}
