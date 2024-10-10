package com.example.taskmanager.core.data.local

import androidx.room.TypeConverter
import com.example.taskmanager.core.data.local.model.ModificationType

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