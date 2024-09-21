package pl.training.payments.adapters.output.persistence.jpa

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import pl.training.payments.CardTestFixtures.validCardEntity
import kotlin.test.assertEquals

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
@ExtendWith(SpringExtension::class)
class SpringDataJpaCardRepositoryTest {

    @Autowired
    private lateinit var repository: SpringDataJpaCardRepository
    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun `should find card by number`() {
        val cardEntity = validCardEntity()
        entityManager.persist(cardEntity)
        entityManager.flush()
        assertEquals(cardEntity, repository.findByNumber(cardEntity.number))
    }

}