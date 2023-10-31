package com.xxgradzix.MealPrepApp.ingredient.repository;

import com.xxgradzix.MealPrepApp.ingredient.entity.PublicIngredient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class PublicIngredientRepositoryTest {

    @Autowired
    private PublicIngredientRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldSelectPublicIngredientByNameIgnoreCase() {

        //given
        PublicIngredient ingredient = new PublicIngredient(
                1L,
                "Apple",
                true,
                true,
                100,
                20.0,
                3.0,
                2.0,
                2.0
        );
        underTest.save(ingredient);

        //when
        Optional<PublicIngredient> publicIngredient = underTest.findByNameIgnoreCase("Apple");

        //then
        assertThat(publicIngredient.isPresent()).isTrue();
    }
    @Test
    void itShouldNotSelectPublicIngredientByNameIgnoreCase() {

        //given

        //when
        Optional<PublicIngredient> publicIngredient = underTest.findByNameIgnoreCase("Apple");

        //then
        assertThat(publicIngredient.isPresent()).isFalse();
    }
}