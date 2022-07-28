<template>
  <div class="app-container">
    <!-- 表格 -->
    <el-table :data="list" border stripe>
      <el-table-column type="index" width="50" />
      <el-table-column prop="borrowAmount" label="借款额度" />
      <el-table-column prop="integralStart" label="积分区间开始" />
      <el-table-column prop="integralEnd" label="积分区间结束" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <!-- 可以理解为超链接 -->
          <router-link
            :to="'/core/integral-grade/edit/' + scope.row.id"
            style="margin-right: 5px"
          >
            <el-button type="primary" size="mini" icon="el-icon-edit">
              修改
            </el-button>
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeById(scope.row.id)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
// 引入api模块
import integralGrade from '@/api/core/integral-grade'
export default {
  data() {
    return {
      list: []
    }
  },
  methods: {
    fetchData() {
      integralGrade.list().then((response) => {
        this.list = response.data.list
      })
    },
    removeById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          return integralGrade.removeById(id)
        })
        .then((response) => {
          this.$message({
            showClose: true,
            message: response.message,
            type: 'success'
          })
          this.fetchData()
        })
        .catch((error) => {
          // 防止服务器出错也弹出取消删除信息提示
          // 取消删除的error的值是cancel
          if (error === 'cancel') {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          }
        })
    }
  },
  created() {
    this.fetchData()
  }
}
</script>
<style scoped>
</style>