package com.carros.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.carros.api.exception.ObjectNotFoundException;
import com.carros.domain.dto.CarroDTO;


@Service
public class CarroService {
	
	@Autowired
	private CarroRepository rep;
	
	public List<CarroDTO> getCarros() {
//		List<Carro> carros = rep.findAll();
//		
//		List<CarroDTO> list = new ArrayList<>();
//		for(Carro c : carros) {
//			list.add(CarroDTO.create(c));
//		}
//		return list;
		
//		List<CarroDTO> list = carros.stream().map(c -> new CarroDTO(c)).collect(Collectors.toList());
//		return list;
		
		//Sintaxe resumida
		return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
	}
	

	public CarroDTO getCarrosById(Long id) {
		Optional<Carro> carro = rep.findById(id);
		
		return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException ("Carro nao encontrado"));
	}

	public List<CarroDTO> getCarrosByTipo(String tipo) {
		
		return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public CarroDTO insert(Carro carro) {
		Assert.isNull(carro.getId(), "Nao foi possivel inserir o registro");
		
		return CarroDTO.create(rep.save(carro));
		
	}

	public CarroDTO update(Carro carro, Long id) {
		Assert.notNull(id, "Nao foi possivel atualizar o registro");
		
		Optional<Carro> optional = rep.findById(id);
		
		if(optional.isPresent()) {
			Carro db = optional.get();
			
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			
			System.out.println("Carro id " + db.getId());
			
			rep.save(db);
			
			return CarroDTO.create(db);
		}else {
			throw new RuntimeException("Nao foi possivel atualizar o registro");
		}
	}

		public void delete(Long id) {
				rep.deleteById(id);
		
		}

}
