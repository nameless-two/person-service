package telran.java52.person.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java52.person.dto.AddressDto;
import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.dto.PersonDto;
import telran.java52.person.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

	final PersonService personService;

	@PostMapping("")
	public Boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}

	@GetMapping("/{id}")
	public PersonDto findPersonById(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}

	@GetMapping("/city/{city}")
	public PersonDto[] findPersonsByCity(@PathVariable String city) {
		return personService.findPersonsByCity(city);
	}

	@GetMapping("/ages/{minAge}/{maxAge}")
	public PersonDto[] findPersonsByAge(@PathVariable Integer minAge, @PathVariable Integer maxAge) {
		return personService.findPersonsByAgeBetween(minAge, maxAge);
	}

	@PutMapping("/{id}/name/{name}")
	public PersonDto updatePersonName(@PathVariable Integer id, @PathVariable String name) {
		return personService.updatePersonName(id, name);
	}

	@GetMapping("/name/{name}")
	public PersonDto[] findPersonsByName(@PathVariable String name) {
		return personService.findPersonsbyName(name);
	}

	@GetMapping("/population/city")
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		return personService.getCitiesPopulation();
	}

	@PutMapping("/{id}/address")
	public PersonDto updatePersonAddress(@PathVariable Integer id, @RequestBody AddressDto addressDto) {
		return personService.updatePersonAddress(id, addressDto);
	}

	@DeleteMapping("/{id}")
	public PersonDto deletePerson(@PathVariable Integer id) {
		return personService.deletePerson(id);
	}

	@GetMapping("/children")
	public PersonDto[] findAllChildren() {
		return personService.findAllChildren();
	}

	@GetMapping("/salary/{minSalary}/{maxSalary}")
	public PersonDto[] findEmployeesBySalaryBetween(@PathVariable long minSalary, @PathVariable long maxSalary) {
		return personService.findBySalaryBetween(minSalary, maxSalary);
	}
}
