package com.example.bostatask_1.ui.album

import androidx.lifecycle.*
import com.example.bostatask_1.network.AlbumProperty
import com.example.bostatask_1.network.Network.NetworkServices
import com.example.bostatask_1.network.PhotoProperty
import timber.log.Timber

class AlbumViewModelFactory(
    private val selectedAlbum: AlbumProperty
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            return AlbumViewModel(selectedAlbum) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AlbumViewModel(private val selectedAlbum: AlbumProperty) : ViewModel() {

    private var _titleText = MutableLiveData(selectedAlbum.title)
    val titleText: LiveData<String>
        get() = _titleText

    private var _photosList = liveData {
        try {
            emit(NetworkServices.getPhotosForAlbumId(selectedAlbum.id))
        } catch (e: Exception) {
            _showToast.value = true
            Timber.tag("REPOSITORY_ERROR_STRING").e(e.stackTraceToString())
        }
    }
    val photosList: LiveData<List<PhotoProperty>>
        get() = _photosList

    private var _searchText = MutableLiveData("")

    fun setSearchText(queryText: String) {
        _searchText.value = queryText
    }

    var queriedPhotosList = _searchText.map { queryText ->
        val list: LiveData<List<PhotoProperty>> = if (queryText != "") {
            _photosList.map { list -> list.filter { it.title.contains(queryText) } }
        } else {
            _photosList
        }
        list
    }

    private var _showToast = MutableLiveData(false)
    val showToast: LiveData<Boolean>
        get() = _showToast

    fun clearShowToast() {
        _showToast.value = false
    }
}