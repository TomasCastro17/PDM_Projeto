package pt.ipbeja.pdm_projeto.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Profile(
    val name: String,
    val section: String,
    val picturePath: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

/*
@Entity(foreignKeys = [
    ForeignKey(
        entity = Profile::class,
        parentColumns = ["id"],
        childColumns = ["contactId"],
        onDelete = ForeignKey.CASCADE
    )
])*/
