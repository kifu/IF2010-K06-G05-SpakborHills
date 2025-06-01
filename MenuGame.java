public class MenuGame {
    public static void printSpakborHillsArt() {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ███████╗██████╗  █████╗ ██╗  ██╗██████╗  ██████╗ ██████╗     ██╗  ██╗██╗██╗     ██╗     ███████╗ ║");
        System.out.println("║  ██╔════╝██╔══██╗██╔══██╗██║ ██╔╝██╔══██╗██╔═══██╗██╔══██╗    ██║  ██║██║██║     ██║     ██╔════╝ ║");
        System.out.println("║  ███████╗██████╔╝███████║█████╔╝ ██████╔╝██║   ██║██████╔╝    ███████║██║██║     ██║     ███████╗ ║");
        System.out.println("║  ╚════██║██╔═══╝ ██╔══██║██╔═██╗ ██╔══██╗██║   ██║██╔══██╗    ██╔══██║██║██║     ██║     ╚════██║ ║");
        System.out.println("║  ███████║██║     ██║  ██║██║  ██╗██████╔╝╚██████╔╝██║  ██║    ██║  ██║██║███████╗███████╗███████║ ║");
        System.out.println("║  ╚══════╝╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝    ╚═╝  ╚═╝╚═╝╚══════╝╚══════╝╚══════╝ ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }

    // Jika permainan belum dimulai
    public static void showMenuUnstarted() {
        System.out.println("===== Menu Utama =====");
        System.out.println("1. New Game");
        System.out.println("2. Help");
        System.out.println("3. Credits");
        System.out.println("4. Exit");
        System.out.println("=======================");
        System.out.print("Pilih aksi (1-4): ");
    }

    // Jika permainan sudah dimulai
    public static void showMenuStarted() {
        System.out.println("===== Menu Utama =====");
        System.out.println("1. New Game");
        System.out.println("2. Help");
        System.out.println("3. View Player Info");
        System.out.println("4. Statistics");
        System.out.println("5. Actions");
        System.out.println("6. Credits");
        System.out.println("7. Exit");
        System.out.println("8. Menu Cheats (Dev)");
        System.out.println("=======================");
        System.out.print("Pilih aksi (1-8): ");
    }

    public static void main(String[] args) {
        MenuGame.showMenuStarted();
    }
}
