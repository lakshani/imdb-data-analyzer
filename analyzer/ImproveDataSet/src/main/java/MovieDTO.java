/**
 * Created by LakshaniP on 9/10/2017.
 */
public class MovieDTO {
    private static String title = null;
    private static String releasedYear = null;
    private static String category = null;
    private static String releasedDate = null;
    private static String duration = null;
    private static String genre = null;
    private static String directorsName = null;

    public static int getDirectorFbLikes() {
        return directorFbLikes;
    }

    public static void setDirectorFbLikes(int directorFbLikes) {
        MovieDTO.directorFbLikes = directorFbLikes;
    }

    public static int getActor1FbLikes() {
        return actor1FbLikes;
    }

    public static void setActor1FbLikes(int actor1FbLikes) {
        MovieDTO.actor1FbLikes = actor1FbLikes;
    }

    public static int getActor2FbLikes() {
        return actor2FbLikes;
    }

    public static void setActor2FbLikes(int actor2FbLikes) {
        MovieDTO.actor2FbLikes = actor2FbLikes;
    }

    public static int getActor3FbLikes() {
        return actor3FbLikes;
    }

    public static void setActor3FbLikes(int actor3FbLikes) {
        MovieDTO.actor3FbLikes = actor3FbLikes;
    }

    public static int getActor4FbLikes() {
        return actor4FbLikes;
    }

    public static void setActor4FbLikes(int actor4FbLikes) {
        MovieDTO.actor4FbLikes = actor4FbLikes;
    }

    private static int directorFbLikes = 0;
    private static String actorName1 = null;
    private static int actor1FbLikes = 0;
    private static String actorName2 = null;
    private static int actor2FbLikes = 0;
    private static String actorName3 = null;
    private static int actor3FbLikes = 0;
    private static String actorName4 = null;
    private static int actor4FbLikes = 0;
    private static String language = null;
    private static String country = null;
    private static String imdbRatings = null;
    private static String budget = null;
    private static String type = null;
    private static String noOfVotedUsers = null;

    public static String getProduction() {
        return production;
    }

    public static void setProduction(String production) {
        MovieDTO.production = production;
    }

    private static String production = null;

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        MovieDTO.title = title;
    }

    public static String getReleasedYear() {
        return releasedYear;
    }

    public static void setReleasedYear(String releasedYear) {
        MovieDTO.releasedYear = releasedYear;
    }

    public static String getCategory() {
        return category;
    }

    public static void setCategory(String category) {
        MovieDTO.category = category;
    }

    public static String getReleasedDate() {
        return releasedDate;
    }

    public static void setReleasedDate(String releasedDate) {
        MovieDTO.releasedDate = releasedDate;
    }

    public static String getDuration() {
        return duration;
    }

    public static void setDuration(String duration) {
        MovieDTO.duration = duration;
    }

    public static String getGenre() {
        return genre;
    }

    public static void setGenre(String genre) {
        MovieDTO.genre = genre;
    }

    public static String getDirectorsName() {
        return directorsName;
    }

    public static void setDirectorsName(String directorsName) {
        MovieDTO.directorsName = directorsName;
    }

    public static String getActorName1() {
        return actorName1;
    }

    public static void setActorName1(String actorName1) {
        MovieDTO.actorName1 = actorName1;
    }

    public static String getActorName2() {
        return actorName2;
    }

    public static void setActorName2(String actorName2) {
        MovieDTO.actorName2 = actorName2;
    }

    public static String getActorName3() {
        return actorName3;
    }

    public static void setActorName3(String actorName3) {
        MovieDTO.actorName3 = actorName3;
    }

    public static String getActorName4() {
        return actorName4;
    }

    public static void setActorName4(String actorName4) {
        MovieDTO.actorName4 = actorName4;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        MovieDTO.language = language;
    }

    public static String getCountry() {
        return country;
    }

    public static void setCountry(String country) {
        MovieDTO.country = country;
    }

    public static String getImdbRatings() {
        return imdbRatings;
    }

    public static void setImdbRatings(String imdbRatings) {
        MovieDTO.imdbRatings = imdbRatings;
    }

    public static String getBudget() {
        return budget;
    }

    public static void setBudget(String budget) {
        MovieDTO.budget = budget;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        MovieDTO.type = type;
    }

    public static String getNoOfVotedUsers() {
        return noOfVotedUsers;
    }

    public static void setNoOfVotedUsers(String noOfVotedUsers) {
        MovieDTO.noOfVotedUsers = noOfVotedUsers;
    }


}
