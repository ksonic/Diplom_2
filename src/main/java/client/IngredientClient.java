package client;

import io.restassured.response.ValidatableResponse;

import static client.Settings.getBaseSpec;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class IngredientClient {
    private static final String INGREDIENTS_PATH = "api/ingredients";
    //GET https://stellarburgers.nomoreparties.site/api/ingredients
    @Step("Getting ingredients")
    public ValidatableResponse ingredients() {
        ValidatableResponse ingredientsResponse = given()
                .spec(getBaseSpec())
                .and()
                .get(INGREDIENTS_PATH)
                .then();
        return ingredientsResponse;

    }
}
