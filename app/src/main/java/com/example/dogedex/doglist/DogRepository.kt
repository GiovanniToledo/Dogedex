package com.example.dogedex.doglist

import com.example.dogedex.R
import com.example.dogedex.domain.Dog
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.api.DogsApi.retrofitService
import com.example.dogedex.api.dto.AddDogToUserDTO
import com.example.dogedex.api.dto.DogDTOMapper
import com.example.dogedex.api.makeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(Dispatchers.IO) {
            val allDogsListDeferred = async { downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }
            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            if (allDogsListResponse is ApiResponseStatus.Error) {
                allDogsListResponse
            } else if (userDogsListResponse is ApiResponseStatus.Error) {
                userDogsListResponse
            } else if (allDogsListResponse is ApiResponseStatus.Success
                && userDogsListResponse is ApiResponseStatus.Success
            ) {
                ApiResponseStatus.Success(
                    getCollectionList(
                        allDogsListResponse.data,
                        userDogsListResponse.data
                    )
                )
            } else {
                ApiResponseStatus.Error(R.string.unknown_error)
            }
        }
    }

    private fun getCollectionList(
        allDogsListResponse: List<Dog>,
        userDogsListResponse: List<Dog>
    ) = allDogsListResponse.map {
        if (userDogsListResponse.contains(it)) {
            it
        } else {
            Dog(
                it.id, it.index, "", "", "",
                "", it.imageUrl, "",
                "", "", "",
                inCollection = false
            )
        }
    }.sorted()


    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListResponse = retrofitService.getAllDogs()
        val dogDTOList = dogListResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListResponse = retrofitService.getUserDogs()
        val dogDTOList = dogListResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogId)
        val defaultResponse = retrofitService.addDogToUser(addDogToUserDTO)

        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }
    }

    suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> = makeNetworkCall {
        val defaultResponse = retrofitService.getDogByMlId(mlDogId)
        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }

        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOtoDogDomain(defaultResponse.data.dog)
    }
}