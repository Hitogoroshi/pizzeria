
package fr.pizzeria.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LivreurTest {

	@Test
	public void creerLivreur () {
		Livreur l = new Livreur("DUPONT", "Jean");
		assertEquals("Jean", l.getPrenom());
		assertEquals("DUPONT", l.getNom());
	}
	
	@Test
	public void genererCode_casNominal () {
		Livreur l = new Livreur("DUPONT", "Jean");
		assertEquals("DUPJEA", l.genererCodeBrut());
	}
	
	@Test
	public void genererCode_tropCourt () {
		Livreur l = new Livreur("WA", "Xi");
		assertEquals("WA-XI-", l.genererCodeBrut());
		Livreur l2 = new Livreur("A", "H");
		assertEquals("A--H--", l2.genererCodeBrut());
		Livreur l3 = new Livreur("Ba", "H");
		assertEquals("BA-H--", l3.genererCodeBrut());
		Livreur l4 = new Livreur("bA", "h");
		assertEquals("BA-H--", l4.genererCodeBrut());
	}
	
	@Test
	public void genererCode_nomAvecAccents () {
		Livreur l1 = new Livreur("COËN", "Édouard");
		assertEquals("COEEDO", l1.genererCodeBrut());
		Livreur l2 = new Livreur("RÖSTI", "Çamuwel");
		assertEquals("ROSCAM", l2.genererCodeBrut());
	}
	
	@Test
	public void genererCode_nomAvecCaracteresSpeciaux () {
		Livreur l1 = new Livreur("D'ÉLYSÉE", "Martin");
		assertEquals("DELMAR", l1.genererCodeBrut());
		Livreur l2 = new Livreur("''dF'aaa", "sldfk");
		assertEquals("DFASLD", l2.genererCodeBrut());
	}
	
	@Test
	public void genererCode_casVide () {
		Livreur l1 = new Livreur("", "");
		assertEquals("DEFAUT", l1.genererCodeBrut());
		Livreur l2 = new Livreur("'''-'''-''", "..'.''**£");
		assertEquals("DEFAUT", l2.genererCodeBrut());
	}
	
	
}
