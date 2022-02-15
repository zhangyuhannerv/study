<template>
  <div class="cart-bottom-bar">
    <div class="check-content">
      <check-button
        class="check-button"
        :is-checked="isSelectAll"
        @click.native="checkClick"
      />
      <span>全选</span>
    </div>
    <div class="price">
      合计:{{ totalPrice }}
    </div>
    <div class="calculate">
      去计算({{ checkLength }})
    </div>
  </div>
</template>

<script>
import CheckButton from "components/content/checkButton/CheckButton";
import {mapGetters} from "vuex";

export default {
  name: "CartBottomBar",
  components: {
    CheckButton
  },
  computed: {
    ...mapGetters(['cartList']),
    totalPrice() {
      return '￥' + this.cartList.filter(e => e.checked).reduce((preValue, item) => {
        return item.price * item.count + preValue
      }, 0).toFixed(2)
    },
    checkLength() {
      return this.cartList.filter(e => e.checked).length
    },
    isSelectAll() {
      // 加个边界情况，没有商品不全选
      if (this.cartList.length === 0) {
        return false;
      }

      // 没有选中的商品的长度
      // 如果为0取反就是全选
      // 如果不为0取反就是没有全选
      // return !(this.cartList.filter(e => !e.checked).length)

      // 找到没有选中的就是没有全选
      // 没有找到没有选中的就是全选
      // find()找到一个就停止。不用遍历全部。性能较高。
      return !this.cartList.find(e => !e.checked)
    }
  },
  methods: {
    /**
     * 点击全选
     */
    checkClick() {
      // if (this.isSelectAll) {
      //   // 如果原来都是全选，点击一次，全部不选中
      //   this.cartList.forEach(e => e.checked = false);
      // } else {
      //   // 如果原来有没有选中的，那么点击一次，全部选中
      //   this.cartList.forEach(e => e.checked = true);
      // }


      /**
       * 高手写法，仔细读
       */
      const selectStatus = this.isSelectAll
      this.cartList.forEach(e => e.checked = !selectStatus)
    }
  }
}
</script>

<style scoped>
.cart-bottom-bar {
  position: absolute;
  bottom: 49px;
  height: 40px;
  left: 0;
  right: 0;
  line-height: 40px;
  display: flex;
  background-color: #eee;
}

.check-content {
  display: flex;
  align-items: center;
  margin-left: 10px;
  width: 60px;
}

.check-button {
  width: 20px;
  height: 20px;
  line-height: 20px;
  margin-right: 5px;
}

.price {
  margin-left: 30px;
  flex: 1;
}

.calculate {
  width: 90px;
  background-color: red;
  color: #fff;
  text-align: center;
}
</style>
