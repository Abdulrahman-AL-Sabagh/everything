package everything.service

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String = "",
    val email: String = "",
    val password: String = ""
)
