package fr.pizzeria.admin.metier;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sun.mail.smtp.SMTPTransport;

import fr.pizzeria.model.Client;
import fr.pizzeria.model.Email;

@Stateless
public class EMailService {

	//private Session session; //a supprimer si pas bug

	@PersistenceContext
	protected EntityManager em;

	@Inject
	ClientService clientService;

	public void setEm(EntityManager em2) {
		this.em = em2;
	}

	public List<Email> findAll() {
		return em.createQuery("select e from Email e", Email.class).getResultList();
	}

	public void envoyeEmail(String adresseMail, String pizza) throws MessagingException {
		String value = String.format("<!DOCTYPE html>" + "Bonjour cher client !" + "<br>"
				+ "Nous avons le plaisir de vous proposer cette semaine une promotion exeptionnelle ! " + "<br>"
				+ "La pizza <strong>%s</strong> est à moitié prix !" + "<br>"
				+ "Venez vite à notre pizzeria pour en profiter.", pizza);
		send(adresseMail, "promotion de la semaine", value, pizza);

	}

	public void envoyeEmailPromotionPizza(String pizza) throws MessagingException {
		List<Client> clients = clientService.findAll();
		System.err.println("Liste des clients :" + clients);
		for (Client client : clients) {
			System.err.println("EMail du client :" + client.getEmail());
			if (client.isAbonne()) {
				envoyeEmail(client.getEmail(), pizza);
			}

		}

	}

	public void envoyeEmailPasswordModification(String adresseMail, String prenom, String nom, String motdepasse) {
		String value = String.format("<!DOCTYPE html>" + "Bonjour %s %s!" + "<br>"
				+ "Nous avons le plaisir de vous compter parmis nous en tant que nouveau client! " + "<br>"
				+ "votre mot de passe <strong> %s </strong> !" + "<br>", prenom, nom, motdepasse);

		send(adresseMail, "Modifier votre mot de passe", value);

	}
	
	
	
	public void send(String addresses, String topic, String textMessage, String pizza) throws MessagingException {

		String username = getUserProperties()[0];
		String password = getUserProperties()[1]; 
		Session session = properties();

		try {
			
			
			Date date = new Date();
			final MimeMessage msg = new MimeMessage(session);
			envoieFinal(addresses, topic, textMessage, username, password, session, msg);
			// sauvegarde de l'email pour historique
			Email email = new Email(username, addresses, date, topic, pizza, textMessage);
			saveEmail(email);

		} catch (MessagingException e) {
			Logger.getLogger(EMailService.class.getName()).log(Level.WARNING, "Cannot send mail", e);
		}
	}

	public void send(String addresses, String topic, String textMessage) {
		String username = getUserProperties()[0];
		String password = getUserProperties()[1]; 
		Session session = properties();
		try {
			// -- Create a new message --
			final MimeMessage msg = new MimeMessage(session);
			// -- Set the FROM and TO fields --
			envoieFinal(addresses, topic, textMessage, username, password, session, msg);

		} catch (MessagingException e) {
			Logger.getLogger(EMailService.class.getName()).log(Level.WARNING, "Cannot send mail", e);
		}
	}

	private String[] getUserProperties() {
		String tab[]={"clever.institut.test","formationdta"};
		return tab;
		
	}

	private void envoieFinal(String addresses, String topic, String textMessage, String username, String password,
			Session session, final MimeMessage msg)
			throws MessagingException, AddressException, NoSuchProviderException, SendFailedException {
		msg.setFrom(new InternetAddress(username + "@gmail.com"));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses, false));

		msg.setSubject(topic);
		msg.setContent(textMessage, "text/html; charset=utf-8");
		msg.setSentDate(new Date());

		SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

		t.connect("smtp.gmail.com", username, password);
		t.sendMessage(msg, msg.getAllRecipients());
		t.close();
	}
	
	// properties
	private Session properties() {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtps.auth", "true");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtps.quitwait", "false");
		Session session = Session.getInstance(prop, null);
		return session;
	}

	// sauvegarde des emails pour historique
	public void saveEmail(Email email) {
		em.persist(email);
	}

}