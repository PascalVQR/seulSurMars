package com.bansheesoftware.seulsurmars.services;

import com.bansheesoftware.seulsurmars.domain.*;
import com.bansheesoftware.seulsurmars.domain.Monde.Status;
import com.bansheesoftware.seulsurmars.domain.Objet.GRAPHISME;

@org.springframework.stereotype.Service
public class TimerService {

	enum TYPE {NOURRITURE, OXYGENE};
	
	public void handleTime(Monde monde, String timer) {
		switch (timer) {
			case "oxygene":
				if (MondeService.checkInventaireType(monde, GRAPHISME.oxygene)) {
					monde.inventaire = null;
					monde.timerOxygene = 30;
				} else {
					monde.status = Status.gameOver;
				}
				break;
				
			case "nourriture":
				if (MondeService.checkInventaireType(monde, GRAPHISME.cupcake) ||
						MondeService.checkInventaireType(monde, GRAPHISME.tomate)) {
					monde.inventaire = null;
					monde.timerNourriture = 120;
				} else {
					monde.status = Status.gameOver;
				}
				break;
				
			default:
				itemCountDown(monde, timer);
				break;
		}
	}
	
	private void itemCountDown(Monde monde, String timer) {
		for (int i = 0; i < monde.objets.size(); i++) {
			Objet obj = monde.objets.get(i);//.clone();
			if (obj.id.equals(timer)) {
				obj.animation = 0;
				
				switch (obj.graphisme) {
				
				case tomatequipousse:
					obj.graphisme = GRAPHISME.tomate;
					break;
					
				case cupcakequicuit:
					obj.graphisme = GRAPHISME.cupcake;
					break;
				
				case decompteexplosion:
					obj.graphisme = GRAPHISME.explosion;
					obj.animation = 1;
					break;
					
				case explosion:
					obj.animation = 0;
					int index = MondeService.getItemIndexById(monde, obj.id);
					monde.objets.remove(index);
					break;
				
				case decomptefeu:
					obj.graphisme = GRAPHISME.feu;
					obj.animation = 1;
					break;
				
				case feu:
					obj.graphisme = GRAPHISME.bouteille;
					obj.animation = 0;
					break;
					
				default:
					break;
				}
			}
		}
		
	}
}
