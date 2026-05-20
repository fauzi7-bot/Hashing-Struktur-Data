import java.util.*;
import java.util.LinkedList;
import java.util.Scanner;


public class HashTable {

    static final int TABLE_SIZE = 127; // Bilangan prima agar distribusi merata

    // Setiap slot berupa LinkedList untuk menangani collision (chaining)
    private LinkedList<Integer>[] table;
    private int totalData;

    // ----------------------------------------------------------
    // KONSTRUKTOR
    // ----------------------------------------------------------
    @SuppressWarnings("unchecked")
    public HashTable(int size) {
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
        totalData = 0;
    }

    // ----------------------------------------------------------
    // HASH FUNCTION: modulo dengan ukuran tabel
    // ----------------------------------------------------------
    private int hashFunction(int key) {
        return key % TABLE_SIZE;
    }

    // ----------------------------------------------------------
    // 1. INPUT DATA
    // ----------------------------------------------------------
    public boolean insert(int key) {
        int index = hashFunction(key);
        LinkedList<Integer> chain = table[index];

        // Cek duplikat
        if (chain.contains(key)) {
            System.out.println("  [!] Data " + key + " sudah ada di tabel (duplikat ditolak).");
            return false;
        }

        chain.add(key);
        totalData++;
        System.out.println("  [+] Data " + key + " berhasil dimasukkan -> index [" + index + "]");
        return true;
    }

    // ----------------------------------------------------------
    // 2. HAPUS DATA
    // ----------------------------------------------------------
    public boolean delete(int key) {
        int index = hashFunction(key);
        LinkedList<Integer> chain = table[index];

        if (chain.remove(Integer.valueOf(key))) {
            totalData--;
            System.out.println("  [-] Data " + key + " berhasil dihapus dari index [" + index + "].");
            return true;
        } else {
            System.out.println("  [!] Data " + key + " tidak ditemukan. Tidak ada yang dihapus.");
            return false;
        }
    }

    // ----------------------------------------------------------
    // 3. CARI DATA
    // ----------------------------------------------------------
    public boolean search(int key) {
        int index = hashFunction(key);
        LinkedList<Integer> chain = table[index];

        int pos = chain.indexOf(key);
        if (pos != -1) {
            System.out
                    .println("  [v] Data " + key + " DITEMUKAN -> index [" + index + "], posisi chain ke-" + pos + ".");
            return true;
        } else {
            System.out.println("  [x] Data " + key + " TIDAK ditemukan di tabel.");
            return false;
        }
    }

    // ----------------------------------------------------------
    // TAMPILKAN TABEL (hanya slot yang terisi)
    // ----------------------------------------------------------
    public void display() {
        System.out.println("\n" + "=".repeat(55));
        System.out.printf("  HASH TABLE  (size=%d, total data=%d)%n", TABLE_SIZE, totalData);
        System.out.println("=".repeat(55));

        int occupied = 0;
        int collisionSlots = 0;

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (!table[i].isEmpty()) {
                occupied++;
                if (table[i].size() > 1)
                    collisionSlots++;
                System.out.printf("  [%3d] -> %s%n", i, table[i]);
            }
        }

        System.out.println("-".repeat(55));
        System.out.printf("  Slot terisi    : %d / %d%n", occupied, TABLE_SIZE);
        System.out.printf("  Slot collision : %d%n", collisionSlots);
        System.out.printf("  Load factor    : %.3f%n", (double) totalData / TABLE_SIZE);
        System.out.println("=".repeat(55) + "\n");
    }

    // ==============================================================
    // MAIN PROGRAM
    // ==============================================================
    public static void main(String[] args) {
        HashTable ht = new HashTable(TABLE_SIZE);
        Scanner scanner = new Scanner(System.in);

        // --- Inisialisasi: masukkan 100 angka random unik (1 – 9999) ---
        System.out.println("=".repeat(55));
        System.out.println("  Inisialisasi: memasukkan 100 angka random unik...");
        System.out.println("=".repeat(55));

        List<Integer> pool = new ArrayList<>();
        for (int i = 1; i <= 9999; i++)
            pool.add(i);
        Collections.shuffle(pool);
        List<Integer> randomData = pool.subList(0, 100);

        for (int num : randomData) {
            ht.insert(num);
        }

        List<Integer> sorted = new ArrayList<>(randomData);
        Collections.sort(sorted);
        System.out.println("\n  100 data berhasil dimasukkan.");
        System.out.println("  Data awal: " + sorted + "\n");

        // --- Menu Interaktif ---
        while (true) {
            System.out.println("\n" + "=".repeat(55));
            System.out.println("         MENU HASH TABLE");
            System.out.println("=".repeat(55));
            System.out.println("  1. Input Data");
            System.out.println("  2. Hapus Data");
            System.out.println("  3. Cari Data");
            System.out.println("  4. Tampilkan Hash Table");
            System.out.println("  5. Keluar");
            System.out.println("=".repeat(55));
            System.out.print("  Pilih menu (1-5): ");

            String pilihan = scanner.nextLine().trim();

            switch (pilihan) {
                case "1":
                    System.out.println("\n  -- INPUT DATA --");
                    System.out.print("  Masukkan angka yang ingin diinput: ");
                    try {
                        int nilai = Integer.parseInt(scanner.nextLine().trim());
                        ht.insert(nilai);
                    } catch (NumberFormatException e) {
                        System.out.println("  [!] Input tidak valid. Masukkan angka bulat.");
                    }
                    break;

                case "2":
                    System.out.println("\n  -- HAPUS DATA --");
                    System.out.print("  Masukkan angka yang ingin dihapus: ");
                    try {
                        int nilai = Integer.parseInt(scanner.nextLine().trim());
                        ht.delete(nilai);
                    } catch (NumberFormatException e) {
                        System.out.println("  [!] Input tidak valid. Masukkan angka bulat.");
                    }
                    break;

                case "3":
                    System.out.println("\n  -- CARI DATA --");
                    System.out.print("  Masukkan angka yang ingin dicari: ");
                    try {
                        int nilai = Integer.parseInt(scanner.nextLine().trim());
                        ht.search(nilai);
                    } catch (NumberFormatException e) {
                        System.out.println("  [!] Input tidak valid. Masukkan angka bulat.");
                    }
                    break;

                case "4":
                    ht.display();
                    break;

                case "5":
                    System.out.println("\n  Program selesai. Sampai jumpa!\n");
                    scanner.close();
                    return;

                default:
                    System.out.println("  [!] Pilihan tidak valid. Masukkan 1-5.");
            }
        }
    }
}
