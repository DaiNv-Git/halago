package com.hitex.halago.service;

import com.hitex.halago.model.dao.PersonalToken;

public interface PersonalTokenService {
    PersonalToken findByPhone(String phone);
}
