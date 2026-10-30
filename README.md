# TP 02 — IoC et injection de dépendances (Spring Core)

Projet `springcoredemo` — Spring Boot 4.1.2, Java 25.

## Lancer le projet

```bash
./mvnw spring-boot:run
```

ou :

```bash
./mvnw package
java -jar target/springcoredemo-0.0.1-SNAPSHOT.jar
```

## Endpoints

| Endpoint | Réponse attendue |
|---|---|
| `GET /dailyworkout` | `Swim 1000 meters as a warm up` (bean `aquatic`, exercice 5.8) |
| `GET /check` | `Comparing beans: myCoach == anotherCoach, true` (CricketCoach singleton) |
| `GET /allworkouts` | Workout de tous les coachs (bonus 2) |
| `GET /request-id` | Identifiant unique par requête HTTP (bonus 1) |

## État final des exercices

- `Coach` : interface commune.
- `CricketCoach` : `@Component`, singleton, avec `@PostConstruct` / `@PreDestroy`.
- `BaseballCoach`, `TennisCoach`, `TrackCoach` : `@Component`.
- `TennisCoach` : `@Lazy` (créé au premier usage).
- `TrackCoach` : `@Primary`.
- `SwimCoach` : classe non annotée, déclarée via `@Bean("aquatic")` dans `SportConfig`.
- `DemoController` : injection par constructeur, `@Qualifier("aquatic")` pour `/dailyworkout`.
- `RequestIdBean` : scope `request` avec proxy `TARGET_CLASS` (bonus 1).

## Exercice 5.6 — /check true puis false

L'état final du projet garde `CricketCoach` en **singleton** (comme demandé à l'exercice 5.7),
donc `/check` renvoie `true`. Pour observer `false`, passer temporairement `CricketCoach` en prototype :

```java
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CricketCoach implements Coach { /* ... */ }
```

Relancer l'application puis appeler `/check` : les deux injections reçoivent alors deux instances
différentes, donc la réponse est `false`. Les deux captures sont disponibles dans `captures/`.

## Documentation

- `REPONSES.md` : réponses aux 8 questions de compréhension + tableau des scopes.
- `BONUS.md` : implémentation des trois bonus.
- `captures/` : captures et sorties de vérification.
