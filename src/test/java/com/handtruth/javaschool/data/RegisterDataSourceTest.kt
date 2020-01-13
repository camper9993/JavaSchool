package com.handtruth.javaschool.data

import com.handtruth.javaschool.connect.mock.CapRepositoryProvider
import com.handtruth.javaschool.data.model.RegisterResponse
import com.handtruth.javaschool.data.model.UserDetail
import junit.framework.TestCase
import org.junit.Test

import org.junit.Assert.*

class RegisterDataSourceTest {

    @Test
    fun registration() {
        val apiMock = CapRepositoryProvider.provideAuthRepository()

        val badUser = UserDetail("Alex", "Romanov", "19", "lr.romanov1999@mail.ru", "11111111")
        val goodUser = UserDetail("A", "R", "19", "romanov@mail.ru", "11111111")
        val badAnswer: RegisterResponse? = apiMock.registration(badUser).blockingFirst()
        val goodAnswer: RegisterResponse? =
            apiMock.registration(goodUser).blockingFirst()

        TestCase.assertNotNull(badAnswer)
        TestCase.assertNotNull(goodAnswer)
        TestCase.assertEquals(-1, badAnswer?.code)
        TestCase.assertEquals(200, goodAnswer?.code)
        TestCase.assertEquals(
            UserDetail("A", "R", "19", "romanov@mail.ru", "11111111"),
            goodAnswer?.user
        )
    }
}