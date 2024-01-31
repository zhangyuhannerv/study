<template>
  <el-tree
    node-key="catId"
    :data="menus"
    :props="defaultProps"
    @node-click="nodeClick"
    ref="menuTree"
  >
  </el-tree>
</template>

<script>
export default {
  data() {
    return {
      menus: [],
      defaultProps: {
        children: "children",
        label: "name"
      }
    };
  },
  methods: {
    getMenus() {
      this.$http({
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get"
      }).then(({ data }) => {
        this.menus = data.data;
      });
    },
    nodeClick(data, node, component) {
      // 向父组件发送事件
      this.$emit("tree-node-click", data, node, component);
    }
  },
  created() {
    this.getMenus();
  }
};
</script>

<style scoped></style>
