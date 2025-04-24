import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.sys.a9store.presentation.navigation.GlobalNavigation
import com.sys.a9store.model.ProductModel

@Composable
fun ProductsPage(modifier: Modifier = Modifier) {
    val productList = remember {
        mutableStateOf<List<ProductModel>>(emptyList())
    }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("products")
            .get().addOnCompleteListener() {
                if (it.isSuccessful) {
                    val resultList = it.result.documents.mapNotNull { doc ->
                        doc.toObject(ProductModel::class.java)
                    }
                    productList.value = resultList
                }
            }
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(productList.value.chunked(2)) { rowItems ->
            Row {
                rowItems.forEach {
                    ProductItemView(modifier = Modifier.weight(1f), product = it)
                }
                if(rowItems.size==1){
                    Spacer(modifier= Modifier.weight(1f))
                }
            }
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 116.dp, end = 16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.surface,
            shape = CircleShape,
            onClick = {
                GlobalNavigation.navController.navigate("add-product")
            },
            modifier = Modifier.shadow(8.dp, shape = CircleShape)
        ) {
            Icon(Icons.Default.Add, "ပစ္စည်းအသစ်ထည့်ပါ။")
        }
    }
}