解决方案：采用post请求，来解决该问题，写一个采用post请求的函数即可
```js
function sendByPost(url, ids){
    var oForm = document.createElement("form");
    oForm.method="post";
    oForm.action=url;
  
    var hasitemsids_input = document.createElement("input");
    hasitemsids_input.type="hidden";
    hasitemsids_input.name="ids";
    hasitemsids_input.value=ids;
    oForm.appendChild(hasitemsids_input);
    document.body.appendChild(oForm);
  
    oForm.submit();
}
```
也可以使用formData