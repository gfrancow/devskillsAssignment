package com.devskiller.services;

import com.devskiller.model.Author;
import com.devskiller.model.Book;
import com.devskiller.model.Genre;
import com.devskiller.model.Reader;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

class BookSuggestionService {

    private final Set<Book> books;
    private final Set<Reader> readers;

    public BookSuggestionService(Set<Book> books, Set<Reader> readers) {
        this.books = books;
        this.readers = readers;
    }

    Set<String> suggestBooks(Reader reader) {
        Set<String> genreSuggestions = getGenreSuggestions(reader);
        Set<String> ratingSuggestions = getDesiredRatingSuggestions(4);
        Set<String> ageSuggestions = getAgeSuggestionsBooks(reader.age());

        return getCommonElements(ratingSuggestions, genreSuggestions, ageSuggestions);
    }

    Set<String> suggestBooks(Reader reader, int rating) {
        Set<String> genreSuggestions = getGenreSuggestions(reader);
        Set<String> ratingSuggestions = getExactRatingSuggestions(rating);
        Set<String> ageSuggestions = getAgeSuggestionsBooks(reader.age());

        return getCommonElements(ratingSuggestions, genreSuggestions, ageSuggestions);
    }

    Set<String> suggestBooks(Reader reader, Author author) {
        Set<String> suggestedBooksByReader = suggestBooks(reader);

        Set<String> booksByAuthor = getBooksByAuthor(author);

        return getCommonElements(suggestedBooksByReader, booksByAuthor);
    }

    private Set<String> getGenreSuggestions(Reader reader) {
        return reader.favouriteGenres().stream()
                     .flatMap(genre -> getFavouriteGenres(genre)
                             .stream())
                     .collect(toSet());
    }

    private Set<String> getFavouriteGenres(Genre genre) {
        return books.stream()
                    .filter(book -> book.genre().equals(genre))
                    .map(Book::title)
                    .collect(toSet());
    }

    private Set<String> getDesiredRatingSuggestions(Integer rating) {
        return books.stream()
                    .filter(book -> book.rating() >= rating)
                    .map(Book::title)
                    .collect(toSet());
    }

    private Set<String> getExactRatingSuggestions(Integer rating) {
        return books.stream()
                    .filter(book -> book.rating() == rating)
                    .map(Book::title)
                    .collect(toSet());
    }

    private Set<String> getAgeSuggestionsBooks(Integer age) {
        return readers.stream()
                      .filter(reader -> reader.age() == age)
                      .flatMap(reader -> reader.favouriteBooks()
                                               .stream().map(Book::title))
                      .collect(toSet());
    }

    private Set<String> getBooksByAuthor(Author author) {
        return books.stream()
                    .filter(book -> book.author().equals(author))
                    .map(Book::title)
                    .collect(toSet());
    }

    private Set<String> getCommonElements(Set<String>... sets) {
        if (Stream.of(sets).anyMatch(Set::isEmpty)) {
            return emptySet();
        }

        return Stream.of(sets)
                     .reduce((set1, set2) -> set1.stream()
                                                 .filter(set2::contains)
                                                 .collect(toSet()))
                     .orElse(emptySet());
    }
}