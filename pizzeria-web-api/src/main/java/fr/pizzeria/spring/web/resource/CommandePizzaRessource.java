package fr.pizzeria.spring.web.resource;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import fr.pizzeria.model.CommandePizza;
import fr.pizzeria.spring.web.repository.ICommandePizzaRepository;


/**
 * Ressource Commande Pizza.
 */
@RestController
@RequestMapping("/commandepizza")
public class CommandePizzaRessource {

		@Autowired
		private ICommandePizzaRepository cmdPizzDao;

		@RequestMapping(method = RequestMethod.POST)
		public void saveCommandePizza(@RequestBody CommandePizza newCmdPizza, HttpServletResponse response) {
			System.out.println("fdsfdfsfd");
		}
		
		@RequestMapping(method = RequestMethod.GET)
		public List<CommandePizza> findAllCommandesPizza() {
			return cmdPizzDao.findAll();
		}
}
