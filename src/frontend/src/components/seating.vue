<template>
  <div>
    <h2 class = "text-center">Seating Chart</h2>
    <table>
      <tbody>
      <!-- loop for rows -->
      <tr v-for="row in 10" :key="`row-${row}`">
        <!-- marker for the start of the row -->
        <td>Row {{ 11 - row }}</td>

        <!-- inner loop for seat nums.-->
        <td v-for="n in 20" :key="`seat-${row}-${n}`" :class="{ taken: isSeatTaken(200 - ((row - 1) * 20) - (n - 1)) }">
          {{ 200 - ((row - 1) * 20) - (n - 1) }}
        </td>
        <!-- markoer for the end of the row-->
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
import movie from "@/components/movie.vue";



export default {
  // eslint-disable-next-line
  name: 'seating',
  props: ['id'],

  data() {
    return {
      takenSeatsString: ''

    }
  },
  created() {
    this.takenSeatsString = this.$route.query.takenSeatsString || '';
  },

  computed: {
    seats() {
      return Array.from({length: 20}, (_, i) => i + 1);
    },

    //have to compute the seats again :(
    takenSeats() {
      //check, to see if the string is being provided properly, which it seems like it is not...
      if (!this.takenSeatsString) {
        console.log('takenSeatsString is empty or not provided');
        console.log(movie.takenSeatsString)
        return [];
      }
      const seatsArray = this.takenSeatsString.split(',').map(Number);
      console.log('Taken seats array:', seatsArray);
      return seatsArray;
    },
  },
  methods: {
    isSeatTaken(seatNumber) {
      const isTaken = this.takenSeats.includes(seatNumber);
      console.log(`Seat ${seatNumber}: ${isTaken ? 'taken' : 'free'}`);
      return isTaken;
    },
    watch: {
      takenSeatsString(newVal) {
        console.log('New taken seats:', newVal);
      }
    },
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
//if the seat is taken, make it red
.taken {
  background-color: red;
}
table td.taken {
  background-color: red !important;
}
</style>