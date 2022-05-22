import client.OrderClient;
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

public class GetOrderTest {
    UserClient userClient = null;
    User user;

    @BeforeEach
    public void setUp() {
        this.userClient=new UserClient();
        user= UserGenerator.getUserWithAllCredentialsRandom();
        userClient.userCreate(user);
    }
    @AfterEach
    public void tearDown() {
        userClient.userDelete(user);
    }

    @Test
    @DisplayName("Check getting order with login")
    @Description("Basic test for api/orders")
    public void getOrdersWithLoginTest() {
        OrderClient orderClient = new OrderClient();

        ValidatableResponse validatableResponse = orderClient.getOrders(user);

        int statusCode = validatableResponse.extract().statusCode();
        Boolean successMessage =Boolean.parseBoolean(validatableResponse.extract().path("success").toString());

        assertThat("Код ответа", statusCode, equalTo(200));
        assertThat("Статус", successMessage, equalTo(true));
    }

    @Test
    @DisplayName("Check getting order without login")
    @Description("Basic test for api/orders")
    public void getOrdersWithoutLoginTest() {
        OrderClient orderClient = new OrderClient();

        ValidatableResponse validatableResponse = orderClient.getOrdersWithoutLogin();

        int statusCode = validatableResponse.extract().statusCode();
        Boolean successMessage = Boolean.parseBoolean(validatableResponse.extract().path("success").toString());
        String message=validatableResponse.extract().path("message").toString();

        assertThat("Код ответа", statusCode, equalTo(401));
        assertThat("Статус", successMessage, equalTo(false));
        assertThat("Сообщение", message,equalTo("You should be authorised"));
    }
}
