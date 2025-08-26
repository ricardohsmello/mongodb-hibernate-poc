package com.mongodb;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class BookService {
  private final SessionFactory sf;
  private final BookRepository repo;

  public BookService(SessionFactory sf) {
    this.sf = sf;
    this.repo = new BookRepository();
  }

  public Book create(String title) {
    try (Session s = sf.openSession()) {
      Transaction tx = s.beginTransaction();
      try {
        Book b = new Book(title);
        repo.save(s, b);
        tx.commit();
        return b;
      } catch (RuntimeException e) {
        tx.rollback();
        throw e;
      }
    }
  }

  public Optional<Book> get(String id) {
    try (Session s = sf.openSession()) {
      return repo.findById(s, id);
    }
  }

  public List<Book> list(int offset, int limit) {
    try (Session s = sf.openSession()) {
      return repo.findAll(s, offset, limit);
    }
  }

  public List<Book> findByTitle(String exactTitle) {
    try (Session s = sf.openSession()) {
      return repo.findByTitle(s, exactTitle);
    }
  }

  public void delete(String id) {
    try (Session s = sf.openSession()) {
      Transaction tx = s.beginTransaction();
      try {
        repo.findById(s, id).ifPresent(b -> repo.delete(s, b));
        tx.commit();
      } catch (RuntimeException e) {
        tx.rollback();
        throw e;
      }
    }
  }
}
