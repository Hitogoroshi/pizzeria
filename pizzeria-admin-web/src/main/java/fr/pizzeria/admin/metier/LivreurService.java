package fr.pizzeria.admin.metier;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.pizzeria.model.Livreur;

@Stateless
public class LivreurService {

	@PersistenceContext
	protected EntityManager em;

	public List<Livreur> findAll() {
		return em.createQuery("select p from Livreur p", Livreur.class).getResultList();
	}
	
	public List<Livreur> findAvailableAll() {
		return em.createQuery("select p from Livreur p where p.actif=1", Livreur.class).getResultList();
	}

	public Livreur findOneLivreur(String id) {
		return em.createQuery("select p from Livreur p where p.id=:id", Livreur.class)
				.setParameter("id", Integer.parseInt(id)).getSingleResult();
	}

	public void updateLivreur(String id, String nom, String prenom, boolean actif) {
		Livreur livreur = findOneLivreur(id); // vérifie qu'un Livreur est présent
		livreur.setNom(nom);
		livreur.setPrenom(prenom);
		livreur.setActif(actif);
		em.merge(livreur);
	}

	public void saveLivreur(Livreur livreurSansId) {
		em.persist(livreurSansId);

		// donner un code si absent
		if (livreurSansId.getCode() == null) {
			livreurSansId.setCode(livreurSansId.genererCodeBrut() + livreurSansId.getId());
			em.merge(livreurSansId);
		}
	}

	public void deleteLivreur(String id) {
		Livreur livreur = findOneLivreur(id); // vérifie qu'un Livreur est présent
		em.remove(livreur);
	}

	public List<Livreur> findLivreur(String nom, String prenom) {
		return em.createQuery("select p from Livreur p where p.nom=:nom and p.prenom=:prenom", Livreur.class)
				.setParameter("nom", nom).setParameter("prenom", prenom).getResultList();
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
