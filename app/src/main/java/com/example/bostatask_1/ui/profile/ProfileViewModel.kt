package com.example.bostatask_1.ui.profile

import androidx.lifecycle.*
import com.example.bostatask_1.network.AlbumProperty
import com.example.bostatask_1.network.Network.NetworkServices
import com.example.bostatask_1.network.UserProperty
import timber.log.Timber

class ProfileViewModel : ViewModel() {

    private var _networkUsers = liveData {
        try {
            emit(NetworkServices.getUsers())
        } catch (e: Exception) {
            _showToast.value = true
            Timber.tag("REPOSITORY_ERROR_STRING").e(e.stackTraceToString())
        }
    }

    private var _user = _networkUsers.map {
        it.shuffled()[0]
    }
    val user: LiveData<UserProperty>
        get() = _user

    private var _albumsList = user.switchMap {
        liveData {
            try {
                emit(NetworkServices.getAlbumsForUserId(it.userId))
            } catch (e: Exception) {
                _showToast.value = true
                Timber.tag("REPOSITORY_ERROR_STRING").e(e.stackTraceToString())
            }
        }
    }
    val albumsList: LiveData<List<AlbumProperty>>
        get() = _albumsList

    private var _showToast = MutableLiveData(false)
    val showToast: LiveData<Boolean>
        get() = _showToast

    fun clearShowToast() {
        _showToast.value = false
    }
}
