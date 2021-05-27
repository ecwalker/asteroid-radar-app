package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "asteroid_details_table")
data class Asteroid(

    @PrimaryKey(autoGenerate = false)
    val id: Long,

    @ColumnInfo(name = "code_name")
    val codename: String,

    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: String,

    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Double,

    @ColumnInfo(name = "estimated_diameter")
    val estimatedDiameter: Double,

    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double,

    @ColumnInfo(name = "distance_from_earth")
    val distanceFromEarth: Double,

    @ColumnInfo(name = "is_potentially_hazardous")
    val isPotentiallyHazardous: Boolean)

//@Parcelize
//data class Asteroid(val id: Long,
//                    val codename: String,
//                    val closeApproachDate: String,
//                    val absoluteMagnitude: Double,
//                    val estimatedDiameter: Double,
//                    val relativeVelocity: Double,
//                    val distanceFromEarth: Double,
//                    val isPotentiallyHazardous: Boolean) : Parcelable