import client.UserClient;
import client.UserGenerator;
import io.qameta.allure.Description;
import models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static client.UserGenerator.getUserWithoutPasswordRandom;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserLoginWithInvalidCredentialsTest {
    UserClient userClient;

    public static Object[][] getUserData() {
        return new Object[][]{
                {UserGenerator.getUserWithoutEmailRandom(),false,"email or password are incorrect"},
                {getUserWithoutPasswordRandom(),false,"email or password are incorrect"},
        };
    }

    @ParameterizedTest
    @MethodSource("getUserData")
    @DisplayName("Check user login")
    @Description("Basic test for /api/auth/user")
    public void UserCreationWithInvalidCredentialsTest(User user) {
        this.userClient=new UserClient();

        int statusCode=userClient.userLogin(user).extract().statusCode();
        String message=userClient.userLogin(user).extract().path("message");
        Boolean successMessage=userClient.userLogin(user).extract().path("success");

        assertThat("Статус ответа",statusCode,equalTo(401));
        assertThat("Статус регистрации",successMessage, equalTo(false) );
        assertThat("Сообщение", message,equalTo("email or password are incorrect") );
    }
}
