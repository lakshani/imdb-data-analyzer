package org.mcs.ucsc.movietrailer;

import com.google.api.services.samples.youtube.cmdline.data.Search;
import com.google.api.services.youtube.model.SearchResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Lakshani on 11/12/2017.
 */
public class MovieTrailerProcessor {
    public static void main(String[] args) throws Exception {
        String csvFile = "input.csv";
        try {
            String line = "";
            String cvsSplitBy = ",";
            PrintWriter pw = new PrintWriter(new File("outputDataWithTrailerInfo.csv"));

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
                    String movieName = movieData[0];

                    sb.append(movieName); //movie_title
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
                    sb.append(movieData[11]); //director name
                    sb.append(',');
                    sb.append(movieData[12]); //director fb likes
                    sb.append(',');
                    sb.append(movieData[13]); //actor1 name
                    sb.append(',');
                    sb.append(movieData[14]); //actor1 fb name
                    sb.append(',');
                    sb.append(movieData[15]); //actor2 name
                    sb.append(',');
                    sb.append(movieData[16]); //actor2 fb name
                    sb.append(',');
                    sb.append(movieData[17]); //actor3 fb name
                    sb.append(',');
                    sb.append(movieData[18]); //actor3 fb name
                    sb.append(',');
                    sb.append(movieData[19]); //actor4 fb name
                    sb.append(',');
                    sb.append(movieData[20]); //actor4 fb name
                    sb.append(',');

                    HashMap<String, Integer> movieInfoMap = getTrailerInfo(movieName);
                    sb.append(movieInfoMap.get("ViewCount")); // movie trailer views
                    sb.append(',');
                    sb.append(movieInfoMap.get("LikeCount")); // movie trailer likes
                    sb.append(',');
                    sb.append(movieInfoMap.get("DislikeCount")); // movie trailer unlikes
                    sb.append(',');
                    sb.append(movieData[24]); // production
                    sb.append(',');
                    sb.append(movieData[25]); // imdb rating
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

    private static HashMap<String, Integer> getTrailerInfo(String movieName) throws IOException {
        List<SearchResult> searchResultList = Search.searchMovies(movieName + " Trailer");
        HashMap<String, Integer> trailerInfoMap = getTrailerWithMaxLikes(searchResultList, movieName);
        if (!trailerInfoMap.containsKey("LikeCount")) {
            trailerInfoMap.put("LikeCount", 0);
            trailerInfoMap.put("ViewCount", 0);
            trailerInfoMap.put("DislikeCount", 0);
        }
        return trailerInfoMap;
    }

    private static HashMap<String, Integer> getTrailerWithMaxLikes(List<SearchResult> searchResultList, String movieName) throws IOException {
        int resultsInputSize = searchResultList.size();
        HashMap<String, Integer> trailerInfoMap = new HashMap<String, Integer>();
        movieName = movieName.replace('*', '\'');

        for (int i = 0; i < resultsInputSize; i++) {
            SearchResult searchResult = searchResultList.get(i);
            if ("youtube#video".equals(searchResult.getId().getKind())) {
                String videoName = searchResult.getSnippet().getTitle();
                if (videoName.contains(movieName) && videoName.contains("Trailer")) {
                    //TO remove fake video
                    if (videoName.contains("Honest Trailers") || videoName.toLowerCase().contains("fanmade")) {
                        continue;
                    }
                    String videoId = searchResult.getId().getVideoId();
                    JSONObject jsonObject = getTrailerResponse(videoId);
                    int likeCount = 0;
                    int dislikeCount = 0;
                    int viewCount = 0;
                    if (jsonObject.has("likeCount")) {
                        likeCount = jsonObject.getInt("likeCount");
                    }
                    if (jsonObject.has("viewCount")) {
                        viewCount = jsonObject.getInt("viewCount");
                    }
                    if (jsonObject.has("dislikeCount")) {
                        dislikeCount = jsonObject.getInt("dislikeCount");
                    }
                    if (trailerInfoMap.containsKey("LikeCount")) {
                        if (trailerInfoMap.get("LikeCount") < likeCount) {
                            System.out.println("videoId " + videoId);
                            trailerInfoMap.put("LikeCount", likeCount);
                            trailerInfoMap.put("ViewCount", viewCount);
                            trailerInfoMap.put("DislikeCount", dislikeCount);
                        }
                    } else {
                        trailerInfoMap.put("LikeCount", likeCount);
                        trailerInfoMap.put("ViewCount", viewCount);
                        trailerInfoMap.put("DislikeCount", dislikeCount);
                    }
                }
            }
        }
        return trailerInfoMap;
    }

    private static JSONObject getTrailerResponse(String videoId) throws IOException {
        //Access token should be replaced
        String accessToken = "AIzaSyA61PT6tBhdVyhJmifm9TIFmU7kYwJsQ4k";
        String searchUrl = "https://www.googleapis.com/youtube/v3/videos?id=" + videoId + "&key=" + accessToken + "&fields=items(id,snippet(channelId,title,categoryId),statistics)&part=snippet,statistics";
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
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        JSONObject jsonStatsObject = jsonArray.getJSONObject(0).getJSONObject("statistics");
        return jsonStatsObject;
    }
}