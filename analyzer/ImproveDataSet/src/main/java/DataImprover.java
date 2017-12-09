import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

public class DataImprover {
    public static void main(String[] args) throws Exception {

        try {
            String csvFile = "input.csv";
            String rateIdFile = "D:\\Lakshani\\Masters\\Thesis\\DataPreProcessing\\FinalProcessedData\\data\\RateFor9.txt";
            String line = "";
            String cvsSplitBy = ",";
            HashSet<String> movieIdSet = new HashSet<String>();
            PrintWriter pw = new PrintWriter(new File("OptimizedProcessedMovieData.csv"));

            StringBuilder sb = new StringBuilder();
            boolean isWritableLine = false;
            int noOfRow = 0;

            sb.append("movie_title");
            sb.append(',');
            sb.append("imdb_id");
            sb.append(',');
            sb.append("duration");
            sb.append(',');
            sb.append("language");
            sb.append(',');
            sb.append("country");
            sb.append(',');
            sb.append("release_date");
            sb.append(',');
            sb.append("release_country");
            sb.append(',');
            sb.append("budget");
            sb.append(',');
            sb.append("genres");
            sb.append(',');
            sb.append("title_year");
            sb.append(',');
            sb.append("num_voted_users");
            sb.append(',');
            sb.append("director_name");
            sb.append(',');
            sb.append("director_facebook_likes");
            sb.append(',');
            sb.append("actor_1_name");
            sb.append(',');
            sb.append("actor_1_facebook_likes");
            sb.append(',');
            sb.append("actor_2_name");
            sb.append(',');
            sb.append("actor_2_facebook_likes");
            sb.append(',');
            sb.append("actor_3_name");
            sb.append(',');
            sb.append("actor_3_facebook_likes");
            sb.append(',');
            sb.append("actor_4_name");
            sb.append(',');
            sb.append("actor_4_facebook_likes");
            sb.append(',');
            sb.append("movie_trailer_views");
            sb.append(',');
            sb.append("movie_trailer_likes");
            sb.append(',');
            sb.append("movie_trailer_unlikes");
            sb.append(',');
            sb.append("production");
            sb.append(',');
            sb.append("imdb_score");
            sb.append('\n');

            try {
                BufferedReader br = new BufferedReader(new FileReader(csvFile));
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    String[] movieData = line.split(cvsSplitBy);

                    if (!movieIdSet.contains(movieData[1])) {
                        movieIdSet.add(movieData[1]);

                        sb.append(movieData[0]); //movie_title
                        sb.append(',');
                        sb.append(movieData[1]); //imdbid
                        sb.append(',');
                        sb.append(movieData[2]); //duration
                        sb.append(',');
                        sb.append(movieData[3]); //language
                        sb.append(',');
                        sb.append(movieData[4]);//country
                        sb.append(',');
                        sb.append(movieData[5]); //releasedate
                        sb.append(',');
                        sb.append(movieData[6]); //releasecountry
                        sb.append(',');
                        sb.append(movieData[7]); //budget
                        sb.append(',');
                        sb.append(movieData[8]); //genres
                        sb.append(',');
                        sb.append(movieData[9]); //title year
                        sb.append(',');
                        sb.append(movieData[10]); // numb of voted users
                        sb.append(',');
                        String directorName = movieData[11];
                        sb.append(directorName); //director name
                        sb.append(',');

                        sb.append(movieData[12]); // fb likes for director
                        sb.append(',');

                        String actorName1 = movieData[13];
                        sb.append(actorName1); //actor_1_name
                        sb.append(',');
                        sb.append(movieData[14]); //actor1 fb likes
                        sb.append(',');
                        String actorName2 = movieData[15];
                        sb.append(actorName2); //actor_2_name
                        sb.append(',');
                        sb.append(movieData[16]); // actor2 fb likes
                        sb.append(',');
                        String actorName3 = movieData[17];
                        sb.append(actorName3);  //actor_3_name
                        sb.append(',');

                        sb.append(movieData[18]); // acor3 fb likes
                        sb.append(',');
                        String actorName4 = movieData[19];
                        sb.append(actorName4);  //actor_4_name
                        sb.append(',');
                        sb.append(movieData[20]); // actor4 fb likes

                        sb.append(',');
                        sb.append(movieData[21]); // movie trailer views
                        sb.append(',');
                        sb.append(movieData[22]); // movie trailer likes
                        sb.append(',');
                        sb.append(movieData[23]); // movie trailer unlikes
                        sb.append(',');
                        sb.append(movieData[24]); // production
                        sb.append(',');
                        sb.append(movieData[25]); // imdb rating
                        sb.append('\n');
                        noOfRow += 1;
                    }
                }
                BufferedReader brForIds = new BufferedReader(new FileReader(rateIdFile));
                String idLine = "";
                while ((idLine = brForIds.readLine()) != null) {
                    idLine = idLine.trim();
                    if (!movieIdSet.contains(idLine)) {
                        movieIdSet.add(idLine);
                        String jsonResponse = addAdditionalRows(idLine);
                        parseJSON(jsonResponse);

                        if (MovieDTO.getType().equalsIgnoreCase("movie")) {

                            sb.append(MovieDTO.getTitle()); //movie_title
                            sb.append(',');
                            sb.append(idLine); //imdbid
                            sb.append(',');

                            sb.append(MovieDTO.getDuration()); //duration)
                            sb.append(',');
                            sb.append(MovieDTO.getLanguage()); //language
                            sb.append(',');
                            sb.append(MovieDTO.getCountry());//country
                            sb.append(',');
                            sb.append(MovieDTO.getReleasedDate()); //releasedate
                            sb.append(',');
                            sb.append("release_country".concat(idLine)); //releasecountry
                            sb.append(',');
                            sb.append("budget"); //budget
                            sb.append(',');
                            sb.append(MovieDTO.getGenre()); //genres
                            sb.append(',');
                            sb.append(MovieDTO.getReleasedYear()); //title year
                            sb.append(',');
                            sb.append(MovieDTO.getNoOfVotedUsers()); // numb of voted users
                            sb.append(',');
                            sb.append(MovieDTO.getDirectorsName()); //director name
                            sb.append(',');
                            sb.append("director_facebook_likes"); // fb likes for director
                            sb.append(',');
                            sb.append(MovieDTO.getActorName1()); //actor_1_name
                            sb.append(',');
                            sb.append("actor_1_facebook_likes"); // actor1 fb likes
                            sb.append(',');
                            sb.append(MovieDTO.getActorName2()); //actor_2_name
                            sb.append(',');
                            sb.append("actor_2_facebook_likes"); // actor2 fb likes
                            sb.append(',');
                            sb.append(MovieDTO.getActorName3());  //actor_3_name
                            sb.append(',');
                            sb.append("actor_3_facebook_likes"); //actor 3 fb likes
                            sb.append(',');
                            sb.append(MovieDTO.getActorName4());  //actor_4_name
                            sb.append(',');
                            sb.append("actor_4_facebook_likes"); // actor 4 fb likes
                            sb.append(',');
                            sb.append("movie_trailer_views"); // movie trailer views
                            sb.append(',');
                            sb.append("movie_trailer_likes"); // movie trailer likes
                            sb.append(',');
                            sb.append("movie_trailer_unlikes"); // movie trailer unlikes
                            sb.append(',');
                            sb.append(MovieDTO.getProduction()); // production
                            sb.append(',');
                            sb.append(MovieDTO.getImdbRatings()); //imdb score
                            sb.append('\n');
                            noOfRow += 1;
                        } else {
                            // this is not a movie.
                        }
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            pw.write(sb.toString());
            pw.close();
            System.out.println("done!");
            System.out.println("No of fields " + noOfRow);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void parseJSON(String jsonResponse) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(jsonResponse);

        JSONObject jsonObject = (JSONObject) obj;

        String title = (String) jsonObject.get("Title");
        if (title != null) {
            MovieDTO.setTitle(title.trim().replace(",", "|"));
        }

        String runTime = (String) jsonObject.get("Runtime");
        if (runTime != null) {
            MovieDTO.setDuration(runTime.trim().replace(",", "|"));
            MovieDTO.setDuration(runTime.trim().replace(" min", ""));
        }

        String country = (String) jsonObject.get("Country");
        if (country != null) {
            MovieDTO.setCountry(country.trim().replace(",", "|"));
        }

        String language = (String) jsonObject.get("Language");
        if (language != null) {
            MovieDTO.setLanguage(language.trim().replace(",", "|"));
        }

        String released = (String) jsonObject.get("Released");
        if (released != null) {
            MovieDTO.setReleasedDate(released.trim().replace(",", "|"));
        }

        String year = (String) jsonObject.get("Year");
        if (year != null) {
            MovieDTO.setReleasedYear(year.trim().replace(",", "|"));
        }

        String genre = (String) jsonObject.get("Genre");
        if (genre != null) {
            MovieDTO.setGenre(genre.trim().replace(",", "|"));
        }

        String imdbRating = (String) jsonObject.get("imdbRating");
        if (imdbRating != null) {
            MovieDTO.setImdbRatings(imdbRating.trim().replace(",", "|"));
        }

        String imdbVotes = (String) jsonObject.get("imdbVotes");
        if (imdbVotes != null) {
            MovieDTO.setNoOfVotedUsers(imdbVotes.trim().replace(",", ""));
        }

        String type = (String) jsonObject.get("Type");
        if (type != null) {
            MovieDTO.setType(type.trim().replace(",", "|"));
        }

        String production = (String) jsonObject.get("Production");
        if (production != null) {
            MovieDTO.setProduction(production.trim().replace(",", "|"));
        }

        String directorName = (String) jsonObject.get("Director");
        if (directorName != null) {
            directorName = directorName.trim();
            MovieDTO.setDirectorsName(directorName.replace(",", "|"));
        }

    }

    // HTTP GET request
    private static String addAdditionalRows(String imdbId) throws Exception {

        System.out.println("imdbId : " + imdbId);

        String url = "http://www.omdbapi.com/?i=" + imdbId + "&apikey=decc7f3a";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
