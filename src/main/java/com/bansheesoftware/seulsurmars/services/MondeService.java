package com.bansheesoftware.seulsurmars.services;

import java.time.Instant;

import com.bansheesoftware.seulsurmars.domain.*;

@org.springframework.stereotype.Service
public class MondeService {
	
	static public String generateId() {
		// Génère un nombre entier positif à partir du temps
		int n = Math.abs(Instant.now().hashCode());
		String s = Integer.toString(n);
		return "objet-"+s;
	}

	static public boolean positionIsWalkable(Monde monde, int x, int y) {
		if (positionIsSol(monde, x, y) || getAscenseurIndex(monde, x, y) > -1 ) {
			return true;
		}
		return false;
	}
	
	static public boolean positionIsSol(Monde monde) {
		boolean isSol = positionIsSol(monde, monde.positionX, monde.positionY);
		return isSol;
	}
	
	
	static public boolean positionIsSol(Monde monde, int x, int y) {
		if (x >= 0 && x < monde.getLargeur()) {
        	Position cible = monde.positions.get(x).get(y);
        	if (cible.type == Position.POSITION_TYPE.SOL) {
        		return true;
        	}
		}
        return false;
	}
    
	static public int getAscenseurIndex(Monde monde, int x, int y) {
    	for (int i = 0; i < monde.decors.size(); i++) {
    		Decor decor = monde.decors.get(i);
    		if (decor.x == x && decor.y == y) {
    			if (decor.graphisme == Decor.GRAPHISME.ascenseur) {
    				return i;
    			}
    			break;
    		}
    	}
    	return -1;
    }
    
    static public boolean positionIsEmpty(Monde monde) {
    	for (Objet obj : monde.objets) {
    		if (obj.x == monde.positionX && obj.y == monde.positionY) {
    			return false;
    		}
    	}
    	return true;
    }
    
    static public boolean checkInventaireType(Monde monde, Objet.GRAPHISME graph) {
    	if (monde.inventaire != null && monde.inventaire.graphisme == graph) {
    		return true;
    	}
    	return false;
    }
    
    static public void moveHero(Monde monde, int x, int y) {
    	if (positionIsWalkable(monde, x, y)) {
			monde.positionX = x;
			monde.positionY = y;
        	for (Salle salle : monde.salles) {
        		if (numberInRange(x, salle.x, salle.x+salle.largeur-1)) {
            		if (numberInRange(y, salle.y, salle.y+salle.hauteur-1)) {
            			monde.timerOxygene = 0;
            			return;
            		}
        		}
        	}
        	monde.timerOxygene = 30;
    	}
    }
	
	static public int getItemIndexById(Monde monde, String itemId) {
		int index = -1;
		for(int i = 0; i < monde.objets.size(); i++) {
			Objet item = monde.objets.get(i);
			if (item.id.equals(itemId)) {
				return i;
			}
		}
		return index;
	}
	
	static private boolean numberInRange(int num, int min, int max) {
		return num >= min && num <= max;
	}
}
