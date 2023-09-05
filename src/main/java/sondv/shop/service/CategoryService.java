package sondv.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import sondv.shop.domain.Category;

public interface CategoryService {

	void deleteAll();

	void deleteAll(Iterable<? extends Category> entities);

	void deleteAllById(Iterable<? extends Integer> ids);

	Category getReferenceById(Integer id);

	void delete(Category entity);

	Category getById(Integer id);

	void deleteById(Integer id);

	long count();

	Category getOne(Integer id);

	void deleteAllInBatch();

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	boolean existsById(Integer id);

	void deleteAllInBatch(Iterable<Category> entities);

	Optional<Category> findById(Integer id);

	void deleteInBatch(Iterable<Category> entities);

	List<Category> findAllById(Iterable<Integer> ids);

	List<Category> findAll();

	<S extends Category> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Category> S saveAndFlush(S entity);

	Page<Category> findAll(Pageable pageable);

	void flush();

	List<Category> findAll(Sort sort);

	<S extends Category> List<S> saveAll(Iterable<S> entities);

	<S extends Category> S save(S entity);

}
