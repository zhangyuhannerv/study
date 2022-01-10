<template>
  <div>
    {{ iid }}
  </div>
</template>

<script>
export default {
  name: "Detail",
  data() {
    return {
      iid: null
    }
  },
  methods: {},
  created() {
    this.iid = this.$route.params.id
  }
}
</script>

<style scoped>

</style>
