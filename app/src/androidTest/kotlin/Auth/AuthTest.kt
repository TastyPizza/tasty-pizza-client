package Auth

import com.example.tastypizzaclient.model.response.AuthResponse
import com.example.tastypizzaclient.service.AuthService
import junit.framework.TestCase.assertEquals

import org.junit.Test


class AuthTest {
    private var authService: AuthService = AuthService()

    @Test
    fun signIn_success() {
        val email = "twilightnotch@gmail.com"
        val expectedResponse = AuthResponse()
        expectedResponse.errorMessage = "200"

        var actualResponse: AuthResponse? = null
        authService.signIn(email) { response ->
            actualResponse = response
            print(actualResponse)
            assertEquals(expectedResponse.errorMessage, actualResponse?.errorMessage.toString())
        }
    }

    @Test
    fun signIn_failure() {
        val email = "incorrect@gmail.com"
        val expectedResponse = AuthResponse()
        expectedResponse.errorMessage = "404"

        var actualResponse: AuthResponse? = null
        authService.signIn(email) { response ->
            actualResponse = response
            print(actualResponse)
            assertEquals(expectedResponse.errorMessage, actualResponse?.errorMessage.toString())
        }
    }

}