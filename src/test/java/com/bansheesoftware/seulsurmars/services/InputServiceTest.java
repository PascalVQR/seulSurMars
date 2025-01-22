package com.bansheesoftware.seulsurmars.services;

import com.bansheesoftware.seulsurmars.domain.Decor;
import com.bansheesoftware.seulsurmars.domain.Monde;
import com.bansheesoftware.seulsurmars.domain.Objet;
import com.bansheesoftware.seulsurmars.domain.Objet.GRAPHISME;
import com.bansheesoftware.seulsurmars.domain.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputServiceTest {

    @Test
    public void testDeplacementGauche() {
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde1(); // le monde 1 est composé uniquement de 3 cases SOL alignées, et l'astronaute au milieu

        InputService inputService = new InputService();
        inputService.handleInput(monde, "ArrowLeft");

        // après déplacement à gauche, je vérifie que la position X est 0
        Assertions.assertEquals(0, monde.positionX);
    }

    @Test
    public void testDeplacementDroite() {
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde1(); // le monde 1 est composé uniquement de 3 cases SOL alignées, et l'astronaute au milieu

        InputService inputService = new InputService();
        inputService.handleInput(monde, "ArrowRight");

        // après déplacement à droite, je vérifie que la position X est 2
        Assertions.assertEquals(2, monde.positionX);
    }

    @Test
    public void testDeplacementGaucheGauche() {
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde1(); // le monde 1 est composé uniquement de 3 cases SOL alignées, et l'astronaute au milieu

        InputService inputService = new InputService();
        inputService.handleInput(monde, "ArrowLeft");
        Assertions.assertEquals(0, monde.positionX);


        // le déplacement est possible une seule fois à gauche, parce que sinon on atteint la limite
        inputService.handleInput(monde, "ArrowLeft");
        Assertions.assertEquals(0, monde.positionX);
    }

    @Test
    public void testDeplacementDroiteDroite() {
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde1(); // le monde 1 est composé uniquement de 3 cases SOL alignées, et l'astronaute au milieu

        InputService inputService = new InputService();
        inputService.handleInput(monde, "ArrowRight");
        Assertions.assertEquals(2, monde.positionX);

        // le déplacement est possible une seule fois à gauche, parce que sinon on atteint la limite
        inputService.handleInput(monde, "ArrowRight");
        Assertions.assertEquals(2, monde.positionX);
    }

    @Test
    public void testDeplacementGaucheVide() {
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde1(); // le monde 1 est composé uniquement de 3 cases SOL alignées, et l'astronaute au milieu
        monde.positions.get(0).get(0).type = Position.POSITION_TYPE.VIDE; // je met la case à gauche VIDE

        InputService inputService = new InputService();
        inputService.handleInput(monde, "ArrowLeft");

        // le déplacement est impossible, parce que sinon c'est le VIDE
        Assertions.assertEquals(1, monde.positionX);
    }

    @Test
    public void testDeplacementDroiteVide() {
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde1(); // le monde 1 est composé uniquement de 3 cases SOL alignées, et l'astronaute au milieu
        monde.positions.get(2).get(0).type = Position.POSITION_TYPE.VIDE; // je met la case à droite VIDE

        InputService inputService = new InputService();
        inputService.handleInput(monde, "ArrowRight");

        // le déplacement est impossible, parce que sinon c'est le VIDE
        Assertions.assertEquals(1, monde.positionX);
    }

    @Test
    public void testDeplacementAscenseur() {
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde2(); // le monde 2 est une monde de 3 par 3, la position initilae est au milieu
        // et il y a un ascenseur à gauche pour descendre
        // et il y a un ascenseur à droite pour monter
        InputService inputService = new InputService();

        // etape 1: je vais à gauche (sur l'ascenseur pour descendre)
        inputService.handleInput(monde, "ArrowLeft");
        Assertions.assertEquals(0, monde.positionX);
        Assertions.assertEquals(1, monde.positionY);

        // etape 2 : j'utilise l'asenseur pour descendre
        inputService.handleInput(monde, "Space");
        Assertions.assertEquals(0, monde.positionX);
        Assertions.assertEquals(0, monde.positionY);


        // etape 2 : j'utilise l'asenseur pour remonter
        inputService.handleInput(monde, "Space");
        Assertions.assertEquals(0, monde.positionX);
        Assertions.assertEquals(1, monde.positionY);
    }

    // TODO : écrire un test avec l'asenseur de droite
    // tu peux également modifier "creerMondeService", pour ajouter des méthodes de création de monde pour les tests
    // tu peux créer tout un tas de tests, pour vérifier les différents fonctionnalités que tu as codées
    
    // START OF MY TESTS
    
    @Test
    public void testDeplacementAscenseur2()  {
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde2();

        InputService inputService = new InputService();

        // etape 1: je vais à droite (sur l'ascenseur qui monte)
        inputService.handleInput(monde, "ArrowRight");
        Assertions.assertEquals(2, monde.positionX);
        Assertions.assertEquals(1, monde.positionY);

        // etape 2 : j'utilise l'asenseur pour moneter
        inputService.handleInput(monde, "Space");
        Assertions.assertEquals(2, monde.positionX);
        Assertions.assertEquals(2, monde.positionY);

        // etape 2 : j'utilise l'asenseur pour redescendre
        inputService.handleInput(monde, "Space");
        Assertions.assertEquals(2, monde.positionX);
        Assertions.assertEquals(1, monde.positionY);
    }

    @Test
    public void testRamasserBouteille() {
        CreerMondeService creerMondeService = new CreerMondeService();
        // le personnage commence sur une bouteille dans le monde 4
        Monde monde = creerMondeService.creerMonde4();
        InputService inputService = new InputService();
        
        // Ramasser la bouteille aux pieds du personnage, il ne reste qu'un objet posé dans le monde
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(Objet.GRAPHISME.bouteille, monde.inventaire.graphisme);
        Assertions.assertEquals(1, monde.objets.size());
    }

    @Test
    public void testPoserBouteille() {
        CreerMondeService creerMondeService = new CreerMondeService();
        // le personnage commence sur une bouteille dans le monde 4
        Monde monde = creerMondeService.creerMonde4();
        InputService inputService = new InputService();
        
        // Ramasser la bouteille et la reposer de suite
        inputService.handleInput(monde, "Enter");
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(null, monde.inventaire);
        Assertions.assertEquals(2, monde.objets.size());
        Assertions.assertEquals(Objet.GRAPHISME.bouteille, monde.objets.get(1).graphisme);
    }

    @Test
    public void testSuperoserObjets() {//Essayer de poser un objet sur un autre
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde4();
        InputService inputService = new InputService();
        
        // Ramasser la bouteille et aller jusqu'au sucre
        inputService.handleInput(monde, "Enter");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        // Essayer de poser la bouteille sur le sucre (ne doit rien faire)
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(Objet.GRAPHISME.bouteille, monde.inventaire.graphisme);
        Assertions.assertEquals(Objet.GRAPHISME.sucre, monde.objets.get(0).graphisme);
    }
    
    @Test
    public void testObjetAscenseur() {// Essayer de poser un objet sur un ascenseur
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde4();
        InputService inputService = new InputService();
        
        //Ramasser la bouteille et aller jusqu'à l'ascenseur
        inputService.handleInput(monde, "Enter");
        inputService.handleInput(monde, "ArrowLeft");
        inputService.handleInput(monde, "ArrowLeft");
        // Essayer de poser l'objet
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(Objet.GRAPHISME.bouteille, monde.inventaire.graphisme);
        Assertions.assertEquals(true, MondeService.positionIsEmpty(monde));
    }
    
    @Test
    public void testCreerHydrogene() {// touche decor sur case hydrazine
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde4();
        InputService inputService = new InputService();
        
        // Aller jusqu'à l'hydrazine et l'activer
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "Space");

        // Le dernier objet posé dans le monde est de l'hydrogène, aux coordonnées du personnage
        Objet dernierObjet = monde.objets.getLast();
        Assertions.assertEquals(Objet.GRAPHISME.hydrogene, dernierObjet.graphisme);
        Assertions.assertEquals(monde.positionX, dernierObjet.x);
        Assertions.assertEquals(monde.positionY, dernierObjet.y);
    }
    
    @Test
    public void testCreerOxygene() {// touche decor sur case Recycleur d'air
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde4();
        InputService inputService = new InputService();
        
        // Aller jusqu'au recycleur et l'activer
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "Space");

        // Le dernier objet posé dans le monde est de l'oxygène, aux coordonnées du personnage
        Objet dernierObjet = monde.objets.getLast();
        Assertions.assertEquals(Objet.GRAPHISME.oxygene, dernierObjet.graphisme);
        Assertions.assertEquals(monde.positionX, dernierObjet.x);
        Assertions.assertEquals(monde.positionY, dernierObjet.y);
    }
    
    @Test
    public void testCreerObjetCaseOccupee() {// poser un objet devant l'hydrazine
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde4();
        InputService inputService = new InputService();
        // Plusieurs de ces actions ont déjà été testées, mais je veux être sûr que tout se passe bien
        // Ramasser la bouteille
        inputService.handleInput(monde,  "Enter");
        Assertions.assertEquals(Objet.GRAPHISME.bouteille, monde.inventaire.graphisme);
        
        // Aller jusqu'à l'hydrazine
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        Assertions.assertEquals(6, monde.positionX);
        
        // Poser la bouteille et activer l'hydrazine
        inputService.handleInput(monde,  "Enter");
        inputService.handleInput(monde, "Space");

        // L'objet aux pieds du joueur doit être une bouteille d'eau, pas de l'hydrogene
        Objet dernierObjet = monde.objets.getLast();
        Assertions.assertEquals(null, monde.inventaire);
        Assertions.assertEquals(Objet.GRAPHISME.bouteille, dernierObjet.graphisme);
        Assertions.assertEquals(monde.positionX, dernierObjet.x);
        Assertions.assertEquals(monde.positionY, dernierObjet.y);
    }
    
    @Test
    public void testArroserTomate() {// utiliser la bouteille sur le potager
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde4();
        InputService inputService = new InputService();
        
        // Ramasser la bouteille et arroser la tomate
        inputService.handleInput(monde,  "Enter");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "Space");

        // Inventaire vide + tomate qui pousse
        Objet dernierObjet = monde.objets.getLast();
        Assertions.assertEquals(null, monde.inventaire);
        Assertions.assertEquals(Objet.GRAPHISME.tomatequipousse, dernierObjet.graphisme);
        Assertions.assertEquals(10, dernierObjet.animation);
        Assertions.assertEquals(monde.positionX, dernierObjet.x);
        Assertions.assertEquals(monde.positionY, dernierObjet.y);
    }
    
    @Test
    public void testCuissonCupcake() {// utiliser le sucre sur le four
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde4();
        InputService inputService = new InputService();
        
        // Ramasser le sucre et activer le four
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde, "ArrowRight");
        inputService.handleInput(monde,  "Enter");
        inputService.handleInput(monde, "ArrowLeft");
        inputService.handleInput(monde, "Space");

        // Inventaire vide + cupcake qui cuit
        Objet dernierObjet = monde.objets.getLast();
        Assertions.assertEquals(null, monde.inventaire);
        Assertions.assertEquals(Objet.GRAPHISME.cupcakequicuit, dernierObjet.graphisme);
        Assertions.assertEquals(3, dernierObjet.animation);
        Assertions.assertEquals(monde.positionX, dernierObjet.x);
        Assertions.assertEquals(monde.positionY, dernierObjet.y);
    }
    
    @Test
    public void testUtiliserOxygene() {// Utiliser une bouteille d'O2
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde1();
        TimerService timerService = new TimerService();
        
        // Ajout d'une bouteille d'oxygène dans l'inventaire
        monde.inventaire = ObjetService.createObjet(0, 0, Objet.GRAPHISME.oxygene);
        timerService.handleTime(monde, "oxygene");
        // Si le joueur n'a plus d'oxygene, il peut utiliser celle qu'il transporte
        Assertions.assertEquals(null, monde.inventaire);
        Assertions.assertEquals(Monde.Status.onGoing, monde.status);
    }
    
    @Test
    public void testAsphyxie() {// À cours d'air !
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde1();
        TimerService timerService = new TimerService();
        
        // Si le joueur n'a plus d'oxygene et un inventaire vide, il meurt
        timerService.handleTime(monde, "oxygene");
        Assertions.assertEquals(Monde.Status.gameOver, monde.status);
    }
    
    @Test
    public void testMangerTomate() {// Manger une tomate
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde1();
        TimerService timerService = new TimerService();
        
        // Ajout d'une tomate dans l'inventaire
        monde.inventaire = ObjetService.createObjet(0, 0, Objet.GRAPHISME.tomate);
        timerService.handleTime(monde, "nourriture");
        // Si la jauge de nourriture tombe à 0, le joueur peut se nourrir depuis son inventaire
        Assertions.assertEquals(null, monde.inventaire);
        Assertions.assertEquals(Monde.Status.onGoing, monde.status);
    }
    
    @Test
    public void testMangerCupcake() {// Manger un cupcake
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde1();
        TimerService timerService = new TimerService();
        
        // Ajout d'une tomate dans l'inventaire
        monde.inventaire = ObjetService.createObjet(0, 0, Objet.GRAPHISME.cupcake);
        timerService.handleTime(monde, "nourriture");
        // Si la jauge de nourriture tombe à 0, le joueur peut se nourrir depuis son inventaire
        Assertions.assertEquals(null, monde.inventaire);
        Assertions.assertEquals(Monde.Status.onGoing, monde.status);
    }
    
    @Test
    public void testInanition() {// À cours de nourriture !
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde1();
        TimerService timerService = new TimerService();
        
        // Si le joueur n'a plus d'oxygene et un inventaire vide, il meurt
        timerService.handleTime(monde, "nourriture");
        Assertions.assertEquals(Monde.Status.gameOver, monde.status);
    }
    
    @Test
    public void testRamasserObjetAvecAnimation() {// Il doit être impossible de ramasser un objet avec une anim.
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        InputService inputService = new InputService();

        // faire pousse une tomate
        String decorId = "decor-1";
        monde.decors.add(new Decor(decorId, 2, 2, Decor.GRAPHISME.potager));
        monde.inventaire = new Objet("0", 0, 0, Objet.GRAPHISME.bouteille);
        inputService.handleInput(monde, "Space");
        
        // Une tomate qui pousse est créée, avec une animation de 10
        Objet dernierObj = monde.objets.getLast();
        Assertions.assertEquals(Objet.GRAPHISME.tomatequipousse, dernierObj.graphisme);
        Assertions.assertEquals(10, dernierObj.animation);
        
        // Essayer de ramasser la tomate qui pousse
        Assertions.assertEquals(null, monde.inventaire);
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(null, monde.inventaire);
    }
    
    @Test
    public void testTomatePrete() {
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        TimerService timerService = new TimerService();
        monde.objets.add(new Objet("objet-1", 2, 2, GRAPHISME.tomatequipousse));
        // tomatequipousse doit devenir tomate
        timerService.handleTime(monde, "objet-1");
        Assertions.assertEquals(Objet.GRAPHISME.tomate, monde.objets.getFirst().graphisme);
    }
    
    @Test
    public void testCupcakePret() {
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        TimerService timerService = new TimerService();
        monde.objets.add(new Objet("objet-1", 2, 2, GRAPHISME.cupcakequicuit));
        // cupcakequicuit doit devenir cupcake
        timerService.handleTime(monde, "objet-1");
        Assertions.assertEquals(Objet.GRAPHISME.cupcake, monde.objets.getFirst().graphisme);
    }
    
    @Test
    public void testCreerInflammable() { // Mélanger hydrogène et oxygène
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        InputService inputService = new InputService();
        monde.objets.add(new Objet("objet-1", 2, 2, GRAPHISME.oxygene));
        monde.inventaire = new Objet("objet-2", 0, 0, GRAPHISME.hydrogene);
        // Mélanger et ramasser le résultat
        inputService.handleInput(monde, "Enter");
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(Objet.GRAPHISME.inflammable, monde.inventaire.graphisme);
    }
    
    @Test
    public void testCreerExplosif() { // Mélanger du sucre et de l'oxygène
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        InputService inputService = new InputService();
        monde.objets.add(new Objet("objet-1", 2, 2, GRAPHISME.oxygene));
        monde.inventaire = new Objet("objet-2", 0, 0, GRAPHISME.sucre);
        // Mélanger et ramasser le résultat
        inputService.handleInput(monde, "Enter");
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(Objet.GRAPHISME.explosif, monde.inventaire.graphisme);
    }
    
    @Test
    public void testAmpoule() { // Débrancher, ramasser et rebrancher une ampoule
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        InputService inputService = new InputService();
        monde.decors.add(new Decor("decor-1", 2, 2, Decor.GRAPHISME.ampouleAllumee));

        // Activer l'ampoule allumée
        inputService.handleInput(monde, "Space");
        Assertions.assertEquals(Decor.GRAPHISME.ampouleEteinte, monde.decors.getLast().graphisme);
        // Ramasser les fils
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(GRAPHISME.electrique, monde.inventaire.graphisme);
        // Rebrancher l'ampoule
        inputService.handleInput(monde, "Space");
        Assertions.assertEquals(null, monde.inventaire);
        Assertions.assertEquals(Decor.GRAPHISME.ampouleAllumee, monde.decors.getLast().graphisme);
    }

    @Test
    public void testJaugeOxygene() { // Dés/activer l'oxygène en entrant/sortant d'une salle
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        InputService inputService = new InputService();

        inputService.handleInput(monde, "ArrowLeft");
        inputService.handleInput(monde, "ArrowLeft");
        Assertions.assertEquals(30, monde.timerOxygene);
        inputService.handleInput(monde, "ArrowRight");
        Assertions.assertEquals(0, monde.timerOxygene);
    }
    
    @Test
    public void testFeuRatee() { // Mélanger inflammable et electrique dans le mauvais ordre
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        InputService inputService = new InputService();
        monde.objets.add(new Objet("objet-1", 2, 2, GRAPHISME.electrique));
        monde.inventaire = new Objet("objet-2", 0, 0, GRAPHISME.inflammable);
        
        // Tenter de mélanger (rien ne doit se produire)
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(Objet.GRAPHISME.inflammable, monde.inventaire.graphisme);
        Assertions.assertEquals(Objet.GRAPHISME.electrique, monde.objets.getLast().graphisme);
    }
    
    @Test
    public void testCreerFeu() { // Mélanger inflammable et electrique
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        InputService inputService = new InputService();
        monde.objets.add(new Objet("objet-1", 2, 2, GRAPHISME.inflammable));
        monde.inventaire = new Objet("objet-2", 0, 0, GRAPHISME.electrique);
        
        // Tenter de mélanger (rien ne doit se produire)
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(null, monde.inventaire);
        Assertions.assertEquals(Objet.GRAPHISME.decomptefeu, monde.objets.getLast().graphisme);
    }
    
    @Test
    public void testExplosifRatee() { // Mélanger explosif et electrique dans le mauvais ordre
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        InputService inputService = new InputService();
        monde.objets.add(new Objet("objet-1", 2, 2, GRAPHISME.electrique));
        monde.inventaire = new Objet("objet-2", 0, 0, GRAPHISME.explosif);
        
        // Tenter de mélanger (rien ne doit se produire)
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(Objet.GRAPHISME.explosif, monde.inventaire.graphisme);
        Assertions.assertEquals(Objet.GRAPHISME.electrique, monde.objets.getLast().graphisme);
    }
    
    @Test
    public void testCreerExplosion() { // Mélanger explosif et electrique
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        InputService inputService = new InputService();
        monde.objets.add(new Objet("objet-1", 2, 2, GRAPHISME.explosif));
        monde.inventaire = new Objet("objet-2", 0, 0, GRAPHISME.electrique);
        
        // Tenter de mélanger (rien ne doit se produire)
        inputService.handleInput(monde, "Enter");
        Assertions.assertEquals(null, monde.inventaire);
        Assertions.assertEquals(Objet.GRAPHISME.decompteexplosion, monde.objets.getLast().graphisme);
    }
    
    @Test
    public void testDecompteExplosion() { // Timer décompte explosion reçu
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        TimerService timerService = new TimerService();
        monde.objets.add(new Objet("objet-1", 2, 2, GRAPHISME.decompteexplosion));
        // L'objet se transforme en explosion
        timerService.handleTime(monde, "objet-1");
        Assertions.assertEquals(GRAPHISME.explosion, monde.objets.getLast().graphisme);
        Assertions.assertEquals(1, monde.objets.getLast().animation);
        // L'objet est détruit
        timerService.handleTime(monde, "objet-1");
        Assertions.assertEquals(0, monde.objets.size());
    }
    
    @Test
    public void testDecompteFeu() { // Timer décompte feu reçu
        CreerMondeService creerMondeService = new CreerMondeService();
        Monde monde = creerMondeService.creerMonde5();
        TimerService timerService = new TimerService();
        monde.objets.add(new Objet("objet-1", 2, 2, GRAPHISME.decomptefeu));
        // L'objet se transforme en feu
        timerService.handleTime(monde, "objet-1");
        Assertions.assertEquals(GRAPHISME.feu, monde.objets.getLast().graphisme);
        Assertions.assertEquals(1, monde.objets.getLast().animation);
        // Le feu se transforme en eau (magie !)
        timerService.handleTime(monde, "objet-1");
        Assertions.assertEquals(GRAPHISME.bouteille, monde.objets.getLast().graphisme);
    }
}