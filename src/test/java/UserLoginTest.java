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
import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest {
    UserClient userClient;
    User user;

    @BeforeEach
    public void setUp() {
        this.userClient=new UserClient();
        this.user= UserGenerator.getUserWithAllCredentialsRandom();
        userClient.userCreate(user);
    }
    @AfterEach
    public void tearDown() {
        userClient.userDelete(user);
    }


    @Test
    @DisplayName("Check courier login with valid credentials")
    @Description("Basic test for /api/auth/login")
    public void userLoginWithValidCredentialsTest() {
        ValidatableResponse validatableResponse=userClient.userLogin(user);

        int statusCode=validatableResponse.extract().statusCode();
        Boolean successMessage=validatableResponse.extract().path("success");

        assertThat("Статус запроса",statusCode, equalTo(200));
        assertThat("Статус регистрации",successMessage, equalTo(true) );
    }



}
