package com.amazon.service;

import com.amazon.bean.AmazonAdmin;
import com.amazon.bean.AmazonUser;

public interface AmazonUserService {

	AmazonUser findByUsername(String username);

	void save(AmazonUser amazonUser);

	void updateState(String code);

	AmazonAdmin findByAdminUsername(String username);

}
