import client.UserClient;
import client.UserGenerator;
import io.qameta.allure.Description;
import models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static client.UserGenerator.getUserWithoutNameRandom;
import static client.UserGenerator.getUserWithoutPasswordRandom;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserCreationWithInvalidCredentialsTest {
    UserClient userClient;


    public static Object[][] getUserData() {
        return new Object[][]{
                {UserGenerator.getUserWithoutEmailRandom()},
                {getUserWithoutPasswordRandom()},
                {getUserWithoutNameRandom()},
        };
    }

    @ParameterizedTest
    @MethodSource("getUserData")
    @DisplayName("Check that it's impossible to create the courier without email")
    @Description("Basic test for /api/auth/user")
    public void UserCreationWithInvalidCredentialsTest(User user) {
        this.userClient=new UserClient();

        int statusCode=userClient.userCreate(user).extract().statusCode();
        String message=userClient.userCreate(user).extract().path("message");
        Boolean successMessage=userClient.userCreate(user).extract().path("success");

        assertThat("Статус ответа",statusCode,equalTo(403));
        assertThat("Статус регистрации",successMessage, equalTo(false) );
        assertThat("Сообщение", message,equalTo("Email, password and name are required fields") );
    }
}
