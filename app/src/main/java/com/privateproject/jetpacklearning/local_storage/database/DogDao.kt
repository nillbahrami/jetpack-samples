package com.privateproject.jetpacklearning.local_storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.privateproject.jetpacklearning.model.DogBread
import retrofit2.http.DELETE

@Dao
interface DogDao {
    @Insert
    suspend fun insertAll(vararg dogs: DogBread): List<Long> //vararg: multiple arguments
    // suspend: it needs to be done in a different thread

    @Query("SELECT * FROM dogbread")
    suspend fun getAll(): List<DogBread>

    @Query("DELETE FROM dogbread")
    suspend fun deleteAll()

    @Query("SELECT * FROM dogbread WHERE uuid = :dogUid")
    suspend fun getById(dogUid: Int): DogBread


}