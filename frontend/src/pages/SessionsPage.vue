<template>
  <div>

  </div>
  <SessionList />
</template>

<script>
import SessionList from "@/components/SessionList.vue";
export default {
  name: "SessionsPage",
  components: {
    SessionList,
  },
  data() {
    return {
      showSlides: false,
      isLoading: true,
    };
  },
};
</script>


<style scoped>

</style>