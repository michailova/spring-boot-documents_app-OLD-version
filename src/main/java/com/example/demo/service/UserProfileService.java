package com.example.demo.service;

import com.example.demo.model.UserProfile;
import com.example.demo.repository.UserProfileRepository;

import java.util.List;
import java.util.Optional;


public interface UserProfileService {

	UserProfile findProfileById(int id);

	UserProfile findProfileByType(String type);
	
	List<UserProfile> findAllProfiles();
	
}
