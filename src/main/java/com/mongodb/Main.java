package com.mongodb;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		var sf = HibernateUtil.getSessionFactory();
		var service = new BookService(sf);
		var scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\n==== MongoDB Hibernate POC ====");
			System.out.println("1 - Create a new Book");
			System.out.println("2 - List all Books");
			System.out.println("3 - Find Book by Title");
			System.out.println("4 - Find Book by ID");
			System.out.println("5 - Delete Book by ID");
			System.out.println("0 - Exit");
			System.out.print("Choose an option: ");

			String option = scanner.nextLine();

			switch (option) {

				case "1":
					System.out.print("Enter book title: ");
					String title = scanner.nextLine();
					Book created = service.create(title);
					System.out.println("Book created with id = " + created.getId());
					break;

				case "2":
					List<Book> books = service.list(0, 100);
					if (books.isEmpty()) {
						System.out.println("No books found.");
					} else {
						books.forEach(b ->
								System.out.println(b.getId() + " - " + b.getTitle()));
					}
					break;

				case "3":
					System.out.print("Enter exact title: ");
					String searchTitle = scanner.nextLine();
					var found = service.findByTitle(searchTitle);
					if (found.isEmpty()) {
						System.out.println("No book found with title = " + searchTitle);
					} else {
						found.forEach(b ->
								System.out.println("Found: " + b.getId() + " - " + b.getTitle()));
					}
					break;

				case "4":
					System.out.print("Enter book ID to find: ");
					Optional<Book> book = service.get(scanner.nextLine());
					book.ifPresent(value -> System.out.println(value.getId() + " - " + value.getTitle()));
					break;
				case "5":
					System.out.print("Enter book ID to delete: ");
					service.delete(scanner.nextLine());
					System.out.println("Book deleted (if it existed).");
					break;

				case "0":
					System.out.println("Exiting...");
					scanner.close();
					sf.close();
					return;

				default:
					System.out.println("Invalid option, try again.");
			}
		}
	}

}