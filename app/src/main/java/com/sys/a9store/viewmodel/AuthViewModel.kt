package com.sys.a9store.viewmodel

import AuthRepository
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.rpc.context.AttributeContext.Auth
import com.sys.a9store.model.UserModel
import kotlinx.coroutines.launch

//class AuthViewModel : ViewModel() {
//
//    private val auth = Firebase.auth
//
//    private val firestore = Firebase.firestore
//
//    fun login(email : String, password: String, onResult: (Boolean, String?) -> Unit){
//        auth.signInWithEmailAndPassword(email,password)
//            .addOnCompleteListener {
//                if(it.isSuccessful){
//                    onResult(true,null)
//                }else{
//                    onResult(false,it.exception?.localizedMessage)
//                }
//            }
//    }
//
//    fun signup(email : String, name : String, password: String, onResult: (Boolean,String?)-> Unit){
//        auth.createUserWithEmailAndPassword(email,password)
//            .addOnCompleteListener {
//                if(it.isSuccessful){
//                    val userId = it.result?.user?.uid;
//                    val userModel = UserModel(name,email, userId!!)
//                    firestore.collection("users").document(userId)
//                        .set(userModel)
//                        .addOnCompleteListener { dbTask ->{
//                            if(dbTask.isSuccessful){
//                                onResult(true,null)
//                            }
//                            else{
//                                onResult(false, "Something went wrong.")
//                            }
//                        } }
//
//                }else{
//                    onResult(false,it.exception?.localizedMessage)
//                }
//            }
//    }
//}


class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = mutableStateOf(AuthUiState())
    val uiState: State<AuthUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                authRepository.login(email, password)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Login failed"
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                authRepository.logout()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Logout failed"
                )
            }
        }
    }

}

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)