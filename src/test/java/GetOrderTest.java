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
    @DisplayName("Check getting order with login")
    @Description("Basic test for api/orders")
    public void orderCreateWithLoginTest() {
        OrderClient orderClient = new OrderClient();

        ValidatableResponse validatableResponse = orderClient.getOrders(user);

        int statusCode = validatableResponse.extract().statusCode();
        Boolean successMessage = validatableResponse.extract().path("success");

        assertThat("Код ответа", statusCode, equalTo(200));
        assertThat("Статус", successMessage, equalTo(true));
    }

    @Test
    @DisplayName("Check getting order without login")
    @Description("Basic test for api/orders")
    public void orderCreateWithoutLoginTest() {
        OrderClient orderClient = new OrderClient();

        ValidatableResponse validatableResponse = orderClient.getOrdersWithoutLogin();

        int statusCode = validatableResponse.extract().statusCode();
        Boolean successMessage = validatableResponse.extract().path("success");
        String message=validatableResponse.extract().path("message");

        assertThat("Код ответа", statusCode, equalTo(401));
        assertThat("Статус", successMessage, equalTo(false));
        assertThat("Сообщение", message,equalTo("You should be authorised"));
    }
}
