package com.bansheesoftware.seulsurmars.services;

//import org.springframework.beans.factory.annotation.Autowired;

import com.bansheesoftware.seulsurmars.domain.Monde;

@org.springframework.stereotype.Service
public class InputService {
	
//	@Autowired
//	private ObjetService objServ;
//	@Autowired
//	private DecorService decServ;
	
	public InputService() {
	}
	
	public void handleInput(Monde monde, String key) {
        if (key == null) {
        	return;
//            return monde;
        }
        int posY = monde.positionY;
        int posX = monde.positionX;
//        Position cible;
        
        switch (key) {
	        // Aller à gauche
	        case "ArrowLeft":
//	        	if (MondeService.positionIsWalkable(monde, posX-1, posY)) {
//	        		monde.positionX--;
//	        	}
	        	MondeService.moveHero(monde, posX-1, posY);
	        	break;
	        	
	        // Aller à droite
	        case "ArrowRight":
//	        	if (MondeService.positionIsWalkable(monde, posX+1, posY)) {
//	        		monde.positionX++;
//	    		}
	        	MondeService.moveHero(monde, posX+1, posY);
	        	break;
	        	
	        // Activer un élément du décor
	        case "Space":
	        	DecorService.useDecor(monde);
	        	break;
	        
	        // Prendre / déposer un objet
	        case "Enter":
	        	ObjetService.handleItem(monde);
	        	break;
        }
        
//        return monde;
        
//        GameService.Touche touche;
//        switch (key) {
//            case "ArrowLeft":
//                touche = GameService.Touche.LEFT;
//                break;
//            case "ArrowRight":
//                touche = GameService.Touche.RIGHT;
//                break;
//            case "Space":
//                touche = GameService.Touche.DECOR;
//                break;
//            case "Enter":
//                touche = GameService.Touche.OBJET;
//                break;
//            default:
//                return monde;
//        }
	}
}
