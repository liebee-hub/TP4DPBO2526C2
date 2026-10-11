# TP4 DPBO – PeopleData (Java Swing GUI)

Aplikasi desktop CRUD sederhana berbasis **Java Swing** (IntelliJ GUI Designer) untuk mengelola data orang di lingkungan kampus.
Struktur kodenya mengikuti latihan `ProductData`, dengan tema **People**.

## Struktur Folder

```
PeopleData/
├── src/
│   ├── Person.java        # model data
│   ├── PeopleMenu.java    # logika GUI + CRUD
│   └── PeopleMenu.form    # desain tampilan (GUI Designer)
├── Dokumentasi/           # screenshot / gif / video untuk README
└── README.md
```

## Desain & Fungsi Program

### Atribut `Person`

| Atribut | Tipe | Komponen di Form | Keterangan |
|---|---|---|---|
| `id` | `String` | `JTextField` | Unik, tidak boleh kembar |
| `nama` | `String` | `JTextField` | |
| `tahunLahir` | `int` | `JTextField` | Harus angka, rentang 1900 – tahun sekarang |
| `kategori` | `String` | `JComboBox` | Mahasiswa / Dosen / Tenaga Kependidikan / Alumni |
| **`jumlahKucing`** *(atribut baru)* | `int` | **`JSpinner`** | Jumlah kucing peliharaan, rentang 0 – 99 (`SpinnerNumberModel`) |

> Atribut baru **Jumlah Kucing** memakai `JSpinner` (bukan `JTextField`), sehingga memenuhi **BONUS (+20)**.

### Komponen Form

- `titleLabel` – judul "Data Orang"
- `idField`, `namaField`, `tahunLahirField` – input teks
- `kategoriComboBox` – pilihan kategori (item pertama `???` = belum memilih)
- `kucingSpinner` – input jumlah kucing peliharaan (0 – 99)
- `addUpdateButton` – berubah teks **Add ⇄ Update** sesuai mode
- `cancelButton` – membersihkan form
- `deleteButton` – hanya tampil saat sebuah baris dipilih
- `peopleTable` – menampilkan seluruh data (kolom: No, ID, Nama, Tahun Lahir, Kategori, Jumlah Kucing; sel tidak bisa diedit langsung)

### Fitur Tambahan

- **Confirmation prompt sebelum delete** (`JOptionPane.showConfirmDialog` Yes/No). Data hanya dihapus jika user memilih **Yes**.
- **Validasi input**: field wajib terisi, tahun lahir harus angka & masuk akal, kategori wajib dipilih, jumlah kucing harus angka bulat 0 – 99, ID tidak boleh duplikat.

## Alur Program

1. **Start** – `main()` membuat window `PeopleMenu`. Constructor menginisialisasi `listPerson`, mengisi data awal lewat `populateList()` (10 data), lalu menampilkannya di tabel lewat `setTable()`.
2. **Create (Add)** – Saat tidak ada baris terpilih (`selectedIndex == -1`), tombol berlabel **Add**. Klik → `insertData()`: baca & validasi form → cek ID unik → `listPerson.add(...)` → refresh tabel → `clearForm()`.
3. **Read** – Seluruh isi `listPerson` selalu ditampilkan di tabel. Klik sebuah baris → `selectedIndex` diisi, nilai baris dimasukkan ke form (termasuk combo box & spinner), tombol berubah menjadi **Update**, dan tombol **Delete** muncul.
4. **Update** – Saat baris terpilih, klik **Update** → `updateData()`: baca & validasi form → cek ID unik (selain dirinya sendiri) → ubah objek di `listPerson` → refresh tabel → `clearForm()`.
5. **Delete** – Klik **Delete** → muncul dialog konfirmasi. Jika **Yes** → `deleteData()`: `listPerson.remove(selectedIndex)` → refresh tabel → `clearForm()`. Jika **No** → tidak ada perubahan.
6. **Cancel** – `clearForm()`: kosongkan semua input, kembalikan tombol ke **Add**, sembunyikan **Delete**, `selectedIndex = -1`.

## Cara Menjalankan

1. Buka IntelliJ IDEA, buat project Java baru bernama `PeopleData` (uncheck *Add sample code*).
2. Pastikan plugin **Swing UI Designer** sudah terpasang.
3. Salin `Person.java`, `PeopleMenu.java`, dan `PeopleMenu.form` ke folder `src`.
4. Jalankan `PeopleMenu.java`.

## Dokumentasi Program Dijalankan

> Letakkan file gambar/gif/video di folder `Dokumentasi/` lalu sesuaikan nama file di bawah ini.

| Operasi | Dokumentasi |
|---|---|
| Tampilan awal | ![Tampilan awal](Dokumentasi/01-tampilan-awal.png) |
| **Create** – menambah data | ![Create](Dokumentasi/02-create.png) |
| **Read** – klik baris, data masuk ke form | ![Read](Dokumentasi/03-read.png) |
| **Update** – mengubah data | ![Update](Dokumentasi/04-update.png) |
| **Delete** – dialog konfirmasi & hasil hapus | ![Delete](Dokumentasi/05-delete.png) |
| Validasi error | ![Validasi](Dokumentasi/06-validasi.png) |

Screen record lengkap semua operasi CRUD: `Dokumentasi/demo.gif`
