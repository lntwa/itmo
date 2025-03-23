package main.items;

import main.human.Human;

public class FirstAidKit extends Item {
    private final int healthRestoreAmount;

    public FirstAidKit(String name, String description, int healthRestoreAmount) {
        super(name, description);
        this.healthRestoreAmount = healthRestoreAmount;
    }

    @Override
    public void use(Human character) {
        int newHealth = character.getHealthPoints() + healthRestoreAmount;
        character.setHealthPoints(newHealth);
        System.out.println(character.getName() + " used " + getName() + ". Restored " + healthRestoreAmount + " health points. New health: " + character.getHealthPoints());
    }
}
