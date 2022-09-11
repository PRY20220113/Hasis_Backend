package com.upc.hasis_backend.controller;

import com.upc.hasis_backend.model.Doctor;
import com.upc.hasis_backend.model.Patient;
import com.upc.hasis_backend.model.User;
import com.upc.hasis_backend.model.dto.request.LoginRequestDTO;
import com.upc.hasis_backend.model.dto.request.RegisterDoctorDTO;
import com.upc.hasis_backend.model.dto.request.RegisterPatientDTO;
import com.upc.hasis_backend.model.dto.response.ResponseDTO;
import com.upc.hasis_backend.service.DoctorService;
import com.upc.hasis_backend.service.PatientService;
import com.upc.hasis_backend.service.UserService;
import com.upc.hasis_backend.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;

    @PostMapping("/registerDoctor")
    public ResponseEntity<ResponseDTO<Doctor>> registerDoctor(@RequestBody RegisterDoctorDTO registerDoctorDTO){

        ResponseDTO<Doctor> responseDTO = new ResponseDTO<>();
        try {

            User usuario = userService.findUserByDni(registerDoctorDTO.getDni());

            if (usuario != null){
                responseDTO.setErrorMessage("El DNI ya se encuentra registrado.");
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO,HttpStatus.OK);
            }

            responseDTO.setErrorMessage("");
            responseDTO.setHttpCode(HttpStatus.CREATED.value());
            responseDTO.setErrorCode(0);
            responseDTO.setData(userService.registerDoctor(registerDoctorDTO));
            return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/registerPatient")
    public ResponseEntity<ResponseDTO<Patient>> registerPatient(@RequestBody RegisterPatientDTO registerPatientDTO){

        ResponseDTO<Patient> responseDTO = new ResponseDTO<>();
        try {

            User usuario = userService.findUserByDni(registerPatientDTO.getDni());

            if (usuario != null){
                responseDTO.setErrorMessage("El DNI ya se encuentra registrado.");
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO,HttpStatus.OK);
            }

            responseDTO.setErrorMessage("");
            responseDTO.setHttpCode(HttpStatus.CREATED.value());
            responseDTO.setErrorCode(0);
            responseDTO.setData(userService.registerPatient(registerPatientDTO));
            return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<?>> login(@RequestBody LoginRequestDTO request){
        //LoginResponseDTO<Object> loginResponseDTO = new LoginResponseDTO<>();
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();

        User user = userService.findUserByDni(request.getDni());
        try {
            if (user != null){
                String password = UtilService.desencriptarContrasena(user.getPassword());
                if (password.equals(request.getPassword())) {
                    if (user.getRol().equals("D")){
                        Doctor doctor = doctorService.findDoctorByDni(user.getDni());
                        responseDTO.setData(doctor);
                    }else{
                        Patient patient = patientService.findPatientByDni(user.getDni());
                        responseDTO.setData(patient);
                    }

                    responseDTO.setHttpCode(HttpStatus.OK.value());
                    responseDTO.setErrorCode(0);
                    responseDTO.setErrorMessage("");


                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.set("Token", UtilService.getJWTToken(request.getDni()));
                    responseHeaders.set("Rol", user.getRol());

                    return new ResponseEntity<>(responseDTO, responseHeaders,HttpStatus.OK);
                } else {

                    responseDTO.setHttpCode(HttpStatus.OK.value());
                    responseDTO.setErrorCode(2);
                    responseDTO.setErrorMessage("La constraseña es incorrecta");
                    responseDTO.setData(null);

                    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
                }
            } else{
                responseDTO.setHttpCode(HttpStatus.OK.value());
                responseDTO.setErrorCode(1);
                responseDTO.setErrorMessage("El usuario no se encuentra registrado");
                responseDTO.setData(null);

                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(3);
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
