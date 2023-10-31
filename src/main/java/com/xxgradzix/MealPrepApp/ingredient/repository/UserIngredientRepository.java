package com.xxgradzix.MealPrepApp.ingredient.repository;

import com.xxgradzix.MealPrepApp.ingredient.entity.UserIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserIngredientRepository extends JpaRepository<UserIngredient, Long> {

    Optional<UserIngredient> findByNameIgnoreCaseAndUserEmailIgnoreCase(String name, String userEmail);

    Iterable<UserIngredient> findByUserEmailIgnoreCase(String userEmail);

}
