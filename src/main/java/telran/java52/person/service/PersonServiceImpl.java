package telran.java52.person.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dao.PersonRepository;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.dto.exception.PersonNotFoundException;
import telran.java52.person.model.Address;
import telran.java52.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId()))
			return false;
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public List<PersonDto> findPersonsByCity(String city) {
		List<Person> res = personRepository.findByCityIgnoreCase(city);
		return res.stream().map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public List<PersonDto> findPersonsByAgeBetween(Integer minAge, Integer maxAge) {
		List<Person> res = personRepository.findByBirthDateBetween(LocalDate.now().minus(Period.ofYears(maxAge)),
				LocalDate.now().minus(Period.ofYears(minAge)));
		return res.stream().map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public List<PersonDto> findPersonsbyName(String name) {
		List<Person> res = personRepository.findByNameIgnoreCase(name);
		return res.stream().map(p -> modelMapper.map(p, PersonDto.class)).toList();
	}

	@Override
	public List<CityPopulationDto> getCitiesPopulation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDto updatePersonAddress(Integer id, AddressDto newAddress) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setAddress(modelMapper.map(newAddress, Address.class));
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto deletePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.deleteById(id);
		return modelMapper.map(person, PersonDto.class);
	}

}
