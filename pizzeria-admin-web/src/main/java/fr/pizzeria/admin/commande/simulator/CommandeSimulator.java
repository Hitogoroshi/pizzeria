package fr.pizzeria.admin.commande.simulator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import fr.pizzeria.admin.metier.ClientService;
import fr.pizzeria.admin.metier.CommandeService;
import fr.pizzeria.admin.metier.LivreurService;
import fr.pizzeria.model.Client;
import fr.pizzeria.model.Commande;
import fr.pizzeria.model.Livreur;
import fr.pizzeria.model.StatutCommande;
import fr.pizzeria.model.StatutCommandePaiement;

@Stateless
public class CommandeSimulator {

	private List<Commande> cmds = new ArrayList<>();

	@Inject
	CommandeService cmdService;

	@Inject
	ClientService clientService;

	@Inject
	LivreurService livreurService;

	public CommandeSimulator() {
	}

	//@Schedule(minute = "*/1", hour = "*") // pour le test chaque minute
	@Schedule(minute = "*/1", hour = "*") // a définir avec le product owner
	public void simulator() {
		Logger.getAnonymousLogger().log(Level.INFO,"simulateur");
		cmds = cmdService.findAll();
		for (Commande cmd : cmds){
			switch (cmd.getStatut()) {
			case NON_TRAITE:
				cmd.setStatut(StatutCommande.EXPEDIE);
				break;
			case EXPEDIE:
				cmd.setStatut(StatutCommande.LIVRE);
				break;
			default:
				break;
			}
		}
	}
}
