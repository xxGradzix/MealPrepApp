package com.xxgradzix.MealPrepApp.ingredient.service;

import com.xxgradzix.MealPrepApp.ingredient.entity.Ingredient;
import com.xxgradzix.MealPrepApp.ingredient.entity.PublicIngredient;
import com.xxgradzix.MealPrepApp.ingredient.entity.UserIngredient;
import com.xxgradzix.MealPrepApp.ingredient.repository.PublicIngredientRepository;
import com.xxgradzix.MealPrepApp.ingredient.repository.UserIngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock
    private UserIngredientRepository userIngredientRepository;

    @Mock
    private PublicIngredientRepository publicIngredientRepository;

    private IngredientService underTest;

    @BeforeEach
    void setUp() {

        underTest = new IngredientService(publicIngredientRepository, userIngredientRepository);
    }

    @Test
    void shouldReturnUserIngredient() {
        //given
        String testIngredientName = "Apple";
        String testEmail = "test@email.com";
        UserIngredient userIngredient = new UserIngredient();
        // when
        when(userIngredientRepository.findByNameIgnoreCaseAndUserEmailIgnoreCase(testIngredientName, testEmail))
                .thenReturn(Optional.of(userIngredient));
        // then
        Ingredient ingredient = underTest.getIngredient(testIngredientName, testEmail);
        assertThat(ingredient).isEqualTo(userIngredient);
    }
    @Test
    void shouldReturnPublicIngredient() {
        //given
        String testIngredientName = "Apple";
        String testEmail = "test@email.com";
        UserIngredient userIngredient = new UserIngredient();
        PublicIngredient publicIngredient = new PublicIngredient();
        // when
        when(userIngredientRepository.findByNameIgnoreCaseAndUserEmailIgnoreCase(testIngredientName, testEmail))
                .thenReturn(Optional.empty());
        when(publicIngredientRepository.findByNameIgnoreCase(testIngredientName))
                .thenReturn(Optional.of(publicIngredient));
        // then
        Ingredient ingredient = underTest.getIngredient(testIngredientName, testEmail);
        assertThat(ingredient).isEqualTo(publicIngredient);
    }
    @Test
    void shouldReturnNull() {
        //given
        String testIngredientName = "Apple";
        String testEmail = "test@email.com";
        UserIngredient userIngredient = new UserIngredient();
        PublicIngredient publicIngredient = new PublicIngredient();
        // when
        when(userIngredientRepository.findByNameIgnoreCaseAndUserEmailIgnoreCase(testIngredientName, testEmail))
                .thenReturn(Optional.empty());
        when(publicIngredientRepository.findByNameIgnoreCase(testIngredientName))
                .thenReturn(Optional.empty());
        // then
        Ingredient ingredient = underTest.getIngredient(testIngredientName, testEmail);
        assertThat(ingredient).isEqualTo(null);
    }

    @Test
    void getAllPublicIngredients() {
        // when
        underTest.getAllPublicIngredients();
        // then
        verify(publicIngredientRepository).findAll();
    }

    @Test
    void getAllPrivateIngredients() {
        // given
        String testEmail = "test@email.com";
        // when
        underTest.getAllPrivateIngredients(testEmail);
        // then
        verify(userIngredientRepository).findByUserEmailIgnoreCase(testEmail);
    }
    @Test
    void addPublicIngredient() {
        // given
        PublicIngredient ingredient = new PublicIngredient(1L,
                "Apple",
                true,
                true,
                100,
                2.0,
                2.0,
                2.0,
                2.0
                );
        //when
        underTest.addPublicIngredient(ingredient);
        //then
        ArgumentCaptor<PublicIngredient> argumentCaptor = ArgumentCaptor.forClass(PublicIngredient.class);
        verify(publicIngredientRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(ingredient);
    }

    @Test
    void addPrivateIngredient() {
        // given
        UserIngredient userIngredient = new UserIngredient();
        // when
        underTest.addPrivateIngredient(userIngredient);
        // then
        ArgumentCaptor<UserIngredient> argumentCaptor = ArgumentCaptor.forClass(UserIngredient.class);
        verify(userIngredientRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(userIngredient);
    }

    @Test
    void shouldUpdatePublicIngredientById() {
        // given
        Long testId = 1L;
        PublicIngredient previousIngredient = new PublicIngredient();
        PublicIngredient newIngredient = new PublicIngredient();
        // when
        when(publicIngredientRepository.findById(testId))
                .thenReturn(Optional.of(previousIngredient));
        underTest.updatePublicIngredientById(testId, newIngredient);
        // then
        ArgumentCaptor<PublicIngredient> argumentCaptor = ArgumentCaptor.forClass(PublicIngredient.class);
        verify(publicIngredientRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(newIngredient);
    }
    @Test
    void shouldThrowWhileUpdatePublicIngredientById() {
        // given
        Long testId = 1L;
        PublicIngredient newIngredient = new PublicIngredient();
        // when
        when(publicIngredientRepository.findById(testId))
                .thenReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> underTest.updatePublicIngredientById(testId, newIngredient))
                .isInstanceOf(EntityNotFoundException.class);
    }
    @Test
    void shouldUpdatePublicIngredientByName() {
        // given
        String testName = "test";
        PublicIngredient previousIngredient = new PublicIngredient();
        PublicIngredient newIngredient = new PublicIngredient();
        // when
        when(publicIngredientRepository.findByNameIgnoreCase(testName))
                .thenReturn(Optional.of(previousIngredient));
        underTest.updatePublicIngredientByName(testName, newIngredient);
        // then
        ArgumentCaptor<PublicIngredient> argumentCaptor = ArgumentCaptor.forClass(PublicIngredient.class);
        verify(publicIngredientRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(newIngredient);
    }
    @Test
    void shouldThrowWhileUpdatePublicIngredientByName() {
        // given
        String testName = "testName";
        PublicIngredient newIngredient = new PublicIngredient();

        // when
        when(publicIngredientRepository.findByNameIgnoreCase(testName))
                .thenReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> underTest.updatePublicIngredientByName(testName, newIngredient))
                .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    void updatePrivateIngredientById() {
        // given
        Long testId = 1L;
        UserIngredient previousIngredient = new UserIngredient();
        UserIngredient newIngredient = new UserIngredient();
        // when
        when(userIngredientRepository.findById(testId))
                .thenReturn(Optional.of(previousIngredient));
        underTest.updatePrivateIngredientById(testId, newIngredient);
        // then
        ArgumentCaptor<UserIngredient> argumentCaptor = ArgumentCaptor.forClass(UserIngredient.class);
        verify(userIngredientRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(newIngredient);

    }
    @Test
    void shouldThrowWhileUpdatePrivateIngredientById() {
        // given
        Long testId = 1L;
        UserIngredient newIngredient = new UserIngredient();
        // when
        when(userIngredientRepository.findById(testId))
                .thenReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> underTest.updatePrivateIngredientById(testId, newIngredient))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void updatePrivateIngredientByName() {
        // given
        String testIngredientName = "testIngredient";
        String testUser = "testUser";
        UserIngredient newIngredient = new UserIngredient();
        UserIngredient previousIngredient = new UserIngredient();
        // when
        when(userIngredientRepository.findByNameIgnoreCaseAndUserEmailIgnoreCase(testIngredientName, testUser))
                .thenReturn(Optional.of(previousIngredient));
        underTest.updatePrivateIngredientByName(testIngredientName, newIngredient, testUser);
        // then
        ArgumentCaptor<UserIngredient> argumentCaptor = ArgumentCaptor.forClass(UserIngredient.class);
        verify(userIngredientRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(newIngredient);


    }
    @Test
    void shouldThrowWhileUpdatePrivateIngredientByName() {
        // given
        String ingredientName = "ingredientName";
        String userName = "userName";
        UserIngredient newIngredient = new UserIngredient();
        // when
        when(userIngredientRepository.findByNameIgnoreCaseAndUserEmailIgnoreCase(ingredientName, userName))
                .thenReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> underTest.updatePrivateIngredientByName(ingredientName, newIngredient, userName))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void removePublicIngredient() {
        // given
        PublicIngredient userIngredient = new PublicIngredient();
        // when
        underTest.removePublicIngredient(userIngredient);
        // then
        ArgumentCaptor<PublicIngredient> argumentCaptor = ArgumentCaptor.forClass(PublicIngredient.class);

        verify(publicIngredientRepository).delete(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(userIngredient);
    }
    @Test
    void removeUserIngredient() {
        // given
        UserIngredient userIngredient = new UserIngredient();
        // when
        underTest.removePrivateIngredient(userIngredient);
        // then
        ArgumentCaptor<UserIngredient> argumentCaptor = ArgumentCaptor.forClass(UserIngredient.class);
        verify(userIngredientRepository).delete(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(userIngredient);
    }


}