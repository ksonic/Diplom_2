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

import static client.OrderGenerator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OrderCreationTest {
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
    @DisplayName("Check order creation without login")
    @Description("Basic test for api/orders")
    public void createOrderWithoutLoginTest() {
        OrderClient orderClient = new OrderClient();

        ValidatableResponse validatableResponse = orderClient.orderCreate(json);

        int statusCode = validatableResponse.extract().statusCode();
        Boolean successMessage = Boolean.parseBoolean(validatableResponse.extract().path("success").toString());

        assertThat("Статус запроса", statusCode, equalTo(200));
        assertThat("Статус регистрации", successMessage, equalTo(true));

    }

    @Test
    @DisplayName("Check order creation with login")
    @Description("Basic test for api/orders")
    public void createOrderWithLoginTest() {
        OrderClient orderClient = new OrderClient();

        ValidatableResponse orderResponse = orderClient.orderCreateWithLogin(json,user);

        int statusCode = orderResponse.extract().statusCode();
        Boolean successMessage = Boolean.parseBoolean(orderResponse.extract().path("success").toString());

        assertThat("Статус запроса", statusCode, equalTo(200));
        assertThat("Статус регистрации", successMessage, equalTo(true));
    }

    @Test
    @DisplayName("Check order creation without ingredients")
    @Description("Basic test for api/orders")
    public void createOrderWithIngredientsTest() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse orderResponse = orderClient.orderCreate(jsonWithoutIngredients);

        int statusCode = orderResponse.extract().statusCode();
        String message=orderResponse.extract().path("message").toString();
        Boolean successMessage = Boolean.parseBoolean(orderResponse.extract().path("success").toString());

        assertThat("Статус запроса", statusCode, equalTo(400));
        assertThat("Статус заказа", successMessage, equalTo(false));
        assertThat("Сообщение", message,equalTo("Ingredient ids must be provided") );
    }

    @Test
    @DisplayName("Check order creation with invalid hash ingredients")
    @Description("Basic test for api/orders")
    public void createOrderWithInvalidIngredientsTest() {
        OrderClient orderClient = new OrderClient();

        ValidatableResponse orderResponse = orderClient.orderCreate(jsonInvalidIngredients);

        int statusCode = orderResponse.extract().statusCode();
        assertThat("Статус запроса", statusCode, equalTo(500));
    }
}


