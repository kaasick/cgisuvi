import axios from "axios";

const MOVIE_API_URL = "http://localhost:8080/api/movies"

class movieService{
    getCatalogItem() {
        return axios.get(MOVIE_API_URL);
    }
}

export default new movieService()