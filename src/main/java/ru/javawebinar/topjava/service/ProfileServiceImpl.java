package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.ProfileDaoInMemoryImpl;
import ru.javawebinar.topjava.model.Profile;

/**
 * Created by Valk on 11.09.15.
 */
public class ProfileServiceImpl implements ProfileService {
    private static ProfileDaoInMemoryImpl profileDaoInMemory = new ProfileDaoInMemoryImpl();

    @Override
    public Profile read(Long id) {
        return profileDaoInMemory.read(id);
    }

    @Override
    public Profile update(Profile profile) {
        return profileDaoInMemory.update(profile);
    }
}
