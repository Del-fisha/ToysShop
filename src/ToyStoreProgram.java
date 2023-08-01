import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Toy {
    private int id;
    private String name;
    private int count;
    private double weight;

    public Toy(int id, String name, int count, double weight) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.weight = weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public double getWeight() {
        return weight;
    }
}

class ToyStore {
    private List<Toy> toys = new ArrayList<>();

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void setToyWeight(int toyId, double weight) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setWeight(weight);
                return;
            }
        }
        System.out.println("Toy not found");
    }

    public Toy getPrizeToy() {
        if (toys.isEmpty()) {
            System.out.println("No toys available");
            return null;
        }

        double totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }

        Random random = new Random();
        double randomNumber = random.nextDouble() * totalWeight;

        double currentWeight = 0;
        for (Toy toy : toys) {
            currentWeight += toy.getWeight();
            if (randomNumber <= currentWeight) {
                toys.remove(toy);
                toy.setCount(toy.getCount() - 1);
                return toy;
            }
        }
        return null;
    }

    public void savePrizeToyToFile(Toy toy) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("prize_toys.txt", true))) {
            writer.write(toy.getName());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class ToyStoreProgram {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();

        Toy toy1 = new Toy(1, "Barbie", 10, 25.0);
        Toy toy2 = new Toy(2, "Hot Wheels", 5, 15.0);
        Toy toy3 = new Toy(3, "LEGO", 8, 20.0);

        toyStore.addToy(toy1);
        toyStore.addToy(toy2);
        toyStore.addToy(toy3);

        toyStore.setToyWeight(1, 30.0);
        toyStore.setToyWeight(2, 10.0);
        toyStore.setToyWeight(3, 60.0);

        Toy prizeToy = toyStore.getPrizeToy();
        if (prizeToy != null) {
            toyStore.savePrizeToyToFile(prizeToy);
        }
    }
}