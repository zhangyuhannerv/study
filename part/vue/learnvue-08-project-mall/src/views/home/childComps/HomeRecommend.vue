<template>
  <div class="recommend">
    <div v-for="item in recommends" class="recommend-item">
      <a :href="item.link">
        <img :src="item.image" alt="">
        <div>{{ item.title }}</div>
      </a>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeRecommend",
  props: {
    recommends: {
      type: Array,
      default() {
        return []
      }
    }
  }
}
</script>

<style scoped>
.recommend {
  display: flex;
  padding: 10px 0 20px;
  border-bottom: 8px solid #eee;
}

.recommend-item {
  flex: 1;
  padding: 10px;
  text-align: center;
  font-size: 12px;
}

.recommend-item img {
  width: 100%;
  margin-bottom: 10px;
}
</style>
