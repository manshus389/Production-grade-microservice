package com.pm.patientservice.service;


import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Patient newPatient = patientRepository.save(patientRequestDTO);
        //need to use mapper to convert patientRequestDTO to patient Model entity because above is giving error
    }
}
