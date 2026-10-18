package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Entity
@Getter
@Setter
public class student {

    @Id
    int sid;
    String sname;
    int sph;
    int smarks;
    String sdept;

}
