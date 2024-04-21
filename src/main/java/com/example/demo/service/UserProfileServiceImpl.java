package com.example.demo.service;

import com.example.demo.model.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService{
	
	@Autowired
	UserProfileRepository userProfileRepository;


	@Override
	public UserProfile findProfileById(int id) {
		return userProfileRepository.findById(id).get();
	}

	@Override
	public UserProfile findProfileByType(String type) {
		List<UserProfile> all = userProfileRepository.findAll();
		for (UserProfile p:
			 all) {
			if (p.getType().toLowerCase().equals(type.toLowerCase())){
				return p;
			}
		}
		return null;
	}

	@Override
	public List<UserProfile> findAllProfiles() {
		return userProfileRepository.findAll();
	}
}
