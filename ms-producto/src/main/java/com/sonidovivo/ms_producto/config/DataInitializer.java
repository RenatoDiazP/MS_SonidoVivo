package com.sonidovivo.ms_producto.config;

import com.sonidovivo.ms_producto.model.Categoria;
import com.sonidovivo.ms_producto.model.Producto;
import com.sonidovivo.ms_producto.repository.CategoriaRepository;
import com.sonidovivo.ms_producto.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() == 0 && productoRepository.count() == 0) {
            Categoria teclados = crearCategoria("Teclados", "Controladores MIDI, sintetizadores y stage pianos");
            Categoria flautas = crearCategoria("Flautas", "Flautas dulces de estudio y concierto");
            Categoria piano = crearCategoria("Piano", "Pianos verticales y de cola acústicos");
            Categoria baterias = crearCategoria("Baterias", "Baterías acústicas completas y percusión");
            Categoria bajos = crearCategoria("Bajos", "Bajos eléctricos de 4 y 5 cuerdas");
            Categoria guitarras = crearCategoria("Guitarras", "Guitarras eléctricas clásicas y modernas");
            Categoria vientosMetal = crearCategoria("Vientos Metal", "Instrumentos de viento metal");

            categoriaRepository.saveAll(List.of(
                    teclados, flautas, piano, baterias, bajos, guitarras, vientosMetal
            ));

            List<Producto> productos = List.of(
                    // TECLADOS
                    crearProducto(
                            "Arturia Keylab 61 MK3 Ultra - Controlador MIDI",
                            "Arturia",
                            "Controlador MIDI profesional de 61 teclas semicontrapesadas con integración DAW, pads sensibles y pantalla a color.",
                            new BigDecimal("549990"),
                            8,
                            "/Img/Sonido_Vivo/Arturia Keylab 61 MK3 Ultra - Controlador MIDI.jpg",
                            teclados
                    ),
                    crearProducto(
                            "Studiologic Numa X Piano GT - Stage Piano",
                            "Studiologic",
                            "Piano de escenario con teclado de madera Fatar TP/400 Wood, motor de sonido avanzado y mezclador digital integrado.",
                            new BigDecimal("1890000"),
                            4,
                            "/Img/Sonido_Vivo/Studiologic Numa X Piano GT - Stage Piano.jpg",
                            teclados
                    ),

                    // FLAUTAS
                    crearProducto(
                            "Flauta Dulce Alto Moeck Tuju 231 Maple",
                            "Moeck",
                            "Flauta dulce contralto fabricada en madera de arce (maple), digitación barroca y tono cálido ideal para ensambles.",
                            new BigDecimal("165990"),
                            12,
                            "/Img/Sonido_Vivo/Flauta Dulce Alto Moeck Tuju 231 Maple.jpg",
                            flautas
                    ),
                    crearProducto(
                            "Flauta Dulce Alto Mollenhauer Solist",
                            "Mollenhauer",
                            "Instrumento de estudio avanzado en madera seleccionada con excelente proyección sonora y respuesta en registros agudos.",
                            new BigDecimal("229990"),
                            10,
                            "/Img/Sonido_Vivo/Flauta Dulce Alto Mollenhauer Solist.jpg",
                            flautas
                    ),
                    crearProducto(
                            "Flauta Dulce Soprano Mollenhauer",
                            "Mollenhauer",
                            "Flauta dulce soprano de afinación precisa, cuerpo ergonómico y timbre claro para estudiantes y músicos.",
                            new BigDecimal("89990"),
                            20,
                            "/Img/Sonido_Vivo/Flauta Dulce Soprano Mollenhauer.jpg",
                            flautas
                    ),

                    // PIANO
                    crearProducto(
                            "Kawai CX5 – Piano Vertical Acústico",
                            "Kawai",
                            "Piano vertical acústico compacto con maquinaria de alta precisión, excelente estabilidad de afinación y sonido equilibrado.",
                            new BigDecimal("3490000"),
                            3,
                            "/Img/Sonido_Vivo/Kawai CX5 – Piano Vertical Acústico.jpg",
                            piano
                    ),
                    crearProducto(
                            "Yamaha C7X PE - Piano Acústico de Cola",
                            "Yamaha",
                            "Piano de gran cola de concierto con acabado negro pulido, resonancia profunda y respuesta dinámica excepcional.",
                            new BigDecimal("24990000"),
                            2,
                            "/Img/Sonido_Vivo/Yamaha C7X PE - Piano Acustico de Cola.jpg",
                            piano
                    ),

                    // BATERÍAS
                    crearProducto(
                            "Batería acústica Mapex Serie Venus VE5295FTVM - Crimson Red Sparkle",
                            "Mapex",
                            "Set completo de 5 piezas con cascos de álamo, bordes SONIClear y acabado rojo brillante con platillos incluidos.",
                            new BigDecimal("699990"),
                            6,
                            "/Img/Sonido_Vivo/Batería acústica Mapex Serie Venus VE5295FTVM - Crimson Red Sparkle.jpg",
                            baterias
                    ),
                    crearProducto(
                            "Batería acústica TAMA Stagestar ST52H5 5 piezas - Sea Blue Mist",
                            "TAMA",
                            "Batería completa de 5 cuerpos con herrajes de doble perfil, soporte de tom Omnisphere y platillos de bronce.",
                            new BigDecimal("749990"),
                            5,
                            "/Img/Sonido_Vivo/Batería acústica TAMA Stagestar ST52H5 5 piezas - Sea Blue Mist.jpg",
                            baterias
                    ),
                    crearProducto(
                            "Set de batería acústica PowerDrums CPJ-5 FKR - Red",
                            "PowerDrums",
                            "Batería acústica de 5 piezas ideal para iniciación y ensayo, incluye atriles, pedal de bombo, sillín y platillos.",
                            new BigDecimal("429990"),
                            9,
                            "/Img/Sonido_Vivo/Set de batería acústica PowerDrums CPJ-5 FKR - Red.jpg",
                            baterias
                    ),

                    // BAJOS
                    crearProducto(
                            "Bajo Eléctrico Jazz Bass Fender",
                            "Fender",
                            "Bajo eléctrico Fender Jazz Standard MIM, fabricado en México, recién calibrado y en excelente estado.",
                            new BigDecimal("750000"),
                            5,
                            "/Img/Sonido_Vivo/Bajo_Electrico_Jazz_Bass_Fender.jpg",
                            bajos
                    ),
                    crearProducto(
                            "Bajo Eléctrico Ibanez GSR-320",
                            "Ibanez",
                            "Bajo ligero y cómodo de serie GIO con mástil delgado de arce y cápsulas dinámicas versátiles para cualquier estilo.",
                            new BigDecimal("289990"),
                            11,
                            "/Img/Sonido_Vivo/Bajo Electrico Ibanez GSR-320.jpg",
                            bajos
                    ),
                    crearProducto(
                            "Bajo Eléctrico Peavey Forum",
                            "Peavey",
                            "Bajo eléctrico de fabricación sólida con electrónica activa/pasiva y gran pegada en frecuencias graves.",
                            new BigDecimal("399990"),
                            7,
                            "/Img/Sonido_Vivo/Bajo Electrico Peavey Forum.jpg",
                            bajos
                    ),
                    crearProducto(
                            "Bajo Eléctrico Sterling by Music Man StingRay 5 Black",
                            "Sterling by Music Man",
                            "Bajo de 5 cuerdas con cápsula humbucker cerámica de alta salida y preamplificador activo de 2 bandas.",
                            new BigDecimal("549990"),
                            6,
                            "/Img/Sonido_Vivo/Bajo Electrico Sterling by Music Man StingRay 5 Black.jpg",
                            bajos
                    ),
                    crearProducto(
                            "Bajo Eléctrico Sterling by Music Man StingRay 5 Mint Green (MG)",
                            "Sterling by Music Man",
                            "Edición en acabado Mint Green de 5 cuerdas con diapasón de arce y el clásico sonido punchy de StingRay.",
                            new BigDecimal("559990"),
                            5,
                            "/Img/Sonido_Vivo/Bajo Electrico Sterling by Music Man StingRay 5 Mint Green (MG).jpg",
                            bajos
                    ),

                    // GUITARRAS
                    crearProducto(
                            "Guitarra Eléctrica Stratocaster - Squier",
                            "Squier",
                            "Guitarra eléctrica clásica con cuerpo contorneado, tres cápsulas single-coil y puente trémolo sincronizado.",
                            new BigDecimal("249990"),
                            14,
                            "/Img/Sonido_Vivo/Guitarra Eléctrica Stratocaster - Squier.jpg",
                            guitarras
                    ),
                    crearProducto(
                            "Guitarra Eléctrica Epiphone Les Paul Standard",
                            "Epiphone",
                            "Cuerpo de caoba con tapa de arce, equipada con humbuckers ProBucker para un tono cálido y sustain prolongado.",
                            new BigDecimal("649990"),
                            7,
                            "/Img/Sonido_Vivo/Guitarra Electrico Epiphone Les Paul Standard.jpg",
                            guitarras
                    ),
                    crearProducto(
                            "Guitarra Eléctrica Squier Stratocaster SSS Affinity",
                            "Squier",
                            "Modelo Affinity ligero con mástil perfil en C y configuración SSS ideal para tonos limpios y rock clásico.",
                            new BigDecimal("299990"),
                            10,
                            "/Img/Sonido_Vivo/Guitarra Electrico Squier Stratocaster SSS Affinity.jpg",
                            guitarras
                    ),
                    crearProducto(
                            "Guitarra Eléctrica Squier Telecaster Classic Vibe 50s",
                            "Squier",
                            "Homenaje a los modelos de los años 50 con cápsulas Alnico diseñadas por Fender y puente estilo vintage.",
                            new BigDecimal("489990"),
                            8,
                            "/Img/Sonido_Vivo/Guitarra Electrico Squier Telecaster Classic Vibe 50s.jpg",
                            guitarras
                    ),

                    // VIENTOS METAL
                    crearProducto(
                            "Trombón Tenor King Cleveland",
                            "King",
                            "Trombón tenor de vara suave con campana de latón lacado, proyección brillante y estuche rígido incluido.",
                            new BigDecimal("459990"),
                            6,
                            "/Img/Sonido_Vivo/Trombon Tenor King Cleveland.jpg",
                            vientosMetal
                    )
            );

            productoRepository.saveAll(productos);
        }
    }

    private Categoria crearCategoria(String nombre, String descripcion) {
        Categoria c = new Categoria();
        c.setNombre(nombre);
        c.setDescripcion(descripcion);
        return c;
    }

    private Producto crearProducto(String nombre, String marca, String descripcion,
                                   BigDecimal precio, Integer stock, String imagenUrl,
                                   Categoria categoria) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setMarca(marca);
        p.setDescripcion(descripcion);
        p.setPrecio(precio);
        p.setStock(stock);
        p.setImagenUrl(imagenUrl);
        p.setActivo(true);
        p.setCategoria(categoria);
        return p;
    }
}