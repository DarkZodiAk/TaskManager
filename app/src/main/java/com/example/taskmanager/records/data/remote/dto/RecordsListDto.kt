package com.example.taskmanager.records.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecordsListDto(
    val records: List<RecordDto>
)