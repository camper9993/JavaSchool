//package com.handtruth.javaschool.data
//
//import com.handtruth.javaschool.connect.mock.CapRepositoryProvider
//import com.handtruth.javaschool.data.model.AuthResponse
//import com.handtruth.javaschool.data.model.UserDetail
//import junit.framework.TestCase
//import org.junit.Test
//
//import org.junit.Assert.*
//
//class LoginDataSourceTest {
//
//    @Test
//    fun login() {
//        val apiMock = CapRepositoryProvider.provideAuthRepository()
//
//        val badAnswer: AuthResponse? = apiMock.auth("login", "password").blockingFirst()
//        val goodAnswer: AuthResponse? =
//            apiMock.auth("lr.romanov1999@mail.ru", "11111111").blockingFirst()
//
//        TestCase.assertNotNull(badAnswer)
//        TestCase.assertNotNull(goodAnswer)
//        TestCase.assertEquals(-1, badAnswer?.code)
//        TestCase.assertEquals(200, goodAnswer?.code)
//        TestCase.assertEquals(
//            UserDetail("Alex", "Romanov", "19", "lr.romanov1999@mail.ru", "11111111"),
//            goodAnswer?.user
//        )
//    }
//
//    @Test
//    fun logout() {
//    }
//}