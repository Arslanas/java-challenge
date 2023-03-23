### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 3 years experience in Java and I started to use Spring Boot from last year
- I'm a beginner and just recently learned Spring Boot
- I know Spring Boot very well and have been using it for many years


### Changes
####  1. Wrapped JPA repository implementation into custom repository interface`s implementation.
It exposes only the methods used in the project (JPA implementation contains a lot of unused methods), additionally it will help easily switch to another implementation or use both if needed, for example Spring JDBC, MyBatis and so on.

####  2. Security module added to the project.
Can be disabled by activation of spring profile 'test'.\
During tests it will be disabled.\
Admin credentials : username - 'demo', password - '123'
####  3. API tests added
####  4. Added caching logic for database calls. For multiple instances of application it will need to update for more advanced caching technology like Redis, Memcache.
####  5. Updated API status codes
####  6. Added brief description of API endpoints using Swagger
####  7. Added bean validation for entity fields
####  8. Introduced error handler controller.
####  9. Changed annotation bean injection to constructor based injection.


### Further improvement if would have time
1. Upgrade Spring boot version.
2. Improve error handling to make it more user-friendly. For example convert bean validation errors to concise response format.
3. Maybe it would be a good idea to not expose jpa entity directly as API "request body/response body". Instead use some kind of wrapper (data class) around the entity.
4. Configure cache to use distributed cache, like Redis.
5. Use conditional database bean for different environments. For integration tests use H2, for production use real database.
6. Cover more test cases.

### Experience in Java
I have experience with Java and Spring framework for 6 years. Last 3 years was working with Spring Boot applications.
Feel confident with this technology stack.