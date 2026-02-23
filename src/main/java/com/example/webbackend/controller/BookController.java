package com.example.webbackend.controller;
import org.springframework.web.bind.annotation.*;
import com.example.webbackend.entity.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookController {
    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L;

    public BookController() {
        books.add(new Book(nextId++, "Spring Boot in Action", "Craig Walls", 39.99));
        books.add(new Book(nextId++, "Effective Java", "Joshua Bloch", 45.00));
        books.add(new Book(nextId++, "Clean Code", "Robert Martin", 42.50));
        books.add(new Book(nextId++, "Java Concurrency in Practice", "Brian Goetz", 49.99));
        books.add(new Book(nextId++, "Design Patterns", "Gang of Four", 54.99));
        books.add(new Book(nextId++, "Head First Java", "Kathy Sierra", 35.00));
        books.add(new Book(nextId++, "Spring in Action", "Craig Walls", 44.99));
        books.add(new Book(nextId++, "Clean Architecture", "Robert Martin", 39.99));
        books.add(new Book(nextId++, "Refactoring", "Martin Fowler", 47.50));
        books.add(new Book(nextId++, "The Pragmatic Programmer", "Andrew Hunt", 41.99));
        books.add(new Book(nextId++, "You Don't Know JS", "Kyle Simpson", 29.99));
        books.add(new Book(nextId++, "JavaScript: The Good Parts", "Douglas Crockford", 32.50));
        books.add(new Book(nextId++, "Eloquent JavaScript", "Marijn Haverbeke", 27.99));
        books.add(new Book(nextId++, "Python Crash Course", "Eric Matthes", 38.00));
        books.add(new Book(nextId++, "Automate the Boring Stuff", "Al Sweigart", 33.50));
    }

    // get all books
    @GetMapping("/books")
    public List<Book> getBooks() {
        return books;
    }

    // get book by id
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

    // create a new book
    @PostMapping("/books")
    public List<Book> createBook(@RequestBody Book book){
        book.setId(nextId++);
        books.add(book);
        return books;
    }

    @GetMapping("/books/search")
    public List<Book> searchByTitle(
            @RequestParam(required = false, defaultValue = "") String title
    ) {
        if(title.isEmpty()) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());

    }
    // price range
    @GetMapping("/books/price-range")
    public List<Book> getBooksByPrice(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return books.stream()
                .filter(book -> {
                    boolean min = minPrice == null || book.getPrice() >= minPrice;
                    boolean max = maxPrice == null || book.getPrice() <= maxPrice;

                    return min && max;
                }).collect(Collectors.toList());
    }

    // sort
    @GetMapping("/books/sorted")
    public List<Book> getSortedBooks(
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order
    ){
        Comparator<Book> comparator;

        switch(sortBy.toLowerCase()){
            case "author" :
                comparator = Comparator.comparing(Book::getAuthor);
                break;
            case "title":
                comparator = Comparator.comparing(Book::getTitle);
                break;
            default:
                comparator = Comparator.comparing(Book::getTitle);
                break;
        }

        if("desc".equalsIgnoreCase(order)){
            comparator = comparator.reversed();
        }

        return books
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList());

    }

    // http://localhost:8082/api/books/price-range?minRange=10&maxRange=45
    // http://localhost:8082/api/books/sorted?sortBy=author&order=desc

    // Delete book by ID
    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable Long id) {
        Book book = getBook(id);

        if (book != null) {
            books.remove(book);
            return "Book was deleted!";
        }
        return "Book not found!";
    }
    // http://localhost:8082/api/books/3

    // Update book by ID
    @PutMapping("/books/{id}")
    public String updateBook (
            @PathVariable Long id,
            @RequestBody Book updateBook
    ){
        Book oldBook = getBook(id);
        if(oldBook != null) {
            oldBook.setAuthor(updateBook.getAuthor());
            oldBook.setTitle(updateBook.getTitle());
            oldBook.setPrice(updateBook.getPrice());
            return "Book was updated";
        }

        return "Book was not found!";
    }
    // http://localhost:8082/api/books/3

    // Patch Book by id
    @PatchMapping("/books/{id}")
    public String patchBook (
            @PathVariable Long id,
            @RequestBody Book updateBook
    ){
        Book oldBook = getBook(id);
        if(oldBook != null) {
            if (!updateBook.getAuthor().isEmpty() && updateBook.getAuthor() != null){
                oldBook.setAuthor(updateBook.getAuthor());
            }
            if (!updateBook.getTitle().isEmpty() && updateBook.getTitle() != null){
                oldBook.setTitle(updateBook.getTitle());
            }
            if (updateBook.getPrice() >= 0.0 && updateBook.getPrice() != null){
                oldBook.setPrice(updateBook.getPrice());
            }
            return "Book was updated";
        }
        return "Book was not found!";
    }

    // Get books with pages
    @GetMapping("/books/pagination")
    public List<Book> getBooksPaged(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        int startAt = (page - 1) * size;

        return books.stream()
                .skip(startAt)
                .limit(size)
                .collect(Collectors.toList());
    }
    // http://localhost:8082/api/books/pagination?page=1&size=5

    // Advance GET with filtering, sorting, and pagination
    @GetMapping("/books/advance")
    public List<Book> getBookAdvance(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ){
        int startAt = (page - 1) * size;

        Comparator<Book> comparator;

        switch(sortBy.toLowerCase()){
            case "author" :
                comparator = Comparator.comparing(Book::getAuthor);
                break;
            case "title":
                comparator = Comparator.comparing(Book::getTitle);
                break;
            default:
                comparator = Comparator.comparing(Book::getTitle);
                break;
        }

        if("desc".equalsIgnoreCase(order)){
            comparator = comparator.reversed();
        }

        return books.stream()
                .filter(b -> {
                    boolean min = minPrice == null || b.getPrice() >= minPrice;
                    boolean max = maxPrice == null || b.getPrice() <= maxPrice;
                    return min && max;
                })
                .sorted(comparator)
                .skip(startAt)
                .limit(size)
                .collect(Collectors.toList());

    }
    // http://localhost:8082/api/books/advance?page=1&size=5&sortBy=author&minPrice=20.00
}

