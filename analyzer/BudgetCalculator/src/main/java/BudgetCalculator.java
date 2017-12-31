import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by LakshaniP on 12/31/2017.
 */
public class BudgetCalculator {
    public static void main(String[] args) throws Exception {
        String csvFile = "Input1227.csv";
        HashMap<String, Integer> actorsLikeMap = new HashMap<String, Integer>();
        try {
            String line = "";
            String cvsSplitBy = ",";
            HashMap<String, Double> moneyVarianceMap = getMoneyVariance();
            long lowestBudget = 1000000;
            PrintWriter pw = new PrintWriter(new File("BudgetCalculator1228.csv"));

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
            sb.append("calculatedBudget");
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
                    String releaseDate = movieData[5];
                    if (releaseDate.trim().length() > 4) {
                        String [] dateParts = releaseDate.split(" ");
                        releaseDate = dateParts[2];
                    }
                    sb.append(movieData[5]); //releasedate
                    sb.append(',');
                    sb.append(movieData[6]); //releasecountry
                    sb.append(',');
                    long budget = Long.parseLong(movieData[7]);
                    System.out.println("releaseDate-- " + releaseDate);
                    long updatedBudget = (long)(moneyVarianceMap.get(releaseDate) * budget / 100) + budget;

                    sb.append(movieData[7]); //budget
                    sb.append(',');
                    sb.append(updatedBudget); //calculated budget
                    sb.append(',');
                    sb.append(movieData[8]); //genres
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

    private static HashMap<String, Double> getMoneyVariance() {
        HashMap<String, Double> moneyVarianceMap = new HashMap<String, Double>();
        moneyVarianceMap.put("1936", 1674.6);
        moneyVarianceMap.put("1938", 1649.4);
        moneyVarianceMap.put("1939", 1674.6);
        moneyVarianceMap.put("1940", 1661.9);
        moneyVarianceMap.put("1941", 1578.0);
        moneyVarianceMap.put("1942", 1413.3);
        moneyVarianceMap.put("1943", 1325.8);
        moneyVarianceMap.put("1945", 1270.4);
        moneyVarianceMap.put("1946", 1165.0);
        moneyVarianceMap.put("1947", 1006.1);
        moneyVarianceMap.put("1948", 923.5);
        moneyVarianceMap.put("1949", 936.4);
        moneyVarianceMap.put("1951", 848.7);
        moneyVarianceMap.put("1941", 1578.0);
        moneyVarianceMap.put("1952", 830.8);
        moneyVarianceMap.put("1953", 823.9);
        moneyVarianceMap.put("1955", 820.4);
        moneyVarianceMap.put("1956", 806.9);
        moneyVarianceMap.put("1957", 777.8);
        moneyVarianceMap.put("1958", 753.5);
        moneyVarianceMap.put("1959", 747.7);
        moneyVarianceMap.put("1960", 733.3);
        moneyVarianceMap.put("1961", 725.0);
        moneyVarianceMap.put("1962", 716.8);
        moneyVarianceMap.put("1963", 706.1);
        moneyVarianceMap.put("1964", 695.7);
        moneyVarianceMap.put("1965", 683.1);
        moneyVarianceMap.put("1966", 661.3);
        moneyVarianceMap.put("1967", 638.5);
        moneyVarianceMap.put("1968", 608.8);
        moneyVarianceMap.put("1969", 572.1);
        moneyVarianceMap.put("1970", 535.7);
        moneyVarianceMap.put("1971", 509.1);
        moneyVarianceMap.put("1972", 490.1);
        moneyVarianceMap.put("1973", 455.6);
        moneyVarianceMap.put("1974", 400.3);
        moneyVarianceMap.put("1975", 358.5);
        moneyVarianceMap.put("1976", 333.5);
        moneyVarianceMap.put("1977", 307.0);
        moneyVarianceMap.put("1978", 278.3);
        moneyVarianceMap.put("1979", 239.8);
        moneyVarianceMap.put("1980", 199.4);
        moneyVarianceMap.put("1981", 171.4);
        moneyVarianceMap.put("1982", 155.6);
        moneyVarianceMap.put("1983", 147.7);
        moneyVarianceMap.put("1984", 137.4);
        moneyVarianceMap.put("1985", 129.2);
        moneyVarianceMap.put("1986", 125.1);
        moneyVarianceMap.put("1987", 117.1);
        moneyVarianceMap.put("1988", 108.5);
        moneyVarianceMap.put("1989", 98.9);
        moneyVarianceMap.put("1990", 88.7);
        moneyVarianceMap.put("1991", 81.1);
        moneyVarianceMap.put("1992", 75.8);
        moneyVarianceMap.put("1993", 70.7);
        moneyVarianceMap.put("1994", 66.4);
        moneyVarianceMap.put("1995", 61.9);
        moneyVarianceMap.put("1996", 57.2);
        moneyVarianceMap.put("1997", 53.7);
        moneyVarianceMap.put("1998", 51.3);
        moneyVarianceMap.put("1999", 48.1);
        moneyVarianceMap.put("2000", 43.2);
        moneyVarianceMap.put("2001", 39.4);
        moneyVarianceMap.put("2002", 37.1);
        moneyVarianceMap.put("2003", 34.1);
        moneyVarianceMap.put("2004", 30.6);
        moneyVarianceMap.put("2005", 26.3);
        moneyVarianceMap.put("2006", 22.4);
        moneyVarianceMap.put("2007", 19.0);
        moneyVarianceMap.put("2008", 14.6);
        moneyVarianceMap.put("2009", 15.0);
        moneyVarianceMap.put("2010", 13.1);
        moneyVarianceMap.put("2011", 9.7);
        moneyVarianceMap.put("2012", 7.4);
        moneyVarianceMap.put("2013", 5.9);
        moneyVarianceMap.put("2014", 4.2);
        moneyVarianceMap.put("2015", 4.1);
        moneyVarianceMap.put("2016", 2.8);
        moneyVarianceMap.put("2017", 0.0);

        return moneyVarianceMap;
    }

}
