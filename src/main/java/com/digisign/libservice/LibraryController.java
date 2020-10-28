package com.digisign.libservice;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LibraryController {
	
	@PostMapping(value ="/create", consumes = "application/json",produces = "application/json")
	public Book createBook(@RequestBody Book book) {
		
		if ( book==null ) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Book json content");
		
		final Optional<String> title = Optional.ofNullable(book.getTitle()).map(String::trim).filter(s->!s.isEmpty());
		if (!title.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing title");		
		}
		
		Library.getInstance().createBook(book);		
		return book;
	}
	
	@PutMapping(value ="/update", consumes = "application/json",produces = "application/json")
	public String updateBook(@RequestBody Book book) {
		
		if ( book==null ) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Book content");
		
		final Optional<String> id = Optional.ofNullable(book.getId()).map(String::trim).filter(s->!s.isEmpty());
		if (!id.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing book id");		
		}
		
		/// check to see if book id already present
		Library.getInstance().updateBook(book);
		String message = String.format("The Book '%s' has been successfully updated", book.getTitle());
		return message;
	}

	@DeleteMapping(value ="/delete/{bookId}", produces = "text/plain")
	public String deleteBook(@PathVariable String bookId) {
		
		if ( bookId==null ) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book Id is missing");
		
		/// check to see if book id already present
		String deletedTitle = Library.getInstance().deleteBook(bookId);
		String tragedyString="";
		if ( deletedTitle.toLowerCase(Locale.ENGLISH).contains("roadhouse")) {
			tragedyString="The removal of this irreplaceable literary work is truly a sad day for our society as a whole.";			
		}
		
		String message = String.format("The book '%s' has been deleted from the library. %s", deletedTitle, tragedyString );
		return message;
	}
	
	
	
	@GetMapping("/listBooks")
	public List<Book> listBooks() {
		
		/// let's sort the books by title
		List<Book> books = Library.getInstance().getBooks();
		
		Collections.sort( books, Comparator.comparing(Book::getTitle));
		
		return books;	
		
	}
}