package io.movies.cgisuvi;

import io.movies.cgisuvi.models.CatalogItem;
import io.movies.cgisuvi.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class CgisuviApplication implements CommandLineRunner {
	//Running the main method to get an endpoint to get data from.
	//Movies themselves will be hardcoded, hopefully with enough variety to cover
	//testing by hand.

	public static void main(String[] args) {
		SpringApplication.run(io.movies.cgisuvi.CgisuviApplication.class, args);
	}

	private String generateSeatsString() {
		Random random = new Random();
		int numOfSeats = random.nextInt(31) + 10; //Generate num of taken seats in range of 10 - 40
		return IntStream.generate(() -> random.nextInt(200) + 1) //a random int, range 1-200
				.distinct() //to make generated values unique
				.limit(numOfSeats) //so they'd be in the range we generate
				.mapToObj(String::valueOf) // covnert int to str
				.collect(Collectors.joining(",")); //join them with commas
	}

	@Autowired
	private MovieRepo movieRepo;
	@Override
	public void run(String... args) throws Exception {


		CatalogItem movie1 = CatalogItem.builder()
				.title("LOTR")
				.genre("Adventure")
				.language("English")
				.ageLimit("PG13")
				.startTime("08:30")
				.takenSeatsString(generateSeatsString())
				.build();

		CatalogItem lotr1 = CatalogItem.builder()
				.title("LOTR")
				.genre("Adventure")
				.language("English")
				.ageLimit("PG13")
				.startTime("14:00")
				.takenSeatsString(generateSeatsString())
				.build();

		CatalogItem lotr2 = CatalogItem.builder()
				.title("LOTR")
				.genre("Adventure")
				.language("English")
				.ageLimit("PG13")
				.startTime("20:15")
				.takenSeatsString(generateSeatsString())
				.build();

		CatalogItem movie2 = CatalogItem.builder()
				.title("Scary Movie 1")
				.genre("Horror")
				.language("English")
				.ageLimit("PG16")
				.startTime("21:30")
				.takenSeatsString(generateSeatsString())
				.build();

		CatalogItem movie3 = CatalogItem.builder()
				.title("Marley and Me")
				.genre("Drama")
				.language("English")
				.ageLimit("PG18")
				.startTime("16:30")
				.takenSeatsString(generateSeatsString())
				.build();

		CatalogItem movie4 = CatalogItem.builder()
				.title("Cars")
				.genre("Animation")
				.language("Estonian")
				.ageLimit("PG3")
				.startTime("12:30")
				.takenSeatsString(generateSeatsString())
				.build();

		CatalogItem movie5 = CatalogItem.builder()
				.title("Ratatouille")
				.genre("Animation")
				.language("Estonian")
				.ageLimit("PG3")
				.startTime("10:30")
				.takenSeatsString(generateSeatsString())
				.build();
		CatalogItem movie6 = CatalogItem.builder()
				.title("Dune 2")
				.genre("Adventure")
				.language("English")
				.ageLimit("PG13")
				.startTime("16:30")
				.takenSeatsString(generateSeatsString())
				.build();
		CatalogItem movie7 = CatalogItem.builder()
				.title("Dune 2")
				.genre("Adventure")
				.language("Russian")
				.ageLimit("PG13")
				.startTime("21:30")
				.takenSeatsString(generateSeatsString())
				.build();
		CatalogItem movie8 = CatalogItem.builder()
				.title("Remember Me")
				.genre("Drama")
				.language("English")
				.ageLimit("PG18")
				.startTime("13:30")
				.takenSeatsString(generateSeatsString())
				.build();

		movieRepo.save(movie1);
		movieRepo.save(movie2);
		movieRepo.save(movie3);
		movieRepo.save(movie4);
		movieRepo.save(lotr1);
		movieRepo.save(lotr2);
		movieRepo.save(movie5);
		movieRepo.save(movie6);
		movieRepo.save(movie7);
		movieRepo.save(movie8);
	}
}
