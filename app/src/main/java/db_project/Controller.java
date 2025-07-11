package db_project;

import db_project.model.Model;
import db_project.data.Users;
import db_project.data.VideoGames;
import db_project.data.Transactions;
import db_project.data.Reviews;
import db_project.data.Wishlists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

// The controller provides a holistic description of how the outside world can
// interact with our application: each public method is written as
//
//   subject + action + object (e.g. user + clicked + preview)
//
// So just by reading all the methods we know of all the possible interactions
// that can happen in our app. This makes it simpler to track all the possible
// actions that can take place as the application grows.
//
public final class Controller {
    private final Model model;
    private final View view;
    private Users currentUser;

    public Controller(Model model, View view) {
        Objects.requireNonNull(model, "Controller created with null model");
        Objects.requireNonNull(view, "Controller created with null view");
        this.view = view;
        this.model = model;
        this.currentUser = null;
    }
    
    public void testAllTables() {
        var users = this.model.getUsers();
        var videogames = this.model.getVideoGames();
        var videogameDevelopers = this.model.getVideogameDevelopers();
        var genres = this.model.getGenres();
        var videogameGenres = this.model.getVideoGameGenres();
        var languages = this.model.getLanguages();
        var videogameLanguages = this.model.getVideogameLanguages();
        var transactions = this.model.getTransactions();
        var transactionItems = this.model.getTransactionItems();
        var reviews = this.model.getReviews();
        var wishlists = this.model.getWishlists();
        var wishlistItems = this.model.getWishlistItems();
        var achievements = this.model.getAchievements();
        Stream.of(users, videogames, videogameDevelopers, genres,
                  videogameGenres, languages, videogameLanguages, transactions,
                  transactionItems, reviews, wishlists, wishlistItems, achievements)
            .flatMap(List::stream)
            .forEach(optional -> optional.ifPresentOrElse(
                item -> System.out.println("Item: " + item),
                () -> System.out.println("Item not found")
            ));
    }

    public boolean loginUser(int userId) {
        Optional<Users> user = model.find(userId);
        if (user.isPresent()) {
            this.currentUser = user.get();
            return true;
        }
        return false;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public boolean registerUser(String email, String password, String name, String surname, String birthDate) {
        try {

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPublisher(Users user) {
        return user.isPublisher();
    }

    public boolean isAdmin(Users user) {
        return user.isAdministrator();
    }

    public boolean buyGame(Users user, int gameId) {

        return false;
    }

    public boolean createGame(Users user, String title, double price, String description, String requirements, String releaseDate) {

        return false;
    }


    public boolean addToWishlist(Users user, int gameId) {
        try {

            List<Optional<Wishlists>> wishlists = model.getWishlists();
            for (Optional<Wishlists> wishlistOpt : wishlists) {
                if (wishlistOpt.isPresent() && wishlistOpt.get().getUserID() == user.getUserID()) {

                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public List<VideoGames> getUserWishlist(Users user) {
        try {

            List<Optional<Wishlists>> wishlists = model.getWishlists();
            List<VideoGames> wishlistGames = new ArrayList<>();
            
            for (Optional<Wishlists> wishlistOpt : wishlists) {
                if (wishlistOpt.isPresent() && wishlistOpt.get().getUserID() == user.getUserID()) {

                    List<VideoGames> games = wishlistOpt.get().getGameList();
                    wishlistGames.addAll(games);
                }
            }
            return wishlistGames;
        } catch (Exception e) {
            return List.of();
        }
    }


    public boolean addReview(Users user, int gameId, int rating, String comment) {
        try {

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public List<VideoGames> getUserCollection(Users user) {
        try {
            List<Optional<Transactions>> transactions = model.getTransactions();
            List<VideoGames> collectionGames = new ArrayList<>();
            
            for (Optional<Transactions> transactionOpt : transactions) {
                if (transactionOpt.isPresent() && transactionOpt.get().getUserID() == user.getUserID()) {

                }
            }
            return collectionGames;
        } catch (Exception e) {
            return List.of();
        }
    }


    public boolean blockUser(int userId) {

        return false;
    }

    public boolean unblockUser(int userId) {

        return false;
    }

    public List<Optional<VideoGames>> getAllGames() {
        return model.getVideoGames();
    }

    public List<Optional<Users>> getAllUsers() {
        return model.getUsers();
    }

    public List<VideoGames> getTopGames(int limit) {
        try {
            
            List<Optional<VideoGames>> allGames = model.getVideoGames();
            return allGames.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted((g1, g2) -> Double.compare(g2.getAverageRating(), g1.getAverageRating()))
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<Users> getLowRatedPublishers() {
        try {

            List<Optional<Users>> allUsers = model.getUsers();
            return allUsers.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Users::isPublisher)
                .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    public Optional<VideoGames> getMostBoughtGame() {
        try {

            List<Optional<VideoGames>> allGames = model.getVideoGames();
            return allGames.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
