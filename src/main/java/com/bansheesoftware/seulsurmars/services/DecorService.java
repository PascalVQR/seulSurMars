package com.bansheesoftware.seulsurmars.services;

import java.time.Instant;

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
				
			case potager:
//				if (monde.positionIsEmpty() && monde.checkInventaireType(Objet.GRAPHISME.bouteille)) {
//					monde.inventaire = null;
//					Objet tomate = createObjet(x, y, Objet.GRAPHISME.tomatequipousse);
//					tomate.animation = 10;
//					monde.objets.add(tomate);
//				}
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
	
	static private String generateId() {
		// Génère un nombre entier positif à partir du temps
		int n = Math.abs(Instant.now().hashCode());
		String s = Integer.toString(n);
		return "objet-"+s;
	}
	
	static private Objet createObjet(int x, int y, GRAPHISME graph) {
		Objet obj = new Objet(generateId(), x, y, graph);
		return obj;
	}
	
	static private void createObjetIfPossible(Monde monde, GRAPHISME graph) {
		if (MondeService.positionIsEmpty(monde)) {
			Objet obj = createObjet(monde.positionX, monde.positionY, graph);
			monde.objets.add(obj);
		}
	}
	
	static private void processItem(Monde monde, GRAPHISME input, GRAPHISME output, int duration) {
		if (MondeService.positionIsEmpty(monde) && MondeService.checkInventaireType(monde, input)) {
			monde.inventaire = null;
			Objet newObj = createObjet(monde.positionX, monde.positionY, output);
			newObj.animation = duration;
			monde.objets.add(newObj);
		}
	}
	
//	private Monde useItem(Monde monde, Objet.GRAPHISME graph) {
//		if (monde.inventaire.graphisme == graph) {
//			monde.inventaire = null;
//			Objet obj = null;
//			switch (graph) {
//				case bouteille:
//					obj = new Objet(generateId(), monde.positionX, monde.positionY, Objet.GRAPHISME.tomatequipousse);
//					obj.animation = 10;
//					break;
//				case sucre:
//					obj = new Objet(generateId(), monde.positionX, monde.positionY, graph);
//					break;
//			default:
//				break;
//			}
//			monde.objets.add(obj);
//		}
//		return monde;
//	}
}
