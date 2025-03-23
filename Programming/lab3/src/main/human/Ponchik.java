package main.human;

import main.enums.DamageLevel;
import main.interfaces.Damageable;

public class Ponchik extends Human implements Damageable {
    protected double health = getHealthPoints();
    @Override
    public void takeDamage(DamageLevel damageLevel) {
        System.out.println("Ponchik fell into a chamber!!!! ");
        double damage = 0;
        switch (damageLevel) {
            case LOW -> damage = getHealthPoints() * 0.25;
            case HIGH -> damage = getHealthPoints() * 0.75;
            case MEDIUM -> damage = getHealthPoints() * 0.5;
        }
        setHealthPoints(getHealthPoints() - (int) damage);
        System.out.println("Ponchik took damage " + damage + ". Now his health is " + getHealthPoints());
    }
    public void restoreHealth() {
        setHealthPoints(100);
        System.out.println("Ponchik's health is restored! ");
    }
}
