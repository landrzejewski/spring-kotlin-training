package pl.training.payments.client

import org.springframework.web.reactive.function.client.WebClient
import pl.training.payments.adapters.PaymentDto

fun main() {
    val baseUrl = "http://localhost:8080/api/payments"

    val newPayment = PaymentDto(id = "", value = 100.0, currency = "PLN", status = "NOT_CONFIRMED")

    /*println("Adding payment")
    val savedPayment = WebClient.builder()
        .baseUrl(baseUrl)
        .build()
        .post()
        .bodyValue(newPayment)
        .retrieve()
        .bodyToMono(PaymentDto::class.java)
        .map { it.id }
        .subscribe(System.out::println, System.err::println)*/

//    println("Retrieving status")
//    val status = WebClient.builder()
//        .baseUrl(baseUrl)
//        .build()
//        .get()
//        .uri("/${savedPayment!!.id}")
//        .retrieve()
//        .bodyToMono(String::class.java)
//        .block()
//    println("Status: $status")

    println("Retrieving payments:")
    WebClient.builder()
        .baseUrl(baseUrl)
        .build()
        .get()
        .retrieve()
        .bodyToFlux(PaymentDto::class.java)
        .subscribe(System.out::println, System.err::println)

    /*println("Retrieving confirmed payments in EUR:")
    WebClient.builder()
        .baseUrl(baseUrl)
        .build()
        .get()
        .uri("/confirmed?currency=EUR")
        .retrieve()
        .bodyToFlux(PaymentDto::class.java)
        .subscribe(System.out::println, System.err::println)*/

    Thread.sleep(20000)

}