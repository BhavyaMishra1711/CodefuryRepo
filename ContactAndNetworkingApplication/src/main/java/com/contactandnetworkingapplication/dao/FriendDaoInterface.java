package com.contactandnetworkingapplication.dao;

import java.util.List;

import com.contactandnetworkingapplication.model.User;
import com.contactandnetworkingapplication.model.Friend;
public interface FriendDaoInterface {
	List<User> viewFriendsDao(User u);
	int removeFriend(Friend f);
}
