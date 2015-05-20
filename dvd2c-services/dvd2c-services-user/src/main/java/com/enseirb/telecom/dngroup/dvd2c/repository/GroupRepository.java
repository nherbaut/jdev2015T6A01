package com.enseirb.telecom.dngroup.dvd2c.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.enseirb.telecom.dngroup.dvd2c.modeldb.Group;
import com.enseirb.telecom.dngroup.dvd2c.modeldb.User;

//import java.io.Serializable;
@Repository
public interface GroupRepository extends CrudRepository<Group, UUID> {

}
