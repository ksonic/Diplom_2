package client;

import io.restassured.response.ValidatableResponse;

import static client.Settings.getBaseSpec;
import static io.restassured.RestAssured.given;

public class IngredientClient {
    private static final String INGREDIENTS_PATH = "api/ingredients";
    // Получение ингредиентов

    //GET https://stellarburgers.nomoreparties.site/api/ingredients
    public ValidatableResponse ingredients() {
        ValidatableResponse ingredientsResponse = given()
                .spec(getBaseSpec())
                .and()
                .get(INGREDIENTS_PATH)
                .then();
        return ingredientsResponse;

    }
}
