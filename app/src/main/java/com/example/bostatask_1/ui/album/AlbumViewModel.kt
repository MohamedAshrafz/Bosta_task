package com.example.bostatask_1.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bostatask_1.network.AlbumProperty

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

    private var _text = MutableLiveData(selectedAlbum.id + "\n" + selectedAlbum.title)
    val text: LiveData<String>
        get() = _text
}