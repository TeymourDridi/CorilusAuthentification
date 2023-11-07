package tn.corilus.authentification.authentification.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.Conversation;
import tn.corilus.authentification.authentification.entities.Doctor;
import tn.corilus.authentification.authentification.entities.EmailDetails;
import tn.corilus.authentification.authentification.repository.ConversationRepository;
import tn.corilus.authentification.authentification.repository.DoctorRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final AdminService adminService;
    private final ConversationRepository conversationRepository;
    private final KafkaTemplate<String, EmailDetails> kafkaTemplate;

    public DoctorServiceImpl(DoctorRepository doctorRepository, AdminService adminService, ConversationRepository conversationRepository, KafkaTemplate<String, EmailDetails> kafkaTemplate) {
        this.doctorRepository = doctorRepository;
        this.adminService = adminService;
        this.conversationRepository = conversationRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Doctor getDoctor(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Override
    public String addPicture(Long idUser, MultipartFile file) throws IOException {
        Doctor doctor = doctorRepository.findById(idUser).orElse(null);
        if (doctor == null) {
            return "Doctor not found";
        }
        String fileName=doctor.getUsername()+"."+file.getContentType().replaceAll("image/","");
        Path uploadPath = Paths.get("pictures", fileName);
        File uploadDir = new File(uploadPath.toString()).getParentFile();
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!file.isEmpty()) {
            try {
                File outputFile = uploadPath.toFile();
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                outputStream.write(file.getBytes());
                outputStream.flush();
                outputStream.close();
                doctor.setPicture(fileName);
                doctorRepository.save(doctor);
                return fileName;
            } catch (IOException e) {
                return "Error while saving the picture";
            }
        } else {
            return "No picture found in the request";
        }
    }

    @Transactional
    @Override
    public Doctor modifyDoctor(Doctor doctor) {

        Doctor originalDoctor = doctorRepository.findById(doctor.getId()).orElse(null);
        if (originalDoctor != null) {
            originalDoctor.setAddress(doctor.getAddress());
            originalDoctor.setEmail(doctor.getEmail());
            originalDoctor.setName(doctor.getName());
            originalDoctor.setLastname(doctor.getLastname());
            originalDoctor.setPhone(doctor.getPhone());
            return doctorRepository.save(originalDoctor);
        }
        return null;
    }

    @Override
    public String deleteDoctor(Long id) {
        for(Conversation conversation : conversationRepository.findAllByDoctorId(id)){
            conversationRepository.deleteById(conversation.getId());
        }
        doctorRepository.deleteById(id);
        return "Compte Docteur effacé avec succès";
    }

    @Override
    public List<Doctor> getAllDoctorsOrderdByApprooved() {
        return doctorRepository.getAllDoctorsOrderdByApprooved();
    }

    @Override
    public Doctor approveDoctor(Long id,Long idAdmin) {
        Doctor doctor= doctorRepository.findById(id).orElse(null);
        assert doctor != null;
        doctor.setApproved(true);
        doctor.setAdmin(adminService.getAdmin(idAdmin));
        EmailDetails emailDetails= new EmailDetails();
        emailDetails.setRecipient(doctor.getEmail());
        emailDetails.setSubject("Compte Approuvé");
        emailDetails.setUserName(doctor.getUsername());
        emailDetails.setLastName(doctor.getLastname());
        emailDetails.setName(doctor.getName());
        kafkaTemplate.send("email-topic", emailDetails);

        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor blockDoctor(Long id,Long idAdmin) {
        Doctor doctor= doctorRepository.findById(id).orElse(null);
        assert doctor != null;
        doctor.setBlocked(true);
        doctor.setAdmin(adminService.getAdmin(idAdmin));
        EmailDetails emailDetails= new EmailDetails();
        emailDetails.setRecipient(doctor.getEmail());
        emailDetails.setSubject("Compte Bloqué");
        emailDetails.setUserName(doctor.getUsername());
        emailDetails.setLastName(doctor.getLastname());
        emailDetails.setName(doctor.getName());
        kafkaTemplate.send("email-topic", emailDetails);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor unblockDoctor(Long id,Long idAdmin) {
        Doctor doctor= doctorRepository.findById(id).orElse(null);
        assert doctor != null;
        doctor.setBlocked(false);
        doctor.setAdmin(adminService.getAdmin(idAdmin));
        EmailDetails emailDetails= new EmailDetails();
        emailDetails.setRecipient(doctor.getEmail());
        emailDetails.setSubject("Compte Débloqué");
        emailDetails.setUserName(doctor.getUsername());
        emailDetails.setLastName(doctor.getLastname());
        emailDetails.setName(doctor.getName());
        kafkaTemplate.send("email-topic", emailDetails);
        return doctorRepository.save(doctor);
    }

    @Override
    public String getDoctorsAddedThisMonth() {
        return String.valueOf(doctorRepository.findDoctorsAddedThisMonth().size());
    }

}
