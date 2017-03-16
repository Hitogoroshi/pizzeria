package fr.pizzeria.admin.metier;

import fr.pizzeria.model.Utilisateur;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UtilisateurService {

	@PersistenceContext
	protected EntityManager em;

	public List<Utilisateur> findAll() {
		return em.createQuery("select u from Utilisateur u", Utilisateur.class).getResultList();
	}

	public Utilisateur findOneUtilisateur(String email) {
		return em.createQuery("select u from Utilisateur u where u.email=:email", Utilisateur.class)
				.setParameter("email", email).getSingleResult();
	}

	public List<Utilisateur> isEmailTaken(String email) {
		return em.createQuery("select u from Utilisateur u where u.email=:email", Utilisateur.class)
				.setParameter("email", email).getResultList();
	}

	public void updateUtilisateur(String email, Utilisateur utilisateurAvecId) {
		Utilisateur utilisateur = findOneUtilisateur(email); // Vérifie qu'un utilisateur est présent
		if (utilisateurAvecId.getMotDePasse() == null) {
			utilisateurAvecId.setMotDePasse(utilisateur.getMotDePasse()); // Si l'utilisateur n'a pas changer son mot de passe
		}
		em.merge(utilisateurAvecId);
	}

	public void saveUtilisateur(Utilisateur utilisateurSansId) {
		em.persist(utilisateurSansId);
	}

	public void deleteUtilisateur(String email) {
		em.remove(findOneUtilisateur(email));
	}

	public String encode(String password) {
		byte[] uniqueKey = password.getBytes();
		byte[] hash = null;

		try {
			hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
		} catch (NoSuchAlgorithmException e) {
			throw new Error("No MD5 support in this VM.");
		}

		StringBuilder hashString = new StringBuilder();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(hash[i]);
			if (hex.length() == 1) {
				hashString.append('0');
				hashString.append(hex.charAt(hex.length() - 1));
			} else
				hashString.append(hex.substring(hex.length() - 2));
		}
		return hashString.toString();
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
