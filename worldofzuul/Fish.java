package worldofzuul;

/**When toString is called on these objects it returns  {@link #toString() Fish.toString}  */
public enum Fish {
    MAKREL("Makrel", 25, 1.2, 0.1, 1.06),
    SILD("Sild", 30, 1.3, 0.2, 1.03),
    LAKS("Laks", 45, 1.35, 0.2, 1.07),
    ÅL("Ål", 20, 1.5, 0.15, 1.06);


    private String name;
    private double salesPrice;
    private double reproductionRate;
    private double deathRate;
    private double migrationRate;


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
