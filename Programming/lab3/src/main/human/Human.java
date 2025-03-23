package main.human;
import java.util.Objects;

public class Human {
    protected int HealthPoints;
    protected int IQlevel;
    protected String Mood;
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHealthPoints(int healthPoints) {
        this.HealthPoints = healthPoints;
    }

    public int getHealthPoints() {
        return HealthPoints;
    }

    public void setIQlevel(int IQlevel) {
        this.IQlevel = IQlevel;
    }

    public int getIQlevel() {
        return IQlevel;
    }

    public void setMood(String mood) {
        this.Mood = mood;
    }

    public String getMood() {
        return Mood;
    }

    public void interactWith(Human other) {
        if (this.HealthPoints > 50 && other.HealthPoints < 50) {
            other.setMood("Grateful");
        } else if (this.IQlevel > other.IQlevel) {
            other.setMood("Impressed");
        } else {
            other.setMood("Neutral");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Human human = (Human) obj;
        return HealthPoints == human.HealthPoints &&
                IQlevel == human.IQlevel &&
                Objects.equals(Mood, human.Mood) &&
                Objects.equals(name, human.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, HealthPoints, IQlevel, Mood);
    }

    @Override
    public String toString() {
        return "{" +
                "name = '" + name + '\'' +
                ", healthPoints = " + HealthPoints +
                ", IQlevel = " + IQlevel +
                ", mood = '" + Mood + '\'' +
                '}';
    }
}