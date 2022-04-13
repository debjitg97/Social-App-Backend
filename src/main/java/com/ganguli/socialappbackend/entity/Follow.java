package com.ganguli.socialappbackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follow")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Follow {
	@Id
	@Column(name = "follow_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer followId;
	
	@ManyToOne
	@JoinColumn(name = "followee_user_id")
	private SocialUser followee;
	
	@ManyToOne
	@JoinColumn(name = "follower_user_id")
	private SocialUser follower;
}
