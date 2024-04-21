package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.*;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserProfileRepository userProfileRepository;

    @Resource
    private DepartmentRepository departmentRepository;

   @Resource
    private PasswordEncoder passwordEncoder;




    //    @Transactional
    @Override
    public UserDto addUser(UserDto userDto) {
        User user = new User();
        user = mapDtoToEntityUser(userDto);
        Set<UserProfile> userProfiles = userDto.getUserProfiles();
        user.setUserProfiles(userProfiles);
        User savedUser = userRepository.save(user);
        return mapEntityToDtoUser(savedUser);

    }

    @Override
    public UserDto getUserById(Long id) {
        if (userRepository.existsById(id)) {
            UserDto userDto = mapEntityToDtoUser(userRepository.getById(id));
            return userDto;
        }
        throw new RuntimeException("User with id:" + id + " not exist");
    }

    @Override
    public List<UserDto> getUserByLastName(String lName) {
        List<UserDto> all = userRepository.findAll().stream().map(this::mapEntityToDtoUser).toList();
        List<UserDto> finds = new ArrayList<>();
        for (UserDto urd :
                all) {
            if (urd.getLastName().toLowerCase().contains(lName.toLowerCase())) {
                finds.add(urd);
            }
        }
        return finds;
    }


    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapEntityToDtoUser).toList();
    }


    //    @Transactional
    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.getById(id);
        user.setId(id);
        user.setFirst_name(userDto.getFirstName());
        user.setLast_name(userDto.getLastName());
        user.setDocuments(userDto.getDocuments());
        user.set_active(userDto.isActive());
        user.setEmail(userDto.getEmail());
        user.setDepartment(userDto.getDepartment());
        user.setPerson_number(userDto.getPerson_number());
        user.setUserProfiles(userDto.getUserProfiles());
        userRepository.save(user);
        return mapEntityToDtoUser(user);
    }

    @Override
    public User update(Long id, User user) {
        User userUpdated = userRepository.getById(id);
        userUpdated.setId(id);
        userUpdated.setFirst_name(user.getFirst_name());
        userUpdated.setLast_name(user.getLast_name());
        userUpdated.setDocuments(user.getDocuments());
        userUpdated.set_active(user.is_active());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setDepartment(user.getDepartment());
        userUpdated.setPerson_number(user.getPerson_number());
        userUpdated.setUserProfiles(user.getUserProfiles());
        userUpdated.setPassword(user.getPassword());
        userRepository.save(userUpdated);
        return userUpdated;
    }



    @Override
    public String deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setUserProfiles(null);
            userRepository.deleteById(user.get().getId());
            return "User with id: " + userId + " deleted successfully!";
        }
        throw new RuntimeException("Could not delete  user with id:" + userId);
    }


    @Override
    public List<Document> getDocumentsByUserId(long id) {
        UserDto dto = mapEntityToDtoUser(userRepository.getReferenceById(id));
        List<Document> documents = dto.getDocuments();
        return documents;
    }

    private User mapDtoToEntityUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirst_name(userDto.getFirstName());
        user.setLast_name(userDto.getLastName());
        user.setDocuments(userDto.getDocuments());
        user.set_active(userDto.isActive());
        user.setEmail(userDto.getEmail());
        user.setDepartment(userDto.getDepartment());
        user.setPerson_number(userDto.getPerson_number());
        user.setUserProfiles(userDto.getUserProfiles());

        /**
         * When a manager creates a user, a login is created automatically using a unique personal
         * number and last name (the login is not included in the DTO)
         */
        if (null == user.getLogin()) {
            user.setLogin(userDto.getPerson_number() + userDto.getFirstName());
        }
        /**
         * When a manager creates a user, a password is created automatically using a unique personal
         * number and last name being encrypted (the password is not included in the DTO)
         */
        if (null == user.getPassword()) {
            //user.setPassword(EncryptDecryptUtils.encrypt(userDto.getPerson_number() + userDto.getFirstName()));
            user.setPassword(passwordEncoder.encode(userDto.getPerson_number() + userDto.getFirstName()));
        }
        return user;
    }

    public UserDto mapEntityToDtoUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirst_name());
        userDto.setLastName(user.getLast_name());
        userDto.setDepartment(user.getDepartment());
        userDto.setActive(user.is_active());
        userDto.setPerson_number(user.getPerson_number());
        userDto.setEmail(user.getEmail());
        userDto.setDocuments(user.getDocuments());
        userDto.setUserProfiles(user.getUserProfiles());
        return userDto;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = new User();
        List<User> users = userRepository.findAll();
        for (User u :
                users) {
            if (u.getEmail().equals(email)) {
                user = u;
            } else {
                throw new RuntimeException("User with email:" + email + " not exist");
            }
        }
        return user;
    }

    private UserProfile checkProfileExist(){
        UserProfile profile = new UserProfile();
        profile.setType("ROLE_ADMIN");
        return userProfileRepository.save(profile);
    }


    @Override
    public void saveUser(User user) {
        Set <UserProfile> profiles = user.getUserProfiles();
        UserProfile  profile = userProfileRepository.findByType("ROLE_USER");
        profiles.add(profile);
        user.setUserProfiles(profiles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    public List<UserDto> search(String keyword) {
        List<UserDto> all = userRepository.findAll().stream().map(this::mapEntityToDtoUser).toList();
        List<UserDto> searchUsers = new ArrayList<>();
        for (UserDto u:
             all) {
            if (u.getEmail().toLowerCase().contains(keyword.toLowerCase())||u.getFirstName().toLowerCase().contains(keyword.toLowerCase())
                    || u.getLastName().toLowerCase().contains(keyword.toLowerCase())){
                searchUsers.add(u);
            }
        }
        return searchUsers;
    }

    @Override
    public List<UserDto> getUsersByDepartment(Department department) {
       List<UserDto> all = getAllUsers();
        List<UserDto> users = new ArrayList<>();
        for (UserDto u:
             all) {
            if (u.getDepartment().equals(department)){
                users.add(u);
            }
        }
       return  users;
    }
}
