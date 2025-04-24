import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.sys.a9store.AppUtil
import com.sys.a9store.GlobalNavigation
import com.sys.a9store.model.CategoryModel
import com.sys.a9store.model.ProductModel
import com.sys.a9store.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductPage(modifier: Modifier = Modifier) {

    val productViewModel: ProductViewModel = viewModel()
    var name by remember {
        mutableStateOf("")
    }
    var purchasePrice by remember {
        mutableStateOf("")
    }
    var sellingPrice by remember {
        mutableStateOf("")
    }
    var stockQuantity by remember {
        mutableStateOf("")
    }
    var image by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    var context = LocalContext.current

    val categoryList = remember {
        mutableStateOf<List<CategoryModel>>(emptyList())
    }

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedCategory by remember {
//        mutableStateOf(categoryList.value.get(0).id)
        mutableStateOf("")
    }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data")
            .document("stock")
            .collection("categories")
            .get().addOnCompleteListener() {
                if (it.isSuccessful) {
                    val resultList = it.result.documents.mapNotNull { doc ->
                        doc.toObject(CategoryModel::class.java)
                    }
                    categoryList.value = resultList
                    // initialize selected category if list isn't empty
                    if (resultList.isNotEmpty() && selectedCategory.isEmpty()) {
                        selectedCategory = resultList[0].id
                    }
                }
            }
    }



    Column(modifier = Modifier.fillMaxWidth()) {

        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    GlobalNavigation.navController.navigate("home")
                    {
                        // clear back stack to the home route
                        popUpTo("home") {
                            inclusive = false
                        }
//                        // Avoid multiple copies of the same destination
//                        launchSingleTop = true
                    }
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
            },
            title = {
                Text("ပစ္စည်းအသစ်ထည့်ပါ")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text(text = "ပစ္စည်းအမည်")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = purchasePrice,
                onValueChange = {
                    purchasePrice = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(text = "ဝယ်စျေး")
                },

                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = sellingPrice,
                onValueChange = {
                    sellingPrice = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(text = "ရောင်းစျေး")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = stockQuantity,
                onValueChange = {
                    stockQuantity = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                label = {
                    Text(text = "အ‌ရေအတွက်")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = image,
                onValueChange = {
                    image = it
                },
                label = {
                    Text(text = "ဓါတ်ပုံ(လင့်)")
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = !isExpanded }
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = Color.White,
                        focusedTextColor = Color.Black,  // Text color when focused
                        unfocusedTextColor = Color.Black,  // Text color when not focused
                    ),
                    value = categoryList.value.find { it.id == selectedCategory }?.name
                        ?: "ပစ္စည်းအမျိုးအစား",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                    label = { Text("ပစ္စည်းအမျိုးအစား") }
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    categoryList.value.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(text = category.name) },
                            onClick = {
                                selectedCategory = category.id
                                isExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    isLoading = true

                    if (name == "" || purchasePrice == "" || sellingPrice == "" || stockQuantity == "") {
                        isLoading = false
                        AppUtil.showToast(context, "အချက်အလက်များဖြည့်သွင်းရန်ကျန်သေးသည်")
                    } else {
                        val newProduct = ProductModel(
                            name = name,
                            purchasedPrice = purchasePrice.toDouble(),
                            sellingPrice = sellingPrice.toDouble(),
                            stockQuantity = stockQuantity.toInt(),
                            category = selectedCategory
                        )

                        productViewModel.createProduct(newProduct) { success, errorMessage ->
                            if (success) {
                                isLoading = false
                                name = ""
                                purchasePrice = ""
                                sellingPrice = ""
                                stockQuantity = ""
                                image = ""
                                AppUtil.showToast(context, "ပစ္စည်းအသစ်ထည့်ပြီးပါပြီ")
                            } else {
                                isLoading = false
                                AppUtil.showToast(context, errorMessage ?: "Something went wrong")
                            }
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(60.dp)
            ) {
                Text(text = if (isLoading) "ခနစောင့်ပါ.." else "ထည့်မည်", fontSize = 22.sp)
            }
        }

    }
}