package main;

import main.enums.DamageLevel;
import main.human.Neznaika;
import main.human.Ponchik;
import main.human.Zvezdochka;
import main.items.FirstAidKit;
import main.items.Item;
import main.rocket.*;

public class Main {
    public static void main(String[] args) {
        Walls walls = new Walls();
        Stair stair = new Stair();
        Bottom bottom = new Bottom();
        Ponchik ponchik = new Ponchik();
        Neznaika neznaika = new Neznaika();
        Chamber chamber = new Chamber();
        Rocket rocket = new Rocket();
        Zvezdochka zvezdochka = new Zvezdochka();
        System.out.println("Lets prepare for a flight: ");
        rocket.setCurrentState(100);
        rocket.setSpeed(1000);
        rocket.addMissionLog("Mission started");
        rocket.addMissionLog("Reached orbit");
        rocket.displayMissionLogs();
        chamber.setOxygenConcentration(45.0);
        chamber.monitorOxygenLevel();
        ponchik.setName("Ponchik");
        ponchik.setHealthPoints(75);
        ponchik.setIQlevel(30);
        ponchik.setMood("Happy");
        zvezdochka.setName("Zvezdochka");
        zvezdochka.setHealthPoints(100);
        zvezdochka.setIQlevel(120);
        zvezdochka.setMood("Blessed");
        neznaika.setName("Neznaika");
        neznaika.setHealthPoints(20);
        neznaika.setMood("Depressed");
        neznaika.setIQlevel(88);
        System.out.println("Before interaction: " + neznaika);
        System.out.println("Before interaction: " + ponchik);
        ponchik.interactWith(neznaika);
        System.out.println("After interaction: " + neznaika);
        System.out.println("After interaction: " + ponchik);
        ponchik.takeDamage(DamageLevel.LOW);
        neznaika.helpFriend(ponchik);
        bottom.absorbImpact(50);
        walls.absorbImpact(30);
        stair.absorbImpact(20);
        zvezdochka.takeDamage(DamageLevel.HIGH);
        Item firstAidKit = new FirstAidKit("First Aid Kit", "A kit to restore health", 50);
        System.out.println("Using item: " + firstAidKit.getName());
        firstAidKit.use(zvezdochka);
    }
}