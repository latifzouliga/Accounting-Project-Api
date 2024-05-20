package com.zouliga.service;

import com.zouliga.entity.User;

public interface SecurityService{

    User getLoggedInUser();
}
