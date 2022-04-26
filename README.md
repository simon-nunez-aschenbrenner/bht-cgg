###### Deutsch (english version below)

### Computergrafik Grundlagen
**Übungsaufgaben im Wintersemester 2020 (3. Studienplansemester)**

*Berliner Hochschule für Technik*  
*Prof. Dr. Henrik Tramberend*

`src/cgg` Autor: Simon Núñez Aschenbrenner  
`src/cgg-tools` Autor: Prof. Dr. Henrik Tramberend

Bearbeitung von elf Übungsaufgaben (`src/cgg/A01.java` etc.) zur Erlernung der Grundlagen der digitalen Bildsynthese.
(Gerenderte Bilder in `doc`, verwendete Texturen in `tex`)

Implementierung eines Renderers in Java:
- Stochastisches Raytracing
- Aufbau von Szenengraphen aus Ebenen, Hyperboloiden, Kugeln, Quadern und Zylindern
- Diffuse, spiegelnde und transparente Oberflächen mit transfomierbaren Texturen
- Globale Beleuchtung aus Punkt- und Richtungslichtquellen sowie diffusen, emittierenden Materialien
- Affine Transformationen auf Kamera und Szene
- Beschleuningung mit Hüllkörperhierarchie und Multithreading

Ausgiebiger Einsatz von Kompositum Entwurfsmuster und Rekursion, Vertiefen von linearer Algebra.

Grobe Struktur, ersichtlich z.B. in `A11.java`:
```
Image
└── Raytracer
    ├── Camera
    └── World
        ├── Light
        └── Shape
            └── Material
                └── Texture
```


###### English

### Principles of Computer Graphics
**Exercises of fall/winter semester 2020 (3rd study plan semester)**

*Berlin University of Applied Sciences and Technology*  
*Prof. Dr. Henrik Tramberend*

`src/cgg` Author: Simon Núñez Aschenbrenner  
`src/cgg-tools` Author: Prof. Dr. Henrik Tramberend

Completion of eleven exercises (`src/cgg/A01.java` etc.) to learn the principles of digital image synthesis.
(Rendered images in `doc`, used textures in `tex`)

Implementation of a renderer in Java:
- Stochastic raytracing
- Setup of a scene graph consisting of cuboids, cylinders, hyperboloids, spheres and planes
- Diffuse, reflecting and transparent materials with transformable textures
- Global illumination by point and directional light sources as well as diffuse emitting materials
- Affine transformations on camera and scene
- Acceleration with bounding volume hierarchy and multithreading

Extensive use of compositum pattern and recursion, deepening knowledge on linear algebra.

Rough structure, such as in `A11.java`:
```
Image
└── Raytracer
    ├── Camera
    └── World
        ├── Light
        └── Shape
            └── Material
                └── Texture
```
