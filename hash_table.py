import random

# ============================================================
#   HASH TABLE - Tugas #8: Hashing in Data Structure
#   Teknik Collision: Chaining (Separate Chaining)
#   Nama: [Isi Nama Anda]
# ============================================================

TABLE_SIZE = 127  # Ukuran hash table (bilangan prima agar distribusi merata)


class HashTable:
    def __init__(self, size):
        self.size = size
        # Setiap slot berupa list (chain) untuk menangani collision
        self.table = [[] for _ in range(size)]
        self.total_data = 0

    # ----------------------------------------------------------
    # HASH FUNCTION: modulo dengan ukuran tabel (bilangan prima)
    # ----------------------------------------------------------
    def hash_function(self, key):
        return key % self.size

    # ----------------------------------------------------------
    # 1. INPUT DATA
    # ----------------------------------------------------------
    def insert(self, key):
        index = self.hash_function(key)
        chain = self.table[index]

        # Cek duplikat
        if key in chain:
            print(f"  [!] Data {key} sudah ada di tabel (duplikat ditolak).")
            return False

        chain.append(key)
        self.total_data += 1
        print(f"  [+] Data {key} berhasil dimasukkan -> index [{index}]")
        return True

    # ----------------------------------------------------------
    # 2. HAPUS DATA
    # ----------------------------------------------------------
    def delete(self, key):
        index = self.hash_function(key)
        chain = self.table[index]

        if key in chain:
            chain.remove(key)
            self.total_data -= 1
            print(f"  [-] Data {key} berhasil dihapus dari index [{index}].")
            return True
        else:
            print(f"  [!] Data {key} tidak ditemukan. Tidak ada yang dihapus.")
            return False

    # ----------------------------------------------------------
    # 3. CARI DATA
    # ----------------------------------------------------------
    def search(self, key):
        index = self.hash_function(key)
        chain = self.table[index]

        if key in chain:
            pos = chain.index(key)
            print(f"  [✓] Data {key} DITEMUKAN -> index [{index}], posisi chain ke-{pos}.")
            return True
        else:
            print(f"  [✗] Data {key} TIDAK ditemukan di tabel.")
            return False

    # ----------------------------------------------------------
    # TAMPILKAN TABEL (ringkas: hanya slot yang terisi)
    # ----------------------------------------------------------
    def display(self):
        print("\n" + "=" * 55)
        print(f"  HASH TABLE  (size={self.size}, total data={self.total_data})")
        print("=" * 55)
        occupied = 0
        collision_slots = 0
        for i, chain in enumerate(self.table):
            if chain:
                occupied += 1
                if len(chain) > 1:
                    collision_slots += 1
                print(f"  [{i:>3}] -> {chain}")
        print("-" * 55)
        print(f"  Slot terisi     : {occupied} / {self.size}")
        print(f"  Slot collision  : {collision_slots}")
        load_factor = self.total_data / self.size
        print(f"  Load factor     : {load_factor:.3f}")
        print("=" * 55 + "\n")


# ==============================================================
#   MAIN PROGRAM
# ==============================================================
def main():
    ht = HashTable(TABLE_SIZE)

    # --- Inisialisasi: masukkan 100 angka random unik (1 – 9999) ---
    print("=" * 55)
    print("  Inisialisasi: memasukkan 100 angka random unik...")
    print("=" * 55)
    random_data = random.sample(range(1, 10000), 100)
    for num in random_data:
        ht.insert(num)

    print(f"\n  100 data berhasil dimasukkan.")
    print(f"  Data awal: {sorted(random_data)}\n")

    # --- Menu Interaktif ---
    while True:
        print("\n" + "=" * 55)
        print("         MENU HASH TABLE")
        print("=" * 55)
        print("  1. Input Data")
        print("  2. Hapus Data")
        print("  3. Cari Data")
        print("  4. Tampilkan Hash Table")
        print("  5. Keluar")
        print("=" * 55)

        pilihan = input("  Pilih menu (1-5): ").strip()

        if pilihan == "1":
            print("\n  -- INPUT DATA --")
            try:
                nilai = int(input("  Masukkan angka yang ingin diinput: "))
                ht.insert(nilai)
            except ValueError:
                print("  [!] Input tidak valid. Masukkan angka bulat.")

        elif pilihan == "2":
            print("\n  -- HAPUS DATA --")
            try:
                nilai = int(input("  Masukkan angka yang ingin dihapus: "))
                ht.delete(nilai)
            except ValueError:
                print("  [!] Input tidak valid. Masukkan angka bulat.")

        elif pilihan == "3":
            print("\n  -- CARI DATA --")
            try:
                nilai = int(input("  Masukkan angka yang ingin dicari: "))
                ht.search(nilai)
            except ValueError:
                print("  [!] Input tidak valid. Masukkan angka bulat.")

        elif pilihan == "4":
            ht.display()

        elif pilihan == "5":
            print("\n  Program selesai. Sampai jumpa!\n")
            break

        else:
            print("  [!] Pilihan tidak valid. Masukkan 1-5.")


if __name__ == "__main__":
    main()
