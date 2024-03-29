<template>
  <div v-if="session" class="flex d-flex flex-row justify-content-end align-items-center">
    <div  class="col-md-8" style="text-align: center;">
      <h1>{{ session.movie.title }}</h1>
      <div v-for="(row, indexX) in session.seats" :key="indexX"
           style="display: flex; flex-direction: column; align-items: center;">
        <div style="display: flex; flex-direction: row;">
          <div v-for="(col, indexY) in row" :key="indexY">
            <div v-if="session.seats[indexX][indexY] === true" @click="incrementReservedSeats">
              <button style="background-color: red;" disabled></button>
            </div>
            <div v-else-if="isRecommendedSeat(indexX, indexY)">
              <button style="background-color: green;" disabled></button>
            </div>

            <div v-else>
              <button style="background-color: gray;"></button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-4 justify-content-center">
      <div class="flex d-flex flex-row justify-content-center">
        <div>
          <button @click="decreaseNum" :disabled="this.numOfSeats === 1">
            <i class="arrow left"></i>
          </button>
        </div>
        <div>
          <p>
            {{ this.numOfSeats }}
          </p>
        </div>
        <div>
          <button @click="increaseNum" :disabled="this.numOfSeats === this.freeSeats">
            <i class="arrow right"></i>
          </button>
        </div>
      </div>
      <div>
        <button class="go-to-film" @click="recommendFreeSeats">
          Soovita kohad
        </button>
      </div>
    </div>



  </div>
  <div>
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
  components: {},
  data() {
    return {
      session: null,
      recommendedSeats: {},
      reservedSeats: 0,
      numOfSeats: 1,
      freeSeats: 0,
    }
  },
  async created() {
    try {
      const response1 = await axios.get(`http://localhost:8080/api/sessions/${this.$route.params.id}`);
      this.session = response1.data;
      const response2 = await
          axios.get(`http://localhost:8080/api/sessions/${this.$route.params.id}/free_seats=${this.numOfSeats}`);
      this.recommendedSeats = response2.data;
      this.getTotalSeats();
      console.log(this.recommendedSeats)
    } catch (error) {
      console.error("Not found such session!")
    }
  },
  methods: {
    async recommendFreeSeats() {
      try {
        const response = await axios.get(`http://localhost:8080/api/sessions/${this.$route.params.id}/free_seats=${this.numOfSeats}`);
        this.recommendedSeats = response.data;
      } catch (error) {
        console.log(error);
      }
    },

    getTotalSeats() {
      this.totalSeats = this.session.seats.length * this.session.seats[0].length;
    },

    increaseNum() {
      this.numOfSeats++
    },
    decreaseNum() {
      this.numOfSeats--
    },
    incrementReservedSeats() {
      this.reservedSeats++;
    },
    isRecommendedSeat(indexX, indexY) {
      if (this.recommendedSeats === null) {
        return false;
      }
      for (let i = 0; i < this.recommendedSeats.length; i++) {
        if (Array.isArray(this.recommendedSeats[i]) && this.recommendedSeats[i].length === 2) {
          if (this.recommendedSeats[i][0] === indexX && this.recommendedSeats[i][1] === indexY) {
            return true; // Coordinates found, return true
          }
        }
      }
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
    },

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

.go-to-film {
  margin-top: 50px;
  width: 200px;
  height: 60px;
  border-radius: 5px;
}

.arrow {
  border: solid black;
  border-width: 0 3px 3px 0;
  display: inline-block;
  padding: 3px;
}

.right {
  transform: rotate(-45deg);
  -webkit-transform: rotate(-45deg);
}

.left {
  transform: rotate(135deg);
  -webkit-transform: rotate(135deg);
}
</style>