package fr.pizzeria.spring.web.resource;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import fr.pizzeria.model.Client;
import fr.pizzeria.spring.web.repository.IClientRepository;

/**
 * Ressource Client.
 */
@RestController
@RequestMapping("/clients")
public class ClientResource {

	@Autowired
	private IClientRepository clientDao;

	@RequestMapping(method = RequestMethod.POST)
	public void saveClients(@RequestBody Client newClient, HttpServletResponse response) {
		if (isBlank(newClient.getNom()) || isBlank(newClient.getEmail()) || isBlank(newClient.getPassword())) {
			response.setStatus(400);
		} else {
		newClient.setPasswordEncrypt(newClient.getPassword());
			clientDao.save(newClient);
			response.setStatus(200);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Client> findAllClients() {
		return clientDao.findAll();
	}

	protected boolean isBlank(String param) {
		return param == null || param.isEmpty();
	}

	@RequestMapping(value = "/connection", method = RequestMethod.POST)
	public Client findClients(@RequestBody Client client, HttpServletRequest req, HttpServletResponse response)
			throws IOException {
		Client clientTrouve = null;
		if (isBlank(client.getEmail()) || isBlank(client.getPassword())) {
			response.setStatus(400);
		} else {
			clientTrouve = clientDao.findByEmail(client.getEmail());
			if (client.encodage(client.getPassword()).equals(clientTrouve.getPassword())) {
				//clientTrouve.setPassword(null);
				clientTrouve.setNonePassword();
				response.setStatus(200);
			} else {
				response.setStatus(400);
				clientTrouve = null;
			}
		}
		return clientTrouve;
	}
}
