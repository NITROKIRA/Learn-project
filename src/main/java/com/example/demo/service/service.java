package com.example.demo.service;

import com.example.demo.model.student;
import com.example.demo.repository.repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class service {

    private final repository repo;

    public void addStudent(student s)
    {
        repo.save(s);
    }
    public String print(student s)
    {
        return s.getSname();
    }
}
