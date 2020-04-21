import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `/home`
  });

export default {

	// Get featured items for home slider.
    getFeaturedItems(quantity) {
        return AXIOS.get('/getFeatured/' + quantity)
    },

	// Get recommended eagles gear items for home slider.
    getEagleGear(quantity) {
        return AXIOS.get('/getEagleGear/' + quantity)
    }
}