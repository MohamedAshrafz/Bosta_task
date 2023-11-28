package com.example.bostatask_1.ui.profile

import androidx.lifecycle.*
import com.example.bostatask_1.network.AlbumProperty
import com.example.bostatask_1.network.Network.NetworkServices
import com.example.bostatask_1.network.UserProperty

class ProfileViewModel : ViewModel() {

    private var _networkUsers = liveData {
        emit(NetworkServices.getUsers())
    }

    private var _user = _networkUsers.map {
        it.shuffled()[0]
    }
    val user: LiveData<UserProperty>
        get() = _user

    private var _albumsList = user.switchMap {
        liveData {
            emit(NetworkServices.getAlbumsForUserId(it.userId))
        }
    }
    val albumsList: LiveData<List<AlbumProperty>>
        get() = _albumsList
}
