package top.umair.tecjaunttask.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "innovator")
data class InnovatorEntity(
    @PrimaryKey
    var documentId: String ="",
    var firstName: String?=null,
    var lastName: String?=null,
    var age: String?=null,
    var country: String?=null,
    var description: String?=null,
    var college: String?=null
)
