import axios from 'axios';

const AXIOS = axios.create({
   baseURL: `/error`
});

export default {

	// Handle the 400 and 500 page error routes.
   handleError() {
      return AXIOS.get('/error');
   },
}