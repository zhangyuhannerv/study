var progressDom = document.querySelector('.progress')

var textDom = document.querySelector('.text')

function rotateCircle(percent) {
  textDom.innerHTML = percent + '%'

  var circleLength = Math.floor(
    2 * Math.PI * parseFloat(progressDom.getAttribute('r'))
  )

  var value = (percent * circleLength) / 100
  progressDom.setAttribute('stroke-dasharray', value + ',10000')
}

let num = 0
setInterval(() => {
  num++
  if (num > 100) {
    num = 0
  }
  rotateCircle(num)
}, 30)
