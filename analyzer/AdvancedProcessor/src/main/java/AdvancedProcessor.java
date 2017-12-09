import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

public class AdvancedProcessor { public static void main(String[] args) throws Exception {
    String csvFile = "InputAdvancedProcessorFile.csv";
    HashMap<String, Integer> actorsLikeMap = new HashMap<String, Integer>();
    try {
        String line = "";
        String cvsSplitBy = ",";
        HashSet<String> actorsName = new HashSet<String>();
        PrintWriter pw = new PrintWriter(new File("NewProcessedMovieDataWithActorFBlikes.csv"));

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
                String[] movieData = line.split(cvsSplitBy);

                int roundRatingValue = 0;
                if (!(movieData[25].equals("N/A"))) {
                   roundRatingValue = (int)java.lang.Math.ceil(Double.parseDouble(movieData[25]));
                } else {
                    System.out.println(movieData[0]+ " is out.. bcz rating N/A");
                    continue;
                }

                if (!(movieData[10].equals("N/A"))
                        && (roundRatingValue > 6) && ((Integer.parseInt(movieData[10])) < 10000)) {
                    System.out.println(movieData[0]+ " is out..");
                    continue;
                } else{
                    if (movieData[3].contains("English")) {
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
                        sb.append(movieData[10]); //numb of voted users
                        sb.append(',');
                        String directorName = movieData[11];
                        sb.append(directorName); //director name
                        sb.append(',');

                        if (!directorName.contains("|") && !movieData[12].equals("director_facebook_likes")) {
                            actorsLikeMap.put(directorName, Integer.parseInt(movieData[12])); //// fb likes for director
                        }

                        if (directorName != "null") {
                            sb.append(getFBUser(directorName, actorsLikeMap)); // fb likes for director

                        } else {
                            //set facebook likes equals to zero, if director name equals null
                            sb.append(0); // fb likes for director
                        }
                        sb.append(',');
                        String actorName1 = movieData[13];
                        sb.append(actorName1); //sb.append("actor_1_name");
                        sb.append(',');

                        if (!movieData[14].equals("actor_1_facebook_likes")) {
                            actorsLikeMap.put(actorName1, Integer.parseInt(movieData[14]));
                        }
                        if (actorName1 != "null") {
                            if (actorsLikeMap.get(actorName1) != null) {
                                sb.append(actorsLikeMap.get(actorName1));
                            } else {
                                int fbLikes = getFBLikes(actorName1);
                                actorsLikeMap.put(actorName1, fbLikes);
                                sb.append(fbLikes);
                            }
                        } else {
                            sb.append(0); //actor1 fb likes
                        }
                        sb.append(',');

                        String actorName2 = movieData[15];
                        sb.append(actorName2); //actor_2_name
                        sb.append(',');

                        if (!movieData[16].equals("actor_2_facebook_likes")) {
                            actorsLikeMap.put(actorName2, Integer.parseInt(movieData[16]));
                        }
                        if (actorName2 != "null") {
                            if (actorsLikeMap.get(actorName2) != null) {
                                sb.append(actorsLikeMap.get(actorName2));
                            } else {
                                int fbLikes = getFBLikes(actorName2);
                                actorsLikeMap.put(actorName2, fbLikes);
                                sb.append(fbLikes);
                            }
                        } else {
                            sb.append(0); //actor2 fb likes
                        }
                        sb.append(',');

                        String actorName3 = movieData[17];
                        sb.append(actorName3);  //actor_3_name
                        sb.append(',');

                        if (!movieData[18].equals("actor_3_facebook_likes")) {
                            actorsLikeMap.put(actorName3, Integer.parseInt(movieData[18]));
                        }
                        if (actorName3 != "null") {
                            if (actorsLikeMap.get(actorName3) != null) {
                                sb.append(actorsLikeMap.get(actorName3));
                            } else {
                                int fbLikes = getFBLikes(actorName3);
                                actorsLikeMap.put(actorName3, fbLikes);
                                sb.append(fbLikes);
                            }
                        } else {
                            sb.append(0); // actor3 fb likes
                        }
                        sb.append(',');
                        String actorName4 = movieData[19];
                        sb.append(actorName4);  //actor_4_name
                        sb.append(',');

                        if (!movieData[20].equals("actor_4_facebook_likes")) {
                            actorsLikeMap.put(actorName4, Integer.parseInt(movieData[20]));
                        }
                        if (actorName4 != "null") {
                            if (actorsLikeMap.get(actorName4) != null) {
                                sb.append(actorsLikeMap.get(actorName4));
                            } else {
                                int fbLikes = getFBLikes(actorName4);
                                actorsLikeMap.put(actorName4, fbLikes);
                                sb.append(fbLikes);
                            }
                        } else {
                            sb.append(0); // acor4 fb likes
                        }
                        sb.append(',');
                        sb.append(movieData[21]); // movie trailer views
                        sb.append(',');
                        sb.append(movieData[22]); // movie trailer likes
                        sb.append(',');
                        sb.append(movieData[23]); // movie trailer unlikes
                        sb.append(',');
                        sb.append(movieData[24]); // production
                        sb.append(',');
                        sb.append(roundRatingValue); // imdb rating
                        sb.append('\n');
                        noOfRow += 1;
                    } else {
                        //Remove movie if the movie is not an English movie
                        System.out.println(movieData[0] + " is not a English movie");
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

    private static int getFBUser(String userName, HashMap<String, Integer> actorsLikeMap) throws IOException, InterruptedException {
        int userMaxLikes = 0;
        if (userName.contains("|")) {
            String[] userNameList = userName.split("\\|");
            for(int i  = 0; i < userNameList.length; i++) {
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
        String accessToken = "EAACEdEose0cBABgxx85tThyiZBQ1R57C0exDk9fqpCpPNe0nGZBxM3YqftZAOSao1758TQCXYmTKRa4YBEXLsXWSD0uXGZCZAGLncvjSiQ9ROonK9BFUf45MECmiLEadNQxJxTmgWiwBZAZC36BD4UVx0rqISKqkOJMCcJZBSZAy2A6axnd8H1UEsonOmCBgCK9eRvirrU3B50wZDZD";
        String encodedUserName = userName.replace(" ", "+");
        String searchUrl = "https://graph.facebook.com/v2.10/search?q=" + encodedUserName + "&type=page&fields=fan_count,name,is_verified&access_token=" + accessToken ;
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
        JSONArray jsonArray =jsonObject.getJSONArray("data");
        Thread.sleep(1000);
        return getVerifiedUserFBLikes(jsonArray, userName);
    }

    private static int getVerifiedUserFBLikes(JSONArray jsonArray, String userName) throws IOException {
        int resultsInputSize = jsonArray.length();
        String[] nameValues = userName.split(" ");
        int totalLikes = 0;
        for (int i = 0; i < resultsInputSize; i++) {
            JSONObject userJsonObject = jsonArray.getJSONObject(i);
            String resultedUserName = userJsonObject.get("name").toString();
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