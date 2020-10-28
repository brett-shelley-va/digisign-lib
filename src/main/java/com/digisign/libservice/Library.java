package com.digisign.libservice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Library {

	private static Library instance;
	
	public static Library getInstance() {
		if ( instance==null ) {
			instance = new Library();
			instance.initializeLibraryWithClassicBooks();
		}
		return instance;
	}
	
	public void createBook( Book book ) {

		/// check to see if title already present
		getBooks().forEach(b->{
			if ( b.getTitle().equalsIgnoreCase(book.getTitle())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title already in library");	
			}			
		});


		book.setId(UUID.randomUUID().toString());	
		book.setLastModifiedDate(LocalDateTime.now());
		getBooks().add(book);
	}
	
	public void updateBook( Book book) {

		/// check to see if book id already present
		final AtomicBoolean isFound = new AtomicBoolean(false);
		getBooks().forEach(b->{
			if ( b.getId().equalsIgnoreCase(book.getId())) {
				isFound.set(true);
				/// don't allow the book id to change
				//b.setId(book.getId());
				
				// update everything else
				b.setAuthors(book.getAuthors());
				b.setDescription(book.getDescription());
				b.setPublisher(book.getPublisher());
				b.setTitle(book.getTitle());
				b.setYear(book.getYear());	
				b.setLastModifiedDate(LocalDateTime.now());				
			}			
		});

		if (!isFound.get()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unable to find book with id");
		}
		
	}
	
	public String deleteBook(String bookId) {
		
		int deleteIndex =-1;
		for ( int i =0; i < books.size(); i++) {
			if ( bookId.equals(books.get(i).getId())) {
				deleteIndex =i; 
				break;
			}
		}
		if ( deleteIndex==-1) {
			String msg = String.format("unable to find book to delete with id %s",bookId);			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);			
		}
		
		String title = books.get(deleteIndex).getTitle();		
		books.remove(deleteIndex);
		return title;
	}

	
	
	
	private void initializeLibraryWithClassicBooks() {
		
		Book b1 = new Book();
		b1.setId("5555");
		b1.setAuthors(Arrays.asList("Patrick Swayze", "Lord David Shakespeare IV"));
		b1.setTitle("Roadhouse The Movie");
		b1.setDescription("This book explores the making of what may be the greatest single Cinematic Masterpiece of the 20th Century");
		b1.setYear("1989");
		b1.setPublisher("Random House Books");		
		b1.setLastModifiedDate(LocalDateTime.now());
		
		Book b2 = new Book();
		b2.setId(UUID.randomUUID().toString());
		b2.setAuthors(Arrays.asList("Scott Shelley", "Colleen Shelley", "Erin Shelley"));
		b2.setTitle("Weekends at the Philly Zoo");
		b2.setDescription("A strange true story about an Army Veteran who worked as a primate standin at the Philadelphia Zoo for over a year without anyone noticing.");
		b2.setYear("2014");
		b2.setPublisher("Random House Books");	
		b2.setLastModifiedDate(LocalDateTime.now());
		
		Book b3 = new Book();
		b3.setId(UUID.randomUUID().toString());
		b3.setAuthors(Arrays.asList("Brett Shelley"));
		b3.setTitle("YouRateNews - Architecting an Enterprise Software Product from Vision to Reality Kindle Edition");
		b3.setDescription("This book goes through the full creative process of envisioning, architecting and implementing a software idea using modern (2017) open source technologies. The book covers the process of building an enterprise application from the ground up with technologies such as: Mongo DB, Java 8, Eclipse, Spring Boot, RESTful web services, HTML5, CSS3, Scalable Vector Graphics, JQuery, Secure Socket Layer and RSS");
		b3.setYear("2017");
		b3.setPublisher("Amazon Publishing");
		b3.setLastModifiedDate(LocalDateTime.now());
		
		Book b4 = new Book();
		b4.setId(UUID.randomUUID().toString());
		b4.setAuthors(Arrays.asList("Bill Murray"));
		b4.setTitle("What About Bob?");
		b4.setDescription("A psychiatrist meets his match");
		b4.setYear("2002");
		b4.setPublisher("Random House Publishing");
		b4.setLastModifiedDate(LocalDateTime.now());
		
		
		Book b5 = new Book();
		b5.setId(UUID.randomUUID().toString());
		b5.setAuthors(Arrays.asList("Mary Shelley"));
		b5.setTitle("Frankenstein");
		b5.setDescription("The world's first horror novel");
		b5.setYear("1819");
		b5.setPublisher("Hudson Bay Company");
		b5.setLastModifiedDate(LocalDateTime.now());

		books = new ArrayList<>();
		books.addAll(Arrays.asList(b1,b2,b3,b4,b5));
		
		
		
	}
	

	private List<Book> books;
	
	private Library() {

	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}


}
