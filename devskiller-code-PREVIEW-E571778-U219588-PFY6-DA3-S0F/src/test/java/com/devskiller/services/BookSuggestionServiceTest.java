package com.devskiller.services;

import com.devskiller.model.Author;
import com.devskiller.model.Book;
import com.devskiller.model.Reader;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static com.devskiller.model.Genre.*;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;

public class BookSuggestionServiceTest {

    private Author author1 = new Author(randomAlphabetic(8), randomAlphabetic(10));
    private Author author2 = new Author(randomAlphabetic(8), randomAlphabetic(10));
    private Author author3 = new Author(randomAlphabetic(8), randomAlphabetic(10));
    private Author author4 = new Author(randomAlphabetic(8), randomAlphabetic(10));
    private Author author5 = new Author(randomAlphabetic(8), randomAlphabetic(10));
    private Author author6 = new Author(randomAlphabetic(8), randomAlphabetic(10));
    private Book book1 = new Book(author1, randomAlphabetic(15), randomAlphabetic(10), HORROR, 5);
    private Book book2 = new Book(author1, randomAlphabetic(15), randomAlphabetic(10), HORROR, 4);
    private Book book3 = new Book(author2, randomAlphabetic(15), randomAlphabetic(10), HORROR, 3);
    private Book book4 = new Book(author3, randomAlphabetic(15), randomAlphabetic(10), ROMANTIC, 5);
    private Book book5 = new Book(author4, randomAlphabetic(15), randomAlphabetic(10), HORROR, 5);
    private Book book6 = new Book(author5, randomAlphabetic(15), randomAlphabetic(10), DRAMA, 5);
    private Book book7 = new Book(author1, randomAlphabetic(15), randomAlphabetic(10), HORROR, 3);
    private Book book8 = new Book(author1, randomAlphabetic(15), randomAlphabetic(10), FICTION, 4);
    private Book book9 = new Book(author1, randomAlphabetic(15), randomAlphabetic(10), HORROR, 4);
    private Book book10 = new Book(author6, randomAlphabetic(15), randomAlphabetic(10), FICTION, 4);
    private int randomAge1 = nextInt(0, 120);
    private int randomAge2 = nextInt(0, 120);
    private Reader reader1 = new Reader(randomAge1);
    private Reader reader2 = new Reader(randomAge1);
    private Reader reader3 = new Reader(randomAge2);
    private Reader reader4 = new Reader(randomAge2);
    private Reader reader5 = new Reader(121);
    private BookSuggestionService suggestionService;

    @Before
    public void setUp() {
        reader1.addToFavourites(HORROR);
        reader1.addToFavourites(ROMANTIC);
        reader2.addToFavourites(book2);
        reader2.addToFavourites(book1);
        reader2.addToFavourites(book3);
        reader2.addToFavourites(book4);
        reader2.addToFavourites(book7);
        reader2.addToFavourites(book8);
        reader2.addToFavourites(book10);
        reader3.addToFavourites(book5);
        Set<Book> books = newHashSet(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10);
        Set<Reader> readers = newHashSet(reader1, reader2, reader3, reader4, reader5);
        suggestionService = new BookSuggestionService(books, readers);
    }

    @Test
    public void shouldSuggestBookTitlesWithCorrectRating() {
        // when:
        Set<String> suggestedBooks = suggestionService.suggestBooks(reader1, 4);

        // then:
        assertThat(suggestedBooks).isEqualTo(newHashSet(book2.title()));
    }

    @Test
    public void shouldSuggestBookTitlesWithDefaultRatingOfFourOrHigher() {
        // when:
        Set<String> suggestedBooks = suggestionService.suggestBooks(reader1);

        // then:
        assertThat(suggestedBooks).isEqualTo(newHashSet(book1.title(), book2.title(),
                                                        book4.title()));
    }

    @Test
    public void shouldOnlySuggestBookTitlesOfGivenAuthor() {
        // when:
        Set<String> suggestedBooks = suggestionService.suggestBooks(reader1, author1);

        // then:
        assertThat(suggestedBooks).isEqualTo(newHashSet(book1.title(), book2.title()));
    }

    @Test
    public void shouldReturnEmptySetForReaderWithoutFavourites(){
        // when:
        Set<String> suggestedBooksWithExactRating = suggestionService.suggestBooks(reader4, 4);
        Set<String> suggestedBooksGivenReader = suggestionService.suggestBooks(reader4);
        Set<String> suggestedBooksGivenAuthor = suggestionService.suggestBooks(reader4, author1);

        // then:
        assertThat(suggestedBooksWithExactRating).isEqualTo(Collections.emptySet());
        assertThat(suggestedBooksGivenReader).isEqualTo(Collections.emptySet());
        assertThat(suggestedBooksGivenAuthor).isEqualTo(Collections.emptySet());
    }

    @Test
    public void shouldReturnEmptySetForReaderWithSameAgeWithoutFavourites(){
        // when:
        Set<String> suggestedBooksWithExactRating = suggestionService.suggestBooks(reader3, 4);
        Set<String> suggestedBooksGivenReader = suggestionService.suggestBooks(reader3);
        Set<String> suggestedBooksGivenAuthor = suggestionService.suggestBooks(reader3, author1);

        // then:
        assertThat(suggestedBooksWithExactRating).isEqualTo(Collections.emptySet());
        assertThat(suggestedBooksGivenReader).isEqualTo(Collections.emptySet());
        assertThat(suggestedBooksGivenAuthor).isEqualTo(Collections.emptySet());
    }

    @Test
    public void shouldReturnEmptySetForReaderWithSameAgeWithoutFavourites3(){
        // when:
        Set<String> suggestedBooksWithExactRating = suggestionService.suggestBooks(reader5, 4);
        Set<String> suggestedBooksGivenReader = suggestionService.suggestBooks(reader5);
        Set<String> suggestedBooksGivenAuthor = suggestionService.suggestBooks(reader5, author1);

        // then:
        assertThat(suggestedBooksWithExactRating).isEqualTo(Collections.emptySet());
        assertThat(suggestedBooksGivenReader).isEqualTo(Collections.emptySet());
        assertThat(suggestedBooksGivenAuthor).isEqualTo(Collections.emptySet());
    }
}
