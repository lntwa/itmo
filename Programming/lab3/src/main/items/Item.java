package main.items;

import main.human.Human;

public abstract class Item {
    protected String name;
    protected String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void use(Human character);

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
