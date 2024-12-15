package com.devskiller.model;

import com.google.common.collect.Sets;

import java.util.Set;

public record Reader(Set<Book> favouriteBooks, Set<Genre> favouriteGenres, int age) {

	public Reader() {
		this(Sets.newHashSet(), Sets.newHashSet(), 0);
	}

	public Reader(int age) {
		this(Sets.newHashSet(), Sets.newHashSet(), age);
	}

	public void addToFavourites(Book book) {
		favouriteBooks.add(book);
	}

	public void addToFavourites(Genre genre) {
		favouriteGenres.add(genre);
	}

}
