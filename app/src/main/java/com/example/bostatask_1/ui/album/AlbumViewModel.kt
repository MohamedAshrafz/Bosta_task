package com.example.bostatask_1.ui.album

import androidx.lifecycle.*
import com.example.bostatask_1.network.AlbumProperty
import com.example.bostatask_1.network.Network.NetworkServices
import com.example.bostatask_1.network.PhotoProperty

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
        emit(NetworkServices.getPhotosForAlbumId(selectedAlbum.id))
    }
    val photosList: LiveData<List<PhotoProperty>>
        get() = _photosList
}