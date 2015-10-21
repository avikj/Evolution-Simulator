import java.util.*;

public class EvoSimulator {
	// selection strength 0-10,
	// 0 = all survive, 10 = all die in first gen
	final static int SELECTION_STRENGTH = 5;

	final static int[] INITIAL_ORGANISMS = {10, 10, 11, 10, 9, 12};
	final static int NUMBER_OF_GENERATIONS = 8;

	static OrganismStack population = new OrganismStack();
	private static int generations = 0;

	public static void main(String[] args) {
		System.out.println("Initial Size: " + INITIAL_ORGANISMS.length);
		System.out.println("Selection Strength: " + SELECTION_STRENGTH);
		System.out.println();
		addInitialOrganisms();
		printCurrentGen();
		for (int i = 0; i < NUMBER_OF_GENERATIONS; i++) {
			runGeneration();
			printCurrentGen();
			if (population.size == 0)
				i = 1000;
		}

	}

	public static void addInitialOrganisms() {
		for (int i = 0; i < INITIAL_ORGANISMS.length; i++)
			population.push(new Organism(INITIAL_ORGANISMS[i]));		//initial organisms have fitness of 10
		generations++;
	}

	public static void reproduce(Organism parent) {
		population.push(new Organism(parent));
		population.push(new Organism(parent));
	}

	public static boolean survives(Organism o) {
		if (o.fitness * Math.random() > SELECTION_STRENGTH)
			return true;
		else
			return false;
	}

	public static void runGeneration() {
		Organism current;
		for (int i = 0; i < population.size; i++) {
			current = (population.pop());
			if (survives(current))
				reproduce(current);
		}
		generations++;
	}

	public static void printCurrentGen() {
		System.out.println("Generation " + generations);
		System.out.println("Population Size: " + population.size);
		System.out.println("Average Fitness: " + population.getAvgFitness()
				+ "\n");
		population.printPopulation();
	}

}

class Organism {
	public int fitness;

	public Organism(Organism parent) {
		fitness = parent.fitness;
		if (Math.random() < 0.01)		//good mutations
			fitness += 1;
		if (Math.random() < 0.01)		//bad mutations
			fitness -= 1;
	}

	public Organism(int fitness) {
		this.fitness = fitness;
	}

	public void print() {
		System.out.println("fitness: " + fitness);
	}
}

class OrganismStack {
	Organism[] stack = new Organism[(int) (100000000)];
	public int size = 0;

	public Organism push(Organism o) {
		stack[size] = o;
		size++;
		return o;
	}

	public Organism pop() {
		Organism o;
		o = stack[--size];
		return o;
	}

	public double getAvgFitness() {
		double total = 0;
		if (size == 0)
			return 0.0;
		else {
			for (int i = 0; i < size; i++) {
				total += stack[i].fitness;
			}
			return total / size;
		}
	}
	public void printPopulation(){
		for(int i = 0; i < size; i++){
			System.out.print(stack[i].fitness+", ");
		}
		System.out.println("\n");
	}
}