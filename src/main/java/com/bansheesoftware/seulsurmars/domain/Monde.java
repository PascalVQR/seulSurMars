package com.bansheesoftware.seulsurmars.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Monde {

    private final int id;

    public int largeur;
    public int hauteur;

    public List<List<Position>> positions = new ArrayList<>();

    public int positionX = 2;
    public int positionY = 2;

    public List<Objet> objets = new ArrayList<>();
    public List<Decor> decors = new ArrayList<>();

    public List<Salle> salles = new ArrayList<>();

    public Objet inventaire;

    public int timerOxygene = 0;
    public int timerNourriture = 120;

    public Status status = Status.onGoing;

    private int increment = 100;
    public int increment() {
        return ++increment;
    }

    public enum Status {
        onGoing, gameOver, victory
    }


    public Monde(int id, int largeur, int hauteur) {
        this.id = id;
        this.largeur = largeur;
        this.hauteur = hauteur;

        positions = new ArrayList<>(largeur);
        for(int i=0; i<largeur; i++) {
            positions.add(new ArrayList<>(hauteur));
            for(int j=0; j<hauteur; j++) {
                positions.get(i).add(new Position(i,j, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.vide));
            }
        }
    }

    public Monde position(int x, int y, Position.POSITION_TYPE type, Position.GRAPHISME graphisme) {
        positions.get(x).get(y).type = type;
        positions.get(x).get(y).graphisme = graphisme;
        return this;
    }

	public int getLargeur() {
		return this.largeur;
	}

    public int getId() {
        return id;
    }

    public Monde clone() {
        Monde clone = new Monde(this.id, this.largeur, this.hauteur);

        for(int i = 0; i<this.positions.size(); i++) {
            for(int j = 0; j<this.positions.get(i).size(); j++) {
                clone.positions.get(i).get(j).type = this.positions.get(i).get(j).type;
                clone.positions.get(i).get(j).graphisme = this.positions.get(i).get(j).graphisme;
            }
        }
        clone.positionX = this.positionX;
        clone.positionY = this.positionY;

        for(int i = 0; i<this.objets.size(); i++) {
            clone.objets.add(this.objets.get(i).clone());
        }
        for(int i = 0; i<this.decors.size(); i++) {
            clone.decors.add(this.decors.get(i).clone());
        }
        for(int i = 0; i<this.salles.size(); i++) {
            clone.salles.add(this.salles.get(i).clone());
        }

        if(this.inventaire != null) {
            clone.inventaire = this.inventaire.clone();
        }

        clone.increment = this.increment;

        return clone;

    }
    
    
    public Monde(int largeur, int hauteur, int posX, int posY, int hauteurSol) {
    	Instant inst = Instant.now();
    	this.id = inst.hashCode();
    	this.largeur = largeur;
    	this.hauteur = hauteur;
    	this.positionX = posX;
    	this.positionY = posY;
    	
    	// Boucler sur chaque ligne
    	for(int x = 0; x < largeur; x++) {

        	// Problème initialisation
    		List<Position> colonne = new ArrayList<Position>(hauteur);
    		// Boucler sur chaque colonne
    		for(int y = 0; y < hauteur; y++) {
    			Position pos = new Position(x, y, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.vide);
    			// Mettre du sol à la hauteur désignée
    			if (y==hauteurSol) {
    				pos.type = Position.POSITION_TYPE.SOL;
    				pos.graphisme = Position.GRAPHISME.sol;
    			}
    			// Les cases sous le sol sont affichées comme de la roche
    			if (y < hauteurSol) {
    				pos.graphisme = Position.GRAPHISME.roche;
    			}
    			// Ajouter chaque position à la ligne en cours
    			colonne.add(pos);
    		
    		}
    		// Ajouter chaque nouvelle ligne (liste de positions) à la liste principale
    		this.positions.add(colonne);
    	}
    }
}