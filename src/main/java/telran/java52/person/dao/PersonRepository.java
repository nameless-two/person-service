package telran.java52.person.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.java52.person.dto.CityPopulationDto;
import telran.java52.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	List<Person> findByNameIgnoreCase(String name);

	List<Person> findByAddressCityIgnoreCase(@Param("cityName") String city);

	List<Person> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

	@Query("select new telran.java52.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) DESC")
	List<CityPopulationDto> getCitiesPopulation();

	@Query("select c from Child c")
	List<Person> findAllChildren();

	@Query("select e from Employee e where e.salary between ?1 and ?2")
	List<Person> findBySalaryBetween(long minSalary, long maxSalary);

}
