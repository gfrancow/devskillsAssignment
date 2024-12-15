package com.devskiller.model;

public record Book(Author author, String title, String isbn, Genre genre, int rating) {

	public Book {
		validate(rating);
	}

	private void validate(int rating) {
		if (rating > 5 || rating < 1) {
			throw new IllegalArgumentException();
		}
	}
}
