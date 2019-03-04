package com.amazon.dao;

import com.amazon.bean.AmazonAdmin;
import com.amazon.bean.AmazonUser;

public interface AmazonUserDao {

	AmazonUser findByUsername(String username);

	void save(AmazonUser amazonUser);

	void updateState(String code);

	AmazonAdmin findByAdminUsername(String username);

}
