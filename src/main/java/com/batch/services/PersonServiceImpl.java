package com.batch.services;

import com.batch.entities.Person;
import com.batch.persistences.IPersonDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private IPersonDAO personDAO;

    @Override
    @Transactional
    public void saveAll(List<Person> personList) {
        personDAO.saveAll(personList);
    }
}
