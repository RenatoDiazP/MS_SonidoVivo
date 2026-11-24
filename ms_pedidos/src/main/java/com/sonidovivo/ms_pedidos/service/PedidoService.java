package com.sonidovivo.ms_pedidos.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.sonidovivo.ms_pedidos.DTO.PedidoRequestDTO;
import com.sonidovivo.ms_pedidos.DTO.PedidoResponseDTO;
import com.sonidovivo.ms_pedidos.DTO.ProductoDTO;
import com.sonidovivo.ms_pedidos.mapper.PedidoMapper;
import com.sonidovivo.ms_pedidos.model.DetallePedido;
import com.sonidovivo.ms_pedidos.model.EstadoPedido;
import com.sonidovivo.ms_pedidos.model.Pedido;
import com.sonidovivo.ms_pedidos.repository.PedidoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class PedidoService {
    
    private final PedidoRepository repository;
    private final RestClient restClient;
    private final PedidoMapper mapper;

    // Calcular Total, subtotal e iva del pedido
    private void calcularTotales(Pedido pedido){
        BigDecimal subtotal = pedido.getDetalle().stream().map(DetallePedido::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal iva = subtotal.multiply(BigDecimal.valueOf(0.19));
        BigDecimal total = subtotal.add(iva);
        pedido.setSubtotal(subtotal);
        pedido.setIva(iva);
        pedido.setTotal(total);
    }

    // Obtener el precio de un producto
    private BigDecimal obtenerPrecioProducto(Long idProducto){
        ProductoDTO producto = restClient.get().uri("http://localhost:8082/api/productos/" + idProducto).retrieve().body(ProductoDTO.class);
        if(producto == null){
            throw new RuntimeException("Producto no Encontrado");
        }
        return producto.getPrecio();
    }

    // Funcion pa que devuelva la entity
    private Pedido buscarEntidadPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no Encontrado"));
    }

    public List<PedidoResponseDTO> listarPedidos(){
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public PedidoResponseDTO buscarPorId(Long id){
        Pedido pedido = repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no Encontrado"));
        return mapper.toDto(pedido);
    }

    // Transactional para evitar errores
    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto){
        Pedido pedido = new Pedido();

        pedido.setDireccionId(dto.getDireccionId());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        List<DetallePedido> detalles = dto.getDetalle().stream().map(detalleDTO -> {
            DetallePedido detalle = new DetallePedido();

            detalle.setIdProducto(detalleDTO.getIdProducto());
            detalle.setCantidad(detalleDTO.getCantidad());

            detalle.setPedido(pedido);

            BigDecimal precio = obtenerPrecioProducto(detalle.getIdProducto());

            detalle.setPrecioUnitario(precio);

            detalle.setSubtotal(precio.multiply(BigDecimal.valueOf(detalle.getCantidad())));

            return detalle;
        }).toList();
        pedido.setDetalle(detalles);
        calcularTotales(pedido);
        Pedido guardado = repository.save(pedido);
        return mapper.toDto(guardado);
    }

    @Transactional
    public PedidoResponseDTO actualizarEstado(Long id, EstadoPedido estado){
        Pedido pedido = buscarEntidadPorId(id);
        pedido.setEstado(estado);
        Pedido actualizado = repository.save(pedido);
        return mapper.toDto(actualizado);
    }

    @Transactional
    public void eliminarPedido(Long id){
        Pedido pedido = buscarEntidadPorId(id);
        repository.delete(pedido);
    }
}