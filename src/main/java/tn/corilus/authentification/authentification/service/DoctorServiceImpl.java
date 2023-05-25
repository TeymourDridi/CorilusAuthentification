package tn.corilus.authentification.authentification.service;

import org.springframework.stereotype.Service;
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.repository.DoctorRepository;


@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }


    @Override
    public Doctor getDoctor(Long id) {
        return doctorRepository.findById(id).orElse(new Doctor());
    }
}
