import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;

    private int age;
    protected static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        age = 0;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Getter for the age field
     * @return the age of the animal
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the animal age to a new value
     * @param age new age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Determines whether or not this animal can breed
     * @return true if the animal can breed, else false
     */
    public boolean canBreed() {
        return age >= getBreedingAge();    
    }

    /**
     * Return the breeding age of this animal
     * @return the breeding age of the animal
     */
    abstract protected int getBreedingAge();

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    public void incrementAge() {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Get the maximum age of the animal before death
     * @return animal's max age before death
     */
    abstract protected int getMaxAge();

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    public int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getMaxLitterSize()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * Get the max litter size
     * @return the maximum number of offspring in a single litter
     */
    abstract protected int getMaxLitterSize();

    /**
     * Get the breeding probability
     * @return the probability of producing offspring
     */
    abstract protected double getBreedingProbability();
}
