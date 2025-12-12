# Snakey

| Nama           | NRP        |      
| ---            | ---        |
| Muhammad Zaky Zein | 5025241148 | 
| Isabella Sienna Sulisthio | 5025241199 | 
| Hartmann Kanisius Galla' Massang | 5025241160 | 

## Deskripsi :
Snakey adalah permainan Java dengan Pemrograman Berbasis Objek. Berbeda dengan permainan Snake klasik, Snakey menambahkan elemen baru berupa item bomb yang harus dihindari oleh pemain selama permainan berlangsung.

Prinsip-prinsip Pemrograman Berbasis Objek yang diterapkan :
- **Class & Object**: Setiap entity (Snake, Apple, Bomb) adalah object dari class
- **Inheritance & Polymorphism**: `Item` (abstract) → `Apple`, `Bomb`. Juga melakukan override pada `applyEffect()` & `checkCollision()`.
- **Abstraction**: `Item` menyembunyikan detail untuk posisi acak.
- **Encapsulation**: `Snake` memakai getter dan setter untuk melacak posisi sekarang. 
- **Composition**: `GameEngine` terhubung dengan `Snake`, `Apple`, `Bombs`, `Score`; `Gameplay` terhubung dengan `GameEngine` dan `GameRenderer`.

## Rancangan Kelas :
- `SnakeGame` → Entry point. Juga membuat `JFrame` dan menambah `Gameplay`.
- `Gameplay` → KeyListener. Menerima input (arrow, SPACE, SHIFT) dan kontrol restart.
- `GameEngine` → Logika game (update, collision, generate item, speed, reset).
- `GameRenderer` → Load dan render asset (snake, apple, bombs, scoreboard, controls).
- `GameConfig` → Konstanta grid, ukuran window, jumlah bomb, delay awal.
- `Item` → Mengatur posisi acak, dan sebagai abstraksi dari `checkCollision()` & `applyEffect()`.
- `Apple` → Extend `Item`.  Dengan memakai`applyEffect()` , menambah panjang ular & skor.
- `Bomb` → Extend `Item`. Dengan memakai `applyEffect()`, membuat snake mati.
- `Snake` → Inisiasi posisi ular, pergerakan, dan keadaannya (arah sekarang, mati/tidak, panjang).
- `Score` → Menyimpan skor tertinggi di file `highscore.dat`.

## Diagram Kelas :
![](/images/class.jpeg)
## Gambar Aplikasi
![](/images/gambarapp.png)
## Gameplay
![](/images/gameplay.png)

## Penjelasan Game :
### Kontrol

| Key | Action |
|-----|--------|
| `↑` `↓` `←` `→` | Kontrol arah gerakan snake |
| `SPACE` | Start game / Restart setelah game over |
| `SHIFT` (hold) | Boost sementara |

#### Tujuan Game:
Kumpulkan skor sebanyak mungkin dengan memakan apel sambil menghindari obstacle.

#### Cara Bermain:
1. **Start**: Tekan `SPACE` untuk memulai
2. **Move**: Gunakan arrow keys untuk mengarahkan snake
3. **Objective**: Makan apel hijau untuk:
   - Menambah panjang snake (+1 segment)
   - Menambah score (+1 point)
4. **Hindari**: 
   - ⚠️ Dinding arena 
   - ⚠️ Tubuh sendiri
   - 💣 Bom

#### Mekanisme:
- Setiap 5 skor, kecepatan game meningkat
- Setelah setiap apel dimakan, apel dan semua bom muncul di posisi baru yang random
- Jika mati (Game Over) :
    - Score final ditampilkan
    - Score disimpan ke high score list
    - Tekan `SPACE` untuk restart
    - High score table updated otomatis di `highscore.dat`

## Cara memainkan:
**Jalankan SnakeGame.java**

```
javac -d out .\src\*.java
```

```
java -cp out src.SnakeGame
```

## Link Source Code :
[Ctrl-Zack/Snakey](https://github.com/Ctrl-Zack/Snakey)

## Link Video Demo :
[Demo Snakey]()
