package com.xxgradzix.MealPrepApp.ingredient.repository;

import com.xxgradzix.MealPrepApp.ingredient.entity.PublicIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicIngredientRepository extends JpaRepository<PublicIngredient, Long> {

    Optional<PublicIngredient> findByNameIgnoreCase(String name);

}
