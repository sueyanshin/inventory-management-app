import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun BannerView(modifier: Modifier = Modifier) {
    var bannerList by remember {
        mutableStateOf<List<String>>(emptyList())
    }
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data")
            .document("banners")
            .get().addOnCompleteListener(){
                bannerList=it.result.get("urls") as List<String>
            }
    }
    Column(modifier = modifier) {
        val pagerState = rememberPagerState (0){
            bannerList.size
        }
        HorizontalPager(state = pagerState, pageSpacing = 24.dp) {
            AsyncImage(
                model = bannerList.get(it),
                contentScale = ContentScale.FillWidth,
                contentDescription = "Banner image",
                modifier = Modifier.fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                )
        }
        Spacer(modifier = Modifier.height(10.dp))

        DotsIndicator(dotCount = bannerList.size,type = ShiftIndicatorType(
            DotGraphic(color = MaterialTheme.colorScheme.primary,
                size = 6.dp
                )
        ), pagerState = pagerState
            )

    }
}