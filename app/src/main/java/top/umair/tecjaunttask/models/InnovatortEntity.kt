package top.umair.tecjaunttask.models

import androidx.room.Entity
@Entity
data class InnovatortEntity(
    val localDbId: Int?=null,
    var documentId: String? = null,
    var uuid: String? = null,
    var profileImage: String? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var age: String? = null,
    var country: String? = null,
    var description: String? = null,
    var college: String? = null,
)
