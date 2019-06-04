package com.myApplication.dao;

import com.myApplication.data.Organization;

import java.util.List;

public interface OrganizationDAO {

    Organization getOrganizationById(Long id);

    List<Organization> getAllOrganizations();

    boolean deleteOrganization(Organization organization);

    boolean updateOrganization(Organization organization);

    void createOrganization(Organization organization);

    List<Organization> findByName(String param);

}
