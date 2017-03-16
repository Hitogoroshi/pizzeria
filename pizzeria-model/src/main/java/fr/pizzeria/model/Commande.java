package fr.pizzeria.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Commande {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String numeroCommande;
	@Enumerated(EnumType.STRING)
	private StatutCommande statut;
	@Enumerated(EnumType.STRING)
	private StatutCommandePaiement statutPaiement;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateCommande;
	private boolean supprime = false;

	@ManyToOne
	private Livreur livreur;
	@ManyToOne
	private Client client;
	@OneToMany(mappedBy = "commande", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommandePizza> pizzas = new ArrayList<>();

	public Commande(String numeroCommande, StatutCommandePaiement statutPaiement, StatutCommande statut,
			Calendar dateCommande, Livreur livreur, Client client) {
		this.numeroCommande = numeroCommande;
		this.statut = statut;
		this.statutPaiement = statutPaiement;
		this.dateCommande = dateCommande;
		this.livreur = livreur;
		this.client = client;
	}

	public Commande(Integer id, String numeroCommande, StatutCommandePaiement statutPaiement, StatutCommande statut,
			Calendar dateCommande, Livreur livreur, Client client) {
		this.id = id;
		this.numeroCommande = numeroCommande;
		this.statut = statut;
		this.statutPaiement = statutPaiement;
		this.dateCommande = dateCommande;
		this.livreur = livreur;
		this.client = client;
	}
	
	public Commande(String numeroCommande, StatutCommandePaiement statutPaiement, StatutCommande statut,
			Calendar dateCommande, Client client) {
		this.numeroCommande = numeroCommande;
		this.statut = statut;
		this.statutPaiement = statutPaiement;
		this.dateCommande = dateCommande;
		this.client = client;
	}


	public Commande() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroCommande() {
		return numeroCommande;
	}

	public void setNumeroCommande(String numeroCommande) {
		this.numeroCommande = numeroCommande;
	}

	public StatutCommande getStatut() {
		return statut;
	}

	public void setStatut(StatutCommande statut) {
		this.statut = statut;
	}

	public Calendar getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Calendar dateCommande) {
		this.dateCommande = dateCommande;
	}

	public Livreur getLivreur() {
		return livreur;
	}

	public StatutCommandePaiement getStatutPaiement() {
		return statutPaiement;
	}

	public void setStatutPaiement(StatutCommandePaiement statutPaiement) {
		this.statutPaiement = statutPaiement;
	}

	public void setLivreur(Livreur livreur) {
		this.livreur = livreur;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public boolean isSupprime() {
		return supprime;
	}

	public void setSupprime(boolean supprime) {
		this.supprime = supprime;
	}

	public List<CommandePizza> getPizzas() {
		return new ArrayList<>(pizzas);
	}

	public void setPizzas(List<CommandePizza> pizzas) {
		this.pizzas = pizzas;
	}

	public void addPizza(Pizza pizza, int qte) {
		CommandePizza commandePizza = new CommandePizza(this, pizza, qte);
		commandePizza.setPizzaId(pizza.getId());
		commandePizza.setCommandeId(this.getId());
		this.pizzas.add(commandePizza);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Commande [id=" + id + ", numeroCommande=" + numeroCommande + "]\n");

		sb.append("Pizza(s) : ");
		this.pizzas.forEach(p -> sb.append("\n" + p.getPizza().toString() + " x" + p.getQuantite()));

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((dateCommande == null) ? 0 : dateCommande.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((livreur == null) ? 0 : livreur.hashCode());
		result = prime * result + ((numeroCommande == null) ? 0 : numeroCommande.hashCode());
		result = prime * result + ((pizzas == null) ? 0 : pizzas.hashCode());
		result = prime * result + ((statut == null) ? 0 : statut.hashCode());
		result = prime * result + ((statutPaiement == null) ? 0 : statutPaiement.hashCode());
		result = prime * result + (supprime ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commande other = (Commande) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (dateCommande == null) {
			if (other.dateCommande != null)
				return false;
		} else if (!dateCommande.equals(other.dateCommande))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (livreur == null) {
			if (other.livreur != null)
				return false;
		} else if (!livreur.equals(other.livreur))
			return false;
		if (numeroCommande == null) {
			if (other.numeroCommande != null)
				return false;
		} else if (!numeroCommande.equals(other.numeroCommande))
			return false;
		if (pizzas == null) {
			if (other.pizzas != null)
				return false;
		} else if (!pizzas.equals(other.pizzas))
			return false;
		if (statut != other.statut)
			return false;
		if (statutPaiement != other.statutPaiement)
			return false;
		if (supprime != other.supprime)
			return false;
		return true;
	}

}
