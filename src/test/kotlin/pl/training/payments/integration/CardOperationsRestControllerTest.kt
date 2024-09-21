package pl.training.payments.integration

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import pl.training.Application
import pl.training.payments.CardTestFixtures.testCardNumber
import pl.training.payments.CardTestFixtures.validCardEntity

@WithMockUser(roles = ["ADMIN"])
@SpringBootTest(classes = [Application::class], webEnvironment = DEFINED_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class CardOperationsRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var entityManager: EntityManager

    @Transactional
    @Test
    fun `add operation`() {
        val cardEntity = validCardEntity()
        entityManager.persist(cardEntity)
        entityManager.flush()
        val payload = """
            {
              "operationType": "INFLOW",
              "amount": 200.0,
              "currencyCode": "PLN"
            }
        """
        mockMvc.perform(
            post("/api/cards/" + testCardNumber.value + "/operations")
                .contentType(APPLICATION_JSON)
                .content(payload)
        )
        .andExpect(status().isNoContent())
    }

}
