<template>

  <!-- 2 tables, a movie table of movies showing -->
  <!-- A table where movies can be added as "Watched Movies" -->
  <!-- Buttons to add to Watched Movies and or go to seating. -->


  <div class = "container">
    <h1 class = "text-center">Movies list</h1>
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
      </tr>
    </table>
    <!-- Button to clear the list. Button to remove a singular movie?? -->
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
    addToWatched(movie) {
      //Avoid duplicates
      if (!this.watchedMovies.some(watchedMovie => watchedMovie.title ===  movie.title)) {
        this.watchedMovies.push(movie);
      }
    },
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
    availableSeats(takenSeatsString) {
      if (!takenSeatsString) return 200; //if no seats are taken <=> takenSeatsString is empty

      const takenSeatCount = takenSeatsString.split(',').length; //take the string, split, get the length
      return 200 - takenSeatCount //return 200 - seatcount
    },
    //Method to calculate the similarity score, with weights
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
    sortMoviesByWatched() {
      const scoredMovies = this.movies.map(movie => {
        return {
          ...movie,
          similairtyscore: this.SimilarityScore(movie)
        };
      });

      scoredMovies.sort((a,b) => {
        if (a.similairtyscore > b.similairtyscore) return -1;
        if (a.similairtyscore < b.similairtyscore) return 1;
        return a.id - b.id; //if scores are euqal, smaller id comes first.
      });
      //updating movies array
      this.movies = scoredMovies;
    }
  },
  created() {
    this.getMovies()
  }
}

</script>

<!--

<style>

</style>

-->
