package com.example.demo.dto;

import com.example.demo.model.Department;
import com.example.demo.model.Document;
import com.example.demo.model.UserProfile;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private long id;
    @NotEmpty
    private String firstName;
    private String lastName;
    private Department department;
    private Set<UserProfile> userProfiles;
    private int person_number;
    @NotEmpty
    private String email;
//    private String password;
    private boolean isActive;
    private List<Document> documents;

}

