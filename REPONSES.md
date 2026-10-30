# REPONSES

## Réponses aux questions de la section 6

### 6.1) Différence entre IoC et DI
IoC c'est le fait que le conteneur Spring s'occupe lui même de créer et gérer les objets.  
DI c'est le mécanisme qui permet au conteneur d'injecter ces objets dans les classes qui en ont besoin.  
Avec `new CricketCoach()`, c'est nous qui créons l'objet. Avec Spring, c'est le conteneur qui le crée et l'injecte, donc le contrôle est inversé.

### 6.2) Pourquoi l'injection par constructeur est recommandée ?
L'injection par constructeur force les dépendances à être présentes dès la création de l'objet, donc l'objet est toujours valide.  
On peut aussi mettre les champs en `final` ce qui rend l'objet plus sûr et plus facile à tester avec des mocks.  
L'injection par champ est plus courte mais plus difficile à tester, donc moins recommandée.

### 6.3) NoUniqueBeanDefinitionException et solutions
On obtient cette erreur quand il y a plusieurs beans du même type dans le conteneur et que Spring ne sait pas lequel injecter.  
Deux façons de la résoudre : utiliser `@Qualifier("nomDuBean")` pour choisir précisément un bean, ou utiliser `@Primary` pour désigner un bean par défaut.  
Si les deux sont présents, `@Qualifier` est prioritaire sur `@Primary`.

### 6.4) Id par défaut d'un bean
Spring calcule l'id par défaut en prenant le nom de la classe avec la première lettre en minuscule. Exemple : `CricketCoach` -> `cricketCoach`.  
On peut forcer un id manuellement avec `@Component("monId")` ou avec `@Bean("monId")` dans une classe `@Configuration`.

### 6.5) Avantages et inconvénients de @Lazy
Avantage : le démarrage de l'application est plus rapide car les beans lazy ne sont créés qu'au premier usage.  
Inconvénient : les erreurs de configuration ne sont pas détectées au démarrage, mais seulement au moment où le bean est demandé.  
C'est pourquoi il faut l'utiliser avec parcimonie.

### 6.6) /check true puis false
Au début, `CricketCoach` était en `singleton` donc les deux injections donnaient la même instance -> `true`.  
Après avoir mis `@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)`, une nouvelle instance est créée à chaque demande -> les deux références sont différentes -> `false`.  
Ça prouve que la portée détermine le nombre d'instances créées.

### 6.7) Ordre du cycle de vie et @PreDestroy pour prototype
Pour un bean `singleton` : `constructeur` -> `@PostConstruct` -> utilisation -> `@PreDestroy`.  
Pour un bean `prototype`, Spring ne garde pas le bean après l'avoir donné. Il ne le gère plus, donc il ne sait pas quand le détruire.  
C'est pour ça que `@PreDestroy` n'est pas appelé pour les beans `prototype`.

### 6.8) Quand préférer @Bean plutôt que @Component
On préfère `@Bean` dans une classe `@Configuration` dans ces cas :  
- quand la classe est tierce et non modifiable, on ne peut donc pas ajouter `@Component` dessus.  
- quand on veut créer plusieurs beans du même type avec des configurations différentes.  
- quand la création de l'objet est un peu complexe (logique, factory...) et qu'on veut la centraliser dans la config.

## Tableau des scopes complété

| Scope | Description |
|---|---|
| singleton (défaut) | Une seule instance dans le conteneur |
| prototype | Nouvelle instance à chaque demande |
| request | Une instance par requête HTTP (web only) |
| session | Une instance par session HTTP (web only) |
| application | Une instance par ServletContext (web only) |