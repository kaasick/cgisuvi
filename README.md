Summer internship test project for CGI


A simple cinema movie catalog with basic sorting and a recommender based on watched movies.  

**Basic sorting, add to watched movies list and get recomendations based on that**:
For watched movies, click on the ones you have seen (unique titles), and then a recommendation is given if so is wished.
The recommendation is based on a simple assignment of weights, ending with each movie having a score and then based on that score a new list will be shown, in order of the scores, descebdingly, where the first (highest score) movie is the most recommended one. Note it can only be done if the Watched Movies list has at least 1 movie in it.  

**Seating chart**:
When a suitable movie is chosen, a seating chart will be shown, where chosen number of seats can be chosen from the seating chart with randomly generated seats being taken.


In the seating charts, a recommendation for seats is given, hoping for a middle position that is neither too far or too close, nor too close to the edges,
this functions to up to 5 tickets. Otherwise, seats can be chosen/unchosen by clicking on them and finally, confirm your seats by clicking the "Confirm seats" button.  


No account creating system was created to save time and try to focus on the funcionality. The database connection dependancies are there, but the data has been hardcoded and not connected to the database.

Prerequisites to run the app:
1) **Node.js**: Required to run the Vue.js frontend.)
2) **npm (Node Package Manager)**: Used for managing dependencies.

Node.js and npm can be downloaded from [Node.js official website](https://nodejs.org/).


To run the project
1) clone the repo
2) Build the application
3) Run CgisuviApplication (./src/main/java/CgisuviApplication
    -- can check on (localhost:8080)
    -- default run is on port 8080, if occupied, goes to next available one (i.e 8081)
    -- which might be bad, because vue is fetching port 8080....
4) from IDE terminal, run "npm run serve" (might need to navigate to "./src/frontend)
    -- runs on port 3000, can be changed in the vue.config.js
    -- if the backend doesn't run on port 8080, then the new port to fetch from can be changed here as well.
5) Additionally have used axios (npm add axios) and installed bootstrap (npm install bootstrap --save)
