<template>
  <div>
    <el-tree
      node-key="catId"
      show-checkbox
      :data="menus"
      :props="defaultProps"
      :expand-on-click-node="false"
      :default-expanded-keys="expandedKey"
      draggable
      :allow-drop="allowDrop"
      @node-drop="handleDrop"
    >
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button
            v-if="node.level <= 2"
            type="text"
            size="mini"
            @click="() => append(data)"
          >
            Append
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="() => edit(data)"
          >
            Edit
          </el-button>
          <el-button
            v-if="node.childNodes.length == 0"
            type="text"
            size="mini"
            @click="() => remove(node, data)"
          >
            Delete
          </el-button>
        </span>
      </span>
    </el-tree>
    <el-dialog :title="dialogType" :visible.sync="dialogVisible" width="30%" :close-on-click-modal="false">
      <el-form :model="category">
        <el-form-item label="分类名称">
          <el-input v-model="category.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="category.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="计量单位">
          <el-input v-model="category.productUnit" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitData">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>

export default {
  data() {
    return {
      menus: [],
      defaultProps: {
        children: "children",
        label: "name"
      },
      expandedKey: [],
      dialogVisible: false,
      dialogType: "",// edit,add
      category: {
        catId: null,
        name: "",
        icon: "",
        productUnit: "",
        // ----
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        sort: 0,

      },
      maxLevel: 0,
      updateNodes: []
    };
  },
  methods: {
    getMenus() {
      this.$http({
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get"
      }).then(({data}) => {
        this.menus = data.data;
      });
    },
    append(data) {
      this.dialogType = 'add'
      this.dialogVisible = true;
      this.category.parentCid = data.catId;
      this.category.catLevel = data.catLevel * 1 + 1;

      this.category.catId = null
      this.category.name = ""
      this.category.icon = ""
      this.category.productUnit = ""
      this.category.showStatus = 1
      this.category.sort = 0
    },

    // 添加三级分类的方法
    addCategory() {
      this.$http({
        url: this.$http.adornUrl("/product/category/save"),
        method: "post",
        data: this.$http.adornData(this.category, false)
      }).then(({data}) => {
        this.$message({
          message: "菜单保存成功",
          type: "success"
        });
        // 关闭对话框
        this.dialogVisible = false
        // 刷新新的菜单
        this.getMenus();
        // 设置需要默认展开的菜单
        this.expandedKey = [this.category.parentCid];
      })
    },

    edit(data) {
      this.dialogType = 'edit'
      this.dialogVisible = true

      // 发送请求获取当前节点最新的数据
      this.$http({
        url: this.$http.adornUrl(`/product/category/info/${data.catId}`),
        method: "get",
      }).then(({data}) => {
        this.category.catId = data.data.catId
        this.category.name = data.data.name
        this.category.icon = data.data.icon
        this.category.productUnit = data.data.productUnit
        this.category.parentCid = data.data.parentCid
        this.category.catLevel = data.data.catLevel
        this.category.showStatus = data.data.showStatus
        this.category.sort = data.data.sort
      })

    },

    editCategory() {
      this.$http({
        url: this.$http.adornUrl("/product/category/update"),
        method: "post",
        data: this.$http.adornData(this.category, false)
      }).then(({data}) => {
        this.$message({
          message: "菜单修改成功",
          type: "success"
        });
        // 关闭对话框
        this.dialogVisible = false
        // 刷新新的菜单
        this.getMenus();
        // 设置需要默认展开的菜单
        this.expandedKey = [this.category.parentCid];
      })
    },

    submitData() {
      if (this.dialogType === 'add') {
        this.addCategory()
      } else {
        this.editCategory()
      }
    },

    remove(node, data) {
      let ids = [data.catId];
      this.$confirm(`是否删除【${data.name}】菜单`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/product/category/delete"),
            method: "post",
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            this.$message({
              message: "菜单删除成功",
              type: "success"
            });
            // 刷新新的菜单
            this.getMenus();
            // 设置需要默认展开的菜单
            this.expandedKey = [node.parent.data.catId];
          });
        })
        .catch(() => {
        });
    },
    allowDrop(draggingNode, dropNode, type) {
      // 被拖动的当前节点以及所在的父节点总层数不能大于3
      // 被拖动的当前节点总层数
      this.countNodeLevel(draggingNode.data);
      // 当前拖动的节点+父节点所在的深度不大于3
      let deep = this.maxLevel - draggingNode.data.catLevel + 1

      if (type === 'inner') {
        return (deep + dropNode.level) <= 3
      } else {
        return (deep + dropNode.parent.level) <= 3
      }

    },
    // 统计被拖动节点的总层数
    countNodeLevel(node) {
      this.maxLevel = node.catLevel
      // 找到所有子节点，找到最大深度
      if (node.children && node.children.length) {
        for (let i = 0; i < node.children.length; i++) {
          if (node.children[i].catLevel > this.maxLevel) {
            this.maxLevel = node.children[i].catLevel
          }
          this.countNodeLevel(node.children[i])
        }
      }
    },
    handleDrop(draggingNode, dropNode, dropType, ev) {
      this.updateNodes = []
      // 当前节点最新的父节点id
      let pCid = dropType === 'inner' ? dropNode.data.catId : dropNode.parent.data.catId
      if (!pCid) {
        pCid = 0
      }
      // 当前节点的最新顺序
      let siblings = null
      if (dropType === 'inner') {
        siblings = dropNode.childNodes
      } else {
        siblings = dropNode.parent.childNodes
      }

      for (let i = 0; i < siblings.length; i++) {
        if (siblings[i].data.catId === draggingNode.data.catId) {
          // 如果遍历到当前正在拖拽的节点,需要额外更新父id
          this.updateNodes.push({
            catId: siblings[i].data.catId,
            sort: i,
            parentCid: pCid
          })
        } else {
          this.updateNodes.push({catId: siblings[i].data.catId, sort: i})
        }
      }

      // 当前节点的最新层级
      console.log(this.updateNodes)
    },
  },
  created() {
    this.getMenus();
  }
};
</script>
<style></style>
