```js
//如果dom没有就让整个页面全屏
const full = document.querySelector('#box')||document.documentElement

if (full.RequestFullScreen) {

full.RequestFullScreen()

//兼容Firefox

} else if (full.mozRequestFullScreen) {

full.mozRequestFullScreen()

//兼容Chrome, Safari and Opera等

} else if (full.webkitRequestFullScreen) {

full.webkitRequestFullScreen()

//兼容IE/Edge

} else if (full.msRequestFullscreen) {

full.msRequestFullscreen()

}
```