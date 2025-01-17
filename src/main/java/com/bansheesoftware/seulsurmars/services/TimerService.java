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
				for (int i = 0; i < monde.objets.size(); i++) {
					Objet newObj = monde.objets.get(i).clone();
					if (newObj.id.equals(timer)) {
						if (newObj.graphisme == GRAPHISME.tomatequipousse) {
							newObj.graphisme = GRAPHISME.tomate;
						} else if (newObj.graphisme == GRAPHISME.cupcakequicuit) {
							newObj.graphisme = GRAPHISME.cupcake;
						}
						monde.objets.remove(i);
						newObj.animation = 0;
						monde.objets.add(newObj);
					}
				}
				break;
		}
	}
}
