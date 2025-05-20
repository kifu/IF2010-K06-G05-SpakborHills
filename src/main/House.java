import java.util.*;

public class House {
    private List<Action> actions;

    public House(List<Action> houseActions) {
        this.actions = houseActions;
    }

    public void showMenu(Player player) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\n== House Menu ==");
            for (int i = 0; i < actions.size(); i++) {
                System.out.println((i+1) + ". " + actions.get(i).getName());
            }
            System.out.println("0. Exit");
            System.out.print("Pilih aksi: ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                exit = true;
                System.out.println("Keluar dari House.");
            } else if (choice > 0 && choice <= actions.size()) {
                actions.get(choice-1).execute(player);
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }
}