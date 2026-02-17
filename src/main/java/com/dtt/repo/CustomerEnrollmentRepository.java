package com.dtt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.dtt.model.CustomerEnrollment;

@Repository
public interface CustomerEnrollmentRepository extends JpaRepository<CustomerEnrollment, Long> {

    // Optional: if you need to find by customerCode later
//    CustomerEnrollment findByCustomerCode(String customerCode);
//
//    CustomerEnrollment findByIdentificationNumber(String identificationNumber);
//
//    CustomerEnrollment findByEmail(String email);
//
//    List<CustomerEnrollment> findByEmailOrCustomerCodeOrIdentificationNumber(
//            String email,
//            String customerCode,
//            String identifier);
}