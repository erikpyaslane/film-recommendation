<!-- Template(only movie-details part) and style genereated with
ChatGPT https://chat.openai.com/c/a7a92d90-8b30-48ad-9209-ce7e288d4a57
-->

<template>
  <div class="flex w-100 justify-content-center align-items-center">
    <div class="w-100 flex row justify-content-center align-items-center filter-line mx-0">
      <div class="schedule-line col-md-7 flex justify-content-center align-self-center">
        <div v-for="(day, index) in nextSevenDays"
             :key="index"
             :class="['schedule-day', { 'active': index === activeDayIndex }]"
             @click="setActiveDay(index)"
             class="py-2">

          {{ day }}
        </div>
      </div>
      <div class="dropdown col-md-2">
        <button
            class="btn btn-secondary dropdown-toggle"
            type="button"
            id="genreDropdownButton"
            @click="toggleDropdown('genre')"
        >
          Žanrid
        </button>
        <div
            class="dropdown-menu"
            :class="{ 'show': isGenreDropdownOpen }"
            aria-labelledby="genreDropdownButton"
        >
          <div v-for="(genre, index) in genres" :key="index">
            <label>
              <input type="checkbox" v-model="chosenGenres" :value="genre" >
              {{ genre }}
            </label>
          </div>
        </div>
      </div>

      <div class="dropdown col-md-2">
        <button
            class="btn btn-secondary dropdown-toggle"
            type="button"
            id="ageDropdownButton"
            @click="toggleDropdown('age')"
        >
          Vanuse piirangud
        </button>
        <div class="dropdown-menu" :class="{ 'show': isAgeDropdownOpen }" aria-labelledby="ageDropdownButton">
          <div v-for="(restriction, index) in ageRestrictions" :key="index">
            <label>
              <input type="checkbox" v-model="chosenRestrictions" :value="restriction">
              {{ restriction }}
            </label>
          </div>
        </div>
      </div>
    </div>
    <div v-if="filteredSessions.length === 0">
      <p>No sessions available</p>
    </div>
    <div v-else class="mt-5">
      <div v-for="(session) in filteredSessions" :key="session.id">
        <div class="session-details">
          <div class="grid-container">
            <div class="time"><h5>Algus:</h5>
              <h1>{{ session.timeOfSession }}</h1></div>
            <div class="title"><h1>{{ session.movie.title }}</h1></div>
            <div class="rating"><h5>Rating:</h5>
              <h1>{{ session.movie.rating }}</h1></div>
          </div>
          <!-- Second Row -->
          <div class="second-row flex justify-content-between">
            <div class="movie-details">
              <p><strong>Vanuse reiting:</strong> {{ session.movie.ageRestriction }}</p>
              <p><strong>Väljalastud:</strong> {{ session.movie.releaseYear }}</p>
              <p><strong>Žanrid:</strong> {{ session.movie.genres.join(', ') }}</p>
            </div>
            <div class="session-info">
              <h3>Seanssi detailid</h3>
              <p><strong>Kuupäev:</strong> {{ session.dateOfSession }}</p>
              <p><strong>Keel:</strong> {{ session.language }}</p>
            </div>
          </div>
          <div class="button-container">
            <router-link :to="'/session/' + session.id">
              <button>Broneeri kohad</button>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'SessionList',
  data() {
    return {
      isGenreDropdownOpen: false,
      isAgeDropdownOpen: false,
      nextSevenDays: [],
      nextSevenDates: [],
      filteredSessions: [],
      ageRestrictions: [],
      genres: [],
      chosenRestrictions: [],
      chosenGenres: [],
      activeDayIndex: 0,
    };
  },
  async created() {
    try {
      const currentDate = new Date();
      currentDate.setHours(currentDate.getHours() + 2);
      const response1 = await axios.get("http://localhost:8080/api/sessions", {
        params: {
          date: currentDate
        }
      });
      const response2 = await axios.get("http://localhost:8080/api/age_restrictions");
      const response3 = await axios.get("http://localhost:8080/api/genres");
      this.ageRestrictions = response2.data;
      this.filteredSessions = response1.data;
      this.genres = response3.data;
    } catch (error) {
      console.error("Error :", error);
      this.filteredSessions = [];
    }
  },
  mounted() {
    this.generateNextSevenDays();
  },
  methods: {
    filterSessions(date) {
      try {
        console.log(this.chosenGenres)
        axios.get("http://localhost:8080/api/sessions", {
          params: {
            date: date,
            genres: this.chosenGenres.join(','),
            ageRestrictions: this.chosenRestrictions.join(',')
          },
        }).then(response => {
          console.log(response.data);
          this.filteredSessions = response.data;
        }).catch(error => {
          console.error("Error fetching sessions:", error);
          this.filteredSessions = [];
        });
      } catch (error) {
        console.error("Error:", error);
        this.filteredSessions = [];
      }
    },
    formatDate(date) {
      const year = date.getFullYear();
      const month = ('0' + (date.getMonth() + 1)).slice(-2); // Month is zero-based, so add 1
      const day = ('0' + date.getDate()).slice(-2);
      const hour = ('0' + date.getHours()).slice(-2);
      const minute = ('0' + date.getMinutes()).slice(-2);
      const second = ('0' + date.getSeconds()).slice(-2);

      // Construct the formatted date string
      return `${year}-${month}-${day}T${hour}:${minute}:${second}`;
    },

    toggleDropdown(dropdown) {
      if (dropdown === 'genre') {
        this.isGenreDropdownOpen = !this.isGenreDropdownOpen;
        this.isAgeDropdownOpen = false; // Close other dropdowns if needed
      } else if (dropdown === 'age') {
        this.isAgeDropdownOpen = !this.isAgeDropdownOpen;
        this.isGenreDropdownOpen = false; // Close other dropdowns if needed
      }
    },
    generateNextSevenDays() {
      const today = new Date();
      for (let i = 0; i < 7; i++) {
        const nextDay = new Date();
        nextDay.setDate(today.getDate() + i);
        this.nextSevenDates.push(nextDay);
        const options = {weekday: 'long', month: 'short', day: 'numeric'};
        const formattedDate = nextDay.toLocaleDateString('en-US', options);
        this.nextSevenDays.push(formattedDate);
      }
    },
    setActiveDay(index) {
      this.activeDayIndex = index;
      const date = this.nextSevenDates[index];
      const formattedDate = this.formatDate(date);
      console.log(formattedDate)
      this.filterSessions(formattedDate);
    }
  }
}
</script>


<style scoped>
.session-details {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 20px;
}

.first-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.second-row {
  display: flex;
  justify-content: space-between;
}

.movie-details,
.session-info {
  flex: 1;
}

.movie-details p,
.session-info p {
  margin: 5px 0;
}

.button-container {
  margin-top: 10px;
}

.button-container button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 4px;
}

.button-container button:hover {
  background-color: #0056b3;
}

.session-details {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 20px;
}

.grid-container {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  gap: 10px;
  align-items: center;
}

.grid-container > div {
  text-align: center;
  border: none;
}

.time,
.title,
.rating {
  border: 1px solid #ccc;
  padding: 10px;
}

.schedule-line {
  display: flex;
}

.schedule-day {
  flex: 1;
  max-width: 100px;
  min-height: 60px;
  text-align: center;
  cursor: pointer;
  color: white;
  border: none;
  padding-left: 5px;
  padding-right: 5px;
  background-color: black;
}

/* Style for active day */
.schedule-day.active {

  background-color: #ffdd00;
  color: black;
}

button {
  color: white;
  border: none;
  background-color: black;
}

button:active {
  background-color: #ffdd00;
  border: none;
}

.filter-line {
  background-color: black;
}

select {
  min-height: 150px;
}
</style>