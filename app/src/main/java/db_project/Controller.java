package db_project;

import db_project.data.Users;
import db_project.model.Model;

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

    public Controller(Model model, View view) {
        Objects.requireNonNull(model, "Controller created with null model");
        Objects.requireNonNull(view, "Controller created with null view");
        this.view = view;
        this.model = model;
    }
    
    public void testAllTables() {
        var users = this.model.getUsers();
        var videogames = this.model.getVideoGames();
        var transactions = this.model.getTransactions();
        var reviews = this.model.getReviews();
        var wishlists = this.model.getWishlists();
        Stream.of(users, videogames, transactions, reviews, wishlists)
            .flatMap(List::stream)
            .forEach(optional -> optional.ifPresentOrElse(
                item -> System.out.println("Item: " + item),
                () -> System.out.println("Item not found")
            ));
    }
}
