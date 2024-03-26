Summer internship test project for CGI

A simple cinema movie catalog with basic sorting and a recommender based on watched movies.
For watched movies, click on the ones you have seen, and then they can be sorted if so is wished.
When a suitable movie is chosen, a seating chart will be shown, where chosen number of seats can be chosen from the seating chart with randomly generated seats being taken.

No account creating system was created to save time and try to focus on the funcionality.
[todo]

To run the project
1) clone the repo
2) Run CgisuviApplication (./src/main/java/CgisuviApplication
   -- can check on (localhost:8080)
   -- default run is on port 8080, if occupied, goes to next available one (i.e 8081)
   -- in which case idk what happens, because vue is fetching port 8080....
3) from IDE terminal, run "npm run serve" (might need to navigate to "./src/frontend
  -- runs on port 3000, can be changed in the vue.config.js
  -- if the backend doesn't run on 8080, then the new port to fetch from should probably be changed here as well.
4) Additionally have used axios (npm add axios) and installed bootstrap (npm install bootstrap --save)
