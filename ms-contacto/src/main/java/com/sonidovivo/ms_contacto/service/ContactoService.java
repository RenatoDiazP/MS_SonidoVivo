package com.sonidovivo.ms_contacto.service;

import com.sonidovivo.ms_contacto.dto.ContactoRequestDTO;
import com.sonidovivo.ms_contacto.dto.ContactoResponseDTO;
import com.sonidovivo.ms_contacto.exception.ResourceNotFoundException;
import com.sonidovivo.ms_contacto.model.EstadoMensaje;
import com.sonidovivo.ms_contacto.model.MensajeContacto;
import com.sonidovivo.ms_contacto.repository.MensajeContactoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactoService {

    private final MensajeContactoRepository contactoRepository;

    public ContactoResponseDTO enviarMensaje(ContactoRequestDTO dto) {
        MensajeContacto entity = new MensajeContacto();
        entity.setNombre(dto.getNombre().trim());
        entity.setEmail(dto.getEmail().trim().toLowerCase());
        entity.setTelefono(dto.getTelefono() != null ? dto.getTelefono().trim() : null);
        entity.setAsunto(dto.getAsunto().trim());
        entity.setMensaje(dto.getMensaje().trim());

        return toDTO(contactoRepository.save(entity));
    }

    public List<ContactoResponseDTO> listarTodos() {
        return contactoRepository.findAllByOrderByFechaEnvioDesc().stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ContactoResponseDTO> listarPorEstado(EstadoMensaje estado) {
        return contactoRepository.findByEstadoOrderByFechaEnvioDesc(estado).stream()
                .map(this::toDTO)
                .toList();
    }

    public ContactoResponseDTO obtenerPorId(Long id) {
        MensajeContacto entity = contactoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensaje de contacto no encontrado con ID: " + id));
        return toDTO(entity);
    }

    public ContactoResponseDTO cambiarEstado(Long id, EstadoMensaje nuevoEstado) {
        MensajeContacto entity = contactoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensaje de contacto no encontrado con ID: " + id));
        entity.setEstado(nuevoEstado);
        return toDTO(contactoRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!contactoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mensaje de contacto no encontrado con ID: " + id);
        }
        contactoRepository.deleteById(id);
    }

    private ContactoResponseDTO toDTO(MensajeContacto m) {
        return new ContactoResponseDTO(
                m.getId(),
                m.getNombre(),
                m.getEmail(),
                m.getTelefono(),
                m.getAsunto(),
                m.getMensaje(),
                m.getEstado(),
                m.getFechaEnvio()
        );
    }
}