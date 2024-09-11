package pl.training.coroutines/*
    The easiest way to create a coroutine scope object is by using the CoroutineScope
    factory function. It creates a scope with the provided context (and an additional
    Job for structured concurrency if no job is already part of the context).

    @Configuration
    public class CoroutineScopeConfiguration {

        @Bean
        fun coroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO.limitedParallelism(50)

        @Bean
        fun coroutineExceptionHandler() {
            val logger = LoggerFactory.getLogger("DefaultHandler")
            CoroutineExceptionHandler { _, throwable ->
                logger.error("Unhandled exception", throwable)
            }
        }

        @Bean
        fun coroutineScope(coroutineDispatcher: CoroutineDispatcher, coroutineExceptionHandler: CoroutineExceptionHandler,) =
            CoroutineScope(SupervisorJob() + coroutineDispatcher + coroutineExceptionHandler)

}

    A similar scope can also be used to start coroutines for each request in a backend
    application.However, this is typically done by the framework we use (both Spring
    Boot and Ktor have their own ways of starting coroutines for each request).

    Of course, a scope can also be created in the class that needs it. Dependency
    injection is not mandatory, but it is very useful for testing and for making code
    more modular. Here is an example of how to construct a scope in a class:
*/