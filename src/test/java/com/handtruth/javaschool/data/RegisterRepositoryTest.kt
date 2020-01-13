package com.handtruth.javaschool.data

import com.handtruth.javaschool.data.model.UserDetail
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class RegisterRepositoryTest {
    private lateinit var repository: RegisterRepository
    private lateinit var alexUser: UserDetail
    private lateinit var lexUser: UserDetail



    @Before
    fun setUp() {
        repository = RegisterRepository(RegisterDataSource())
        alexUser = UserDetail("Alex", "Romanov", "19",  "lr.romanov1999@mail.ru", "11111111")
        lexUser = UserDetail("Alex", "Romanov", "19",  "romanov1999@mail.ru", "11111100")
    }

    @After
    fun tearDown() {
    }

    @Test
    fun registration() {
        val badResult = repository.registration(
            alexUser.firstName,
            alexUser.secondName,
            alexUser.age,
            alexUser.login,
            alexUser.password
        )

        assertTrue(badResult is Result.Error)

        val goodResult = repository.registration(
            lexUser.firstName,
            lexUser.secondName,
            lexUser.age,
            lexUser.login,
            lexUser.password
        )
        assertTrue(goodResult is Result.Success)
        assertEquals(lexUser, (goodResult as Result.Success).data)
    }
}