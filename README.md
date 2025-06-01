# Spakbor Hills

Game 'Spakbor Hills' ini dibuat oleh:

#### IF2010 - Kelompok K06 G05

#### 18221339 - Andi Syaichul Mubaraq

#### 18223117 - Khairunnisa Azizah

#### 18223132 - Muhammad Rafly Fauzan

#### 18223130 - M Rabbani K A

#### 18223136 - Geraldo Linggom Samuel T.

# Instalasi dan Penggunaan Gradle untuk Java di Windows

## Prasyarat

Pastikan kamu sudah menginstal perangkat berikut:

- Java Development Kit (JDK) versi 8 atau lebih tinggi
- Gradle (untuk pengelolaan dependensi dan build otomatis)

### 1. Instalasi Gradle di Windows

#### a. Menggunakan Installer Gradle

1. Kunjungi situs resmi Gradle di [https://gradle.org/install/](https://gradle.org/install/).
2. Klik **"Windows"** pada bagian **"Manual Installation"** dan unduh file `.zip`.
3. Ekstrak file `.zip` yang sudah diunduh ke lokasi yang kamu pilih, misalnya `C:\Gradle`.

#### b. Mengonfigurasi Variabel Lingkungan (Environment Variables)

1. Klik kanan pada **This PC** atau **Computer** dan pilih **Properties**.
2. Pilih **Advanced system settings** di sisi kiri.
3. Klik tombol **Environment Variables**.
4. Di bagian **System variables**, klik **New** untuk menambahkan variabel baru:
   - **Variable name**: `GRADLE_HOME`
   - **Variable value**: `C:\Gradle\gradle-x.y.z` (Gantilah `x.y.z` dengan versi Gradle yang kamu instal, misalnya `gradle-7.0`).
5. Pada bagian **System variables**, cari variabel **Path**, lalu klik **Edit** dan tambahkan path ke folder `bin` di dalam direktori Gradle yang baru saja kamu ekstrak. Contoh: `C:\Gradle\gradle-x.y.z\bin`.

#### c. Verifikasi Instalasi Gradle

Untuk memastikan Gradle terinstal dengan benar, buka **Command Prompt** dan jalankan perintah berikut:

```bash
gradle -v
```

# Panduan Menjalankan Game Spakbor Hills

## Cara Menjalankan

1. Masuk ke folder project
2. Jalankan aplikasi

```cmd
./gradlew build
```

3. Kemudian run

```cmd
./gradlew run
```
