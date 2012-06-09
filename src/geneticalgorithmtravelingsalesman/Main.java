/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithmtravelingsalesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * <p>Command-line utility to test genetic algorithm on the traveling salesman problem.</p>
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class Main {

    final static int DEFAULT_TIMES_RUN_GENETIC = 10;
    final static int DEFAULT_TIMES_RUN_RANDOM = 10;
    final static int DEFAULT_HEIGHT = 15;
    final static int DEFAULT_WIDTH = 100;
    final static int DEFAULT_SITES = 25;
    final static int DEFAULT_GENERATIONS = 200;
    final static int DEFAULT_POPULATION_SIZE = DEFAULT_SITES;
    final static double DEFAULT_PERCENTAGE_MATE = 0.10;
    final static double DEFAULT_CROSS_OVER_RATE = 0.3;
    final static double DEFAULT_MUTATION_RATE = 0.05;
    final static boolean DEFAULT_ELITISM = true;
    final static boolean DEFAULT_PRINT_BEST_OF_EACH_GENERATION = false;
    final static Random random = new Random();

    private static void printUsage() {
        System.err.println();
        System.err.println("USAGE");
        System.err.println();
        System.err.println("DESCRIPTION");
        System.err.println("    Simple (but heavily parameterized) tool for testing genetic algorithms on the traveling saleman problem. All parameters are optional, and have default values; the real value of this tool is to learn how various variables in genetic algorithm impact its efficacy.");
        System.err.println();
        System.err.println("EXAMPLES");
        System.err.println("    java -Xmx512m -jar GeneticAlgorithm-TravelingSalesman.jar");
        System.err.println("        Runs all defaults. See usage for default values.");
        System.err.println();
        System.err.println("    java -Xmx512m -jar GeneticAlgorithm-TravelingSalesman.jar -h 200 -w 300");
        System.err.println("        Runs tests with world map that is 200 x 300 points in size.");
        System.err.println();
        System.err.println("    java -Xmx512m -jar GeneticAlgorithm-TravelingSalesman.jar -h 200 -w 300 -G 20 -R 0");
        System.err.println("        Above, but run genetic algorithms 20 times and don't run any random tests.");
        System.err.println();
        System.err.println("PARAMETERS - TOTAL TEST REPLICATIONS");
        System.err.println("    -G, --geneticreps       Values: whole number        Number of times to run test using genetic algorithm. Default is "+DEFAULT_TIMES_RUN_GENETIC+".");
        System.err.println("    -R, --randomreps        Values: whole number        Number of times to run test using random algorithm. Default is "+DEFAULT_TIMES_RUN_RANDOM+".");
        System.err.println();
        System.err.println("PARAMETERS - SALESMAN, MAP AND PLACES TO VISIT");
        System.err.println("    -h, --mapheight         Values: whole number        The height of the world map. Default is "+DEFAULT_HEIGHT+".");
        System.err.println("    -s, --sites             Values: whole number        The number of places salesman must visit. Default is "+DEFAULT_SITES+".");
        System.err.println("    -w, --mapwidth          Values: whole number        The width of the world map. Default is "+DEFAULT_WIDTH+".");
        System.err.println();
        System.err.println("PARAMETERS - DISPLAY");
        System.err.println("    -P, --printparents      Values: true or false       Prints out all of the suriving solutions in each generation that end up in the mating pool. Default is "+DEFAULT_PRINT_BEST_OF_EACH_GENERATION+".");
        System.err.println("    -h, --help              Values: none                Print usage (you're looking at it!) and exit normally.");
        System.err.println();
        System.err.println("PARAMETERS - GENETIC ALGORITHM");
        System.err.println("    -c, --crossoverrate     Values: 0.0 - 1.0           Likelihood for crossover between chromosomes for parent solutions. 1.0 means will always crossover; 0.0 means never. Default is "+DEFAULT_CROSS_OVER_RATE+".");
        System.err.println("    -e, --elitism           Values: true or false       Whether to use elitism (best solution in each generation are added to next generation). Default is "+DEFAULT_ELITISM+".");
        System.err.println("    -g, --generations       Values: whole number        The total number of generations of solutions to use for each run. Default is "+DEFAULT_GENERATIONS+".");
        System.err.println("    -m, --matepercentage    Values: 0.0 - 1.0           Percentage of solutions for each generation that survive long enought to mate. Default is "+DEFAULT_PERCENTAGE_MATE+".");
        System.err.println("    -p, --population        Values: whole number        Population size of solutions for each generation. Default is "+DEFAULT_POPULATION_SIZE+".");
        System.err.println("    -u, --mutationrate      Values: 0.0 - 1.0           Rate of mutations for each data point in genetic algorithms. 1.0 will be entirely random. Default is "+DEFAULT_MUTATION_RATE+".");
        System.err.println();
        System.err.println("RETURN CODES");
        System.err.println("    0: Exitted normally");
        System.err.println("    1: Unknown error");
        System.err.println("    2: Unrecognized parameter");
        System.err.println("    3: Invalid value for parameter");
        System.err.println();
        System.err.println("AUTHOR");
        System.err.println("    Bryan Smith - bryanesmith@gmail.com");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int sites = DEFAULT_SITES;
        int height = DEFAULT_HEIGHT;
        int width = DEFAULT_WIDTH;
        int populationSize = DEFAULT_POPULATION_SIZE;
        int generations = DEFAULT_GENERATIONS;
        double percentageMate = DEFAULT_PERCENTAGE_MATE;
        double crossOverRate = DEFAULT_CROSS_OVER_RATE;
        double mutationRate = DEFAULT_MUTATION_RATE;
        boolean elitism = DEFAULT_ELITISM;
        boolean printBestOfEachGeneration = DEFAULT_PRINT_BEST_OF_EACH_GENERATION;
        int timesRunGenetic = DEFAULT_TIMES_RUN_GENETIC;
        int timesRunRandom = DEFAULT_TIMES_RUN_RANDOM;

        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-h") || args[i].equals("--help")) {
                    printUsage();
                    System.exit(0);
                } else if (args[i].equals("-s") || args[i].equals("--sites")) {
                    sites = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("-h") || args[i].equals("--mapheight")) {
                    height = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("-w") || args[i].equals("--mapwidth")) {
                    width = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("-p") || args[i].equals("--population")) {
                    populationSize = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("-g") || args[i].equals("--generations")) {
                    generations = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("-m") || args[i].equals("--matepercentage")) {
                    percentageMate = Double.parseDouble(args[i + 1]);
                } else if (args[i].equals("-c") || args[i].equals("--crossoverrate")) {
                    crossOverRate = Double.parseDouble(args[i + 1]);
                } else if (args[i].equals("-u") || args[i].equals("--mutationrate")) {
                    mutationRate = Double.parseDouble(args[i + 1]);
                } else if (args[i].equals("-e") || args[i].equals("--elitism")) {
                    elitism = Boolean.parseBoolean(args[i + 1]);
                } else if (args[i].equals("-P") || args[i].equals("--printparents")) {
                    printBestOfEachGeneration = Boolean.parseBoolean(args[i + 1]);
                } else {
                    System.err.println("Unrecognized parameter: " + args[i]);
                    printUsage();
                    System.exit(2);
                }
            }



            final SalesmanMap map = SalesmanMap.generate(sites, height, width);

            // Print out the map
            System.out.println();
            System.out.println("DESCRIPTION: The salesman must visit all " + sites + " sites. We want to create an itinerary that is as sort as possible, contrasting the results between genetic algorithmic approach and random walks.");
            System.out.println();
            System.out.println("The salesman starts at point 'a' on the following map:");
            System.out.println(map);

            List<Double> distances = new ArrayList(timesRunGenetic + timesRunRandom);
            Map<Double, String> descriptions = new HashMap();

            double geneticTotal = 0;
            List<Double> geneticScores = new ArrayList(timesRunGenetic);

            // Run all the genetic trials
            for (int i = 0; i < timesRunGenetic; i++) {
                System.out.println();
                System.out.println(">>> RUNNING GENETIC TRIAL #" + (i + 1) + " out of " + timesRunGenetic + " <<<");
                double val = runTestWithGeneticAlgorithm(map, populationSize, percentageMate, generations, elitism, crossOverRate, mutationRate, printBestOfEachGeneration);
                System.out.println("After " + generations + " generations, found shortest distance of: " + val);

                distances.add(val);
                descriptions.put(val, "Genetic trial");

                geneticTotal += val;
                geneticScores.add(val);
            }

            double randomTotal = 0;
            List<Double> randomScores = new ArrayList(timesRunRandom);

            // Run all the random trials
            for (int i = 0; i < timesRunRandom; i++) {
                System.out.println();
                System.out.println(">>> RUNNING RANDOM TRIAL #" + (i + 1) + " out of " + timesRunRandom + " <<<");
                double val = runTestWithRandom(map, populationSize, generations, printBestOfEachGeneration);
                System.out.println("After " + generations + " generations, found shortest distance of: " + val);

                distances.add(val);
                descriptions.put(val, "Random trial");

                randomTotal += val;

                randomScores.add(val);
            }

            Collections.sort(distances);

            System.out.println();
            System.out.println("--- Rankings of distances ---:");
            for (int i = 0; i < distances.size(); i++) {
                Double val = distances.get(i);
                String desc = descriptions.get(val);
                System.out.println("    " + (i + 1) + ": " + val + " (" + desc + ")");
            }

            // Get genetic stats
            if (timesRunGenetic > 0) {
                double avg = geneticTotal / (double) timesRunGenetic;
                System.out.println();
                System.out.println("--- Statistics for genetic algorithm distances ---:");
                System.out.println("    * Average: " + avg);

                Collections.sort(geneticScores);
                double medianVal = -1;
                if (timesRunGenetic % 2 == 0) {
                    int indexOne = (int) ((double) timesRunGenetic / 2.0) - 1;
                    int indexTwo = indexOne + 1;

                    medianVal = (geneticScores.get(indexOne) + geneticScores.get(indexTwo)) / 2.0;
                } else {
                    int index = (int) Math.floor((double) timesRunGenetic / 2.0);
                    medianVal = geneticScores.get(index);
                }
                System.out.println("    * Median: " + medianVal);

                int sum = 0;
                int sumSquared = 0;
                for (double val : geneticScores) {
                    double valSquared = val * val;
                    sum += val;
                    sumSquared += valSquared;
                }

                double standardDev = (sumSquared - ((sum * sum) / (double) timesRunGenetic)) / (double) timesRunGenetic;
                System.out.println("    * Standard deviation: " + standardDev);
            }

            // Get random stats
            if (timesRunRandom > 0) {
                double avg = randomTotal / (double) timesRunRandom;
                System.out.println();
                System.out.println("--- Statistics for random algorithm distances ---:");
                System.out.println("    * Average: " + avg);

                Collections.sort(randomScores);
                double medianVal = -1;
                if (timesRunRandom % 2 == 0) {
                    int indexOne = (int) ((double) timesRunRandom / 2.0) - 1;
                    int indexTwo = indexOne + 1;

                    medianVal = (randomScores.get(indexOne) + randomScores.get(indexTwo)) / 2.0;
                } else {
                    int index = (int) Math.floor((double) timesRunRandom / 2.0);
                    medianVal = randomScores.get(index);
                }

                System.out.println("    * Median: " + medianVal);

                int sum = 0;
                int sumSquared = 0;
                for (double val : randomScores) {
                    double valSquared = val * val;
                    sum += val;
                    sumSquared += valSquared;
                }

                double standardDev = (sumSquared - ((sum * sum) / (double) timesRunRandom)) / (double) timesRunRandom;
                System.out.println("    * Standard deviation: " + standardDev);

            }
            System.out.println();
            System.exit(0);
        } catch (Exception e) {
            System.err.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            printUsage();
            System.exit(1);
        }
    }

    private static double runTestWithGeneticAlgorithm(SalesmanMap map, int populationSize, double percentageMate, int generations, boolean elitism, double crossOverRate, double mutationRate, boolean printBestOfEachGeneration) {

        Map<Double, byte[]> itineraryPopulation = new HashMap();
        List<Double> totalDistances = new ArrayList(populationSize);

        // How many of the fitest get to mate?
        final int numFitestToMate = (int) Math.round(populationSize * percentageMate);

        POPULATION:
        for (int pop = 0; pop < populationSize; pop++) {
            byte[] itinerary = generateRandomItinerary(map.getLastSite());
            double distance = getTotalTravel(itinerary, map);
            itineraryPopulation.put(distance, itinerary);
            totalDistances.add(distance);
        }

        byte[] bestSolution = null;
        double bestSolutionDistance = -1;

        GENERATIONS:
        for (int gen = 0; gen < generations; gen++) {

            // STEP 1: Get the fitest together
            Collections.sort(totalDistances);
            List<byte[]> fitestItineraries = new ArrayList(numFitestToMate);

            for (int i = 0; i < numFitestToMate; i++) {
                Double distance = totalDistances.get(i);
                byte[] itinerary = itineraryPopulation.get(distance);

                // Each generation, reset the best!
                if (i == 0) {
                    if (printBestOfEachGeneration) {
                        System.out.println("  Best solution in generation #" + gen + ": " + byteArrayToString(itinerary) + " -> " + distance);
                    }
                    bestSolution = itinerary;
                    bestSolutionDistance = distance;
                }
                fitestItineraries.add(itinerary);
            }

            // STEP 2: Mate to generate next population
            itineraryPopulation.clear();
            totalDistances.clear();

            int pop = 0;

            // If using elitism, automatically put best solution from previous generation in next
            if (elitism) {
                pop = 1;
                itineraryPopulation.put(bestSolutionDistance, bestSolution);
                totalDistances.add(bestSolutionDistance);
            }

            POPULATION:
            for (; pop < populationSize; pop++) {
                List<byte[]> parents = randomlyPickTwoParents(fitestItineraries);
                byte[] itinerary = mate(parents.get(0), parents.get(1), crossOverRate, mutationRate);
                double distance = getTotalTravel(itinerary, map);
                itineraryPopulation.put(distance, itinerary);
                totalDistances.add(distance);
            }
        }

        return bestSolutionDistance;
    }

    private static double runTestWithRandom(SalesmanMap map, int populationSize, int generations, boolean printBestOfEachGeneration) {
        Map<Double, byte[]> itineraryPopulation = new HashMap();
        List<Double> totalDistances = new ArrayList(populationSize);

        byte[] bestSolution = null;
        double bestSolutionDistance = -1;

        GENERATIONS:
        for (int gen = 0; gen < generations; gen++) {
            POPULATION:
            for (int pop = 0; pop < populationSize; pop++) {
                byte[] itinerary = generateRandomItinerary(map.getLastSite());
                double distance = getTotalTravel(itinerary, map);

                if (bestSolutionDistance == -1 || distance < bestSolutionDistance) {
                    bestSolutionDistance = distance;
                    bestSolution = itinerary;
                }

                itineraryPopulation.put(distance, itinerary);
                totalDistances.add(distance);
            }
            if (printBestOfEachGeneration) {
                System.out.println("  Best solution in generation #" + gen + ": " + byteArrayToString(bestSolution) + " -> " + bestSolutionDistance);
            }
        }

        return bestSolutionDistance;
    }

    /**
     * <p>Picks two parents using rank selection.</p>
     * <p>Rank selection basically weight the fittest as n, next as n-1, all the way down to least fitest as 1, when there are n items in matingPool. Then pick them randomly based on their relative weights (higher ranks more likely to be chosen).</p>
     * @param matingPool
     * @return
     */
    private static List<byte[]> randomlyPickTwoParents(List<byte[]> matingPool) {

        if (matingPool.size() < 2) {
            throw new RuntimeException("Requires a mating pool of size 2, instead found: " + matingPool.size());
        }

        List<byte[]> parents = new ArrayList();

        int length = matingPool.size();
        int totalWeight = 0;

        // Calculate total weight
        for (int i = length; i >= 1; i--) {
            totalWeight += i;
        }

        List<Integer> chosenIndices = new ArrayList();
        while (parents.size() < 2) {
            int randomVal = random.nextInt(totalWeight);

            int weight = 0;
            // Find selected parent
            FIND_PARENT:
            for (int i = length - 1; i >= 1; i--) {
                weight += i;
                if (randomVal < weight) {
                    if (!chosenIndices.contains(i)) {
                        // Add the parent
                        chosenIndices.add(i);
                        parents.add(matingPool.get(i));
                        break FIND_PARENT;
                    } else {
                        // Already selected that parent -- try again
                        break FIND_PARENT;
                    }
                }
            }
        }

        if (parents.size() != 2) {
            throw new RuntimeException("ASSERTION ERROR: Expected two parents, found: " + parents.size());
        }

        return parents;
    }

    /**
     * 
     * @param parent1
     * @param parent2
     * @param crossOverRate
     * @param mutationRate
     * @return
     */
    private static byte[] mate(byte[] parent1, byte[] parent2, double crossOverRate, double mutationRate) {
        if (parent1.length != parent2.length) {
            throw new RuntimeException("ASSERTION FAILED: Expected both parents to be same size. Instead, parent1.length<" + parent1.length + "> != parent2.length<" + parent2.length + ">");
        }
        byte[] child = new byte[parent1.length];

        // Pick a parent randomly
        boolean isParent1 = random.nextBoolean();

        // Start encoding from both parents
        for (int index = 0; index < parent1.length; index++) {

            if (isParent1) {
                child[index] = parent1[index];
            } else {
                child[index] = parent2[index];
            }

            // By chance, crossover
            if (random.nextFloat() < crossOverRate) {
                isParent1 = !isParent1;
            }
        }

        checkArrayForIntegrity(child);

        // Do some random mutations
        for (int index = 0; index < child.length; index++) {
            double r = random.nextFloat();

            // The reason we double the random vaule is because in the process of checking for integrity,
            // half chance go back to way was before!
            if (r < mutationRate * 2) {
                byte mutationValue = (byte) random.nextInt(parent1.length);
                child[index] = mutationValue;
                checkArrayForIntegrity(child);
            }
        }


        return child;
    }

    /**
     * 
     * @param child
     */
    private static void checkArrayForIntegrity(byte[] child) {
        // There will likely be duplicates and some without copies due to crossover. Fix this.
        SWAP_TWO_SITES:
        while (true) {
            int[] instanceCount = new int[child.length];
            for (int i = 0; i < instanceCount.length; i++) {
                instanceCount[i] = 0;
            }

            for (byte b : child) {
                instanceCount[b]++;
            }

            byte byteWithZeroInstances = -1;
            byte byteWithTwoInstances = -1;

            FIND_BYTES_TO_SWAP:
            for (byte index = 0; index < instanceCount.length; index++) {
                if (instanceCount[index] == 0) {
                    if (byteWithZeroInstances == -1) {
                        byteWithZeroInstances = index;
                    }
                } else if (instanceCount[index] == 2) {
                    if (byteWithTwoInstances == -1) {
                        byteWithTwoInstances = index;
                    }
                } else if (instanceCount[index] != 1) {
                    throw new RuntimeException("ASSERTION FAILED: Expected 0, 1 or 2 instances, instead found: " + instanceCount[index]);
                }

                if (byteWithTwoInstances != -1 && byteWithZeroInstances != -1) {
                    break FIND_BYTES_TO_SWAP;
                }
            }

            // Assert that if there is one that is non-zero, there is another
            if (byteWithZeroInstances == -1 && byteWithTwoInstances == -1) {
                break SWAP_TWO_SITES;
            } else if (byteWithZeroInstances != -1 && byteWithTwoInstances == -1) {
                throw new RuntimeException("ASSERTION FAILED: Found a byte with no instances, but couldn't find byte with two instances.");
            } else if (byteWithZeroInstances == -1 && byteWithTwoInstances != -1) {
                throw new RuntimeException("ASSERTION FAILED: Found a byte with two instances, but couldn't find byte with no instances.");
            }

            // Should we swap the first byte of two, or the second
            boolean isSwapFirst = random.nextBoolean();

            int found = 0;
            for (int index = 0; index < child.length; index++) {
                if (child[index] == byteWithTwoInstances) {
                    found++;
                    if (found > 2) {
                        throw new RuntimeException("Found should never be greater than 2, but is: " + found);
                    }
                    boolean swapTheFirst = (found == 1 && isSwapFirst);
                    boolean swapTheSecond = (found == 2 && !isSwapFirst);
                    if (swapTheFirst || swapTheSecond) {
                        child[index] = byteWithZeroInstances;
                        continue SWAP_TWO_SITES;
                    }
                }
            }
        }
    }

    /**
     * 
     * @param array
     * @param b
     * @return
     */
    private static boolean contains(byte[] array, byte b) {

        for (byte b2 : array) {
            if (b2 == b) {
                return true;
            }
        }

        return false;
    }

    /**
     * 
     * @param start
     * @param finish
     * @return
     */
    private static byte[] generateRandomItinerary(byte finish) {
        int size = finish;
        List<Byte> bytesList = new ArrayList(size);
        for (byte b = 1; b <= finish; b++) {
            bytesList.add(b);
        }
        Collections.shuffle(bytesList);

        byte[] bytesArr = new byte[size + 1];

        // Always start 
        bytesArr[0] = 0;
        for (int i = 1; i < bytesArr.length; i++) {
            bytesArr[i] = bytesList.get(i - 1);
        }

        return bytesArr;
    }

    /**
     * 
     * @param itinerary
     * @param map
     * @return
     */
    private static double getTotalTravel(byte[] itinerary, SalesmanMap map) {
        double distance = 0;

        for (int index = 0; index < itinerary.length - 1; index++) {
            byte b1 = itinerary[index];
            byte b2 = itinerary[index + 1];
            distance += map.getDistance(b1, b2);
        }

        return distance;
    }

    /**
     * 
     * @param b
     * @return
     */
    private static String byteArrayToString(byte[] b) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < b.length; i++) {
            buffer.append(SalesmanMap.convertByteToChar(b[i]));
            if (i < b.length - 1) {
                buffer.append(" ");
            }
        }

        return buffer.toString();
    }
}
