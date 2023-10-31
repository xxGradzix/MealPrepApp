package com.xxgradzix.MealPrepApp.ingredient.repository;

import com.xxgradzix.MealPrepApp.ingredient.entity.UserIngredient;
import com.xxgradzix.MealPrepApp.user.Role;
import com.xxgradzix.MealPrepApp.user.UserEntity;
import com.xxgradzix.MealPrepApp.user.repository.UserEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserIngredientRepositoryTest {

    @Autowired
    private UserIngredientRepository userIngredientRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;
    @AfterEach
    void tearDown() {
        userIngredientRepository.deleteAll();
        userEntityRepository.deleteAll();
    }

    @Test
    void findByNameIgnoreCaseAndUserEmailIgnoreCase() {

        // given

        UserEntity user = new UserEntity(1L,
                "John",
                "Travolta",
                "john@email.com",
                "Password",
                Role.USER);
        UserIngredient userIngredient = new UserIngredient(
                1L,
                user,
                "AppLe",
                true,
                true,
                100,
                2.0,
                3.0,
                4.0,
                5.0
        );

        userEntityRepository.save(user);
        userIngredientRepository.save(userIngredient);

        // when

        Optional<UserIngredient> optionalUserIngredientTrue = userIngredientRepository.findByNameIgnoreCaseAndUserEmailIgnoreCase("aPPle", "JohN@email.com");
        Optional<UserIngredient> optionalUserIngredientFalse1 = userIngredientRepository.findByNameIgnoreCaseAndUserEmailIgnoreCase("aPle", "JohN@email.com");
        Optional<UserIngredient> optionalUserIngredientFalse2 = userIngredientRepository.findByNameIgnoreCaseAndUserEmailIgnoreCase("aPPle", "JoN@email.com");

        // then

        assertThat(optionalUserIngredientTrue.isPresent()).isTrue();
        assertThat(optionalUserIngredientFalse1.isPresent()).isFalse();
        assertThat(optionalUserIngredientFalse2.isPresent()).isFalse();
    }

    @Test
    void findByUserEmailIgnoreCase() {
        // given
        UserEntity user1 = new UserEntity(1L,
                "John",
                "Travolta",
                "john@email.com",
                "Password",
                Role.USER);
        UserEntity user2 = new UserEntity(2L,
                "Max",
                "Maniac",
                "max@email.com",
                "Password2",
                Role.USER);
        UserIngredient userIngredient1 = new UserIngredient(
                1L,
                user1,
                "AppLe",
                true,
                true,
                100,
                2.0,
                3.0,
                4.0,
                5.0
        );
        UserIngredient userIngredient2 = new UserIngredient(
                2L,
                user1,
                "Orange",
                true,
                true,
                200,
                3.0,
                4.0,
                4.0,
                111.0
        );
        UserIngredient userIngredient3 = new UserIngredient(
                3L,
                user2,
                "Passion Fruit",
                true,
                true,
                500,
                23.0,
                4.0,
                4.0,
                11.0
        );
        userEntityRepository.saveAll(List.of(user2, user1));
        userIngredientRepository.saveAll(List.of(userIngredient1, userIngredient2, userIngredient3));
        // when

        List<UserIngredient> ingredients = (List<UserIngredient>) userIngredientRepository.findByUserEmailIgnoreCase("joHn@EMAIL.com");

        // then

        assertThat(ingredients).asList().containsAll(List.of(userIngredient1, userIngredient2));
    }
}