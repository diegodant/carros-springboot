package com.carros;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.carros.api.exception.ObjectNotFoundException;
import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import static junit.framework.TestCase.*;
import static junit.framework.TestCase.assertNotNull;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarrosServiceTests {
	
	@Autowired
	private CarroService service;

	@Test
	public void testSave() {

		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("Esportivo");
		
		CarroDTO c = service.insert(carro);
		
		assertNotNull(c);
		
		Long id = c.getId();
		assertNotNull(id);
		
		//Buscar o objeto
		c = service.getCarrosById(id);
		assertNotNull(c);
		
		assertEquals("Ferrari", c.getNome());
		assertEquals("Esportivo", c.getTipo());
		
		// Deletar o Objeto
		service.delete(id);
		
		// Verifico se ja deletou
		try {
			assertNull(service.getCarrosById(id));
		} catch (ObjectNotFoundException e) {
			// TODO: handle exception
		}
	}
	
	@Test
	public void TestLista() {
		
		List<CarroDTO> carros = service.getCarros();
		
		assertEquals(30, carros.size());
		
	}
	
	@Test
	public void TestListaPorTipo() {
		
		assertEquals(10, service.getCarrosByTipo("classicos").size());
		assertEquals(10, service.getCarrosByTipo("esportivos").size());
		assertEquals(10, service.getCarrosByTipo("luxo").size());
		
		assertEquals(0, service.getCarrosByTipo("x").size());
		
	}
	
	@Test
	public void TestGet() {
		CarroDTO c = service.getCarrosById(11L);
		
		assertNotNull(c);
		
		assertEquals("Ferrari FF", c.getNome());
		
	}

}
