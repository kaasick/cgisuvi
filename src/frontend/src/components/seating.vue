<template>
  <div>
    <h2 class = "text-center">Seating Chart</h2>
    <table>
      <tbody>
      <!-- Outer loop for rows -->
      <tr v-for="row in 10" :key="`row-${row}`">
        <!-- Marker for the start of a row -->
        <td>Row {{ 11 - row }}</td>

        <!-- Inner loop for seat numbers -->
        <td v-for="seat in 20" :key="`row-${row}-seat-${seat}`">{{ seat }}</td>

        <!-- Marker for the end of a row -->
        <td>Row {{ 11 - row }}</td>
      </tr>
      </tbody>
    </table>
    <!-- Screen representation -->
    <div class="screen-container">
      <div class="screen">Screen</div>
    </div>
  </div>
</template>

<script>
//TBD
export default {
  // eslint-disable-next-line
  name: 'seating',
  pros: ['id'],
  computed: {
    seats() {
      return Array.from({length: 20}, (_, i) => i + 1);
    }
  }
};
</script>

<style>

.screen-container {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

.screen {
  background-color: black;
  color: white;
  padding: 10px 150px;
  text-align: center;
  border-radius: 5px;
}

table {
  width: 100%;
  text-align: center;
}
td {
  border: 1px solid #000;
  padding: 8px;
}
</style>