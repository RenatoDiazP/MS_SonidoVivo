package com.sonidovivo.ms_contacto.repository;

import com.sonidovivo.ms_contacto.model.EstadoMensaje;
import com.sonidovivo.ms_contacto.model.MensajeContacto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeContactoRepository extends JpaRepository<MensajeContacto, Long> {
    List<MensajeContacto> findAllByOrderByFechaEnvioDesc();
    List<MensajeContacto> findByEstadoOrderByFechaEnvioDesc(EstadoMensaje estado);
}