package fr.pizzeria.model;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Pizza {

	private final static Map<String, String> FORMAT = new HashMap<String, String>();
	private final static String AUTRE_FORMAT = "(%s)";

	static {
		FORMAT.put("code", "%s ->");
		FORMAT.put("nom", "%s ***");
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ToString
	private String code;
	@ToString(uppercase = true)
	private String nom;
	@ToString
	private BigDecimal prix;
	@ToString
	@Enumerated(EnumType.STRING)
	private CategoriePizza categorie;
	private String urlImage;

	@OneToMany(mappedBy = "id.pizza", orphanRemoval=true, cascade=CascadeType.ALL)
	private List<PizzaIngredients> ingredients = new ArrayList<>();

	private boolean actif = true;

	public Pizza() {
		// implémentation par défaut
	}

	public Pizza(String code, String nom, BigDecimal prix, CategoriePizza cat) {
		this();
		this.code = code;
		this.nom = nom;
		this.prix = prix;
		this.categorie = cat;
	}

	public Pizza(Integer id, String code, String nom, BigDecimal prix, CategoriePizza categorie, String urlImage) {
		this.code = code;
		this.nom = nom;
		this.prix = prix;
		this.categorie = categorie;
		this.urlImage = urlImage;
		this.id = id;
	}

	public Pizza(Integer id, String code, String nom, BigDecimal prix, CategoriePizza categorie, String urlImage, Boolean actif) {
		this.code = code;
		this.nom = nom;
		this.prix = prix;
		this.categorie = categorie;
		this.urlImage = urlImage;
		this.id = id;
		this.actif = actif;
	}
	
	public Pizza(Integer id, String code, String nom, BigDecimal prix, CategoriePizza categorie, String urlImage,List<PizzaIngredients> ingredients) {
		this.code = code;
		this.nom = nom;
		this.prix = prix;
		this.categorie = categorie;
		this.urlImage = urlImage;
		this.id = id;
		this.ingredients = ingredients;
	}

	public Pizza(Integer id, String code, String nom, BigDecimal prix, CategoriePizza categorie, String urlImage,List<PizzaIngredients> ingredients, Boolean actif) {
		this.code = code;
		this.nom = nom;
		this.prix = prix;
		this.categorie = categorie;
		this.urlImage = urlImage;
		this.id = id;
		this.ingredients = ingredients;
		this.actif = actif;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {

		this.actif = actif;
	}

	public void toggleActif() {
		this.setActif(!this.actif);
	}

	public void setIngredients(List<PizzaIngredients> nouveauxIngredients) {
		this.ingredients = nouveauxIngredients;
	}

	public List<PizzaIngredients> getIngredients() {
		return this.ingredients;
	}

	public void addIngredient(PizzaIngredients newIngredient) {
		this.ingredients.add(newIngredient);
	}

	public boolean deleteIngredient(Ingredient delIngredient) {
		return this.ingredients.remove(delIngredient);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Utiliser plutôt getNouveauPrix()
	 * 
	 * @return
	 */
	public BigDecimal getPrix() {
		return prix;
	}

	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}

	public CategoriePizza getCategorie() {
		return categorie;
	}

	public void setCategorie(CategoriePizza categorie) {
		this.categorie = categorie;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	public void resetIngredient(){
		this.ingredients.clear();
	}

	@Override
	public String toString() {
		return Arrays.asList(this.getClass().getDeclaredFields()).stream().filter(field -> field.getAnnotation(ToString.class) != null).map(getValeurDuChamp()).collect(Collectors.joining(" "));

	}

	private Function<? super Field, ? extends String> getValeurDuChamp() {
		return field -> {

			String resultat = "";
			try {
				resultat = field.getAnnotation(ToString.class).uppercase() ? field.get(this).toString().toUpperCase() : field.get(this).toString();
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException e1) {
				Logger.getAnonymousLogger().log(Level.SEVERE,"Exception Handle inside Model Pizza",e1);
			}

			String formatResultat = FORMAT.get(field.getName()) == null ? AUTRE_FORMAT : FORMAT.get(field.getName());

			return String.format(formatResultat, resultat);
		};
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(code).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Pizza rhs = (Pizza) obj;
		return new EqualsBuilder().append(code, rhs.code).append(id, rhs.id).isEquals();
	}

	public Pizza copy() {
		Pizza pizza = new Pizza(this.getId(), this.getCode(), this.getNom(), this.getPrix(), this.getCategorie(), this.getUrlImage());
		// TODO A adapter :)
		for (PizzaIngredients ing : this.ingredients) {
			pizza.ingredients.add(ing);
		}
		return pizza;
	}
}
