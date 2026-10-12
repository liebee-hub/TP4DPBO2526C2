import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.Year;
import java.util.ArrayList;

public class PeopleMenu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        PeopleMenu menu = new PeopleMenu();

        // atur ukuran window
        menu.setSize(700, 600);

        // letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris tabel yang sedang dipilih (-1 = tidak ada)
    private int selectedIndex = -1;
    // list untuk menampung semua data person
    private ArrayList<Person> listPerson;

    // component hasil binding dari PeopleMenu.form
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunLahirField;
    private JComboBox<String> kategoriComboBox;
    private JSpinner kucingSpinner;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JTable peopleTable;

    // constructor
    public PeopleMenu() {
        // inisialisasi listPerson
        listPerson = new ArrayList<>();

        // isi listPerson
        populateList();

        // isi tabel person
        peopleTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = { "???", "Mahasiswa", "Dosen", "Tenaga Kependidikan", "Alumni" };
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // atur spinner jumlah kucing (nilai awal 0, minimal 0, maksimal 99, naik 1)
        kucingSpinner.setModel(new SpinnerNumberModel(0, 0, 99, 1));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan -> minta konfirmasi dulu
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = listPerson.get(selectedIndex).getNama();
                int pilihan = JOptionPane.showConfirmDialog(
                        PeopleMenu.this,
                        "Yakin ingin menghapus data \"" + nama + "\"?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (pilihan == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        peopleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                int row = peopleTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                selectedIndex = row;

                // simpan value textfield, combo box, dan spinner
                String curId = peopleTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = peopleTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curTahun = peopleTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = peopleTable.getModel().getValueAt(selectedIndex, 4).toString();
                String curKucing = peopleTable.getModel().getValueAt(selectedIndex, 5).toString();

                // ubah isi textfield, combo box, dan spinner
                idField.setText(curId);
                namaField.setText(curNama);
                tahunLahirField.setText(curTahun);
                kategoriComboBox.setSelectedItem(curKategori);
                kucingSpinner.setValue(Integer.parseInt(curKucing));

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = { "No", "ID", "Nama", "Tahun Lahir", "Kategori", "Jumlah Kucing" };

        // buat objek tabel dengan kolom yang sudah dibuat
        // (sel dibuat tidak bisa diedit langsung, edit lewat form)
        DefaultTableModel tmp = new DefaultTableModel((Object[][]) null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // isi tabel dengan listPerson
        for (int i = 0; i < listPerson.size(); i++) {
            Object[] row = { i + 1,
                    listPerson.get(i).getId(),
                    listPerson.get(i).getNama(),
                    listPerson.get(i).getTahunLahir(),
                    listPerson.get(i).getKategori(),
                    listPerson.get(i).getJumlahKucing()
            };
            tmp.addRow(row);
        }

        return tmp;
    }

    public void insertData() {
        // ambil & validasi value dari form
        Person person = buildPersonFromForm();
        if (person == null) {
            return;
        }

        // ID tidak boleh kembar
        if (isIdUsed(person.getId(), -1)) {
            showError("ID sudah digunakan!");
            return;
        }

        // tambahkan data ke dalam list
        listPerson.add(person);

        // update tabel
        peopleTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert berhasil");
        JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan");
    }

    public void updateData() {
        // ambil & validasi data dari form
        Person person = buildPersonFromForm();
        if (person == null) {
            return;
        }

        // ID tidak boleh kembar dengan data lain
        if (isIdUsed(person.getId(), selectedIndex)) {
            showError("ID sudah digunakan!");
            return;
        }

        // ubah data person di list
        Person target = listPerson.get(selectedIndex);
        target.setId(person.getId());
        target.setNama(person.getNama());
        target.setTahunLahir(person.getTahunLahir());
        target.setKategori(person.getKategori());
        target.setJumlahKucing(person.getJumlahKucing());

        // update tabel
        peopleTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update berhasil");
        JOptionPane.showMessageDialog(this, "Data berhasil diubah");
    }

    public void deleteData() {
        // hapus data dari list
        listPerson.remove(selectedIndex);

        // update tabel
        peopleTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil");
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
    }

    public void clearForm() {
        // kosongkan semua textfield, combo box, dan spinner
        idField.setText("");
        namaField.setText("");
        tahunLahirField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        kucingSpinner.setValue(0);

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // hilangkan highlight baris tabel
        peopleTable.clearSelection();

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    // ---------- method bantu ----------

    // baca isi form, validasi, lalu kembalikan sebagai object Person (null jika tidak valid)
    private Person buildPersonFromForm() {
        String id = idField.getText().trim();
        String nama = namaField.getText().trim();
        String tahunText = tahunLahirField.getText().trim();

        if (id.isEmpty() || nama.isEmpty() || tahunText.isEmpty()) {
            showError("ID, Nama, dan Tahun Lahir tidak boleh kosong!");
            return null;
        }

        int tahunLahir;
        try {
            tahunLahir = Integer.parseInt(tahunText);
        } catch (NumberFormatException ex) {
            showError("Tahun lahir harus berupa angka!");
            return null;
        }

        int tahunSekarang = Year.now().getValue();
        if (tahunLahir < 1900 || tahunLahir > tahunSekarang) {
            showError("Tahun lahir harus antara 1900 dan " + tahunSekarang + "!");
            return null;
        }

        if (kategoriComboBox.getSelectedIndex() <= 0) {
            showError("Silakan pilih kategori!");
            return null;
        }
        String kategori = kategoriComboBox.getSelectedItem().toString();

        // pastikan teks yang diketik di spinner sudah diproses menjadi nilai
        try {
            kucingSpinner.commitEdit();
        } catch (ParseException ex) {
            showError("Jumlah kucing harus berupa angka bulat 0 - 99!");
            return null;
        }
        int jumlahKucing = (Integer) kucingSpinner.getValue();

        return new Person(id, nama, tahunLahir, kategori, jumlahKucing);
    }

    // cek apakah ID sudah dipakai (ignoreIndex = index yang dikecualikan, -1 jika tidak ada)
    private boolean isIdUsed(String id, int ignoreIndex) {
        for (int i = 0; i < listPerson.size(); i++) {
            if (i != ignoreIndex && listPerson.get(i).getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // data awal
    private void populateList() {
        listPerson.add(new Person("P001", "Ucup Santoso", 2004, "Mahasiswa", 2));
        listPerson.add(new Person("P002", "Alucard", 2003, "Mahasiswa", 0));
        listPerson.add(new Person("P003", "Alok", 1985, "Dosen", 1));
        listPerson.add(new Person("P004", "Mas Rusdi", 1990, "Dosen", 3));
        listPerson.add(new Person("P005", "Si Imut", 1995, "Tenaga Kependidikan", 0));
        listPerson.add(new Person("P006", "Wednesday", 1998, "Tenaga Kependidikan", 5));
        listPerson.add(new Person("P007", "Heri Puter", 2001, "Alumni", 1));
        listPerson.add(new Person("P008", "Sepidermen", 2000, "Alumni", 0));
        listPerson.add(new Person("P009", "Om Gatot", 2005, "Mahasiswa", 4));
        listPerson.add(new Person("P010", "Titut", 2002, "Mahasiswa", 2));
    }
}
