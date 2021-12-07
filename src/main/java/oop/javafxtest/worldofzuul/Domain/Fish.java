package oop.javafxtest.worldofzuul.Domain;

/**When toString is called on these objects it returns  {@link #toString() Fish.toString}  */
enum Fish {
    MAKREL("Makrel", 0.2, 1.02, 0.1, 1.0001),
    SILD("Sild", 0.5, 1.03, 0.2, 1.0001),
    LAKS("Laks", 0.6, 1.035, 0.2, 1.0001),
    ÅL("Ål", 0.3, 1.05, 0.15, 1.0001);

    private final String name;
    private final double salesPrice;
    private final double reproductionRate;
    private final double deathRate;
    private final double migrationRate;

    //constructor:
    Fish(String name, double salesPrice, double reproductionRate, double deathRate, double migrationRate) {
        this.name = name;
        this.salesPrice = salesPrice;
        this.reproductionRate = reproductionRate;
        this.deathRate = deathRate;
        this.migrationRate = migrationRate;
    }

    //Getters for all attributes
    public String getName() {
        return name;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public double getReproductionRate() {

        return reproductionRate;
    }

    public double getDeathRate() {

        return deathRate;
    }

    public double getMigrationRate() {
        return migrationRate;
    }

    /** @return the name/type of the fish */
    @Override
    public String toString()
    {
        return this.name;
    }
}
