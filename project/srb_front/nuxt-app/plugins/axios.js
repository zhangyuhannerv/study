export default function({ $axios, redirect }) {
  $axios.onRequest((config) => {
    console.log('Making request to ' + config.url)
  })
  $axios.onResponse((response) => {
    console.log('Making resposne')
    return response
  })
  $axios.onError((error) => {
    console.log(error) // for debug
  })
}
