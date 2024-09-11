package pl.training.extras

import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApi {

    @GET("users")
    suspend fun getUsersIds(): List<Long>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Long): UserDto

}
