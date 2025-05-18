package test;
import  java.util.ArrayList;
import javax.swing.LookAndFeel;
public class NPC{
        private String name;
        private int heartPoint;
        private ArrayList<Item> loveditems;
        private ArrayList<Item> likeditems;
        private RelationshipStatus relationshipStatus;
        private int chattingcount;
        private int giftingcouint;
        private int visitingcount;
        
        public NPC(String name, int heartPoint, ArrayList<Item> loveditems, ArrayList<Item> likeditems, RelationshipStatus relationshipStatus, int chattingcount, int giftingcouint,  int visitingcount){
            this.name = name;
            this.heartPoint = heartPoint;
            this.loveditems = loveditems;
            this.likeditems = likeditems;
            this.relationshipStatus = relationshipStatus;
            this.chattingcount = chattingcount;
            this.giftingcouint = giftingcouint;
            this.visitingcount = visitingcount;

        }

    public String getName(){
        return this.name;
    }

    public int getHeartpoin(){
        return this.heartPoint;
    }

    public int getchattingcount(){
        return this.chattingcount;
    }

    public int getgiftingcount(){
        return this.giftingcouint;
    }

    public int getvisitorcount(){
        return this.visitingcount;
    }

     public String setName(String newName){
        this.name = newName;
    }

    public void serHeartpoin(int newHP){
        this.heartPoint = newHP;
    }

    public void setchattingcount(int newChattingcount){
       this.chattingcount = newChattingcount;
    }

    public void setgiftingcount(int newgiftcount){
        this.giftingcouint = newgiftcount;
    }

    public voidsetvisitorcount(){
        return this.visitingcount;
    }
} 
