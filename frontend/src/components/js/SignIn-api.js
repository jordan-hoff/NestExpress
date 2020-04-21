import axios from 'axios'

const AXIOS = axios.create({
   baseURL: `/signin`
});

export default {

   postSignInData(username, password) {
      return AXIOS.post('/user', {
         username: username,
         password: password});
   }
}