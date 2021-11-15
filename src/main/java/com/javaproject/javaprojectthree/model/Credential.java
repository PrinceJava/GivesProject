package com.javaproject.javaprojectthree.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name= "credential")
@Data
@NoArgsConstructor
public class Credential {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String MODE;

}
