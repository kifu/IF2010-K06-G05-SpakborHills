package main;

public class Seeds extends Item{
    // atribut
    private Season SeedsSeason;
    private int HarvDur;
    // konstruktor
    /** Membuat object Seed Baru
     * syntax: Seeds(String name, Season S, int Dur, Gold Price)
     * @param String name
     * @param Season S
     * @param int Dur
     * @param Gold Price
     */
    public Seeds(String name, Season S, int Dur, int buyPrice){
       super(name,buyPrice,0);
       this.SeedsSeason = S;
       this.HarvDur = Dur;

    }
    /**
     * memberikan nilai nama dari seed
     * @return String
     */
    @Override
    public String getName(){
        return super.getName().concat("Seeds");
    }
    /**
     * memberikan nilai Harverst Duration dari seed
     * @return int
     */
    public int getHarvestDuration(){
        return this.HarvDur;
    }
    /**
     * memberikan nilai Season dari seed
     * @return Season
     */
    public Season getSeason(){
        /* memberika season dari seed */
        return this.SeedsSeason;
    }

    /**
     * mengubah nilai Harverst Duration dari seed
     * @param Seeds seed
     * @param int newDur
     */
    public void setHarvestDuration(Seeds seed, int newDur){
        seed.HarvDur = newDur;
    }
    /**
     * menguban nilai Seeds season dari seed
     * @param Seeds seed
     * @param Season newSS
     */
    public void setSeedsSeason(Seeds seed, Season newSS){
        seed.SeedsSeason = newSS;
    }
    

}