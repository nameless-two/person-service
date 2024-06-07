package telran.java52.person.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dao.PersonRepository;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.ChildDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.EmployeeDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.dto.exception.PersonNotFoundException;
import telran.java52.person.model.Address;
import telran.java52.person.model.Child;
import telran.java52.person.model.Employee;
import telran.java52.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;
	final PersonModelDtoMapper mapper;

	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId()))
			return false;
		personRepository.save(mapper.mapToModel(personDto));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return mapper.maptoDto(person);
	}

	@Override
	public PersonDto[] findPersonsByCity(String city) {
		List<Person> res = personRepository.findByAddressCityIgnoreCase(city);
		return res.stream().map(p -> mapper.maptoDto(p)).toArray(PersonDto[]::new);
	}

	@Override
	public PersonDto[] findPersonsByAgeBetween(Integer minAge, Integer maxAge) {
		List<Person> res = personRepository.findByBirthDateBetween(LocalDate.now().minus(Period.ofYears(maxAge)),
				LocalDate.now().minus(Period.ofYears(minAge)));
		return res.stream().map(p -> mapper.maptoDto(p)).toArray(PersonDto[]::new);
	}

	@Transactional
	@Override
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
		return mapper.maptoDto(person);
	}

	@Override
	public PersonDto[] findPersonsbyName(String name) {
		List<Person> res = personRepository.findByNameIgnoreCase(name);
		return res.stream().map(p -> mapper.maptoDto(p)).toArray(PersonDto[]::new);
	}

	@Override
	public List<CityPopulationDto> getCitiesPopulation() {
		return personRepository.getCitiesPopulation();
	}

	@Transactional
	@Override
	public PersonDto updatePersonAddress(Integer id, AddressDto newAddress) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setAddress(modelMapper.map(newAddress, Address.class));
		return mapper.maptoDto(person);
	}

	@Transactional
	@Override
	public PersonDto deletePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.deleteById(id);
		return mapper.maptoDto(person);
	}

	@Override
	public PersonDto[] findAllChildren() {
		return personRepository.findAllChildren().stream().map(c -> mapper.maptoDto(c)).toArray(ChildDto[]::new);
	}

	@Override
	public PersonDto[] findBySalaryBetween(long minSalary, long maxSalary) {
		return personRepository.findBySalaryBetween(minSalary, maxSalary).stream().map(e -> mapper.maptoDto(e))
				.toArray(EmployeeDto[]::new);
	}

	@Override
	public void run(String... args) throws Exception {
		if (personRepository.count() == 0) {
			Person person = new Person(1000, "John", LocalDate.of(1985, 3, 11),
					new Address("Tel Aviv", "Ben Gvirol", 81));
			Child child = new Child(2000, "Moshe", LocalDate.of(2018, 7, 5), new Address("Ashkelon", "Bar Kohva", 21),
					"Shalom");
			Employee employee = new Employee(3000, "Sarah", LocalDate.of(1995, 11, 23),
					new Address("Rehovot", "Herzl", 7), "Motorola", 20000);
			personRepository.save(person);
			personRepository.save(child);
			personRepository.save(employee);
		}

	}

}
