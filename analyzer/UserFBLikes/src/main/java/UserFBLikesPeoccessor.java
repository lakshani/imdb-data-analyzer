import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

public class UserFBLikesPeoccessor {
    public static void main(String[] args) throws Exception {
        String csvFile = "D:\\Lakshani\\Masters\\Thesis\\DataPreProcessing\\FinalProcessedData\\WEKAProcessingData\\rounding-imdb-rating-2.csv";

        try {
            String line = "";
            String cvsSplitBy = ",";
            HashSet<String> actorsName = new HashSet<String>();
            PrintWriter pw = new PrintWriter(new File("MovieDataWithFBlikes.csv"));

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
                    sb.append(movieData[2]); //movie_imdb_link
                    sb.append(',');
                    sb.append(movieData[3]); //duration
                    sb.append(',');
                    sb.append(movieData[4]); //language
                    sb.append(',');
                    sb.append(movieData[5]);//country
                    sb.append(',');
                    sb.append(movieData[6]); //releasedate
                    sb.append(',');
                    sb.append(movieData[7]); //releasecountry
                    sb.append(',');
                    sb.append(movieData[8]); //budget
                    sb.append(',');
                    sb.append(movieData[9]); //genres
                    sb.append(',');
                    sb.append(movieData[10]); //plot keywords
                    sb.append(',');
                    sb.append(movieData[11]); //title year
                    sb.append(',');
                    sb.append(movieData[12]); // numb of voted users
                    sb.append(',');
                    String directorName = movieData[13];
                    sb.append(directorName); //director name
                    sb.append(',');

                    sb.append(getFBUser(directorName)); // fb likes for director
                    sb.append(',');
                    String writerName = movieData[15];
                    sb.append(writerName); // writer
                    sb.append(',');
                    sb.append(getFBLikes(writerName)); // writer fb likes
                    sb.append(',');
                    String actorName1 = movieData[17];
                    sb.append(actorName1); //actor_1_name
                    sb.append(',');
                    sb.append(getFBLikes(actorName1)); // actor1 fb likes
                    sb.append(',');
                    String actorName2 = movieData[19];
                    sb.append(actorName2); //actor_2_name;
                    sb.append(',');
                    sb.append(getFBLikes(actorName2)); // actor2 fb likes
                    sb.append(',');
                    String actorName3 = movieData[21];
                    sb.append(actorName3);  //actor_3_name");
                    sb.append(',');
                    sb.append(getFBLikes(actorName3)); //actor 3 fb likes
                    sb.append(',');
                    String actorName4 = movieData[23];
                    sb.append(actorName4);  //actor_4_name
                    sb.append(',');
                    sb.append(getFBLikes(actorName4)); //actor 4 fb likes
                    sb.append(',');
                    sb.append(movieData[25]); // movie trailer views
                    sb.append(',');
                    sb.append(movieData[26]); // movie trailer likes
                    sb.append(',');
                    sb.append(movieData[27]); // movie trailer unlikes
                    sb.append(',');
                    sb.append(movieData[28]); // production
                    sb.append(',');
                    sb.append(movieData[29]); // imdb rating
                    sb.append('\n');
                    noOfRow += 1;
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

    private static int getFBUser(String userName) throws IOException, InterruptedException {
        int userMaxLikes = 0;
        if (userName.contains("|")) {
            String[] userNameList = userName.split("\\|");
            for (int i = 0; i < userNameList.length; i++) {
                System.out.println("user-- " + userNameList[i]);
                int fbLikesForCurrentUser = getFBLikes(userNameList[i]);
                if (userMaxLikes < fbLikesForCurrentUser) {
                    userMaxLikes = fbLikesForCurrentUser;
                }
            }
        } else {
            userMaxLikes = getFBLikes(userName);
        }
        return userMaxLikes;
    }

    private static int getFBLikes(String userName) throws IOException, InterruptedException {
        String accessToken = "EAACEdEose0cBADtxCnKZAjPrRBljPSodMv3ZCMq2tKZAnUi5zdFGUTPk1GWR8qfQRHZBy4eD5OSLivWYZCfw2wqNyKysrHOZAXCqXokKdqP36cmCpC8W93mt7ZByeCi8jjqhLWt9Q7BZAJVRJyEYWjSYMuMMEKgV5UvufVilaDEZAA1TZA0pKS5R16stIWewGNSWLcqb2H1YZAKcAZDZD";
        String encodedUserName = userName.replace(" ", "+");
        String searchUrl = "https://graph.facebook.com/v2.10/search?q=" + encodedUserName + "&type=page&fields=fan_count,name,is_verified&access_token=" + accessToken;
        URL obj = new URL(searchUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        Thread.sleep(20000);
        return getVerifiedUserFBLikes(jsonArray, userName);

    }

    private static int getVerifiedUserFBLikes(JSONArray jsonArray, String userName) throws IOException {
        int resultsInputSize = jsonArray.length();

        int totalLikes = 0;
        for (int i = 0; i < resultsInputSize; i++) {
            JSONObject userJsonObject = jsonArray.getJSONObject(i);
            String resultedUserName = userJsonObject.get("name").toString();
            int fanLikes = userJsonObject.getInt("fan_count");
            if (userName.trim().equals(resultedUserName.trim())) {
                if (totalLikes < fanLikes) {
                    totalLikes = fanLikes;
                }
            }
        }
        return totalLikes;
    }
}

