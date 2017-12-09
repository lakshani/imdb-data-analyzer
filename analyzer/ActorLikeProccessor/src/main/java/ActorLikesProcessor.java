import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

public class ActorLikesProcessor {
    public static void main(String[] args) throws Exception {
        String csvFile = "MovieDataWithActorFBlikes.csv";
        HashMap<String, Integer> actorsLikeMap = new HashMap<String, Integer>();
        try {
            String line = "";
            String cvsSplitBy = ",";
            HashSet<String> actorsName = new HashSet<String>();
            PrintWriter pw = new PrintWriter(new File("ProcessedMovieDataWithActorFBlikes.csv"));

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
                    sb.append(movieData[2]); //movie_imdb_link;
                    sb.append(',');
                    sb.append(movieData[3]); //duration;
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
                    sb.append(movieData[9]); //genres;
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

                    if (!directorName.contains("|") && !movieData[14].equals("director_facebook_likes")) {
                        actorsLikeMap.put(directorName, Integer.parseInt(movieData[14]));
                    }

                    sb.append(getFBUser(directorName, actorsLikeMap)); // fb likes for director
                    sb.append(',');

                    String actorName1 = movieData[15];
                    sb.append(actorName1); //actor_1_name
                    sb.append(',');

                    if (!movieData[16].equals("actor_1_facebook_likes")) {
                        actorsLikeMap.put(actorName1, Integer.parseInt(movieData[16]));
                    }
                    if (actorsLikeMap.get(actorName1) != null) {
                        sb.append(actorsLikeMap.get(actorName1));
                    } else {
                        int fbLikes = getFBLikes(actorName1);
                        actorsLikeMap.put(actorName1, fbLikes);
                        sb.append(fbLikes);
                    }
                    sb.append(',');
                    String actorName2 = movieData[17];
                    sb.append(actorName2); //actor_2_name
                    sb.append(',');

                    if (!movieData[18].equals("actor_2_facebook_likes")) {
                        actorsLikeMap.put(actorName2, Integer.parseInt(movieData[18]));
                    }
                    if (actorsLikeMap.get(actorName2) != null) {
                        sb.append(actorsLikeMap.get(actorName2));
                    } else {
                        int fbLikes = getFBLikes(actorName2);
                        actorsLikeMap.put(actorName2, fbLikes);
                        sb.append(fbLikes);
                    }
                    sb.append(',');
                    String actorName3 = movieData[19];
                    sb.append(actorName3);  //actor_3_name
                    sb.append(',');

                    if (!movieData[20].equals("actor_3_facebook_likes")) {
                        actorsLikeMap.put(actorName3, Integer.parseInt(movieData[20]));
                    }
                    if (actorsLikeMap.get(actorName3) != null) {
                        sb.append(actorsLikeMap.get(actorName3));
                    } else {
                        int fbLikes = getFBLikes(actorName3);
                        actorsLikeMap.put(actorName3, fbLikes);
                        sb.append(fbLikes);
                    }

                    sb.append(',');
                    String actorName4 = movieData[21];
                    sb.append(actorName4);  //sb.append("actor_4_name");
                    sb.append(',');

                    if (!movieData[22].equals("actor_4_facebook_likes")) {
                        actorsLikeMap.put(actorName4, Integer.parseInt(movieData[22]));
                    }
                    if (actorsLikeMap.get(actorName4) != null) {
                        sb.append(actorsLikeMap.get(actorName4));
                    } else {
                        int fbLikes = getFBLikes(actorName4);
                        actorsLikeMap.put(actorName4, fbLikes);
                        sb.append(fbLikes);
                    }

                    sb.append(',');
                    sb.append(movieData[23]); // movie trailer views
                    sb.append(',');
                    sb.append(movieData[24]); // movie trailer likes
                    sb.append(',');
                    sb.append(movieData[25]); // movie trailer unlikes
                    sb.append(',');
                    sb.append(movieData[26]); // production
                    sb.append(',');
                    sb.append(movieData[27]); // imdb rating
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

    private static int getFBUser(String userName, HashMap<String, Integer> actorsLikeMap) throws IOException, InterruptedException {
        int userMaxLikes = 0;
        if (userName.contains("|")) {
            String[] userNameList = userName.split("\\|");
            for (int i = 0; i < userNameList.length; i++) {
                int fbLikes = 0;
                if (actorsLikeMap.get(userNameList[i]) != null) {
                    fbLikes = actorsLikeMap.get(userNameList[i]);
                } else {
                    fbLikes = getFBLikes(userNameList[i]);
                    actorsLikeMap.put(userNameList[i], fbLikes);
                }
                if (userMaxLikes < fbLikes) {
                    userMaxLikes = fbLikes;
                }
            }
        } else {
            if (actorsLikeMap.get(userName) != null) {
                userMaxLikes = actorsLikeMap.get(userName);
            } else {
                userMaxLikes = getFBLikes(userName);
                actorsLikeMap.put(userName, userMaxLikes);
            }
        }
        return userMaxLikes;
    }

    private static int getFBLikes(String userName) throws IOException, InterruptedException {
        String inputLine;
        //Replace accessToken when expires
        String accessToken = "EAACEdEose0cBADdz9yPD1eCA8pSDRIgWdZCnuPmNRXvgZAwo8FrhN9kGETanzF3uifvfoEZAZCTpZB4X1Piakyx3MGaftHOYMEUlEvOypKtf4zebaxbFks8ZCMn2JVWin3yqTTXvZAv7rDiplABjDxtrQcpD3yIeZA3R4pYYlpHlFJcpjFrFVNGesHSZAERV9FkYiPCSeKzFjjQZDZD";
        String encodedUserName = userName.replace(" ", "+");
        String searchUrl = "https://graph.facebook.com/v2.10/search?q=" + encodedUserName + "&type=page&fields=fan_count,name,is_verified&access_token=" + accessToken;
        URL obj = new URL(searchUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        Thread.sleep(1000);
        return getVerifiedUserFBLikes(jsonArray, userName);
    }

    private static int getVerifiedUserFBLikes(JSONArray jsonArray, String userName) throws IOException {
        int resultsInputSize = jsonArray.length();
        String[] nameValues = userName.split(" ");
        System.out.println(nameValues.length);
        int totalLikes = 0;
        for (int i = 0; i < resultsInputSize; i++) {
            JSONObject userJsonObject = jsonArray.getJSONObject(i);
            String userId = userJsonObject.get("id").toString();
            String resultedUserName = userJsonObject.get("name").toString();
            boolean isVerifiedUser = userJsonObject.getBoolean("is_verified");
            int fanLikes = userJsonObject.getInt("fan_count");
            for (int j = 0; j < nameValues.length; j++) {
                String namePart = nameValues[j];
                if (resultedUserName.contains(namePart)) {
                    if (totalLikes < fanLikes) {
                        totalLikes = fanLikes;
                    }
                    break;
                }
            }
        }
        return totalLikes;
    }
}
