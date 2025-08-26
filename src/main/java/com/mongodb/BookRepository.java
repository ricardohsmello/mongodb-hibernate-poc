package com.mongodb;

 import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class BookRepository {

  public BookRepository() {
  }

  public void save(Session session, Book b) {
    session.persist(b);
  }

  public Optional<Book> findById(Session session, String id) {
    return Optional.ofNullable(session.get(Book.class, id));
  }

   public List<Book> findByTitle(Session session, String exactTitle) {
    return session.createQuery("from Book b where b.title = :t", Book.class)
                  .setParameter("t", exactTitle)
                  .getResultList();
  }

  public List<Book> findAll(Session session, int offset, int limit) {
    return session.createQuery("from Book b order by b.title", Book.class)
                  .setFirstResult(offset)
                  .setMaxResults(limit)
                  .getResultList();
  }

  public void delete(Session session, Book b) {
    session.remove(b);
  }
}
