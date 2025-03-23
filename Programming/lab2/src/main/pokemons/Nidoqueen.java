package src.main.pokemons;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import src.main.moves.physical.Crunch;
import src.main.moves.special.FireBlast;
import src.main.moves.special.Thunderbolt;
import src.main.moves.status.TailWhip;

public class Nidoqueen extends Nidorina {
    public Nidoqueen(String name, int level) {
        super(name, level);
        setType(Type.POISON, Type.GROUND);
        setStats(90, 92, 87, 75, 85, 76);
        setMove(new TailWhip(), new Thunderbolt(), new Crunch(), new FireBlast());
    }
}
