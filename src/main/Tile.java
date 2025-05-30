package main;
public class Tile{
    private TileType type;
    private boolean isWatered;
    private Seeds plantedItem;
    private int daysGrown;

    public enum TileType{
        Soil("Soil"),
        Tilled("Tilled"),
        Planted("Planted"),
        Harvest("Harvest");

        public String displayName;
        TileType(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
    public Tile(){
        this.type = TileType.Soil;
        this.isWatered = true;
        this.plantedItem = null;
        this.daysGrown = 0;
    }
    public Tile(TileType type, boolean isWatered, Seeds plantedItem, int daysGrown){
        this.type = type;
        this.isWatered = isWatered;
        this.plantedItem = plantedItem;
        this.daysGrown = daysGrown;
    }
    
    /**
     * mengembalikan tipe tile yang dipijak
     * @return Tiletype 
     */
    public TileType getType(){
        return this.type;
    }

    /**
     * memengubah tipe tile pada posisi player yang dipijak 
     * @param Tiletype
     */
    public void setType(TileType newtype){
        this.type = newtype;
    }

    /**
     * memberikan nilai boolean pada kondisi isWatered
     * @return boolean 
     */
    public boolean isWatered(){
        return this.isWatered;
    }

    /**
     * mengubah kondisi isWatered tanah
     * @param boolean 
     */
    public void setIsWatered(boolean status){
        this.isWatered = status;
    }

    /**
     * memengembalikan item yang ditanam pada tiled lad
     * @return Tiletype 
     */
    public Seeds getPlanted(){
        return this.plantedItem;
    }

    /**
     * mengubah item yang ditanam pada tiled lad
     * @param Seeds
     */
    public void setPlanted(Seeds seedsPlanted){
        this.plantedItem = seedsPlanted;
    }
    /**
     * menunjukan progress harian dari tile
     * @return int 
     */
    public int daysGrown(){
        return this.daysGrown;
    }

    public void incrementDays(){
        this.daysGrown++;
    }
    
    public boolean isHarvestable(){
        if (this.daysGrown == this.plantedItem.getHarvestDuration()){
            this.daysGrown = 0;
            return true;
        }
        else{
            return false;
        }
    
    }

}