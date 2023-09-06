package model

import BirdsViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

class BirdScreen : Screen {

    @Composable
    override fun Content() {
        BirdViewScreen()
    }

}

@Composable
fun BirdViewScreen() {
    val birdsViewModel = getViewModel(Unit, viewModelFactory { BirdsViewModel() })
    BirdsPage(birdsViewModel)
}

@Composable
fun BirdsPage(viewModel: BirdsViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = "Bird Detail",
                style = MaterialTheme.typography.h6
            )

        }

        AnimatedVisibility(true) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
                content = {
                    if (uiState.images?.photos != null) {
                        val itemsData = uiState.images?.photos as ArrayList<BirdImage>
                        println("ResponseData $itemsData")
                        items(itemsData) {
                            BirdImageCell(it, modifier = Modifier)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun BirdImageCell(image: BirdImage, modifier: Modifier) {
    val navigator = LocalNavigator.currentOrThrow

    Card(
        modifier = modifier
            .height(220.dp)
            .padding(8.dp).clickable {
                navigator.push(BirdFullScreen(image))
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Box(
                modifier = modifier.height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                KamelImage(
                    asyncPainterResource("${image.url}"),
                    "${image.title} by ${image.user}",
                    contentScale = ContentScale.Crop,
                )
            }

            Column(
                modifier = modifier.padding(10.dp)
            ) {
                Text(
                    text = image.title.toString(),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = modifier.height(4.dp))

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
