package src.main.pokemons;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import src.main.moves.physical.Crunch;
import src.main.moves.special.Thunderbolt;
import src.main.moves.status.TailWhip;

public class Nidorina extends NidoranF {
    public Nidorina(String name, int level) {
        super(name, level);
        setType(Type.POISON);
        setStats(70,62,67,55, 55, 56);
        setMove(new TailWhip(), new Thunderbolt(), new Crunch());
    }
}
