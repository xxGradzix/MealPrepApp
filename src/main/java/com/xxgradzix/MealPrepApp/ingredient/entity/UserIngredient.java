package com.xxgradzix.MealPrepApp.ingredient.entity;

import com.xxgradzix.MealPrepApp.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserIngredient implements Ingredient {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private UserEntity user;

    private String name;

    private boolean glutenFree;
    private boolean lactoseFree;

    private int calories;

    private Double carbohydrates;
    private Double fat;
    private Double protein;
    private Double fiber;


}
