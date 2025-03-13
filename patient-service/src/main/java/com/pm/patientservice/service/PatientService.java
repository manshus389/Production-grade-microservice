package com.pm.patientservice.service;


import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    //why used patientResponseDTO
    public List<PatientResponseDTO> getPatient() {
        List<Patient> patients = patientRepository.findAll();
        //converting patient entity model object into patientResponseDTO object

        List<PatientResponseDTO> patientResponseDTOs = patients.stream()
                .map(PatientMapper::toDTO).toList();

        //.map(patient -> PatientMapper.toDTO(patient)) was changed to method reference
        return patientResponseDTOs;
    }
}
