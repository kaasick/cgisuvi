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
    </div>
    <table class = "table table-striped">
      <thead>
      <th> Movie Title</th>
      <th> Movie Genre</th>
      <th> Movie Language</th>
      <th> Movie Age Limit</th>
      <th> Movie Starting time</th>
      </thead>
      <!-- Table body -->
      <tbody>
      <tr v-for = "movie in movies" v-bind:key = "movie.id">
        <td> {{ movie.title }} </td>
        <td> {{ movie.genre }} </td>
        <td> {{ movie.language }} </td>
        <td> {{ movie.ageLimit }} </td>
        <td> {{ movie.startTime }} </td>
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
  //hopefully the method names are self-explnetory enoug for now


  // eslint-disable-next-line
  name: 'Movies',
  data() {
    return {
      movies: [],
      watchedMovies: [],
      selectedSort: 'startTime' //default sorting criteria
    }
  },
  methods: {
    getMovies() {
      movieService.getCatalogItem().then((response) =>
          this.movies = response.data)
    },
    sortMovies() {
      this.movies.sort((a, b) => {
        if (a[this.selectedSort] < b[this.selectedSort]) return -1;
        if (a[this.selectedSort] > b[this.selectedSort]) return 1;
        return 0;
      })
    },
    addToWatched(movie) {
      //Avoid duplicates
      if (!this.watchedMovies.some(watchedMovie => watchedMovie.id ===  movie.id)) {
        this.watchedMovies.push(movie);
      }
    },
    clearWatchedMovies() {
      this.watchedMovies = [];
    },
    goToSeating(movieId) {
      this.$router.push({ name: 'seating', params: { id: movieId } });
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
