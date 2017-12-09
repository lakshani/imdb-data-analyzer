import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CSVReader {

    public static void main(String[] args) throws Exception {
        //  String csvFile = "D:\\Lakshani\\Masters\\Thesis\\DataPreProcessing\\imdb-5000-movie-dataset\\movie_metadata.csv";

        try {
            String csvFile = "D:\\Lakshani\\Masters\\Thesis\\DataPreProcessing\\imdb-5000-movie-dataset\\2nd-movie_metadata.csv";
            String line = "";
            String cvsSplitBy = ",";
            HashSet<String> actorsName = new HashSet<String>();
            PrintWriter pw = new PrintWriter(new File("ProcessedMovieData.csv"));

            StringBuilder sb = new StringBuilder();
            boolean isWritableLine = false;
            int noOfRow = 0;

            sb.append("movie_title");
            sb.append(',');
            sb.append("imdb_id");
            sb.append(',');
            sb.append("movie_imdb_link");
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
            sb.append("plot_keywords");
            sb.append(',');
            sb.append("title_year");
            sb.append(',');
            sb.append("imdb_score");
            sb.append(',');
            sb.append("num_voted_users");
            sb.append(',');
            sb.append("director_name");
            sb.append(',');
            sb.append("director_facebook_likes");
            sb.append(',');
            sb.append("writer_name");
            sb.append(',');
            sb.append("writer_facebook_likes");
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
            sb.append('\n');

            try {
                BufferedReader br = new BufferedReader(new FileReader(csvFile));
                while ((line = br.readLine()) != null) {
                    String[] movieData = line.split(cvsSplitBy);
                    String movieImdbLink = movieData[17];
                    String imdbId = null;
                    if (movieImdbLink != null) {
                        String[] imdb = movieImdbLink.trim().split("/");
                        if (imdb.length >= 4) {
                            imdbId = imdb[4];
                        }
                    }
                    MovieDTO.setLanguage(movieData[20]);

                    if (imdbId != null) {
                        String jsonResponse = sendGet(imdbId);
                        parseJSON(jsonResponse, actorsName);
                        if (MovieDTO.getType().equalsIgnoreCase("movie")) {

                            sb.append(MovieDTO.getTitle()); //movie_title
                            sb.append(',');
                            sb.append(imdbId); //imdbid
                            sb.append(',');
                            sb.append(movieData[17]); //movie_imdb_link
                            sb.append(',');

                            sb.append(MovieDTO.getDuration()); //duration
                            sb.append(',');
                            sb.append(MovieDTO.getLanguage()); //language
                            sb.append(',');
                            sb.append(MovieDTO.getCountry());//country
                            sb.append(',');
                            sb.append(MovieDTO.getReleasedDate()); //releasedate
                            sb.append(',');
                            sb.append("release_country".concat(imdbId)); //releasecountry
                            sb.append(',');
                            sb.append(movieData[22]); //budget
                            sb.append(',');
                            sb.append(MovieDTO.getGenre()); //genres
                            sb.append(',');
                            sb.append(movieData[16]); //plot keywords
                            sb.append(',');
                            sb.append(MovieDTO.getReleasedYear()); //title year
                            sb.append(',');
                            sb.append(MovieDTO.getImdbRatings()); //imdb score
                            sb.append(',');
                            sb.append(MovieDTO.getNoOfVotedUsers()); // numb of voted users
                            sb.append(',');
                            sb.append(MovieDTO.getDirectorsName()); //director name
                            sb.append(',');
                            sb.append("director_facebook_likes"); // fb likes for director
                            sb.append(',');
                            sb.append(MovieDTO.getWritersName()); // writer
                            sb.append(',');
                            sb.append("writer_facebook_likes"); // writer fb likes
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
            writeNamesToFile(actorsName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeNamesToFile(HashSet<String> actorsName) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(new File("ProcessedActorsName.txt")));
        Iterator it = actorsName.iterator();
        while (it.hasNext()) {
            out.write(it.next() + "\n");
        }
        out.close();
    }

    private static void parseJSON(String jsonResponse, HashSet<String> actorsName) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(jsonResponse);

        JSONObject jsonObject = (JSONObject) obj;
        String title = (String) jsonObject.get("Title");
        if (title != null) {
            MovieDTO.setTitle(title.trim().replace(",", "|"));
        }

        String runTime = (String) jsonObject.get("Runtime");
        if(runTime != null) {
            MovieDTO.setDuration(runTime.trim().replace(",", "|"));
        }

        String country = (String) jsonObject.get("Country");
        if (country != null) {
            MovieDTO.setCountry(country.trim().replace(",", "|"));
        }

        String language = (String) jsonObject.get("Language");
        if(language != null) {
            MovieDTO.setLanguage(language.trim().replace(",", "|"));
        }

        String released = (String) jsonObject.get("Released");
        if(released != null) {
            MovieDTO.setReleasedDate(released.trim().replace(",", "|"));
        }

        String year = (String) jsonObject.get("Year");
        if (year != null) {
            MovieDTO.setReleasedYear(year.trim().replace(",", "|"));
        }

        String genre = (String) jsonObject.get("Genre");
        if(genre != null) {
            MovieDTO.setGenre(genre.trim().replace(",", "|"));
        }

        String imdbRating = (String) jsonObject.get("imdbRating");
        if (imdbRating != null) {
            MovieDTO.setImdbRatings(imdbRating.trim().replace(",", "|"));
        }

        String imdbVotes = (String) jsonObject.get("imdbVotes");
        if (imdbVotes != null) {
            MovieDTO.setNoOfVotedUsers(imdbVotes.trim().replace(",", "|"));
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
            actorsName.add(directorName);
            MovieDTO.setDirectorsName(directorName.replace(",", "|"));
        }
        String writerName = (String) jsonObject.get("Writer");
        if (writerName != null) {
            writerName = writerName.trim();
            actorsName.add(writerName);
            MovieDTO.setWritersName(writerName.replace(",", "|"));
        }
        setActorsName(jsonObject, actorsName);
    }

    private static void setActorsName(JSONObject jsonObject, HashSet<String> actorsName) {
        String actors = (String) jsonObject.get("Actors");
        if (actors != null) {
            String[] actorsList = actors.split(",");
            if (actorsList != null) {
                int actorsListLength = actorsList.length;
                if (actorsListLength >= 1 && actorsList[0] != null) {
                    String acctor1Name = actorsList[0].trim();
                    MovieDTO.setActorName1(acctor1Name);
                    actorsName.add(acctor1Name);
                } else {
                    return;
                }
                if (actorsListLength >= 2 && actorsList[1] != null) {
                    String acctor2Name = actorsList[1].trim();
                    MovieDTO.setActorName2(acctor2Name);
                    actorsName.add(acctor2Name);
                } else {
                    return;
                }
                if (actorsListLength >= 3 && actorsList[2] != null) {
                    String acctor3Name = actorsList[2].trim();
                    MovieDTO.setActorName3(acctor3Name);
                    actorsName.add(acctor3Name);
                } else {
                    return;
                }
                if (actorsListLength >= 4 && actorsList[3] != null) {
                    String acctor4Name = actorsList[3].trim();
                    MovieDTO.setActorName4(acctor4Name);
                    actorsName.add(acctor4Name);
                } else {
                    return;
                }
            }
        }
    }

    // HTTP GET request
    private static String sendGet(String imdbId) throws Exception {
        String url = "http://www.omdbapi.com/?i=" + imdbId + "&apikey=decc7f3a";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}
