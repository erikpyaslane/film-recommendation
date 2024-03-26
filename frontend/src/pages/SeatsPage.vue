<template>
  <div style="text-align: center;">
    <h1>{{ session.movie.title }}</h1>
    <div v-for="(row, indexX) in session.seats" :key="indexX" style="display: flex; flex-direction: column; align-items: center;">
      <div style="display: flex; flex-direction: row;">
        <div v-for="(col, indexY) in row" :key="indexY">
          <div v-if="session.seats[indexX][indexY] === true">
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
      freeSeats: {},
    }
  },
  async created() {
    try {
      const response = await axios.get(`http://localhost:8080/api/sessions/${this.$route.params.id}` );
      this.session = response.data;

    } catch (error) {
      console.error("Not found such session!")
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