package telran.java52.person.service;

import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.PersonDto;

public interface PersonService {

	Boolean addPerson(PersonDto personDto);

	PersonDto findPersonById(Integer id);

	Iterable<PersonDto> findPersonsByCity(String city);

	Iterable<PersonDto> findPersonsByAgeBetween(Integer minAge, Integer maxAge);

	PersonDto updatePersonName(Integer id, String name);

	Iterable<PersonDto> findPersonsbyName(String name);

	Iterable<CityPopulationDto> getCitiesPopulation();

	PersonDto updatePersonAddress(Integer id, AddressDto newAddress);

	PersonDto deletePerson(Integer id);

}
