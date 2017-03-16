package fr.pizzeria.spring.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.pizzeria.model.Client;
import fr.pizzeria.model.Commande;

public interface ICommandeRepository extends JpaRepository<Commande, Integer> {
	// List<Commande> findByActif(boolean actif);
	List<Commande> findByClient(Client client);
}
