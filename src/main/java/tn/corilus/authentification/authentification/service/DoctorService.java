package tn.corilus.authentification.authentification.service;

import tn.corilus.authentification.authentification.entities.Doctor;

/**
 * Doctor CRUD
 *
 * @author Teymour Dridi
 */
public interface DoctorService {

    Doctor getDoctor(Long id);
}
