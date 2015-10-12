package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by Valk on 10.10.15.
 */
/*
Вариант Profiles.HSQLDB_MEMORY только для JPA, чтобы не плодить
*/
@ActiveProfiles({Profiles.HSQLDB_MEMORY, Profiles.JPA})
public class UserMealServiceTest_HsqldbMemory_Jpa extends UserMealServiceTest {
    @Override
    public void testDelete() throws Exception {
        super.testDelete();
    }

    @Override
    public void testDeleteNotFound() throws Exception {
        super.testDeleteNotFound();
    }

    @Override
    public void testSave() throws Exception {
        super.testSave();
    }

    @Override
    public void testGet() throws Exception {
        super.testGet();
    }

    @Override
    public void testGetNotFound() throws Exception {
        super.testGetNotFound();
    }

    @Override
    public void testUpdate() throws Exception {
        super.testUpdate();
    }

    @Override
    public void testNotFoundUpdate() throws Exception {
        super.testNotFoundUpdate();
    }

    @Override
    public void testGetAll() throws Exception {
        super.testGetAll();
    }

    @Override
    public void testGetBetween() throws Exception {
        super.testGetBetween();
    }

    @Override
    public void testGetAllWithUser() throws Exception {
        super.testGetAllWithUser();
    }
}
