package com.example.bostatask_1.ui.profile

import androidx.lifecycle.*
import com.example.bostatask_1.model.UserData
import com.example.bostatask_1.network.Network.NetworkServices


//class ProfileViewModelFactory(
//    private val application: Application,
//    private val userId: String
//) : ViewModelProvider.Factory {
//    @Suppress("unchecked_cast")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
//            return ProfileViewModel(application, userId) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

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
        liveData<List<String>> {
            val list = NetworkServices.getAlbumsForUserId(it.id).map { album -> album.title }
            emit(list)
        }
    }
    val albumsList: LiveData<List<String>>
        get() = _albumsList
}
