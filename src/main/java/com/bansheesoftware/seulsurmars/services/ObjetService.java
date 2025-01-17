package com.bansheesoftware.seulsurmars.services;

import com.bansheesoftware.seulsurmars.domain.Monde;
import com.bansheesoftware.seulsurmars.domain.Objet;

@org.springframework.stereotype.Service
public class ObjetService {

    
    static public void handleItem(Monde monde) {
    	int x = monde.positionX;
    	int y = monde.positionY;
    	int id = getItemIndex(monde, x, y);
    	if ((id >= 0 || id < monde.objets.size()) && monde.inventaire==null) {
    		pickUpItem(monde, id);
    	} else if (id == -1 && monde.inventaire!=null && MondeService.positionIsSol(monde, x, y)) {
    		dropItem(monde, x, y);
    	}
//    	return monde;
    }
    
    static private int getItemIndex(Monde monde, int x, int y) {
    	for (int i = 0; i < monde.objets.size(); i++) {
    		Objet item = monde.objets.get(i);
    		if (item.x == x && item.y == y) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    static private void pickUpItem(Monde monde, int id) {
    	// Annuler si index invalide
    	if (id < 0 || id >= monde.objets.size()) {
    		return;
    	// Annuler si l'objet est en cours de changement
    	} else if (monde.objets.get(id).animation != 0) {
    		return;
    	}
    	monde.inventaire = monde.objets.get(id).clone();
    	monde.objets.remove(id);
    }
    
    static public void dropItem(Monde monde, int x, int y) {
    	Objet item = monde.inventaire.clone();
    	monde.inventaire = null;
    	item.x = x;
    	item.y = y;
    	monde.objets.add(item);
    }
}
