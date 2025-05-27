package test;

public class Seeds extends Item{
    // atribut
    private Season SeedsSeason;
    private Int HarvDur;
    private Gold SeedsPrice;
    // konstruktor
    /** Membuat object Seed Baru
     * syntax: Seeds(String name, Season S, int Dur, Gold Price)
     * @param String name
     * @param Season S
     * @param int Dur
     * @param Gold Price
     */
    public Seeds(String name, Season S, int Dur, Gold Price){
       super(name);
       this.SeedsSeason = S;
       this.HarvDur = Dur;
       this.SeedsPrice = Price;

    }
    @Override
    /** Mengembalikan item 
     * @return Seeds
    */
    public String getItem(){
        return Seeds;
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
     * memberikan nilai Price dari seed
     * @return Gold
     */
    public Gold getPrice(Seeds seed){
        return this.SeedsPrice;
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
    /**
     * memberikan nilai Harverst Duration dari seed
     * @param Seeds seed
     * @param Gold newPrice
     */
    public void setSeedsPrice(Seeds seed, Gold newPrice){
        seed.SeedsPrice = newPrice;
    }

}