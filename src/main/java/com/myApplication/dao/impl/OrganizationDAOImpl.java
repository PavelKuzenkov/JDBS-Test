package com.myApplication.dao.impl;

import com.myApplication.dao.OrganizationDAO;
import com.myApplication.data.Organization;
import com.myApplication.service.OrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrganizationDAOImpl implements OrganizationDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    private final String SQL_FIND_ORGANIZATION = "select * from organization where id = ?";
    private final String SQL_DELETE_ORGANIZATION = "delete from organization where id = ?";
    private final String SQL_UPDATE_ORGANIZATION = "update organization set name = ? where id = ?";
    private final String SQL_GET_ALL_ORGANIZATIONS = "select * from organization";
    private final String SQL_GET_ORGANIZATION_WITH_NAME = "select * from organization where name like ?";
    private final String SQL_INSERT_ORGANIZATION = "insert into organization (id, name) values(?, ?)";


    @Autowired
    public OrganizationDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Organization getOrganizationById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_ORGANIZATION, new Object[] { id }, new OrganizationMapper());

    }

    @Override
    public List<Organization> getAllOrganizations() {
        return jdbcTemplate.query(SQL_GET_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    public boolean deleteOrganization(Organization organization) {
        return jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organization.getId()) > 0;
    }

    @Override
    public boolean updateOrganization(Organization organization) {
        return jdbcTemplate.update(
                SQL_UPDATE_ORGANIZATION, organization.getName(), organization.getId()) > 0;
    }

    @Override
    public void createOrganization(Organization organization) {
        jdbcTemplate.update(
                SQL_INSERT_ORGANIZATION, organization.getId(), organization.getName());
    }

    @Override
    public List<Organization> findByName(String param) {
        return mapping(jdbcTemplate.queryForList(SQL_GET_ORGANIZATION_WITH_NAME, "%" + param + "%"));
    }

    private List<Organization> mapping(List<Map<String, Object>> maps) {
        List<Organization> result = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            Organization organization = new Organization();
            organization.setId(new Long((Integer)map.get("id")));
            organization.setName((String)map.get("name"));
            result.add(organization);
        }
        return result;
    }
}
