package pt.ipbeja.pdm_projeto.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Profile(
    val name: String,
    val section: String,
    val picturePath: String,
    val progressName: String,//NEW
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

@Entity
data class Progress (
    val relacionamento: Boolean = false,
    val eqEmocional: Boolean = false,
    val autoestima: Boolean = false,
    val autonomia: Boolean = false,
    val responsabilidade: Boolean = false,
    val coerencia: Boolean = false,
    val descoberta: Boolean = false,
    val aprofundamento: Boolean = false,
    val servico: Boolean = false,
    val desempenho: Boolean = false,
    val autoconhecimento: Boolean = false,
    val bemestarFisico: Boolean = false,
    val procuraConhecimento: Boolean = false,
    val resolucaoProblemas: Boolean = false,
    val criatividade: Boolean = false,
    val exercerCidadania: Boolean = false,
    val solidariedade: Boolean = false,
    val interecao: Boolean = false,
    @PrimaryKey(autoGenerate = true) val progressId: Long = 0
)

@Entity(foreignKeys = [
    ForeignKey(
        entity = Profile::class,
        parentColumns = ["id"],
        childColumns = ["profileId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Progress::class,
        parentColumns = ["progressId"],
        childColumns = ["progressId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
])
data class ProfileProgress (
    val progressDone: Boolean = false,
    val profileId: Long,
    val progressId: Long,
    val stageOne: Boolean = false,
    val stageTwo: Boolean = false,
    val stageThree: Boolean = false,
    @PrimaryKey(autoGenerate = true) val profileProgressId: Long = 0
)