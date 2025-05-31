public class RelationshipInfo {
    private String name;
    private int heartPoint;
    private RelationshipStatus status;

    public RelationshipInfo(String name, int heartPoint, RelationshipStatus status) {
        this.name = name;
        this.heartPoint = heartPoint;
        this.status = status;
    }

    public void displayInfo() {
        System.out.println("NPC: " + name);
        System.out.println("Heart Point: " + heartPoint);
        System.out.println("Status: " + status);
    }
}
