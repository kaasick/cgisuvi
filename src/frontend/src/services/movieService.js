import axios from "axios";

const MOVIE_API_URL = "http://localhost:8080/api/movies"

class movieService{
    //A service to fetch the movies from the endpoint.
    getCatalogItem() {
        return axios.get(MOVIE_API_URL);
    }
}

export default new movieService()