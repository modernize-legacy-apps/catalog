package com.redhat.coolstore.service;

import java.util.List;

import com.redhat.coolstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CatalogRepository {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<Product> rowMapper = (rs, rowNum) -> new Product(
            rs.getString("itemId"),
            rs.getString("name"),
            rs.getString("desc"),
            rs.getDouble("price"));

    @Autowired
    public CatalogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product findById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM catalog WHERE itemId = " + id, rowMapper);
    }

    public List<Product> list() {
        return jdbcTemplate.query("SELECT * FROM catalog", rowMapper);
    }

    public void insert(Product product) {
        jdbcTemplate.update("INSERT INTO catalog (itemId, name, desc, price) VALUES (?, ?, ?, ?)", product.getItemId(),
                product.getName(), product.getDesc(), product.getPrice());
    }

    public boolean update(Product product) {
        int update = jdbcTemplate.update("UPDATE catalog SET desc = ? WHERE itemId = ? ", product.getDesc(), product.getItemId());
        return (update > 0);
    }

    public boolean delete(long id) {
        int update = jdbcTemplate.update("DELETE FROM catalog WHERE itemId = " + id);
        return (update > 0);
    }
}