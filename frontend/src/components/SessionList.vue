<!-- Template(only movie-details part) and style genereated with
ChatGPT https://chat.openai.com/c/a7a92d90-8b30-48ad-9209-ce7e288d4a57
-->

<template>
  <div v-for="(session) in sessions" :key="session">
    <div class="session-details">
      <div class="movie-details">
        <h2>{{ session.movie.title }}</h2>
        <p><strong>Reiting:</strong> {{ session.movie.rating }}</p>
        <p><strong>Vanuse reiting:</strong> {{ session.movie.ageRestriction }}</p>
        <p><strong>Väljalastud:</strong> {{ session.movie.releaseYear }}</p>
        <p><strong>Žanrid:</strong> {{ session.movie.genres.join(', ') }}</p>
      </div>
      <div class="session-info">
        <h3>Session Details</h3>
        <p><strong>Kuupäev:</strong> {{ formatDate(session.dateOfSession) }}</p>
        <p><strong>Aeg:</strong> {{ session.timeOfSession }}</p>
        <p><strong>Keel:</strong> {{ session.language }}</p>
      </div>
    </div>
    <div>
      <router-link :to="'/session/' + session.id">
        <button>Broneeri kohad</button>
      </router-link>
    </div>
  </div>
</template>

<script>

export default {
  name: 'SessionList',
  props: {
    sessions: {
      type: Array,
      required: true
    }
  },
  methods: {
    formatDate(date) {
      const parts = date.split('-');
      const formattedDate = new Date(parts[0], parts[1] - 1, parts[2]);
      return formattedDate.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' });
    }
  }
}

</script>



<style scoped>
.session-details {
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 20px;
  margin-bottom: 20px;
}

/* Movie details */
.movie-details {
  margin-bottom: 15px;
}

.movie-details h2 {
  margin-top: 0;
  color: #333;
}

.movie-details p {
  margin: 5px 0;
}

/* Session info */
.session-info h3 {
  margin-top: 0;
  color: #333;
}

.session-info p {
  margin: 5px 0;
}
</style>