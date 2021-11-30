import java.util.Iterator;
import java.util.List;

/**
 * Capitalism. It devours everything until nothing's left.
 *
 * @author Kenny Castro-Monroy
 * @version 2021.11.29
 */
public class Capitalism extends Animal
{
    // The age at which a capitalism can start to breed.
    private static final int BREEDING_AGE = 1;
    // The age to which capitalism can live.
    private static final int MAX_AGE = 500;
    // The likelihood of a capitalism breeding.
    private static final double BREEDING_PROBABILITY = 1.0;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 25;
    /**
     * Constructor for objects of class Capitalism
     */
    public Capitalism(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param n
     * newAnimals A list to receive newly born animals.
     */
    public void act(List<Animal> newAnimals) {
        incrementAge();
        if(isAlive()) {
            giveBirth(newAnimals);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit || animal instanceof Fox) {
                Animal anim = (Animal) animal;
                if(anim.isAlive()) { 
                    anim.setDead();
                    return where;
                }
            }
        }
        return null;
    }

    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    private void giveBirth(List<Animal> newAnimals)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Capitalism young = new Capitalism(false, field, loc);
            newAnimals.add(young);
        }
    }

    /**
     * Return the breeding age of this animal
     * @return the breeding age of the animal
     */
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Get the maximum age of the animal before death
     * @return animal's max age before death
     */
    protected int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Get the max litter size
     * @return the maximum number of offspring in a single litter
     */
    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * Get the breeding probability
     * @return the probability of producing offspring
     */
    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }
}
