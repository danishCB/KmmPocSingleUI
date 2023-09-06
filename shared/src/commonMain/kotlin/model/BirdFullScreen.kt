package model

import BirdsViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

class BirdFullScreen(val images: BirdImage) : Screen {
    @Composable
    override fun Content() {
        BirdViewFullScreen(images)
    }

}

@Composable
fun BirdViewFullScreen(image: BirdImage) {
    val birdsViewModel = getViewModel(Unit, viewModelFactory { BirdsViewModel() })
    BirdsFullPage(birdsViewModel, image)
}

@Composable
fun BirdsFullPage(viewModel: BirdsViewModel, image: BirdImage) {
    val uiState by viewModel.uiState.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    Column {

        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = { navigator.pop() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }

            Text(
                text = "Bird Detail",
                style = MaterialTheme.typography.h6
            )
        }

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BirdFullImageCell(image)
        }

    }
}

@Composable
fun BirdFullImageCell(image: BirdImage) {

    Card(
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {

            Box(
                modifier = Modifier.height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                KamelImage(
                    asyncPainterResource("${image.url}"),
                    "${image.title} by ${image.user}",
                    contentScale = ContentScale.Crop,
                )
            }

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = image.title.toString(),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = image.description.toString(),
                    style = MaterialTheme.typography.caption,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
