package io.movies.cgisuvi;

import io.movies.cgisuvi.models.CatalogItem;
import io.movies.cgisuvi.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CgisuviApplication implements CommandLineRunner {
	//Running the main method to get an endpoint to get data from.
	//Movies themselves will be hardcoded, hopefully with enough variety to cover
	//testing by hand.

	public static void main(String[] args) {
		SpringApplication.run(io.movies.cgisuvi.CgisuviApplication.class, args);
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
				.build();

		CatalogItem movie2 = CatalogItem.builder()
				.title("Scary Movie 1")
				.genre("Horror")
				.language("English")
				.ageLimit("PG16")
				.startTime("21:30")
				.build();

		CatalogItem movie3 = CatalogItem.builder()
				.title("Marley and Me")
				.genre("Drama")
				.language("English")
				.ageLimit("PG18")
				.startTime("16:30")
				.build();

		CatalogItem movie4 = CatalogItem.builder()
				.title("Cars")
				.genre("Animation")
				.language("Estonian")
				.ageLimit("PG3")
				.startTime("12:30")
				.build();

		movieRepo.save(movie1);
		movieRepo.save(movie2);
		movieRepo.save(movie3);
		movieRepo.save(movie4);
	}
}
