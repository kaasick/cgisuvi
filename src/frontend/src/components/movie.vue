<template>

  <!-- 2 tables, a movie table of movies showing -->
  <!-- A table where movies can be added as "Watched Movies" -->
  <!-- Buttons to add to Watched Movies and or go to seating. -->


  <div class = "container">
    <div class="header-area d-flex justify-content-between align-items-center mb-3">
    <h1 class = "text-center">Movies list</h1>
      <button class="btn btn-primary" @click="refreshMoviesList">Refresh List</button>
    </div>
    <!-- Sorting controls -->
    <div class = "sorting-control">
      <!-- Basic sorting by chosen {{Attribute}} -->
      <select v-model="selectedSort" @change = "sortMovies">

        <option value="genre">Genre</option>
        <option value="Lang">Language</option>
        <option value="ageLimit">Age Limit</option>
        <option value="startTime">Start Time</option>

      </select>
      <!-- The button to sort based on movies watched, is unclickable when the
       watched movies list is empty -->
      <button @click="sortMoviesByWatched" :disabled="watchedMovies.length === 0"
              title="Add movies to Watched Movies list to enable this feature.">
        Recommend Based on Watched</button>
    </div>
    <table class = "table table-striped">
      <thead>
      <th> Movie Title</th>
      <th> Movie Genre</th>
      <th> Movie Language</th>
      <th> Movie Age Limit</th>
      <th> Movie Starting time</th>
      <th> Available seats</th>
      </thead>
      <!-- Table body -->
      <tbody>
      <tr v-for = "movie in movies" v-bind:key = "movie.id">
        <td> {{ movie.title }} </td>
        <td> {{ movie.genre }} </td>
        <td> {{ movie.language }} </td>
        <td> {{ movie.ageLimit }} </td>
        <td> {{ movie.startTime }} </td>
        <td> {{availableSeats(movie.takenSeatsString)}} </td>
        <td>
          <button @click = "addToWatched(movie)"> Add to Watched</button>>
          <button @click = "goToSeating(movie.id)"> View Seating </button>
        </td>
      </tr>
      </tbody>
    </table>
    <h2>Watched Movies</h2>>
    <table class = "table">
      <thead>
      <th> Movie Title</th>
      <th> Movie Genre</th>
      <th> Movie Language</th>
      <th> Movie Age Limit</th>
      <th> Movie Starting Time</th>
      </thead>
      <tr v-for = "movie in watchedMovies" v-bind:key = "movie.id">
        <td> {{ movie.title }}</td>
        <td> {{ movie.genre }}</td>
        <td> {{ movie.language }}</td>
        <td> {{ movie.ageLimit }}</td>
        <td> {{ movie.startTime }}</td>
        <td>
          <!-- Add a button to remove a movie from the watched list -->
          <button @click="removeFromWatched(movie.id)">Remove</button>
        </td>
      </tr>
    </table>
    <!-- Button to clear the list. -->
    <button @click = "clearWatchedMovies"> Clear all Watched Movies </button>

  </div>
</template>

<script>

import movieService from "@/services/movieService";


export default {
  //As of now, the basic methods to provide some funcionality to the page
  //hopefully the method names are self-explanatory enough for now

  //Disabled next line do not get an eslint error for having a bad variable name o.o
  // eslint-disable-next-line
  name: 'Movies',
  data() {
    return {
      movies: [],
      watchedMovies: [],
      selectedSort: 'startTime' //sorted by startTime by default
    }
  },
  methods: {

    getMovies() {
      movieService.getCatalogItem().then((response) => {
        this.movies = response.data;
        this.sortMovies(); //sort the movies the first time they are fetched by startTime
      })
    },
    //func to sort movies based on the attributes of the object (title, startTime etc)
    sortMovies() {
      //Have to handle sorting by age limit a bit differently, because it is PG{digit(s)}
      if (this.selectedSort === 'ageLimit') {
        this.movies.sort((a, b) => {
          const ageA = parseInt(a.ageLimit.replace('PG', ''), 10);
          const ageB = parseInt(b.ageLimit.replace('PG', ''), 10);

          return ageA - ageB;
        });
      } else {
        //default sorting for other fields
        this.movies.sort((a, b) => {
          if (a[this.selectedSort] < b[this.selectedSort]) return -1;
          if (a[this.selectedSort] > b[this.selectedSort]) return 1;
          return 0;
        })
      }
    },
    //add the movie to the watched list
    addToWatched(movie) {
      //Avoid duplicates, as in movies with the same title
      if (!this.watchedMovies.some(watchedMovie => watchedMovie.title ===  movie.title)) {
        this.watchedMovies.push(movie);
      }
    },
    //to clear the list of watched movies
    clearWatchedMovies() {
      this.watchedMovies = [];
    },
    goToSeating(movieId) {
      const movie = this.movies.find(m => m.id === movieId);
      if (movie) {
        this.$router.push({
          name: 'seating',
          params: { id: movieId },
          query: { takenSeatsString: movie.takenSeatsString || '' }
            })
        }
      //this.$router.push({ name: 'seating', params: { id: movieId } });
    },

    //method to show the available seats to a movie
    availableSeats(takenSeatsString) {
      if (!takenSeatsString) return 200; //if no seats are taken <=> takenSeatsString is empty

      const takenSeatCount = takenSeatsString.split(',').length; //take the string, split, get the length
      return 200 - takenSeatCount //return 200 - seatcount
    },
    //Method to calculate the similarity score, with weights
    //func. used in sortByWatchedMovies()
    SimilarityScore(movie) {
      let score = 0;
      this.watchedMovies.forEach(watchedMovie => {
        //genre weights
        if (movie.genre === watchedMovie.genre) score += 3;
        //age limit weights
        if (movie.ageLimit === watchedMovie.ageLimit) score += 3;
        //comparing start Times
        const timeDiff = Math.abs(new Date(movie.startTime)
            - new Date(watchedMovie.startTime));
        if (timeDiff <= 2 * 36000000) score += 1; //2 hours in milliseconds
        //If the movie has been watched, apply a big penalty
        //So it would be at the bottom of the list
        //as in not recommended
        const hasBeenWatched = this.watchedMovies.some(
            watchedMovie => watchedMovie.title == movie.title);
        if (hasBeenWatched) score -= 100

      });
      return score;
    },
    //function for the "Recommend movies" button
    sortMoviesByWatched() {
      //filterin out the watched movies
      const unwatchedMovies = this.movies.filter(movie =>
          !this.watchedMovies.some(watchedMovie => watchedMovie.title === movie.title)
      );

      const scoredMovies = unwatchedMovies.map(movie => {
        return {
          ...movie,
          similairtyscore: this.SimilarityScore(movie)
        };
      });

      scoredMovies.sort((a,b) => {
        if (a.similairtyscore > b.similairtyscore) return -1;
        if (a.similairtyscore < b.similairtyscore) return 1;
        return a.id - b.id; //if scores are equal, smaller id comes first.
      });
      //updating movies array
      this.movies = scoredMovies;
    },
    //to refresh the movies list back to default
    refreshMoviesList() {
      this.getMovies();
      this.sortMovies();
    },
    removeFromWatched(movieId) {
      // Remove one movie, in case of missclicks or smth
      this.watchedMovies = this.watchedMovies.filter(movie => movie.id !== movieId);
    },
  },
  created() {
    this.getMovies()
  }
}

</script>



<style>

</style>


