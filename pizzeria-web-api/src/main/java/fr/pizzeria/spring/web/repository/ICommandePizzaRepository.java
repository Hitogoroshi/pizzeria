package fr.pizzeria.spring.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.pizzeria.model.CommandePizza;

public interface ICommandePizzaRepository extends JpaRepository<CommandePizza, Integer>{
		//List<Commande> findByActif(boolean actif);
}
