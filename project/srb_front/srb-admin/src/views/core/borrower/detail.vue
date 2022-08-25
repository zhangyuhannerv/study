<template>
  <div class="app-container">
    <el-form label-width="100px" class="form-table">
      <el-row>
        <el-col :span="6">
          <el-form-item label="状态">
            {{ borrower.status }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="创建时间">
            {{ borrower.createTime }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="姓名">
            {{ borrower.name }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="性别">
            {{ borrower.sex }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="年龄">
            {{ borrower.age }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="手机">
            {{ borrower.mobile }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="学历">
            {{ borrower.education }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="是否结婚">
            {{ borrower.marry }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="行业">
            {{ borrower.industry }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="还款来源">
            {{ borrower.returnSource }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="身份证号">
            {{ borrower.idCard }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="联系人名称">
            {{ borrower.contactsName }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="联系人手机">
            {{ borrower.contactsMobile }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="联系人关系">
            {{ borrower.contactsRelation }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="身份证正面">
            <span v-for="item in borrower.borrowerAttachVOList" :key="item.id">
              <el-image
                v-if="item.imageType == 'idCard1'"
                style="width: 150px"
                :src="item.imageUrl"
                :preview-src-list="[item.imageUrl]"
              />
            </span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="身份证反面">
            <span v-for="item in borrower.borrowerAttachVOList" :key="item.id">
              <el-image
                v-if="item.imageType == 'idCard2'"
                style="width: 150px"
                :src="item.imageUrl"
                :preview-src-list="[item.imageUrl]"
              />
            </span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="房产信息">
            <span v-for="item in borrower.borrowerAttachVOList" :key="item.id">
              <el-image
                v-if="item.imageType == 'house'"
                style="width: 150px"
                :src="item.imageUrl"
                :preview-src-list="[item.imageUrl]"
              />
            </span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="车辆信息">
            <span v-for="item in borrower.borrowerAttachVOList" :key="item.id">
              <el-image
                v-if="item.imageType == 'car'"
                style="width: 150px"
                :src="item.imageUrl"
                :preview-src-list="[item.imageUrl]"
              />
            </span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row style="text-align: center">
        <el-button @click="back">返回</el-button>
      </el-row>
    </el-form>
  </div>
</template>

<script>
// 引入组件
import borrowerApi from '@/api/core/borrower'
export default {
  data() {
    return {
      borrower: {}, // 借款人信息
      saveBtnDisabled: false, // 防止重复提交
      approvalForm: {
        // 审批表单
        borrowerId: 0,
        status: 2,
        content: '',
        infoIntegral: 30,
        isIdCardOk: false,
        isHouseOk: false,
        isCarOk: false
      }
    }
  },
  created() {
    if (this.$route.params.id) {
      this.fetchDataById(this.$route.params.id)
    }
  },
  methods: {
    fetchDataById(id) {
      borrowerApi.show(id).then((response) => {
        this.borrower = response.data.borrowerDetailVO
      })
    },
    back() {
      this.$router.push('/core/borrower/list')
    }
  }
}
</script>

<style></style>
