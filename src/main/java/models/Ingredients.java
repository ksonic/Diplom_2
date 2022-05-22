package models;

import java.util.List;

public class Ingredients {
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<IngredientsData> getData() {
        return data;
    }

    public void setData(List<IngredientsData> data) {
        this.data = data;
    }

    Boolean success;
    List<IngredientsData> data;

    public Ingredients(Boolean success, List<IngredientsData> data) {
        this.success = success;
        this.data = data;
    }


}
