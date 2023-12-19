package lab2;

import lab2.poke.Eevee;
import lab2.poke.Espeon;
import lab2.poke.Landorus;
import lab2.poke.Poliwag;
import lab2.poke.Poliwhirl;
import lab2.poke.Poliwrath;

import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Main {
    public static void main(String[] args) {
        Battle battle = new Battle();

        Pokemon[] allies = {
                new Eevee("Nelson", 40),
                new Espeon("Mata", 20),
                new Landorus("Priya", 15),
                new Poliwag("Seren", 40),
                new Poliwhirl("Fabio", 40),
                new Poliwrath("Andre", 73)
        };
        Pokemon[] foes = {
                new Eevee("Mike", 40),
                new Espeon("Daniel", 20),
                new Landorus("Anastasia", 15),
                new Poliwag("Alice", 40),
                new Poliwhirl("Mary", 40),
                new Poliwrath("Pablo", 73)
        };

        for (Pokemon ally : allies)
            battle.addAlly(ally);
        for (Pokemon foe : foes)
            battle.addFoe(foe);

        battle.go();
    }
}