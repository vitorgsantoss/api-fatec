package br.com.api.fatec.apifatec.controllers;

import br.com.api.fatec.apifatec.domain.pedidovenda.PedidoVendaService;

import java.util.List;
import java.util.Optional;

import br.com.api.fatec.apifatec.entities.PedidoVenda;
import br.com.api.fatec.apifatec.shared.dto.TotalValorPorClienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/pedido-venda/v1")
public class PedidoVendaController {
    @Autowired
    private PedidoVendaService pedidoVendaService;

	
	@PostMapping
	public ResponseEntity<PedidoVenda> adicionarPedido(@RequestBody PedidoVenda pedidoVenda) {
		PedidoVenda novoPedido = pedidoVendaService.salvarPedidoVenda(pedidoVenda);
		return ResponseEntity.ok(novoPedido);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<PedidoVenda> buscarPedido(@PathVariable Long id) {
		Optional<PedidoVenda> pedidoVendaEncontrado = pedidoVendaService.buscarPorId(id);
		if (pedidoVendaEncontrado.isPresent()) {
			return ResponseEntity.ok(pedidoVendaEncontrado.get());
		} else {
			return ResponseEntity.notFound().build();
		}

		
	}

	@GetMapping("/pedidos/total-valor-por-cliente")
	public ResponseEntity<List<TotalValorPorClienteDto>> calcularTotalValorPorCliente() {
		List<TotalValorPorClienteDto> totalPorCliente = pedidoVendaService.calcularTotalPorCliente();
		return ResponseEntity.ok(totalPorCliente);
	}

	
	@GetMapping
	public List<PedidoVenda> listarPedidos() {
		return pedidoVendaService.listarTodos();
	}

	@PutMapping("/{id}")
	public ResponseEntity<PedidoVenda> atualizarPedido(@PathVariable Long id, @RequestBody PedidoVenda dadosPedido) {
		try {
			PedidoVenda pedidoAtualizado = pedidoVendaService.alterarPedido(id, dadosPedido);
			return ResponseEntity.ok(pedidoAtualizado);
		} catch (RuntimeException ex) {
			return ResponseEntity.notFound().build();
		}
	}

	
	@PutMapping("/{id}/cancelar")
	public ResponseEntity<PedidoVenda> cancelarPedido(@PathVariable Long id) {
		try {
			PedidoVenda pedidoCancelado = pedidoVendaService.cancelarPedido(id);
			return ResponseEntity.ok(pedidoCancelado);
		} catch (IllegalStateException ex) {
			return ResponseEntity.badRequest().body(null);
		} catch (RuntimeException ex) {
			return ResponseEntity.notFound().build();
		}
	}
}
