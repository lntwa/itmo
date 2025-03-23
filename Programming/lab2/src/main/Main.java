package src.main;

import ru.ifmo.se.pokemon.*;
import src.main.pokemons.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Pokemon fp1 = new Dewgong("Большой тюлень", 11);
        Pokemon fp2 = new Jirachi("Летающая этикетка", 9);
        Pokemon fp3 = new Nidoqueen("Динозавр", 22);
        b.addAlly(fp1);
        b.addAlly(fp2);
        b.addAlly(fp3);
        Pokemon sp1 = new NidoranF("Злой хомяк", 12);
        Pokemon sp2 = new Nidorina("Голубая коала", 19);
        Pokemon sp3 = new Seel("Маленький тюлень", 15);
        b.addFoe(sp1);
        b.addFoe(sp2);
        b.addFoe(sp3);
        b.go();
    }
}

