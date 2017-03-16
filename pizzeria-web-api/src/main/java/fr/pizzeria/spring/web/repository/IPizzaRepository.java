package fr.pizzeria.spring.web.repository;


import fr.pizzeria.model.Pizza;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IPizzaRepository extends JpaRepository<Pizza,Integer> {

	List<Pizza> findByActif(boolean actif);
    Pizza findByCode(String code);
}
