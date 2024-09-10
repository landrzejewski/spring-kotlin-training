package pl.training.payments.application

import pl.training.payments.application.input.Cards
import pl.training.payments.application.output.CardsEventPublisher
import pl.training.payments.application.output.CardsRepository
import pl.training.payments.application.output.TimeProvider
import pl.training.payments.domain.*
import pl.training.payments.domain.CardTransactionType.FEE
import pl.training.payments.utils.aop.Loggable
import java.util.function.Consumer
import java.util.logging.Logger

// @Component
open class CardsService /*@Autowired constructor*/(
    private val repository: CardsRepository,
    private val timeProvider: TimeProvider,
    private val eventPublisher: CardsEventPublisher
) : Cards {

    private val log: Logger = Logger.getLogger(CardsService::class.java.name)

    @Loggable
    override fun charge(cardNumber: CardNumber, amount: Money) = processOperation(cardNumber) {
        it.addEventsListener(createEventListener())
        CardTransaction(timeProvider.getTimestamp(), amount)
    }

    override fun chargeFees(cardNumber: CardNumber) = processOperation(cardNumber) {
        val fees = CardTransactionBasedFees(it.transactions).execute()
        CardTransaction(timeProvider.getTimestamp(), fees, FEE)
    }

    override fun getTransactions(cardNumber: CardNumber) = getCard(cardNumber).transactions

    private fun processOperation(cardNumber: CardNumber, operation: (Card) -> CardTransaction) {
        val card = getCard(cardNumber)
        val transaction = operation(card)
        card.addTransaction(transaction)
        repository.save(card)
    }

    private fun getCard(cardNumber: CardNumber) = repository.getByNumber(cardNumber) ?: throw CardNotFoundException()

    private fun createEventListener(): Consumer<CardCharged> {
        return Consumer<CardCharged> {
            val appEvent = CardChargedApplicationEvent(it.number.toString())
            eventPublisher.publish(appEvent)
        }
    }

    // @PostConstruct
    fun initialize() {
        log.info("Initializing Cards")
    }

    // @PreDestroy
    fun destroy() {
        log.info("Destroying Cards")
    }

}
