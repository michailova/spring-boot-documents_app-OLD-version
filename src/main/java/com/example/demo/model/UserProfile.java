package com.example.demo.model;

import com.example.demo.dto.UserDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name="USER_PROFILE")
@ToString(exclude = "users")
@DynamicInsert
@DynamicUpdate
public class UserProfile implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;	

	@Column(name="TYPE", length=15, unique=true, nullable=false)
	private String type = UserProfileType.USER.getUserProfileType();

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "userProfiles")
	private List<User> users;


	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", type=" + type + "]";
	}


}
