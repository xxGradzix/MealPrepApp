package com.xxgradzix.MealPrepApp.ingredient.controller;

import com.xxgradzix.MealPrepApp.ingredient.entity.Ingredient;
import com.xxgradzix.MealPrepApp.ingredient.entity.PublicIngredient;
import com.xxgradzix.MealPrepApp.ingredient.entity.UserIngredient;
import com.xxgradzix.MealPrepApp.ingredient.service.IngredientService;
import com.xxgradzix.MealPrepApp.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/test")
    public ResponseEntity<String> getIngredient(@CurrentSecurityContext Authentication authentication) {
        return ResponseEntity.ok("dupa");
    }
    @GetMapping("/{ingredientName}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable("ingredientName") String name, @CurrentSecurityContext(expression = "authentication.name") String username) {
        System.out.println(username);
        return ResponseEntity.ok(ingredientService.getIngredient(name, username));
    }

    @PostMapping("/public")
    public ResponseEntity<PublicIngredient> addPublicIngredient(@RequestBody PublicIngredient publicIngredient) {
        return ResponseEntity.ok(ingredientService.addPublicIngredient(publicIngredient));
    }
    @PostMapping("/private")
    public ResponseEntity<UserIngredient> addPrivateIngredient(@RequestBody UserIngredient userIngredient, @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        userIngredient.setUser((UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(ingredientService.addPrivateIngredient(userIngredient));
    }

}
