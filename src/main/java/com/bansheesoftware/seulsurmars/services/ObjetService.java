package com.bansheesoftware.seulsurmars.services;

import java.util.Arrays;

import com.bansheesoftware.seulsurmars.domain.Monde;
import com.bansheesoftware.seulsurmars.domain.Objet;
import com.bansheesoftware.seulsurmars.domain.Objet.GRAPHISME;

@org.springframework.stereotype.Service
public class ObjetService {

	static private GRAPHISME[][] pairs = {
			{GRAPHISME.oxygene, GRAPHISME.hydrogene},
			{GRAPHISME.oxygene, GRAPHISME.sucre},
			{GRAPHISME.electrique, GRAPHISME.inflammable},
			{GRAPHISME.electrique, GRAPHISME.explosif}
	};
    
    static public void handleItem(Monde monde) {
    	int id = getItemIndex(monde);
    	
    	if ((id >= 0 || id < monde.objets.size()) && monde.inventaire==null) {
    		pickUpItem(monde, id);
    		
    	} else if (id == -1 && monde.inventaire!=null && MondeService.positionIsSol(monde)) {
    		dropItem(monde);
    		
    	} else if ((id >= 0 || id < monde.objets.size()) && monde.inventaire!=null) {
    		combineItem(monde);
    	}
    }
    
    static private int getItemIndex(Monde monde) {
    	int id = getItemIndex(monde, monde.positionX, monde.positionY);
    	return id;
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
    	monde.inventaire = monde.objets.get(id);//.clone();
    	monde.objets.remove(id);
    }
    
    static public void dropItem(Monde monde) {
    	Objet item = monde.inventaire;//.clone();
    	monde.inventaire = null;
    	item.x = monde.positionX;
    	item.y = monde.positionY;
    	monde.objets.add(item);
    }
	
	static public Objet createObjet(int x, int y, GRAPHISME graph) {
		Objet obj = new Objet(MondeService.generateId(), x, y, graph);
		return obj;
	}
    
    static private void combineItem(Monde monde) {
    	int pairId = getPairId(monde); 
    	if (pairId > -1) {
			switch (pairId) {
			case 0:
				updateItem(monde, GRAPHISME.inflammable);
				break;
			case 1:
				updateItem(monde, GRAPHISME.explosif);
				break;

			case 2:
				if (monde.inventaire.graphisme == GRAPHISME.electrique) {
					updateItem(monde, GRAPHISME.decomptefeu, 3);	
				}
				break;
			case 3:
				if (monde.inventaire.graphisme == GRAPHISME.electrique) {
					updateItem(monde, GRAPHISME.decompteexplosion, 3);	
				}
				break;
			}
    	}
    }
    
    static private int getPairId(Monde monde) {
    	int objId = getItemIndex(monde, monde.positionX, monde.positionY);
    	GRAPHISME inv = monde.inventaire.graphisme;
    	GRAPHISME obj = monde.objets.get(objId).graphisme;
    	
		GRAPHISME[] test1 = {inv, obj};
		GRAPHISME[] test2 = {obj, inv};
		
		int pairId = 0;
    	// Iterate over the list of pairs to look for matching items
    	for (GRAPHISME[] pair : pairs) {
			if (Arrays.compare(pair, test1) == 0 || Arrays.compare(pair, test2) == 0) {
				return pairId;
    		}
			pairId++;
    	}
    	return -1;
    }

    static private void updateItem(Monde monde, GRAPHISME newGraph) {
    	int objId = getItemIndex(monde);
    	updateItem(monde, objId, newGraph, 0);
    }

    static private void updateItem(Monde monde, GRAPHISME newGraph, int animation) {
    	int objId = getItemIndex(monde);
    	updateItem(monde, objId, newGraph, animation);
    }
    
    static private void updateItem(Monde monde, int objId, GRAPHISME newGraph, int animation) {
    	Objet target = monde.objets.get(objId); 
    	target.graphisme = newGraph;
    	target.animation = animation;
    	
		monde.inventaire = null;
    }
}
