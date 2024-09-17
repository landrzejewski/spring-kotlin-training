The `@Component` annotation used at the class level ensures that it will be treated by Spring as a managed component.
This means that the framework will be responsible for creating its instance and providing the necessary dependencies.
By default, the name of the component (bean) is the same as the name of the class spelled with a lowercase letter,
for example, in the case of the `CardsService class`, the name will be defined as `cardsService`.
The default assigned name of the bean can be changed by specifying it at the annotation level, for example `@Component("cards")`.
Annotations such as `@Controller`, `@Service`, `@Repository` can be treated as aliases of `@Component` annotation.
It can be said that they play the role of a stereotype (additional information about the role of the component),
but by default they do not change the operation of the framework. It is possible to create your own stereotype annotations.

Declaration of managed components can also be done via factory methods, marked with the `@Bean` annotation. By default,
the name of the component is the same as the name of the factory method, but it can be modified at the
annotation level (name attribute).

Scope affects how a component is managed - it determines when it is created and destroyed by the container.
To define the scope we use the `@Scope` annotation at the class or bean factory method level.
`singleton` scope - instance created once (usually at application startup) and reused (default scope)
`prototype` scope - instance created on demand
`request` scope - instance lifetime is dependent on the lifetime of the http request
`session` scope - instance lifetime is dependent on the lifetime of the http session

Dependency injection can be done at the constructor, method, and field levels of the class using the `@Autowire` annotation.
In case of conflict due to, for example, the existence of multiple implementations of the same type,
qualification should be made (annotations `@Qualifier` and `@Primary`).

Lifecycle-related methods (initialization, cleanup) can be declared using annotations derived from
Jakarta EE `@PostConstruct` or `@PostConstruct`. Alternatively, they can be defined at the attribute
level of the `@Bean` annotation.
Initialization method is called after all dependencies have been injected.
Clean up method is called before releasing the reference to the bean, works only for `singleton` scope and when 
the container is properly stopped.