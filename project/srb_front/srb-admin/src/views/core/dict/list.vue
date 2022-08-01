<template>
  <div class="app-container">
    <div style="margin-bottom: 10px">
      <!-- excel导入按钮 -->
      <el-button
        @click="dialogVisible = true"
        type="primary"
        size="mini"
        icon="el-icon-download"
      >
        导入Excel
      </el-button>
      <!-- excel导出按钮 -->
      <el-button
        @click="exportData"
        type="primary"
        size="mini"
        icon="el-icon-upload2"
      >
        导出Excel
      </el-button>
    </div>
    <!-- dialog -->
    <el-dialog title="数据字典导入" :visible.sync="dialogVisible" width="30%">
      <el-form>
        <el-form-item label="请选择Excel文件">
          <el-upload
            :auto-upload="true"
            :multiple="false"
            :limit="1"
            :on-exceed="fileUploadExceed"
            :on-success="fileUploadSuccess"
            :on-error="fileUploadError"
            :action="BASE_API + '/admin/core/dict/import'"
            name="file"
            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      dialogVisible: false, // 对话框是否显示
      BASE_API: process.env.VUE_APP_BASE_API
    }
  },
  methods: {
    // 选择多于一个文件时
    fileUploadExceed() {
      this.$message.warning('只能选取一个文件')
    },

    // 上传成功回调（通信成功）
    fileUploadSuccess(response) {
      if (response.code === 0) {
        // 业务成功
        this.$message.success('数据导入成功')
        this.dialogVisible = false
      } else {
        // 业务失败
        this.$message.error(response.message)
      }
    },

    // 上传失败回调（通信失败）
    fileUploadError() {
      this.$message.error('数据导入失败')
    },

    exportData() {
      window.location.href = this.BASE_API + '/admin/core/dict/export'
    }
  }
}
</script>