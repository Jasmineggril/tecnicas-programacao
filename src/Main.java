import com.animal.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {

        List<Animal> animais = new ArrayList<>();
        animais.add(new Animal("Coelho", true,  false));
        animais.add(new Animal("Sapo", true, true));
        animais.add(new Animal("Cobra", false, true));

        //imprimir(animais, new VerificaSeSaltador());

        imprimir_functional(animais, animal -> animal.podeSalta());

       /* Predicate<Animal> podeSaltar = (animal -> animal.podeSalta());
        Predicate<Animal> podeNadar2 = Animal::podeNadar;

        animais.stream().filter(animal -> animal.podeSalta())
                        .forEach(animal -> System.out.println(animal.getNome()));

        animais.stream().filter(animal -> animal.podeNadar())
                .forEach(animal -> System.out.println(animal.getNome()));*/


    }

    public static void imprimir_functional(List<Animal> animais, Predicate<Animal> pa) {
        animais.stream().filter(pa)
                .forEach(animal -> System.out.println(animal.getNome()));
    }

    public static void imprimir(List<Animal> animais, VerificaSeSaltador verificaSeSaltador) {
        for (Animal animal : animais) {
            if(verificaSeSaltador.verificar(animal))
                System.out.println(animal.getNome());
        }
    }

}