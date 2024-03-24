<template>
  <div class = "container">
    <h1 class = "text-center">Movies list</h1>
    <!-- Sorting controls -->
    <div class = "sorting-control">
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
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

import movieService from "@/services/movieService";


export default {
  // eslint-disable-next-line
  name: 'Movies',
  data() {
    return {
      movies: [],
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
