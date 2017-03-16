package fr.pizzeria.spring.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.pizzeria.model.Ingredient;

public interface IIngredientRepository extends JpaRepository<Ingredient, String> {
	
	Ingredient findByCode(String code);
}
