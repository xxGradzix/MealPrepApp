package com.xxgradzix.MealPrepApp.ingredient.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PublicIngredient implements Ingredient {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private boolean glutenFree;
    private boolean lactoseFree;

    private int calories;

    private Double carbohydrates;
    private Double fat;
    private Double protein;
    private Double fiber;


}
