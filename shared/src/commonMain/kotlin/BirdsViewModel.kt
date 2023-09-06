import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import model.BirdResponse

data class BirdsUiState(
    val images: BirdResponse? = null, val selectedCategory: String? = null
) {
    val categories = images?.photos?.map { it.title }?.toSet()
    val selectedImages = images?.photos?.filter { it.title == selectedCategory }
}

class BirdsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<BirdsUiState>(BirdsUiState())
    val uiState = _uiState.asStateFlow()

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                useAlternativeNames = false
                ignoreUnknownKeys = true
            })
        }
    }

    init {
        updateImages()
    }

    override fun onCleared() {
        httpClient.close()
    }

    fun selectCategory(category: String) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
    }

    fun updateImages() {
        viewModelScope.launch {
            val images = getImages()
            _uiState.update {
                it.copy(images = images)
            }
        }
    }

    private suspend fun getImages(): BirdResponse {
        val images: BirdResponse =
            httpClient.get("https://api.slingacademy.com/v1/sample-data/photos").body()
        return images
    }
}