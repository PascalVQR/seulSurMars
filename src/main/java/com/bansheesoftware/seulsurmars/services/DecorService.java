package com.bansheesoftware.seulsurmars.services;

import com.bansheesoftware.seulsurmars.domain.Ascenseur;
import com.bansheesoftware.seulsurmars.domain.Decor;
import com.bansheesoftware.seulsurmars.domain.Monde;
import com.bansheesoftware.seulsurmars.domain.Objet.GRAPHISME;

import com.bansheesoftware.seulsurmars.domain.*;

@org.springframework.stereotype.Service
public class DecorService {
	
	static public void useDecor(Monde monde) {
		
		Decor decor = getDecorTypeByCoord(monde);
		if (decor == null) {
			return;
//			return monde;
		}
		switch (decor.graphisme) {
			case ascenseur:
				Ascenseur ascenseur = (Ascenseur) decor;
				monde.positionY = ascenseur.move();
				break;
				
			case hydrazine:
				createObjetIfPossible(monde, GRAPHISME.hydrogene);
				break;
			
			case recycleurAir:
				createObjetIfPossible(monde, GRAPHISME.oxygene);
				break;
			
			case ampouleAllumee:
				if (createObjetIfPossible(monde, GRAPHISME.electrique)) {
					decor.graphisme = Decor.GRAPHISME.ampouleEteinte;
				}
				break;
			
			case ampouleEteinte:
				if (monde.inventaire.graphisme == GRAPHISME.electrique) {
					decor.graphisme = Decor.GRAPHISME.ampouleAllumee;
					monde.inventaire = null;
				}
				break;
				
			case potager:
				processItem(monde, GRAPHISME.bouteille, GRAPHISME.tomatequipousse, 10);
				break;
			
			case four:
				processItem(monde, GRAPHISME.sucre, GRAPHISME.cupcakequicuit, 3);
				break;
			
			default:
				break;
		}
//		return monde;
	}
	
	static private Decor getDecorTypeByCoord(Monde monde) {
		int x = monde.positionX;
		int y = monde.positionY;
		Decor decor = null;
		for (Decor d : monde.decors) {
			if (d.x == x && d.y == y) {
				decor = d;
				break;
			}
		}
		return decor;
	}
	
	static private boolean createObjetIfPossible(Monde monde, GRAPHISME graph) {
		if (MondeService.positionIsEmpty(monde)) {
			Objet obj = ObjetService.createObjet(monde.positionX, monde.positionY, graph);
			monde.objets.add(obj);
			return true;
		}
		return false;
	}
	
	static private void processItem(Monde monde, GRAPHISME input, GRAPHISME output, int duration) {
		if (MondeService.positionIsEmpty(monde) && MondeService.checkInventaireType(monde, input)) {
			monde.inventaire = null;
			Objet newObj = ObjetService.createObjet(monde.positionX, monde.positionY, output);
			newObj.animation = duration;
			monde.objets.add(newObj);
		}
	}
}
