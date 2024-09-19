package pl.training.extras

import org.springframework.beans.factory.annotation.Value

interface OrderProjection {

    fun getId(): Long

    fun getValue(): Double

    @Value("#{'id: ' + target.id + ', value: ' + target.value}")
    fun getInfo(): String

}