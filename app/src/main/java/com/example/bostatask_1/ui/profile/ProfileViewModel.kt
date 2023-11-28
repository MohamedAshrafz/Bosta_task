package com.example.bostatask_1.ui.profile

import androidx.lifecycle.*
import com.example.bostatask_1.model.UserData
import com.example.bostatask_1.network.AlbumProperty
import com.example.bostatask_1.network.Network.NetworkServices

class ProfileViewModel : ViewModel() {

    private var _networkUsers = liveData {
        emit(NetworkServices.getUsers())
    }

    private var _user = _networkUsers.map {
        val randomUser = it.shuffled()[0]
        UserData(
            id = randomUser.userId,
            name = randomUser.userName,
            address = "${randomUser.userAddress.street}, " +
                    "${randomUser.userAddress.suite}, " +
                    "${randomUser.userAddress.city}, " +
                    randomUser.userAddress.zipcode,
        )
    }
    val user: LiveData<UserData>
        get() = _user

    private var _albumsList = user.switchMap {
        liveData {
            emit(NetworkServices.getAlbumsForUserId(it.id))
        }
    }
    val albumsList: LiveData<List<AlbumProperty>>
        get() = _albumsList
}
