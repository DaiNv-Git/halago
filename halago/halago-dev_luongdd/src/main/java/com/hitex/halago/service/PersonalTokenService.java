package com.hitex.halago.service;

import com.hitex.halago.model.DAO.PersonalToken;

public interface PersonalTokenService {
    PersonalToken findByPhone(String phone);
}
