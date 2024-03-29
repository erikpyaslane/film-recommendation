<template>

  <section v-if="session">
    <div class="w-100">
      <h1 style="font-size: 50px" > MONITOR</h1>
    </div>
    <div class="flex d-flex flex-row justify-content-end align-items-center">
      <div class="col-md-9 justify-content-end" style="text-align: center;">
        <h1>{{ session.movie.title }}</h1>
        <div v-for="(row, indexX) in session.seats" :key="indexX"
             style="display: flex; flex-direction: column; align-items: center;">
          <div class="d-flex flex-row">
            <div v-for="(col, indexY) in row" :key="indexY">
              <div v-if="session.seats[indexX][indexY] === true" @click="incrementReservedSeats">
                <button style="background-color: red;" disabled></button>
              </div>
              <div v-else-if="isRecommendedSeat(indexX, indexY)" @click="incrementFreeSeats">
                <button style="background-color: green;" disabled></button>
              </div>

              <div v-else @click="incrementFreeSeats">
                <button style="background-color: gray;"></button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-3 justify-content-center">
        <div class="flex d-flex flex-row justify-content-center">
          <div>
            <button @click="decreaseNum" :disabled="this.numOfSeats === 1">
              <i class="arrow left"></i>
            </button>
          </div>
          <div style="text-align: center; bottom: 0">
            <p style="text-align:center">
              {{ this.numOfSeats }}
            </p>
          </div>
          <div>
            <button @click="increaseNum" :disabled="this.numOfSeats === this.totalFreeSeats">
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
      <button class="go-to-film" @click="reserveSeats" >
        Broneeri kohad
      </button>
      <p>
        Praegu on reserveeritud {{ totalReservedSeats }} kohta
      </p>
    </div>

  </section>
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
    async reserveSeats() {
      try {
        const response  = await axios.post(`http://localhost:8080/api/watched_movies`,
            {
              movie: this.session.movie
            });
        console.log(response.data);
        window.location.href = '/sessions';
      } catch (error) {
        console.log("Not such film")
      }
    },

    async recommendFreeSeats() {
      try {
        const response = await axios.get(`http://localhost:8080/api/sessions/${this.$route.params.id}/free_seats=${this.numOfSeats}`);
        this.recommendedSeats = response.data;
        console.log(this.freeSeats)
        console.log(this.numOfSeats)
      } catch (error) {
        console.log(error);
      }
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
    incrementFreeSeats() {
      this.freeSeats++;
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
    totalFreeSeats() {
      let freeSeats = 0;
      for (let row of this.session.seats) {
        for (let seat of row) {
          if (seat === false) {
            freeSeats++;
          }
        }
      }
      return freeSeats;
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