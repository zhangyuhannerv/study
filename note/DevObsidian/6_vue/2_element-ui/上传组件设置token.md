```vue
<el-upload
           class="uploadBtn"
           accept="xlsx"
           action="http://127.0.0.1:82/lineDic/sstjq/importData"
           :on-success="importData"
           :show-file-list="false"
           :limit="1"
           :headers="headers"
           :file-list="fileList">
    <el-button size="small" type="primary">导入</el-button>
</el-upload>

data(){
	return{
		headers: {
        	Authorization: null
      	},
	}
}

methods: {
    setToken() {
      let tokenName = window.tokenName || window.top.tokenName
      let token = localStorage.getItem(tokenName)
      this.headers.Authorization = token
    },
}

mounted() {
	this.setToken();
}
```