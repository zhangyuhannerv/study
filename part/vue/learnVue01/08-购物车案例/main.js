const app = new Vue({
  el: '#app',
  data: {
    books: [
      {
        id: '1',
        name: "《算法导论》",
        date: '2006-9',
        price: '85',
        count: 1,
      }, {
        id: '2',
        name: "《UNIX编程艺术》",
        date: '2006-2',
        price: '59',
        count: 1,
      }, {
        id: '3',
        name: "《编程珠玑》",
        date: '2008-10',
        price: '39',
        count: 1,
      }, {
        id: '4',
        name: "《代码大全》",
        date: '2006-3',
        price: '128',
        count: 1,
      },
    ]
  },
  // 数字保留两位小数的方法1：写个单独的格式化方法
  methods: {
    getFinalPrice(price) {
      return '￥' + Number(price).toFixed(2)
    },

    increment(index) {
      this.books[index].count++;
    },

    decrement(index) {
      this.books[index].count--;
    },
    removeHandler(index) {
      this.books.splice(index, 1);
    }
  },

  // 数字保留两位小数的方法2：写个过滤器(推荐)
  filters: {
    showPrice(price) {
      return '￥' + Number(price).toFixed(2)
    }
  },

  computed: {
    totalPrice() {
      let totalPrice = 0;
      // 这里补充几个for循环的方式
      // 1.普通for循环
      /*for (let i = 0; i < this.books.length; i++) {
        totalPrice += this.books[i].price * this.books[i].count;
      }*/

      // 2.in
      // for (let i in this.books) {
      //   totalPrice += this.books[i].price * this.books[i].count;
      // }

      // 3.of
      for (let book of this.books) {
        totalPrice += book.price * book.count;
      }
      return totalPrice;
    }
  }

})
