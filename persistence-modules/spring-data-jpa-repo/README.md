## Spring Data JPA - Repo

This module contains articles about repositories in Spring Data JPA 

### Relevant Articles: 
- [Case Insensitive Queries with Spring Data Repository](https://www.baeldung.com/spring-data-case-insensitive-queries)
- [Derived Query Methods in Spring Data JPA Repositories](https://www.baeldung.com/spring-data-derived-queries)
- [LIKE Queries in Spring JPA Repositories](https://www.baeldung.com/spring-jpa-like-queries)
- [Spring Data – CrudRepository save() Method](https://www.baeldung.com/spring-data-crud-repository-save)
- [Spring Data JPA – Adding a Method in All Repositories](https://www.baeldung.com/spring-data-jpa-method-in-all-repositories)
- [Spring Data Composable Repositories](https://www.baeldung.com/spring-data-composable-repositories)
- [Spring Data JPA Repository Populators](https://www.baeldung.com/spring-data-jpa-repository-populators)
- [Calling Stored Procedures from Spring Data JPA Repositories](https://www.baeldung.com/spring-data-jpa-stored-procedures)
- More articles: [[--> next]](/spring-data-jpa-repo-2/)

### Eclipse Config 
After importing the project into Eclipse, you may see the following error:  
"No persistence xml file found in project"

This can be ignored: 
- Project -> Properties -> Java Persistance -> JPA -> Error/Warnings -> Select Ignore on "No persistence xml file found in project"
Or: 
- Eclipse -> Preferences - Validation - disable the "Build" execution of the JPA Validator 


# Handling associations between entities with Spring Data Rest
 
This repo contains some example code that exposes some Spring Data repositories
via Spring Data REST to show how to handle relationships between JPA entities
via the REST API.

## Companion Blog Post

The companion blog post with more details can be found [here](https://reflectoring.io/relations-with-spring-data-rest/).

## Running the application

Simply run `gradlew bootrun` on Windows or `./gradlew.sh bootrun` on Unix.

https://www.baeldung.com/spring-data-jpa-method-in-all-repositories

Spring Data JPA – Adding a Method in All Repositories
Last modified: July 29, 2020

by baeldungSpring DataJPA
I just announced the new Learn Spring course, focused on the fundamentals of Spring 5 and Spring Boot 2:
>> CHECK OUT THE COURSE
1. Overview
Spring Data makes the process of working with entities a lot easier by merely defining repository interfaces. These come with a set of pre-defined methods and allow the possibility of adding custom methods in each interface.

However, if we want to add a custom method that's available in all the repositories, the process is a bit more complex. So, that's what we'll explore here with Spring Data JPA.


For more information on configuring and using Spring Data JPA, check out our previous articles: Guide to Hibernate with Spring 4 and Introduction to Spring Data JPA.

2. Defining a Base Repository Interface
First, we have to create a new interface that declares our custom method:

@NoRepositoryBean
public interface ExtendedRepository<T, ID extends Serializable> 
  extends JpaRepository<T, ID> {
 
    public List<T> findByAttributeContainsText(String attributeName, String text);
}
Our interface extends the JpaRepository interface so that we'll benefit from all the standard behavior.

You'll also notice we added the @NoRepositoryBean annotation. This is necessary because otherwise, the default Spring behavior is to create an implementation for all subinterfaces of Repository.

Here, we'll want to provide our implementation that should be used, as this is only an interface meant to be extended by the actual entity-specific DAO interfaces.

3. Implementing a Base Class
Next, we'll provide our implementation of the ExtendedRepository interface:

public class ExtendedRepositoryImpl<T, ID extends Serializable>
  extends SimpleJpaRepository<T, ID> implements ExtendedRepository<T, ID> {
    
    private EntityManager entityManager;

    public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> 
      entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    // ...
}
This class extends the SimpleJpaRepository class, which is the default class that Spring uses to provide implementations for repository interfaces.

This requires that we create a constructor with the JpaEntityInformation and EntityManager parameters that calls the constructor from the parent class.

We also need the EntityManager property to use in our custom method.

Also, we have to implement the custom method inherited from the ExtendedRepository interface:

@Transactional
public List<T> findByAttributeContainsText(String attributeName, String text) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> cQuery = builder.createQuery(getDomainClass());
    Root<T> root = cQuery.from(getDomainClass());
    cQuery
      .select(root)
      .where(builder
        .like(root.<String>get(attributeName), "%" + text + "%"));
    TypedQuery<T> query = entityManager.createQuery(cQuery);
    return query.getResultList();
}
Here, the findByAttributeContainsText() method searches for all the objects of type T that have a particular attribute which contains the String value given as parameter.




4. JPA Configuration
To tell Spring to use our custom class instead of the default one for building repository implementations, we can use the repositoryBaseClass attribute:

@Configuration
@EnableJpaRepositories(basePackages = "org.baeldung.persistence.dao", 
  repositoryBaseClass = ExtendedRepositoryImpl.class)
public class StudentJPAH2Config {
    // additional JPA Configuration
}
5. Creating an Entity Repository
Next, let's see how we can use our new interface.

First, let's add a simple Student entity:

@Entity
public class Student {

    @Id
    private long id;
    private String name;
    
    // standard constructor, getters, setters
}
Then, we can create a DAO for the Student entity which extends the ExtendedRepository interface:

public interface ExtendedStudentRepository extends ExtendedRepository<Student, Long> {
}
And that's it! Now our implementation will have the custom findByAttributeContainsText() method.

Similarly, any interface we define by extending the ExtendedRepository interface will have the same method.

6. Testing the Repository
Let's create a JUnit test that shows the custom method in action:

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { StudentJPAH2Config.class })
public class ExtendedStudentRepositoryIntegrationTest {
 
    @Resource
    private ExtendedStudentRepository extendedStudentRepository;
   
    @Before
    public void setup() {
        Student student = new Student(1, "john");
        extendedStudentRepository.save(student);
        Student student2 = new Student(2, "johnson");
        extendedStudentRepository.save(student2);
        Student student3 = new Student(3, "tom");
        extendedStudentRepository.save(student3);
    }
    
    @Test
    public void givenStudents_whenFindByName_thenOk(){
        List<Student> students 
          = extendedStudentRepository.findByAttributeContainsText("name", "john");
 
        assertEquals("size incorrect", 2, students.size());        
    }
}
The test uses the extendedStudentRepository bean first to create 3 Student records. Then, the findByAttributeContains() method is called to find all students whose name contains the text “john”.

The ExtendedStudentRepository class can use both standard methods like save() and the custom method we added.

7. Conclusion
In this quick article, we've shown how we can add a custom method to all repositories in Spring Data JPA.

The full source code for the examples can be found over on GitHub.