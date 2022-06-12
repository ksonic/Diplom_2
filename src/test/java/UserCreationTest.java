import client.Settings;
import client.UserClient;
import client.UserGenerator;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserCreationTest extends Settings {
    UserClient userClient;
    User user;

    @BeforeEach
    public void setUp() {
        this.userClient = new UserClient();
        this.user = UserGenerator.getUserWithAllCredentialsRandom();

    }

    @AfterEach
    public void tearDown() {
        userClient.userDelete(user);
    }

    @Test
    @DisplayName("Check courier creation with valid input data")
    @Description("Basic test for /api/auth/user")
    public void userCreateTest() {

        ValidatableResponse validatableResponse = userClient.userCreate(user);

        int statusCode = validatableResponse.extract().statusCode();
        Boolean successMessage = Boolean.parseBoolean(validatableResponse.extract().path("success").toString());


        assertThat("Статус запроса", statusCode, equalTo(200));
        assertThat("Статус регистрации", successMessage, equalTo(true));
    }

    @Test
    @DisplayName("Check  creation of a user who is already registered")
    @Description("Basic test for /api/auth/user")
    public void userExistedTest() {

        userClient.userCreate(user);

        int statusCode = userClient.userCreate(user).extract().statusCode();
        String message = userClient.userCreate(user).extract().path("message").toString();
        Boolean successMessage = Boolean.parseBoolean(userClient.userCreate(user).extract().path("success").toString());

        assertThat("Статус ответа", statusCode, equalTo(403));
        assertThat("Статус регистрации", successMessage, equalTo(false));
        assertThat("Сообщение", message, equalTo("User already exists"));
    }


}
