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
        if (categoriaRepository.count() == 0) {
            Categoria guitarras = categoriaRepository.save(
                    new Categoria(null, "Guitarras y Bajos", "Guitarras eléctricas, acústicas, bajos y amplificadores"));
            Categoria baterias = categoriaRepository.save(
                    new Categoria(null, "Baterías y Percusión", "Baterías acústicas, electrónicas, platillos y accesorios"));
            Categoria teclados = categoriaRepository.save(
                    new Categoria(null, "Teclados y Pianos", "Pianos digitales, sintetizadores y controladores MIDI"));
            Categoria audio = categoriaRepository.save(
                    new Categoria(null, "Audio y Estudio", "Micrófonos, interfaces de audio y monitores de estudio"));

            if (productoRepository.count() == 0) {
                productoRepository.saveAll(List.of(
                        new Producto(null, "Guitarra Eléctrica Player Stratocaster", "Fender",
                                "Cuerpo de aliso, mástil de arce y tres cápsulas single-coil",
                                new BigDecimal("849990.00"), 12,
                                "https://images.unsplash.com/photo-1550291652-6ea9114a47b1", true, guitarras),
                        new Producto(null, "Guitarra Acústica CD-60S", "Fender",
                                "Tapa sólida de abeto, fondo y aros de caoba",
                                new BigDecimal("269990.00"), 18,
                                "https://images.unsplash.com/photo-1510915361894-db8b60106cb1", true, guitarras),
                        new Producto(null, "Batería Acústica Export EXX725S", "Pearl",
                                "Set de 5 piezas con herrajes serie 830 incluidos",
                                new BigDecimal("929990.00"), 5,
                                "https://images.unsplash.com/photo-1519892300165-cb5542fb47c7", true, baterias),
                        new Producto(null, "Piano Digital P-125 88 Teclas", "Yamaha",
                                "Teclado contrapesado GHS con motor de sonido Pure CF",
                                new BigDecimal("699990.00"), 8,
                                "https://images.unsplash.com/photo-1520523839897-bd0b52f945a0", true, teclados),
                        new Producto(null, "Micrófono Dinámico Cardioide SM58", "Shure",
                                "Estándar de la industria para voces en vivo y estudio",
                                new BigDecimal("119990.00"), 25,
                                "https://images.unsplash.com/photo-1590602847861-f357a9332bbc", true, audio)
                ));
            }
        }
    }
}