package com.xxgradzix.MealPrepApp.ingredient.service;

import com.xxgradzix.MealPrepApp.ingredient.entity.Ingredient;
import com.xxgradzix.MealPrepApp.ingredient.entity.PublicIngredient;
import com.xxgradzix.MealPrepApp.ingredient.entity.UserIngredient;
import com.xxgradzix.MealPrepApp.ingredient.repository.PublicIngredientRepository;
import com.xxgradzix.MealPrepApp.ingredient.repository.UserIngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final PublicIngredientRepository publicIngredientRepository;
    private final UserIngredientRepository userIngredientRepository;

    public Ingredient getIngredient(String name, String userEmail) {

        Optional<UserIngredient> optionalUserIngredient = userIngredientRepository.findByNameIgnoreCaseAndUserEmailIgnoreCase(name, userEmail);
        if(optionalUserIngredient.isPresent()) {
            return optionalUserIngredient.get();
        }
        Optional<PublicIngredient> optionalPublicIngredient = publicIngredientRepository.findByNameIgnoreCase(name);

        if(optionalPublicIngredient.isPresent()) {
            return optionalPublicIngredient.get();
        }
        //TODO chatgpt ingredient
        return null;
    }

    public Iterable<PublicIngredient> getAllPublicIngredients() {
        return publicIngredientRepository.findAll();
    }
    public Iterable<UserIngredient> getAllPrivateIngredients(String userEmail) {
        return userIngredientRepository.findByUserEmailIgnoreCase(userEmail);
    }

    public PublicIngredient addPublicIngredient(PublicIngredient publicIngredient) {
        return publicIngredientRepository.save(publicIngredient);
    }
    public UserIngredient addPrivateIngredient(UserIngredient userIngredient) {
        return userIngredientRepository.save(userIngredient);
    }

    public PublicIngredient updatePublicIngredientById(Long ingredientId, PublicIngredient ingredient) {
        Optional<PublicIngredient> optionalPublicIngredient = publicIngredientRepository.findById(ingredientId);
        if(optionalPublicIngredient.isPresent()) {
            ingredient.setId(ingredientId);
            return publicIngredientRepository.save(ingredient);
        }
        throw new EntityNotFoundException("Ingredient with id " + ingredientId + "not found");
    }
    public PublicIngredient updatePublicIngredientByName(String ingredientName, PublicIngredient ingredient) {
        Optional<PublicIngredient> optionalPublicIngredient = publicIngredientRepository.findByNameIgnoreCase(ingredientName);
        if(optionalPublicIngredient.isPresent()) {
            ingredient.setName(ingredientName);
            return publicIngredientRepository.save(ingredient);
        }
        throw new EntityNotFoundException("Ingredient with name " + ingredientName + "not found");
    }

    public UserIngredient updatePrivateIngredientById(Long ingredientId, UserIngredient ingredient) {
        Optional<UserIngredient> optionalIngredient = userIngredientRepository.findById(ingredientId);
        if(optionalIngredient.isPresent()) {
            ingredient.setId(ingredientId);
            return userIngredientRepository.save(ingredient);
        }
        throw new EntityNotFoundException("Ingredient with id " + ingredientId + "not found");
    }
    public UserIngredient updatePrivateIngredientByName(String ingredientName, UserIngredient ingredient, String userEmail) {
        Optional<UserIngredient> optionalIngredient = userIngredientRepository.findByNameIgnoreCaseAndUserEmailIgnoreCase(ingredientName, userEmail);
        if(optionalIngredient.isPresent()) {
            ingredient.setName(ingredientName);
            return userIngredientRepository.save(ingredient);
        }
        throw new EntityNotFoundException("Ingredient with name " + ingredientName + "not found");
    }

    public void removePublicIngredient(PublicIngredient ingredient) {
        publicIngredientRepository.delete(ingredient);
    }

    public void removePrivateIngredient(UserIngredient ingredient) {
        userIngredientRepository.delete(ingredient);
    }

}

