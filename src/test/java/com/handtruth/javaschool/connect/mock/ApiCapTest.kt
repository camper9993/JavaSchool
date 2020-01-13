package com.handtruth.javaschool.connect.mock

import com.handtruth.javaschool.data.model.AuthResponse
import com.handtruth.javaschool.data.model.RegisterResponse
import com.handtruth.javaschool.data.model.UserDetail
import junit.framework.TestCase.*
import org.junit.*

class ApiCapTest {

    val apiMock: ApiCap = ApiCap()

    companion object {

        @Before
        fun setUp() {
        }

        @After
        fun turnDown() {

        }

    }

    @Test
    fun auth() {
        val badAnswer: AuthResponse? = apiMock.auth("login", "password").blockingFirst()
        val goodAnswer: AuthResponse? =
            apiMock.auth("lr.romanov1999@mail.ru", "11111111").blockingFirst()

        assertNotNull(badAnswer)
        assertNotNull(goodAnswer)
        assertEquals(-1, badAnswer?.code)
        assertEquals(200, goodAnswer?.code)
        assertEquals(
            UserDetail("Alex", "Romanov", "19", "lr.romanov1999@mail.ru", "11111111"),
            goodAnswer?.user
        )
    }

    @Test
    fun registration() {
        val badUser = UserDetail("Alex", "Romanov", "19", "lr.romanov1999@mail.ru", "11111111")
        val goodUser = UserDetail("A", "R", "19", "romanov@mail.ru", "11111111")
        val badAnswer: RegisterResponse? = apiMock.registration(badUser).blockingFirst()
        val goodAnswer: RegisterResponse? =
            apiMock.registration(goodUser).blockingFirst()

        assertNotNull(badAnswer)
        assertNotNull(goodAnswer)
        assertEquals(-1, badAnswer?.code)
        assertEquals(200, goodAnswer?.code)
        assertEquals(
            UserDetail("A", "R", "19", "romanov@mail.ru", "11111111"),
            goodAnswer?.user
        )
    }

    @Test
    fun getModule() {
    }

    @Test
    fun getTests() {
    }

    @Test
    fun getModuleList() {
    }
}