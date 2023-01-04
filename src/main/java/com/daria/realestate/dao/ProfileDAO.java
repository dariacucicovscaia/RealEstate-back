package com.daria.realestate.dao;

import com.daria.realestate.domain.Profile;

public interface ProfileDAO extends DAO<Profile>{
    Profile update(Profile profile);
}
