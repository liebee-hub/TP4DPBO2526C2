# TP4 DPBO – PeopleData (Java Swing GUI)

## Janji

Saya Refan Maher Aysel dengan NIM 2509129 mengerjakan Tugas Praktikum 4 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Isi program

Aplikasi desktop CRUD sederhana berbasis **Java Swing** (IntelliJ GUI Designer) untuk mengelola data orang di lingkungan kampus.

## Struktur Folder

```
PeopleData/
├── src/
│   ├── Person.java        # model data
│   ├── PeopleMenu.java    # logika GUI + CRUD
│   └── PeopleMenu.form    # desain tampilan (GUI Designer)
├── Dokumentasi/           # screenshot 
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

## Dokumentasi Program Dijalankan

Tampilan Awal
<img width="1917" height="1077" alt="tampilan awal" src="https://github.com/user-attachments/assets/05e42d79-008e-492a-bdac-9ee513403eed" />

Create
<img width="1917" height="1077" alt="create" src="https://github.com/user-attachments/assets/1bde7391-f4a6-473b-97e9-90e5f873e438" />

Read
<img width="1917" height="1077" alt="read" src="https://github.com/user-attachments/assets/1abb9af9-b66d-4000-8c92-75ef1ebdc4dd" />

Update
<img width="1917" height="1077" alt="update" src="https://github.com/user-attachments/assets/b52de9fb-bc0f-4e72-9230-ced0d64fde2a" />

Konfirmasi Delete
<img width="1917" height="1077" alt="konfirm delete" src="https://github.com/user-attachments/assets/c9ba357d-cf35-4b3f-a20a-a4fa1f03a871" />

Delete
<img width="1917" height="1077" alt="delete" src="https://github.com/user-attachments/assets/05451196-ccb8-40ad-83b8-f4b1805171c0" />

Contoh Error
<img width="1917" height="1077" alt="contoh error" src="https://github.com/user-attachments/assets/570ff3e8-3092-460d-bb0b-2a934ca1ab14" />






