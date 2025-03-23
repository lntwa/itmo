package main.human;

import main.enums.DamageLevel;
import main.interfaces.Damageable;

public class Zvezdochka extends Human implements Damageable {
    protected double health = getHealthPoints();
    @Override
    public void takeDamage(DamageLevel damageLevel) {
        System.out.println("Zvezdochka accidentally went into outer space!!!! ");
        double damage = 0;
        switch (damageLevel) {
            case LOW -> damage = getHealthPoints() * 0.25;
            case HIGH -> damage = getHealthPoints() * 0.75;
            case MEDIUM -> damage = getHealthPoints() * 0.5;
        }
        setHealthPoints(getHealthPoints() - (int) damage);
        System.out.println("Zvezdochka took damage " + damage + ". Now her health is " + getHealthPoints());
    }
}
