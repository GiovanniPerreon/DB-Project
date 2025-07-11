package db_project.model;

import java.util.List;
import java.util.Optional;

import db_project.data.Genres;
import db_project.data.Reviews;
import db_project.data.Transactions;
import db_project.data.Users;
import db_project.data.VideoGameGenres;
import db_project.data.VideoGames;
import db_project.data.Wishlists;
import db_project.data.Achievements;
import db_project.data.VideogameDevelopers;
import db_project.data.TransactionItems;
import db_project.data.WishlistItems;

public interface Model {
    /**
     * @param userId
     * @return an Optional of the user with the given ID.
     */
    Optional<Users> find(int userId);   
    /**
     * @return all users in the database.
     */
    List<Optional<Users>> getUsers();
    /**
     * @return all the videogames in the database.
     */
    List<Optional<VideoGames>> getVideoGames();
    /**
     * @return all the genres in the database.
     */
    List<Optional<Genres>> getGenres();
    /**
     * @return all the videogame genres in the database.
     */
    List<Optional<VideoGameGenres>> getVideoGameGenres();
    /**
     * @return all the transactions in the database.
     */
    List<Optional<Transactions>> getTransactions();
    /**
     * @return all the reviews in the database.
     */
    List<Optional<Reviews>> getReviews();
    /**
     * @return all the wishlists in the database.
     */
    List<Optional<Wishlists>> getWishlists();
    /**
     * @return all the achievements in the database.
     */
    List<Optional<Achievements>> getAchievements();
    /**
     * @return all the videogame developers in the database.
     */
    List<Optional<VideogameDevelopers>> getVideogameDevelopers();
    /**
     * @return all the transaction items in the database.
     */
    List<Optional<TransactionItems>> getTransactionItems();
    /**
     * @return all the wishlist items in the database.
     */
    List<Optional<WishlistItems>> getWishlistItems();

}
