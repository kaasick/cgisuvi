<template>
  <div>
    <h2 class = "text-center">Seating Chart</h2>
    <!-- Input for the number of tickets -->
    <div class = "tickets-input">
      <label for = "tickets"> Number of Tickets:</label>
      <input type = "number" id = "tickets" v-model.number="totalTickets"
             @change = "ticketChange" min = "1">
    </div>
    <table>
      <tbody>
      <!-- loop for rows -->
      <tr v-for="row in 10" :key="`row-${row}`">
        <!-- marker for the start of the row -->
        <td>Row {{ 11 - row }}</td>

        <!-- inner loop for seat nums.-->
        <td v-for="n in 20" :key="`seat-${row}-${n}`"
            :class="getSeatClass(200 - ((row - 1) * 20) - (n - 1))"
            @click="toggleSeatSelection(200 - ((row - 1) * 20) - (n - 1))">
          {{ 200 - ((row - 1) * 20) - (n - 1) }}
        </td>
        <!--
        <td v-for="n in 20" :key="`seat-${row}-${n}`" :class="{ taken: isSeatTaken(200 - ((row - 1) * 20) - (n - 1)) }">
          {{ 200 - ((row - 1) * 20) - (n - 1) }}
        </td>
        -->
        <!-- markoer for the end of the row-->
        <td>Row {{ 11 - row }}</td>
      </tr>
      </tbody>
    </table>
    <!-- Screen representation -->
    <div class="screen-container">
      <div class="screen">Screen</div>
    </div>
    <div class = "confirmSeatsButton">
    <button @click = "confirmedSeats">Confirm Seats</button>
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
      takenSeatsString: '',
      totalTickets: 1,
      selectedSeats: [],
      recommendedSeats: [],

    }
  },
  created() {
    this.takenSeatsString = this.$route.query.takenSeatsString || '';
    this.recommendSeats();
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
      //console.log(`Seat ${seatNumber}: ${isTaken ? 'taken' : 'free'}`);
      return isTaken;
    },
    toggleSeatSelection(seatNumber) {
      const index = this.selectedSeats.indexOf(seatNumber);
      if (index > -1) {
        this.selectedSeats.splice(index, 1); //deselect
      } else if (this.selectedSeats.length < this.totalTickets) {
        this.selectedSeats.push(seatNumber); //select, if under ticket number
      }
    },
    getSeatClass(seatNumber) {
      if (this.isSeatTaken(seatNumber)) return 'taken';
      if (this.selectedSeats.includes(seatNumber)) return 'selected';
      //logic for recommended
    },
    confirmedSeats() {
      if (this.selectedSeats.length !== this.totalTickets) {
        alert('Please select your chosen number of seats.');
        return;
      }
      alert(`Your seats are: ${this.selectedSeats.join(', ')}`)
    },
    //to recommend seats when the num of tickets changes.
    ticketChange() {
      this.recommendSeats();
    },
    //The idea is to define a "good box", where the initial recommendations are made,
    //if the seats dont fit in a row, a new place will be searched,
    //if such place cant be found in the defined box,
    //the box will be widened
    //of course there is always an option to choose boxes with clicking.
    recommendSeats() {
      if (this.totalTickets > 5) {
        console.log("Auto recommend is for =<5");
        return;
      }

      this.selectedSeats = []; // Reset results

      //
      let startRow = 4; // startrow
      let endRow = 7; // endrow
      let startSeatInRow = 7; // startseat
      let endSeatInRow = 14; // endseat

      // try to find a seat in the box
      let found = this.findSeatsInBox(startRow, endRow, startSeatInRow, endSeatInRow);

      // not found, widen the box
      while (!found && this.selectedSeats.length < this.totalTickets) {
        startRow = Math.max(1, startRow - 1);
        endRow = Math.min(10, endRow + 1);
        startSeatInRow = Math.max(1, startSeatInRow - 1);
        endSeatInRow = Math.min(20, endSeatInRow + 1);

        found = this.findSeatsInBox(startRow, endRow, startSeatInRow, endSeatInRow);
      }
      //if not found, will be console logged, alerted too=?
      if (!found) {
        console.log("Couldn't find a continuous block of seats in a single row for the requested number of tickets.");
      }
    },

    //the finding method for the recommendSeats()
    //iterates thorugh the specified box and tries to find
    //available seats to match the num of tickets
    //seats are to be connected and in one row
    findSeatsInBox(startRow, endRow, startSeat, endSeat) {
      for (let row = startRow; row <= endRow; row++) {
        for (let seat = startSeat; seat <= endSeat - this.totalTickets + 1; seat++) {
          let availableSeats = [];
          for (let offset = 0; offset < this.totalTickets; offset++) {
            const seatNumber = (row - 1) * 20 + seat + offset;
            if (!this.isSeatTaken(seatNumber)) {
              availableSeats.push(seatNumber);
            } else {
              availableSeats = [];
              break;
            }
          }
          if (availableSeats.length === this.totalTickets) {
            this.selectedSeats = availableSeats;
            return true; // seats found
          }
        }
      }
      return false; // not found .
    },
    watch: {
      takenSeatsString(newVal) {
        console.log('New taken seats:', newVal);
      },
      totalTickets() {
          this.recommendSeats();

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
.selected {
  background-color: lawngreen; /* Change color as needed */
}

.confirmSeatsButton {
  display: flex;
  justify-content: center; /* Center horizontally */
  margin-top: 20px; /* Optional: Add some space above the button */
}
</style>