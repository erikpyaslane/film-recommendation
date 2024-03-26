<template>
  <div style="text-align: center;">
    <h1>{{ session.movie.title }}</h1>
    <div v-for="(row, indexX) in session.seats" :key="indexX" style="display: flex; flex-direction: column; align-items: center;">
      <div style="display: flex; flex-direction: row;">
        <div v-for="(col, indexY) in row" :key="indexY">
          <div v-if="isRecommendedSeat(indexX, indexY)">
            <button style="background-color: green;" disabled></button>
          </div>
          <div v-else-if="session.seats[indexX][indexY] === true" @click="incrementReservedSeats">
            <button style="background-color: red;" disabled></button>
          </div>
          <div v-else>
            <button style="background-color: gray;"></button>
          </div>
        </div>
      </div>
    </div>
    <button class="go-to-film">
      Broneeri kohad
    </button>
    <p>
      Praegu on reserveeritud {{ totalReservedSeats }} kohta
    </p>
  </div>
</template>

<script>

import axios from "axios";

export default {
  name: "SeatsPage",
  components: {
  },
  data() {
    return {
      session: {},
      recommendedSeats: {},
      reservedSeats: 0,
    }
  },
  async created() {
    try {
      const response1 = await axios.get(`http://localhost:8080/api/sessions/${this.$route.params.id}`);
      this.session = response1.data;
      const response2 = await
          axios.get(`http://localhost:8080/api/sessions/${this.$route.params.id}/free_seats=${3}`);
      this.recommendedSeats = response2.data;
    } catch (error) {
      console.error("Not found such session!")
    }
  },
  methods: {
    incrementReservedSeats() {
      this.reservedSeats++;
    },
    isRecommendedSeat(indexX, indexY) {
      return this.recommendedSeats.some(seat => seat[0] === indexX && seat[1] === indexY);
    }
  },
  computed: {
    totalReservedSeats() {
      let reservedSeats = 0;
      for (let row of this.session.seats) {
        for (let seat of row) {
          if (seat === true) {
            reservedSeats++;
          }
        }
      }
      return reservedSeats;
    }
  }
};
</script>



<style scoped>
button {
  border-radius: 50%;
  margin: 10px;
  height: 40px;
  width: 40px;
}

.go-to-film{
  margin-top: 50px;
  width: 200px;
  height: 60px;
  border-radius: 5px;
}
</style>