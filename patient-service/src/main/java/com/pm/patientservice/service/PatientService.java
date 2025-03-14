package com.pm.patientservice.service;


import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    //why used patientResponseDTO
    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        //converting patient entity model object into patientResponseDTO object

        //.map(patient -> PatientMapper.toDTO(patient)) was changed to method reference
        return patients.stream()
                .map(PatientMapper::toDTO).toList();

        //List<PatientResponseDTO> patientResponseDTOs = patients.stream()
                //.map(PatientMapper::toDTO).toList(); it was replaced by inline variable
    }

    //why used patientRequestDTO
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        //if business requirement is to have only one email ID per user than the check will be done in the service layer
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email "+
                    "already exists"+ patientRequestDTO.getEmail());
        }

        //need to use mapper to convert patientRequestDTO to patient Model entity because below code is giving error

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);

    }


    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                ()-> new PatientNotFoundException("Patient not found with ID: " + id));

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email "+
                    "already exists"+ patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        //JPA will handle the update of patient and return the updated record
        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(updatedPatient);
    }
}
