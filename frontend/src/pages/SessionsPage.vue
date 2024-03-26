<template>
  <SessionList :sessions="sessions"/>
</template>

<script>
import axios from "axios";
import SessionList from "@/components/SessionList.vue";
export default {
  name: "SessionsPage",
  components: {
    SessionList,
  },
  data() {
    return {
      sessions: [],
      showSlides: false,
      isLoading: true,
    };
  },

  async created() {
    try {
      const response = await axios.get("http://localhost:8080/api/sessions/all");
      this.sessions = response.data;
    } catch (error) {
      console.error("Error :", error);
    }
  },
};
</script>



<style scoped>

</style>