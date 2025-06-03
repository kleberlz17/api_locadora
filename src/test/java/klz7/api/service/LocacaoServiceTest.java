package klz7.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import klz7.api.dto.LocacaoDTO;
import klz7.api.mapper.LocacaoConverter;
import klz7.api.model.Cliente;
import klz7.api.model.Filmes;
import klz7.api.model.Locacao;
import klz7.api.repository.ClienteRepository;
import klz7.api.repository.FilmesRepository;
import klz7.api.repository.LocacaoRepository;
import klz7.api.validator.LocacaoValidator;

public class LocacaoServiceTest {

	@Mock
	private LocacaoRepository locacaoRepository;
	
	@Mock
	private ClienteRepository clienteRepository;
	
	@Mock
	private FilmesRepository filmesRepository;
	
	@Mock
	private LocacaoConverter locacaoConverter;
	
	@Mock
	private LocacaoValidator locacaoValidator;
	
	@InjectMocks
	private LocacaoService locacaoService;
	
	private Locacao locacao;
	
	private Filmes filmes;
	
	private Cliente cliente;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		filmes = new Filmes();
		filmes.setIdFilme(2L);
		filmes.setNome("The Batman");
		filmes.setDataLancamento(LocalDate.of(2022, 3, 12));
		filmes.setDiretor("Matt Reeves");
		filmes.setGenero("super-heróis");
		filmes.setEstoque(1);
		
		cliente = new Cliente();
		cliente.setIdCliente(10L);
		cliente.setNome("Chris Prince");
		cliente.setDataNascimento(LocalDate.of(1998, 3, 12 ));
		cliente.setCpf("11111111111");
		cliente.setTelefone("2199999999");
		cliente.setEmail("chris@email.com");
		cliente.setEndereco("Oxford Street - 333");
		
		locacao = new Locacao();
		locacao.setIdLocacao(1L);
		locacao.setCliente(cliente);
		locacao.setFilmes(filmes);
		locacao.setDataLocacao(LocalDate.now());
		locacao.setDataDevolucao(LocalDate.of(2025, 6, 3));
		locacao.setDevolvido(false);
		locacao.setQuantidade(1);
	}
	
	@Test
	void testSalvarLocacaoComSucesso() {
		LocacaoDTO locacaoDTO = new LocacaoDTO();
		locacaoDTO.setIdLocacao(2L);
		locacaoDTO.setIdCliente(10L);
		locacaoDTO.setIdFilmes(2L);;
		locacaoDTO.setDataLocacao(LocalDate.now());
		locacaoDTO.setDataDevolucao(LocalDate.of(2025, 6, 3));
		locacaoDTO.setDevolvido(false);
		locacaoDTO.setQuantidade(1);
		
		Locacao locacaoSalva = Locacao.builder()
				.idLocacao(2L)
				.cliente(cliente)
				.filmes(filmes)
				.dataLocacao(locacaoDTO.getDataLocacao())
				.dataDevolucao(locacaoDTO.getDataDevolucao())
				.devolvido(locacaoDTO.isDevolvido())
				.quantidade(locacaoDTO.getQuantidade())
				.build();
		
		when(clienteRepository.findById(10L)).thenReturn(Optional.of(cliente));
		when(filmesRepository.findById(2L)).thenReturn(Optional.of(filmes));
		when(locacaoRepository.save(any(Locacao.class))).thenReturn(locacaoSalva);
		
		Locacao resultado = locacaoService.salvar(locacaoDTO);

		assertNotNull(resultado);
		assertEquals(2L, resultado.getIdLocacao());
		assertEquals("Chris Prince", resultado.getCliente().getNome());
		assertEquals("The Batman", resultado.getFilmes().getNome());
		
		verify(locacaoValidator).validarTudo(any(Locacao.class));
		verify(locacaoRepository).save(any(Locacao.class));
	}
	
	@Test
	void testBuscarLocacoesPorClienteComSucesso() {
		when(locacaoRepository.findByClienteId(10L)).thenReturn(List.of(locacao));
		List<Locacao> locacoesCliente = locacaoService.buscarLocacoesPorCliente(10L);
		
		assertNotNull(locacoesCliente);
		assertEquals(1, locacoesCliente.size());
		verify(locacaoRepository, times(1)).findByClienteId(10L);
	}
	
	@Test
	void testRenovarLocacaoComSucesso() {
		Long idLocacao = locacao.getIdLocacao();
		LocalDate devolucaoExtendida = LocalDate.of(2025, 6, 10);
		
		when(locacaoRepository.findById(idLocacao)).thenReturn(Optional.of(locacao));
		when(locacaoRepository.save(any(Locacao.class))).thenReturn(locacao);
		
		Locacao locacaoExtendida = locacaoService.renovarLocacao(idLocacao, devolucaoExtendida);
		
		assertNotNull(locacaoExtendida);
		assertEquals(devolucaoExtendida, locacaoExtendida.getDataDevolucao());
		verify(locacaoValidator).validarDataDevolucao(devolucaoExtendida);
		verify(locacaoRepository).save(locacao);
	}
	
	@Test
	void testBuscarHistoricoFilmeComSucesso() {
		when(locacaoRepository.findByFilmesIdFilme(2L)).thenReturn(List.of(locacao));
		List<Locacao> historicoFilme = locacaoService.buscarHistoricoFilme(2L);
		
		assertNotNull(historicoFilme);
		assertEquals(1, historicoFilme.size());
		verify(locacaoRepository, times(1)).findByFilmesIdFilme(2L);
	}
	
	@Test
	void testCalculcarMultaComSucesso() {
		locacao.setDevolvido(false);
		locacao.setDataDevolucao(LocalDate.now().minusDays(10));
		
		when(locacaoRepository.findById(1L)).thenReturn(Optional.of(locacao));
		
		BigDecimal multa = locacaoService.calcularMulta(1L);
		
		assertEquals(new BigDecimal("70.00"), multa);
	}
	
	@Test
	void testAlugarFilmeComSucesso() {
		
		filmes.setEstoque(1);
		
		when(clienteRepository.findById(10L)).thenReturn(Optional.of(cliente));
		when(filmesRepository.findById(2L)).thenReturn(Optional.of(filmes));
		when(filmesRepository.save(any(Filmes.class))).thenReturn(filmes);
		
		Locacao locacaoSalva = new Locacao();
		locacaoSalva.setIdLocacao(10L);
		locacaoSalva.setCliente(cliente);
		locacaoSalva.setFilmes(filmes);
		locacaoSalva.setQuantidade(1);
		locacaoSalva.setDataLocacao(LocalDate.now());
		locacaoSalva.setDataDevolucao(LocalDate.of(2025, 6, 11));
		
		when(locacaoRepository.save(any(Locacao.class))).thenReturn(locacaoSalva);
		
		Locacao alugar = locacaoService.alugarFilme(10L, 2L, 1, LocalDate.of(2025, 6, 11));
		
		assertNotNull(alugar);
		assertEquals(cliente, alugar.getCliente());
		assertEquals(filmes, alugar.getFilmes());
		assertEquals(1, alugar.getQuantidade());
		assertEquals(LocalDate.now(), alugar.getDataLocacao());
		assertEquals(LocalDate.of(2025, 6, 11), alugar.getDataDevolucao());
		
		verify(clienteRepository).findById(10L);
		verify(filmesRepository).findById(2L);
		verify(filmesRepository).save(any(Filmes.class));
		verify(locacaoRepository).save(any(Locacao.class));
		
		assertEquals(0, filmes.getEstoque());
	}
}
