# BONUS

## 1) Scope `request`

Le bean `RequestIdBean` est déclaré avec le scope `request` et un proxy de type `TARGET_CLASS` :

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestIdBean {
    private final String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }
}
```

L'endpoint `/request-id` expose cet identifiant :

```java
@GetMapping("/request-id")
public String requestId() {
    return "Request id: " + requestIdBean.getId();
}
```

Grâce au proxy, chaque requête HTTP possède sa propre instance (donc son propre id).
Deux appels successifs à `/request-id` renvoient donc deux identifiants différents :

```bash
curl http://localhost:8080/request-id
curl http://localhost:8080/request-id
```

## 2) Injection d'une collection de `Coach`

Le contrôleur récupère tous les beans de type `Coach` via un `ObjectProvider<Coach>` :

```java
private final ObjectProvider<Coach> allCoaches;

@GetMapping("/allworkouts")
public String allWorkouts() {
    return allCoaches.orderedStream()
            .map(Coach::getDailyWorkout)
            .collect(Collectors.joining("<br>"));
}
```

L'endpoint `/allworkouts` liste ainsi le workout de tous les coachs :

```bash
curl http://localhost:8080/allworkouts
```

On utilise `ObjectProvider` plutôt qu'un `List<Coach>` injecté directement dans le constructeur
pour ne pas instancier `TennisCoach` au démarrage : cela préserve la démonstration de `@Lazy`
de l'exercice 5.5. La liste n'est résolue qu'au premier appel de `/allworkouts`.

## 3) `@Bean` avec `initMethod` / `destroyMethod`

`SwimCoach` n'est pas annoté : les callbacks sont déclarés dans la configuration.

```java
@Bean(name = "aquatic", initMethod = "customInit", destroyMethod = "customDestroy")
public Coach swimCoach() {
    return new SwimCoach();
}
```

```java
public void customInit() {
    System.out.println("In customInit()");
}

public void customDestroy() {
    System.out.println("In customDestroy()");
}
```

Au démarrage, la console affiche :

```text
In constructor: SwimCoach
In customInit()
```

À l'arrêt propre de l'application, elle affiche :

```text
In customDestroy()
```

Cela reproduit le comportement de `@PostConstruct` / `@PreDestroy` sans annoter la classe,
ce qui est utile pour une classe tierce non modifiable.
