<<<<<<< HEAD
# IF2010_TB_06_05

# Instalasi dan Penggunaan Gradle untuk Java di Windows

## Prasyarat

Pastikan kamu sudah menginstal perangkat berikut:

=======
# Instalasi dan Penggunaan Gradle untuk Java di Windows

## Prasyarat
Pastikan kamu sudah menginstal perangkat berikut:
>>>>>>> c70650205c373d6105259983fd1ea1e55a6be258
- Java Development Kit (JDK) versi 8 atau lebih tinggi
- Gradle (untuk pengelolaan dependensi dan build otomatis)

### 1. Instalasi Gradle di Windows

#### a. Menggunakan Installer Gradle
<<<<<<< HEAD

=======
>>>>>>> c70650205c373d6105259983fd1ea1e55a6be258
1. Kunjungi situs resmi Gradle di [https://gradle.org/install/](https://gradle.org/install/).
2. Klik **"Windows"** pada bagian **"Manual Installation"** dan unduh file `.zip`.
3. Ekstrak file `.zip` yang sudah diunduh ke lokasi yang kamu pilih, misalnya `C:\Gradle`.

#### b. Mengonfigurasi Variabel Lingkungan (Environment Variables)
<<<<<<< HEAD

=======
>>>>>>> c70650205c373d6105259983fd1ea1e55a6be258
1. Klik kanan pada **This PC** atau **Computer** dan pilih **Properties**.
2. Pilih **Advanced system settings** di sisi kiri.
3. Klik tombol **Environment Variables**.
4. Di bagian **System variables**, klik **New** untuk menambahkan variabel baru:
   - **Variable name**: `GRADLE_HOME`
   - **Variable value**: `C:\Gradle\gradle-x.y.z` (Gantilah `x.y.z` dengan versi Gradle yang kamu instal, misalnya `gradle-7.0`).
5. Pada bagian **System variables**, cari variabel **Path**, lalu klik **Edit** dan tambahkan path ke folder `bin` di dalam direktori Gradle yang baru saja kamu ekstrak. Contoh: `C:\Gradle\gradle-x.y.z\bin`.

#### c. Verifikasi Instalasi Gradle
<<<<<<< HEAD

Untuk memastikan Gradle terinstal dengan benar, buka **Command Prompt** dan jalankan perintah berikut:

```bash
gradle -v
```
=======
Untuk memastikan Gradle terinstal dengan benar, buka **Command Prompt** dan jalankan perintah berikut:
```bash
gradle -v
>>>>>>> c70650205c373d6105259983fd1ea1e55a6be258
