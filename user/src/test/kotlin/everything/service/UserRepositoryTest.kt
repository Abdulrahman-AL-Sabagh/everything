package everything.service

import io.agroal.api.AgroalDataSource
import io.quarkus.test.junit.QuarkusTest
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import  org.assertj.db.api.*
import org.assertj.db.api.Assertions.assertThat
import org.assertj.db.output.Outputs.output
import org.assertj.db.type.Table


@QuarkusTest
class UserRepositoryTest {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var agroalDataSource: AgroalDataSource

    @Test
    @Transactional
    fun `test if user is inserted Successfully into the db`() {
        val bob = User("bob", "bob@bob.com", "12345678")
        userRepository.persistAndFlush(bob)
        val table = Table(agroalDataSource, "AppUser")
        output(table).toConsole()

        assertThat(table)
            .hasNumberOfRows(1)
            .row(0)
            .value("name")
            .isEqualTo("bob")
            .value("email")
            .isEqualTo("bob@bob.com")
            .value("password")
            .isEqualTo("12345678")
    }
}