package com.myApplication.service;

import com.myApplication.data.Organization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationMapper implements RowMapper<Organization> {

    @Override
    public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
        Organization organization = new Organization();
        organization.setId(rs.getLong("id"));
        organization.setName(rs.getString("name"));
        return organization;
    }
}
