package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    /* define list */
    private static List<Person> DB = new ArrayList<>(); /* insert person into DB database */

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst(); /* JAVA stream */
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> foundPerson = selectPersonById(id);
        if (foundPerson.isEmpty()) {
            return 0; /* Person does not exist*/
        }
        DB.remove(foundPerson.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person updatedPerson) {
        return selectPersonById(id)
                .map(personInDb -> {
                    int indexOfPersonToUpdate = DB.indexOf(personInDb);
                    if (indexOfPersonToUpdate >= 0) { /* if index >= 0 that means the person exists */
                        DB.set(indexOfPersonToUpdate, new Person(id, updatedPerson.getName()));
                        return 1;
                    }
                    return 0;
    })
                .orElse(0);
    }
}
