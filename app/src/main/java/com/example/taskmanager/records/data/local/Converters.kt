package com.example.taskmanager.records.data.local

import androidx.room.TypeConverter
import com.example.taskmanager.records.data.model.ModificationType
import com.example.taskmanager.records.domain.util.Urgency

class Converters {
    @TypeConverter
    fun fromModificationType(type: ModificationType) : String {
        return type.toString()
    }

    @TypeConverter
    fun toModificationType(value: String) : ModificationType {
        return ModificationType.valueOf(value)
    }
}