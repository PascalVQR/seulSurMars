package com.bansheesoftware.seulsurmars.services;

import java.util.concurrent.atomic.AtomicInteger;

import com.bansheesoftware.seulsurmars.domain.*;

@org.springframework.stereotype.Service
public class CreerMondeService {
    private AtomicInteger increment = new AtomicInteger(0);


    public Monde creerMonde() {
        Monde monde = creerMonde(10, 10, 6, 6, 6);

        creerSalle(monde, 3,5, 6, 4, true, true);
        creerSalle(monde, 3,0, 6, 4, false, false);

        creerAscenseur(monde, "decors-1", 5, 6, 1, 6);

        monde.objets.add(new Objet("objet-1", 4, 6, Objet.GRAPHISME.sucre));
        monde.objets.add(new Objet("objet-2", 3, 6, Objet.GRAPHISME.bouteille));
        monde.objets.add(new Objet("objet-3", 2, 6, Objet.GRAPHISME.bouteille));
        monde.objets.add(new Objet("objet-4", 4, 1, Objet.GRAPHISME.oxygene));
        monde.objets.add(new Objet("objet-5", 6, 1, Objet.GRAPHISME.hydrogene));

        monde.decors.add(new Decor("decor-7", 4, 6, Decor.GRAPHISME.four));
        monde.decors.add(new Decor("decor-8", 0, 6, Decor.GRAPHISME.potager));
        monde.decors.add(new Decor("decor-9", 4, 1, Decor.GRAPHISME.ampouleAllumee));
        monde.decors.add(new Decor("decor-10", 1, 6, Decor.GRAPHISME.hydrazine));
        monde.decors.add(new Decor("decor-11", 6, 1, Decor.GRAPHISME.recycleurAir));

        return monde;
    }

    public Monde creerMonde(int largeur, int hauteur, int niveauSol, int positionX, int positionY) {

        Monde monde = new Monde(increment.getAndIncrement(), largeur, hauteur);
        monde.positionX = positionX;
        monde.positionY = positionY;
        for(int i=0; i<monde.largeur; i++) {
            monde.position(i, niveauSol, Position.POSITION_TYPE.SOL, Position.GRAPHISME.sol);
        }
        for(int i=0; i<monde.largeur; i++) {
            for(int j=0; j<niveauSol; j++) {
                monde.position(i, j, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.roche);
            }
        }

        return monde;
    }

    public Monde creerMonde1() {
        Monde monde = creerMonde(3, 1, 0, 1, 0);
        return monde;
    }

    public Monde creerMonde2() {
        Monde monde = creerMonde(3, 3, 1, 1, 1);
        creerAscenseur(monde, "decors1", 0,1,0,1);
        creerAscenseur(monde,"decors2", 2,1,1,2);
        return monde;
    }

    public Monde creerMonde3() {
        Monde monde = creerMonde(5, 5, 2, 2, 2);
        creerSalle(monde, 1, 1, 3, 3, true, true);
        return monde;
    }

    public void creerAscenseur(Monde monde, String id, int x, int y, int hauteurBas, int hauteurHaut) {
        monde.position(x, hauteurBas, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.carreau);
        monde.position(x, hauteurHaut, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.carreau);
        monde.decors.add(new Ascenseur(id, x, y, hauteurBas, hauteurHaut));
    }

    public void creerSalle(Monde monde, int x, int y, int largeur, int hauteur, boolean porteGauche, boolean porteDroite) {
        // colonne de gauche
        monde.position(x, y, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.mur6);
        monde.position(x, y+1, porteGauche?Position.POSITION_TYPE.SOL: Position.POSITION_TYPE.VIDE, porteGauche? Position.GRAPHISME.porte:Position.GRAPHISME.mur4);
        for(int j=y+2; j<y+hauteur-1; j++) {
            monde.position(x, j, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.mur4);
        }
        monde.position(x, y+hauteur-1, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.mur1);

        // milieu
        for(int i = x+1; i < x + largeur-1; i++) {
            monde.position(i, y, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.mur7);
            monde.position(i, y+1, Position.POSITION_TYPE.SOL, Position.GRAPHISME.dalle);
            for (int j = y + 2; j < y + hauteur - 1; j++) {
                monde.position(i, j, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.carreau);
            }
            monde.position(i, y + hauteur - 1, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.mur2);
        }

        // colonne de droite
        monde.position(x+largeur-1, y, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.mur8);
        monde.position(x+largeur-1, y+1, porteDroite?Position.POSITION_TYPE.SOL: Position.POSITION_TYPE.VIDE, porteDroite? Position.GRAPHISME.porte:Position.GRAPHISME.mur5);
        for(int j=y+2; j<y+hauteur-1; j++) {
            monde.position(x+largeur-1, j, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.mur5);
        }
        monde.position(x+largeur-1, y+hauteur-1, Position.POSITION_TYPE.VIDE, Position.GRAPHISME.mur3);

        monde.salles.add(new Salle(x, y, largeur, hauteur));
    }
    
    public Monde creerMondeTest() {
//        Monde monde = creerMonde(12, 12, 5, 2, 5);
    	Monde monde = new Monde(12, 12, 2, 5, 5);
        creerSalle(monde, 2, 4, 5, 3,  true, true);
        creerSalle(monde, 4, 1, 5, 3, false, false);
        creerAscenseur(monde, "decors1", 5, 5, 2, 5);
        monde.objets.add(new Objet("objet-2", 8, 5, Objet.GRAPHISME.bouteille));
        monde.objets.add(new Objet("objet-3", 9, 5, Objet.GRAPHISME.sucre));
        monde.decors.add(new Decor("decor-10", 3, 5, Decor.GRAPHISME.hydrazine));
        monde.decors.add(new Decor("decor-11", 7, 5, Decor.GRAPHISME.recycleurAir));
        monde.decors.add(new Decor("decor-12", 4, 5, Decor.GRAPHISME.potager));
        monde.decors.add(new Decor("decor-13", 10, 5, Decor.GRAPHISME.four));
        monde.decors.add(new Decor("decor-100", 6, 2, Decor.GRAPHISME.ampouleAllumee));
        return monde;
    }
    
    public Monde creerMonde4() {
    	Monde monde = new Monde(10, 5, 2, 2, 2);
    	creerSalle(monde, 1, 1, 8, 3, true, true);
        creerAscenseur(monde, "decor-0", 0, 2, 2, 3);
    	// Objets
    	monde.objets.add(new Objet("objet-1", 2, 2, Objet.GRAPHISME.bouteille));
    	monde.objets.add(new Objet("objet-2", 5, 2, Objet.GRAPHISME.sucre));
    	// Decors
        monde.decors.add(new Decor("decor-1", 6, 2, Decor.GRAPHISME.hydrazine));
        monde.decors.add(new Decor("decor-2", 7, 2, Decor.GRAPHISME.recycleurAir));
        monde.decors.add(new Decor("decor-3", 3, 2, Decor.GRAPHISME.potager));
        monde.decors.add(new Decor("decor-4", 4, 2, Decor.GRAPHISME.four));
        monde.decors.add(new Decor("decor-6", 8, 2, Decor.GRAPHISME.ampouleAllumee));
    	return monde;
    }
    
    public Monde creerMonde5() {
    	Monde monde = new Monde(7, 5, 2, 2, 2);
    	creerSalle(monde, 1, 1, 5, 3, true, true);
        creerAscenseur(monde, "decor-0", 0, 2, 2, 3);
    	return monde;
    }
}
