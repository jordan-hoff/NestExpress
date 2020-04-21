import axios from  'axios'

const AXIOS = axios.create({
   baseURL: '/signup',
});

export default{

   createUser(firstname, lastname, username, password, validPassword, dorm, roomNumber){
      return AXIOS.post('/createUser', {
         first_name: firstname,
         last_name: lastname,
         username: username,
         password: password,
         validPassword: validPassword,
         dorm: dorm,
         roomNumber: roomNumber});
   },

   convertDormId(dormName, dormList) {
      return dormList.indexOf(dormName);
   },

   getDormLists() {
      return AXIOS.get('/dorm');
   }
}