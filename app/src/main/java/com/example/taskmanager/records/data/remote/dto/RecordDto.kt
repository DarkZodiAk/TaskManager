package com.example.taskmanager.records.data.remote.dto

import com.example.taskmanager.records.data.local.entity.RecordEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecordDto(
    val id: String,
    val isChecked: Boolean,
    val name: String,
    val description: String,
    val isTask: Boolean,
    //val urgency: String,
    val deadline: Long,
    //val tags: List<String>
)

fun RecordDto.toRecord(): RecordEntity {
    return RecordEntity(
        id,
        isChecked,
        name,
        description,
        isTask,
        //Urgency.valueOf(urgency),
        deadline
    )
}

fun RecordEntity.toRecordDto(): RecordDto {
    return RecordDto(
        id,
        isChecked,
        name,
        description,
        isTask,
        //urgency.toString(),
        deadline,
    )
}