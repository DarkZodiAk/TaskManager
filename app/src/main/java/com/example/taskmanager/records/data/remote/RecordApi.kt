package com.example.taskmanager.records.data.remote

import com.example.taskmanager.records.data.remote.dto.RecordDto
import com.example.taskmanager.records.data.remote.dto.RecordsListDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecordApi {
    @POST("upsert_record")
    suspend fun saveRecord(
        @Body record: RecordDto
    )

    @GET("records")
    suspend fun getRecords(): RecordsListDto

    @DELETE("delete_record/{id}")
    suspend fun deleteRecord(
        @Path("id") id: String
    )
}