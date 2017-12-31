import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

public class GenresProcessor {
    public static void main(String[] args) throws Exception {
        String csvFile = "InputCSV1217.csv";
        HashMap<String, Integer> actorsLikeMap = new HashMap<String, Integer>();
        try {
            String line = "";
            String cvsSplitBy = ",";
            HashSet<String> actorsName = new HashSet<String>();
            long lowestBudget = 1000000;
            PrintWriter pw = new PrintWriter(new File("GenreProcessor1227.csv"));

            StringBuilder sb = new StringBuilder();
            boolean isWritableLine = false;
            int noOfRow = 0;
            sb.append("movie_title");
            sb.append(',');
            sb.append("imdb_id");
            sb.append(',');
            sb.append("duration");
            sb.append(',');
            sb.append("languages");
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
                    String[] movieData = line.split(cvsSplitBy);
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
                    long budget;
                    if (!movieData[7].toLowerCase().contains("budget")) {
                        budget = Long.parseLong(movieData[7]);
                        if (lowestBudget > budget) {
                            lowestBudget = budget;
                        }
                    } else {
                        budget = 200;
                    }
                    sb.append(budget); //budget
                    sb.append(',');
                    sb.append(processGenre(movieData[8])); //genres
                    sb.append(',');
                    sb.append(movieData[9]); //title year
                    sb.append(',');
                    sb.append(movieData[10]); //numb of voted users
                    sb.append(',');
                    sb.append(movieData[11]); //director name
                    sb.append(',');
                    sb.append(movieData[12]); // director fb likes
                    sb.append(',');
                    sb.append(movieData[13]); // actor1 name
                    sb.append(',');
                    sb.append(movieData[14]);  //actor1 fb likes
                    sb.append(',');
                    sb.append(movieData[15]); //actor2 name
                    sb.append(',');
                    sb.append(movieData[16]); //actor2 fb likes
                    sb.append(',');
                    sb.append(movieData[17]); //actor3 name
                    sb.append(',');
                    sb.append(movieData[18]); //actor3 fb likes
                    sb.append(',');
                    sb.append(movieData[19]); //actor 4 name
                    sb.append(',');
                    sb.append(movieData[20]); //actor4 fb likes
                    sb.append(',');
                    sb.append(movieData[21]); // trailer views
                    sb.append(',');
                    sb.append(movieData[22]); // trailer likes
                    sb.append(',');
                    sb.append(movieData[23]); //trailer unlikes
                    sb.append(',');
                    sb.append(movieData[24]); //production
                    sb.append(',');
                    sb.append(movieData[25]);  //imdb rating
                    sb.append("\n");
                    noOfRow += 1;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            pw.write(sb.toString());
            pw.close();
            System.out.println("done!");
            System.out.println("No of fields " + noOfRow);
            System.out.println("Lowest budget " + lowestBudget);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String processGenre(String genreTypes) {
        String genre = "";
        if (genreTypes.toLowerCase().contains("animation")) {
            genre = "Animation";
        } else if (genreTypes.toLowerCase().contains("comedy")) {
            genre = "Comedy";
        } else if (genreTypes.toLowerCase().contains("horror")) {
            genre = "Horror";
        } else if (genreTypes.toLowerCase().contains("romance")) {
            genre = "Romance";
        } else if (genreTypes.toLowerCase().contains("sci-fi")) {
            genre = "Sci-Fi";
        } else if (genreTypes.toLowerCase().contains("action") || genreTypes.toLowerCase().contains("adventure")
                || genreTypes.toLowerCase().contains("thriller")) {
            genre = "Action";
        }
        return  genre;
    }

}