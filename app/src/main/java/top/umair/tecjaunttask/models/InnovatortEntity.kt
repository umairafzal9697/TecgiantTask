package top.umair.tecjaunttask.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

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
) {
    // Add a UUID property
    var uuid: String = UUID.randomUUID().toString()

        get() = field
        set(value) {
            field = value
            documentId = uuid
        }
}

