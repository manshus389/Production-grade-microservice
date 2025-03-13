package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientDTO;
    }

    //added later on for converting PatientRequestDTO to model
    public static Patient toModel(PatientRequestDTO patientrequestDTO){
        Patient patient = new Patient();
        patient.setName(patientrequestDTO.getName());
        patient.setAddress(patientrequestDTO.getAddress());
        patient.setEmail(patientrequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientrequestDTO.getDateOfBirth()));
        patient.setRegistered(LocalDate.parse(patientrequestDTO.getRegisteredDate()));

        return patient;
    }
}
